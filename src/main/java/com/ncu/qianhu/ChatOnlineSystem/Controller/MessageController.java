package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Friend;
import com.ncu.qianhu.ChatOnlineSystem.Bean.Message;
import com.ncu.qianhu.ChatOnlineSystem.Bean.ServerMessage;
import com.ncu.qianhu.ChatOnlineSystem.Bean.User;
import com.ncu.qianhu.ChatOnlineSystem.File.FileIO;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.FriendMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.MessageMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:45
 * @Modified by
 */

@RestController
@RequestMapping("/message/")
public class MessageController {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendMapper friendMapper;


    private ArrayList<ServerMessage> operateMessage(ArrayList<Message> messages, String sender) {
        ArrayList<ServerMessage> serverMessages = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            User user = new User();
            Friend friend = new Friend();
            user.setUserId(messages.get(i).getMessageSenderId());
            friend.setFriendAId(messages.get(i).getMessageReceiverId());
            friend.setFriendBId(messages.get(i).getMessageSenderId());
            User queryUser = userMapper.findUser(user);
            Friend queryFriend = friendMapper.findRemark(friend);
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setAvatar(queryUser.getUserAvatar());
            serverMessage.setRemark(queryFriend.getFriendRemark());
            serverMessage.setContent(messages.get(i).getMessageContent());
            serverMessage.setTime(messages.get(i).getMessageTime());
            serverMessage.setId(messages.get(i).getMessageSenderId());
            if (serverMessage.getId().equals(sender))
                serverMessage.setLocation("right");
            serverMessages.add(serverMessage);
        }
        return serverMessages;
    }

    @CrossOrigin
    @PostMapping("/new")
    public ArrayList<ServerMessage> getNew(@RequestBody Message requestMessage) {
        System.out.println(requestMessage.toString());
        try {
            ArrayList<Message> messages = messageMapper.findMessage(requestMessage);
            messageMapper.updateMessage(requestMessage);
            ArrayList<ServerMessage> serverMessages = new ArrayList<>();
            if (! messages.isEmpty()) {
                serverMessages = operateMessage(messages,null);
            }
            return serverMessages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/history")
    public ArrayList<ServerMessage> getHistory(@RequestBody Message requestMessage) {
        System.out.println(requestMessage.toString());
        try {
            String sender = requestMessage.getMessageSenderId();
            ArrayList<Message> messages = messageMapper.findHistoryRecord(requestMessage);
            ArrayList<ServerMessage> serverMessages = new ArrayList<>();
            if (! messages.isEmpty()) {
               serverMessages = operateMessage(messages,sender);
            }
            return serverMessages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/download")
    public String downloadHistory(@RequestBody Message requestMessage) {
        System.out.println(requestMessage.toString());
        try {
            String sender = requestMessage.getMessageSenderId();
            ArrayList<Message> messages = messageMapper.findHistoryRecord(requestMessage);
            return FileIO.charOutStream(messages,sender);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
