package com.yue.config;

import com.yue.controller.WebSocket;
import com.yue.service.MsgInfoService;
import com.yue.service.SessionListService;
import com.yue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webSocket 配置
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    //WebSocket中无法注入Bean,所以将其设置为静态的，在此注入
    @Autowired
    public void setUserService(UserService userService){
        WebSocket.userService = userService;
    }
    @Autowired
    public void setMsgInfoService(MsgInfoService msgInfoService){
        WebSocket.msgInfoService = msgInfoService;
    }
    @Autowired
    public void setSessionListService(SessionListService sessionListService){
        WebSocket.sessionListService = sessionListService;
    }

}

