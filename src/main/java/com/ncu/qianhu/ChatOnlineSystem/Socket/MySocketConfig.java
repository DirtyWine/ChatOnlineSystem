package com.ncu.qianhu.ChatOnlineSystem.Socket;

import com.ncu.qianhu.ChatOnlineSystem.Interceptor.ClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/7
 * @Modified by
 */

@Configuration
@EnableWebSocketMessageBroker
public class MySocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8080/webSocketServer
        // 来和服务器的WebSocket连接
        stompEndpointRegistry.addEndpoint("/server").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(createClientInterceptor());
    }

    @Bean
    public ClientInterceptor createClientInterceptor() {
        return new ClientInterceptor();
    }
}
