package com.ncu.qianhu.ChatOnlineSystem.Mapper;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Friend;
import com.ncu.qianhu.ChatOnlineSystem.Bean.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:47
 * @Modified by
 */

@Component
@Mapper
public interface MessageMapper {
    ArrayList<Message> findMessage(Message record);
    ArrayList<Message> findHistoryRecord(Message record);
    void insertMessage(Message record);
    void updateMessage(Message record);
    void deleteMessage(Message record);
}
