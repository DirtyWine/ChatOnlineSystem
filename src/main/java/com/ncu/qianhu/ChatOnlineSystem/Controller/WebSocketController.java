package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.*;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.FriendMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.MessageMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.TagMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.UserMapper;
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
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;


    @MessageMapping("/WayTalk")
    @SendTo("/topic/chat")
    public ServerMessage sendDemo(Message message) {
        logger.info(message.toString());
        messageMapper.insertMessage(message);
        User user = new User();
        user.setUserId(message.getMessageSenderId());
        User remark = userMapper.findUser(user);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setId(message.getMessageSenderId());
        serverMessage.setContent(message.getMessageContent());
        serverMessage.setTime(message.getMessageTime());
        serverMessage.setAvatar(message.getSenderAvatar());
        serverMessage.setRemark(remark.getUserNickname());
        return serverMessage;
    }

    @MessageMapping("/message")
    public void privateChat(Message message) {
        logger.info("now online number: "+userRegistry.getUserCount());
        int i = 1;
        String name = message.getMessageReceiverId();
        for (SimpUser user : userRegistry.getUsers()) {
            logger.info("user"+ i++ +": "+user);
            System.out.println(user.toString());
            if (name.equals(user.getName())) {
                messageMapper.insertMessage(message);
                Friend friend = new Friend();
                friend.setFriendAId(message.getMessageReceiverId());
                friend.setFriendBId(message.getMessageSenderId());
                Friend remark = friendMapper.findRemark(friend);
                ServerMessage serverMessage = new ServerMessage();
                serverMessage.setId(message.getMessageSenderId());
                serverMessage.setContent(message.getMessageContent());
                serverMessage.setTime(message.getMessageTime());
                serverMessage.setAvatar(message.getSenderAvatar());
                serverMessage.setRemark(remark.getFriendRemark());
                messagingTemplate.convertAndSendToUser(name,"/queue/messageController",serverMessage);
                return;
            }
        }
        message.setMessageStat(0);
        messageMapper.insertMessage(message);
    }



    @MessageMapping("/delete")
    public void deleteFriend(Friend friend) {
        logger.info(friend.toString());
        friendMapper.deleteFriend(friend);
        Message message = new Message();
        message.setMessageSenderId(friend.getFriendAId());
        message.setMessageReceiverId(friend.getFriendBId());
        System.out.println(message);
        messageMapper.deleteMessage(message);
        Tag tag = new Tag();
        tag.setTagSenderId(friend.getFriendAId());
        tag.setTagReceiverId(friend.getFriendBId());
        tagMapper.deleteTag(tag);
        ArrayList<String> msg1 = new ArrayList<String>();
        ArrayList<String> msg2 = new ArrayList<String>();
        msg1.add("success");
        msg1.add(friend.getFriendBId());
        msg2.add("Whoops");
        msg2.add(friend.getFriendAId());
        messagingTemplate.convertAndSendToUser(friend.getFriendAId(),"/queue/deleteFriend", msg1);
        messagingTemplate.convertAndSendToUser(friend.getFriendBId(),"/queue/deleteFriend", msg2);
    }


    @MessageMapping("/friend")
    public void friendSystem(Friend friendMsg) {
        logger.info(friendMsg.toString());
        int i = 1;
        String friendBId = friendMsg.getFriendBId();
        int type = friendMsg.getFriendStat();
        if (0 == type) {
            friendMapper.insertFriend(friendMsg);
            for (SimpUser user : userRegistry.getUsers()) {
                if (friendBId.equals(user.getName())) {
                    User temp = new User();
                    temp.setUserId(friendMsg.getFriendAId());
                    User friendA = userMapper.findUser(temp);
                    FriendResult friendResult = new FriendResult();
                    friendResult.setType(0);
                    friendResult.setId(friendMsg.getFriendAId());
                    friendResult.setAvatar(friendA.getUserAvatar());
                    friendResult.setNickname(friendA.getUserNickname());
                    messagingTemplate.convertAndSendToUser(friendBId,"/queue/friendController",friendResult);
                    return ;
                }
            }
        } else if (1 == type) {
            friendMapper.updateStat(friendMsg);
            friendMapper.insertFriend(friendMsg);
            for (SimpUser user : userRegistry.getUsers()) {
                if (friendBId.equals(user.getName())) {
                    FriendResult friendResult = new FriendResult();
                    friendResult.setType(1);
                    friendResult.setId(friendMsg.getFriendAId());
                    messagingTemplate.convertAndSendToUser(friendBId,
                            "/queue/friendController", friendResult);
                    friendResult.setType(2);
                    friendResult.setId(friendMsg.getFriendBId());
                    messagingTemplate.convertAndSendToUser(friendMsg.getFriendAId(),
                            "/queue/friendController", friendResult);
                    return;
                }
            }
            Message message = new Message();
            message.setMessageSenderId(friendMsg.getFriendAId());
            message.setMessageReceiverId(friendMsg.getFriendBId());
            message.setMessageContent("We are friends now,Let's talk!");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            message.setMessageTime(sdf.format(d));
            message.setMessageStat(0);
            messageMapper.insertMessage(message);

            FriendResult friendResult = new FriendResult();
            friendResult.setType(2);
            friendResult.setId(friendMsg.getFriendBId());
            messagingTemplate.convertAndSendToUser(friendMsg.getFriendAId(),
                    "/queue/friendController", friendResult);
            return;
        } else {
            friendMapper.deleteFriend(friendMsg);
            FriendResult friendResult = new FriendResult();
            friendResult.setType(-1);
            friendResult.setId(friendMsg.getFriendBId());
            messagingTemplate.convertAndSendToUser(friendMsg.getFriendAId(),
                    "/queue/friendController",friendResult);
        }

    }


}
