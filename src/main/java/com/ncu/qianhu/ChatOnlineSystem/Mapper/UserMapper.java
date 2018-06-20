package com.ncu.qianhu.ChatOnlineSystem.Mapper;

import com.ncu.qianhu.ChatOnlineSystem.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:46
 * @Modified by
 */

@Component
@Mapper
public interface UserMapper {
    void insertUser(User record);
    User findUser(User record);
    void updateNickname(User record);
    void updateGender(User record);
    void updateBirthday(User record);
    void updateHome(User record);
    void updateSign(User record);
}
