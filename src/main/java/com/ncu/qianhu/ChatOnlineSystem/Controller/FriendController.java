package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Friend;
import com.ncu.qianhu.ChatOnlineSystem.Bean.FriendResult;
import com.ncu.qianhu.ChatOnlineSystem.Bean.ServerFriend;
import com.ncu.qianhu.ChatOnlineSystem.Bean.User;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.FriendMapper;
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
@RequestMapping("/friend/")
public class FriendController {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;


    @CrossOrigin
    @PostMapping("/list")
    public ArrayList<ServerFriend> friendList(@RequestBody Friend requestFriend) {
        System.out.println(requestFriend.toString());
        try {
            ArrayList<Friend> list = friendMapper.findFriend(requestFriend);
            ArrayList<ServerFriend> friends = new ArrayList<>();
            if (! list.isEmpty()) {
                for(int i = 0; i < list.size(); i++) {
                    User user = new User();
                    user.setUserId(list.get(i).getFriendBId());
                    User queryUser = userMapper.findUser(user);
                    ServerFriend friend = new ServerFriend(queryUser.getUserId(),
                            queryUser.getUserAvatar(),
                            list.get(i).getFriendRemark(),
                            queryUser.getUserGender(),
                            queryUser.getUserSign(),
                            queryUser.getUserHome());
                    friends.add(friend);
                }
            }
            return friends;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/req")
    public ArrayList<FriendResult> friendReqList(@RequestBody Friend requestFriend) {
        System.out.println(requestFriend.toString());
        try {
            ArrayList<Friend> list = friendMapper.findFriendReq(requestFriend);
            System.out.println("req count:"+list.size());
            ArrayList<FriendResult> friends = new ArrayList<>();
            if (! list.isEmpty()) {
                for(int i = 0; i < list.size(); i++) {
                    User user = new User();
                    user.setUserId(list.get(i).getFriendAId());
                    User queryUser = userMapper.findUser(user);
                    FriendResult friend = new FriendResult(0,
                            queryUser.getUserId(),
                            queryUser.getUserAvatar(),
                            queryUser.getUserNickname());
                    friends.add(friend);
                }
            }
            return friends;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/remark")
    public String remarkFriend(@RequestBody Friend requestFriend) {
        System.out.println(requestFriend.toString());
        try {
            friendMapper.updateRemark(requestFriend);
            return requestFriend.getFriendRemark();
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
