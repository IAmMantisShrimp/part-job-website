package com.yue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.entity.MsgInfo;
import com.yue.entity.SessionList;
import com.yue.entity.User;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.mapper.MsgInfoMapper;
import com.yue.service.MsgInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.service.SessionListService;
import com.yue.service.UserService;
import com.yue.utils.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author liuy
 * @since 2022-03-28
 */
@Service
public class MsgInfoServiceImpl extends ServiceImpl<MsgInfoMapper, MsgInfo> implements MsgInfoService {

    @Autowired
    private MsgInfoMapper msgInfoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionListService sessionListService;

    /**
     * 将websocket接收到的消息持久化
     * @param message
     * @param sessionList
     * @return
     */
    @Override
    public MsgInfo createMsgInfoBymessage(String msgType, String message, SessionList sessionList) {
        User user = userService.getById(sessionList.getUserId());

        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setContent(message);
        msgInfo.setCreateTime(LocalDateTimeUtil.getCurrentLocalDateTime());
        msgInfo.setFromUserId(sessionList.getUserId());
        msgInfo.setFromUserName(user.getNickname());
        msgInfo.setToUserId(sessionList.getToUserId());
        msgInfo.setToUserName(sessionList.getListName());
        msgInfo.setUnReadFlag(false);
        msgInfo.setMsgType(msgType);
        this.msgInfoMapper.insert(msgInfo);

        return msgInfo;
    }

    /**
     * 根据sessionListId查询出当前会话窗口的历史聊天记录
     * 包括对方发给自己的和自己发给对方的消息
     * @param sessionListId
     * @return
     */
    @Override
    public List<MsgInfo> getMsgInfoList(String sessionListId) {
        SessionList sessionList = sessionListService.getById(sessionListId);
        if(ObjectUtil.isEmpty(sessionList)){
            throw new PartJobException(ExceptionEnum.SESSION_LIST_GET_ERROR);
        }
        List<MsgInfo> msgInfos = this.msgInfoMapper.selectMsgInfoListByUserId(sessionList.getUserId(), sessionList.getToUserId());

        //查询消息成功之后，将消息更新为已读状态
        msgInfoMapper.updateMsgInfoRead(sessionList.getUserId(), sessionList.getToUserId());
        //更新会话里的未读消息数
        sessionListService.emptySessionListUnReadCount(sessionList);

        return msgInfos;
    }
}
