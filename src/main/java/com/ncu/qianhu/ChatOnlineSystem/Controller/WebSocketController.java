package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Client;
import com.ncu.qianhu.ChatOnlineSystem.Bean.ClientMessage;
import com.ncu.qianhu.ChatOnlineSystem.Bean.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/8
 * @Modified by
 */

@Controller
public class WebSocketController {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ServerMessage sendDemo(ClientMessage message) {
        logger.info("receive message:"+message.getName());
        return new ServerMessage("send message:"+message.getName());
    }

    @MessageMapping("/message")
    public void sendTo(ClientMessage message) {
        logger.info("now online number: "+userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            logger.info("user"+ i++ +": "+user);
        }

        messagingTemplate.convertAndSendToUser(message.getName(),"/queue/dot",new ServerMessage("message from "+message.getName()));
    }
//    @SubscribeMapping("/subscribeTest")
//    public ServerMessage sub() {
//        logger.info("xxx subscribed me ...");
//        return new ServerMessage("Thanks for subscribing...");
//    }

}
