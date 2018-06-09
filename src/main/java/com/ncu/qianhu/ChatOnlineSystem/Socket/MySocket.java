package com.ncu.qianhu.ChatOnlineSystem.Socket;

import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/7
 * @Modified by
 */

@ServerEndpoint(value = "/ServerSocket")
@Component
public class MySocket {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("连接成功");
    }

    @OnClose
    public void onClose() {
        System.out.println("有一连接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息："+ message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
}
