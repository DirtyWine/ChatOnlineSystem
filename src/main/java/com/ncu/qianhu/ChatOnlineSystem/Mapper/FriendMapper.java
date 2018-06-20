package com.ncu.qianhu.ChatOnlineSystem.Mapper;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:46
 * @Modified by
 */

@Component
@Mapper
public interface FriendMapper {
    ArrayList<Friend> findFriend(Friend record);
    ArrayList<Friend> findFriendReq(Friend record);
    Friend findRemark(Friend record);
    void insertFriend(Friend record);
    void updateStat(Friend record);
    void updateRemark(Friend record);
    void deleteFriend(Friend record);

}
