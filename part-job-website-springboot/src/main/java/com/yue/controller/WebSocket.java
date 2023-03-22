package com.yue.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yue.entity.MsgInfo;
import com.yue.entity.SessionList;
import com.yue.service.MsgInfoService;
import com.yue.service.SessionListService;
import com.yue.service.UserService;
import com.yue.utils.CurPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}/{sessionListId}")
//此注解相当于设置访问URL
public class WebSocket {

    public static UserService userService;

    public static MsgInfoService msgInfoService;

    public static SessionListService sessionListService;

    /**
     * 客户端与服务器建立socket连接时调用
     * @param session
     * @param userId    登录用户id
     * @param sessionListId
     */
    @OnOpen
    public void onOpen(Session session,@PathParam(value="userId")String userId, @PathParam(value="sessionListId")String sessionListId) {
        CurPool.sessionListIdPool.put(userId, sessionListId);   //接收到消息时，用于判断是直接发送给用户，还是持久化到数据库
        CurPool.sessionPool.put(userId,session);    //发送消息时需要使用目标用户的session对象
        log.info("【websocket消息】有新的连接，总数为:"+CurPool.curUserPool.size());
    }

    /**
     * socket连接关闭时调用
     */
    @OnClose
    public void onClose(Session session) {
        // 断开连接删除用户删除session
        String userId = session.getRequestParameterMap().get("userId").get(0);
        CurPool.sessionPool.remove(userId);
        CurPool.sessionListIdPool.remove(userId);
        log.info("【websocket消息】连接断开，总数为:"+CurPool.curUserPool.size());
    }

    /**
     * 接收到客户端发来消息时调用的方法，该方法是消息的中转站
     * @param message   接收到的消息（json数据）
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:"+message);
        //0.接受到客户端传来的message为json格式，解析json对象
        JSONObject obj = JSONUtil.parseObj(message);
        String curSessionListId = obj.getStr("curSessionListId"); // 当前的会话对象（保存着接受消息用户的id）
        String msgType = obj.getStr("msgType"); // 发送的消息类型
        String text = obj.getStr("text"); // 根据消息类型判断，如果是2则是图片，1是文本

        //1.获取到此次连接的sessionList对象
        SessionList sessionList = sessionListService.getById(curSessionListId);
        //2.将接收到的消息持久化
        MsgInfo msgInfo = this.msgInfoService.createMsgInfoBymessage(msgType, text, sessionList);

        // 2.判断接受消息的用户是否在线
        Session toSession = CurPool.sessionPool.get(sessionList.getToUserId());
        if (ObjectUtil.isEmpty(toSession)){
            //2.1 如果用户不在线，更新session_list表中的未读消息数
            //sessionListService.addUnReadCount(curSessionListId);
            //2.1.1 判断是否是自己首次与对方发起会话(是否存在 toUserId --> fromUserId 的session对象)
            SessionList reverseSessionList = sessionListService.getSessionListByUser(sessionList.getToUserId(), sessionList.getUserId());
            if(ObjectUtil.isEmpty(reverseSessionList)){
                //如果是双方第一次发起对话，那创建反向的sessionList对象，再增加未读消息数
                this.sessionListService.createReverseSessionList(sessionList, true);
            }else {
                //增加未读消息数
                this.sessionListService.addUnReadCount(reverseSessionList.getId());
            }
        }else{
            //2.2 如果用户在线，判断对方是否和自己对话过（是否存在 toUserId --> fromUserId 的Sesssionlist对象）(是否是双方首次发起对话)
            SessionList reverseSessionList = sessionListService.getSessionListByUser(sessionList.getToUserId(), sessionList.getUserId());
            if(ObjectUtil.isEmpty(reverseSessionList)){
                // 新增会话列表
                this.sessionListService.createReverseSessionList(sessionList, true);
                // 发送列表消息(需要服务端响应到目标客户端，让其知道有人发消息)
                List<SessionList> sessionListHistory = this.sessionListService.getSessionListHistory(sessionList.getToUserId());
                //将 对方用户的 历史会话重新查出来，刷新页面
                sendTextMessage(sessionList.getToUserId() ,JSONUtil.toJsonStr(sessionListHistory));
            }else{
                //一个用户 在 一个时刻中，只会有一个 sessionList对象， 所以查询目标user 的 sessionlist 对象 是否指向自己
                String toUserSessionListId = CurPool.sessionListIdPool.get(sessionList.getToUserId());
                //2.2.1 判断对方的sessionList对象是否关联到自己(对方也正在和自己聊天)
                if(toUserSessionListId.equals(reverseSessionList.getId())){
                    //如果对方也恰好在和自己聊天，则直接正常发送消息
                    sendTextMessage(sessionList.getToUserId(), JSONUtil.toJsonStr(msgInfo));
                }else{
                    //如果对方没有在和自己聊天，则发送列表消息(需要服务端响应到目标客户端，让其知道有人发消息)
                    // 更新未读消息数量
                    sessionListService.addUnReadCount(reverseSessionList.getId());
                    //发送列表消息
                    List<SessionList> sessionListHistory = this.sessionListService.getSessionListHistory(sessionList.getToUserId());
                    //将 对方用户的 历史会话重新查出来，刷新页面
                    sendTextMessage(sessionList.getToUserId() ,JSONUtil.toJsonStr(sessionListHistory));
                }
            }
        }
    }

    // 此为广播消息
//    public void sendAllMessage(String message) {
//        for(WebSocket webSocket : webSockets) {
//            System.out.println("【websocket消息】广播消息:"+message);
//            try {
//                webSocket.session.getAsyncRemote().sendText(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 此为单点消息 (发送文本)
    public void sendTextMessage(String userId, String message) {
        Session session = CurPool.sessionPool.get(userId);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息 (发送对象)
//    public void sendObjMessage(String sessionId, Object message) {
//        Session session = CurPool.sessionPool.get(sessionId);
//        if (session != null) {
//            try {
////                session.getAsyncRemote().sendObject(message);
//                session.getBasicRemote().sendText(JsonUtils.objectToJson(message));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}