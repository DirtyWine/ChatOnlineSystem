package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Friend;
import com.ncu.qianhu.ChatOnlineSystem.Bean.FriendResult;
import com.ncu.qianhu.ChatOnlineSystem.Bean.Message;
import com.ncu.qianhu.ChatOnlineSystem.Bean.User;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.FriendMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.MessageMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.TagMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:45
 * @Modified by
 */

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private TagMapper tagMapper;



    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User requestUser) {

        System.out.println(requestUser.toString());
        try {
            User queryUser = userMapper.findUser(requestUser);
            if (null == queryUser) {
                return new ResponseEntity<>("userID not exits", HttpStatus.OK);
            }else if(queryUser.getUserPassword().equals(requestUser.getUserPassword())){
                return new ResponseEntity<>("login succeed\t 欢迎 "+queryUser.getUserNickname(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("wrong password", HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error",HttpStatus.OK);
        }
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User requestUser) {


        System.out.println(requestUser.toString());
        try {
            User queryUser = userMapper.findUser(requestUser);
            if(null == queryUser){
                userMapper.insertUser(requestUser);
                return new ResponseEntity<>("Successfully registered", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("This ID has been registered", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error",HttpStatus.OK);
        }
    }

    @CrossOrigin
    @PostMapping("/info")
    public User getInfo(@RequestBody User requestUser) {
        System.out.println(requestUser.toString());
        try {
            User user = userMapper.findUser(requestUser);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/list")
    public ArrayList<FriendResult> userList(@RequestBody User requestUser) {

        System.out.println(requestUser.toString());
        ArrayList<FriendResult> userResult = new ArrayList<>();
        try {
            User list = userMapper.findUser(requestUser);
            if(null != list){
                FriendResult friendResult = new FriendResult(0,
                        list.getUserId(),
                        list.getUserAvatar(),
                        list.getUserNickname()
                );
                userResult.add(friendResult);
            }
            return userResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @CrossOrigin
    @GetMapping("/update")
    public ResponseEntity<String> update(User requestUser) {

        System.out.println(requestUser.toString());
        try {
            User queryUser = userMapper.findUser(requestUser);
            if(null == queryUser){
                return new ResponseEntity<>("您输入的用户名不存在", HttpStatus.OK);
            }
            else{
                userMapper.updateBirthday(requestUser);
                return new ResponseEntity<>("修改成功", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("错误",HttpStatus.OK);
        }
    }

}
