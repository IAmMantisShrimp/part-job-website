package com.yue.utils;

import com.yue.controller.WebSocket;
import com.yue.entity.User;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一管理session、websocket、curUser
 */
public class CurPool {

    //存放websocket的session对象，发消息时使用，key:userID,value:Session
    public static Map<String, Session> sessionPool = new ConcurrentHashMap<>();

    //存放当前用户的 SessionList对象（即 当前用户的正在与谁会话） key:userId,value:SessionList
    public static Map<String, String> sessionListIdPool = new ConcurrentHashMap<>();

    // 保存当前在线的所有用户 key:userId value: user对象 (在用户登陆成功之后就将user对象置入)
    public static Map<String, User> curUserPool = new ConcurrentHashMap<>();
}
