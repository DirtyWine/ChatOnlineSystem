package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:13
 * @Modified by
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    private String userId;
    private String userNickname;
    private String userPassword;
    private String userAvatar;
    private String userGender;
    private String userBirthday;
    private String userHome;
    private String userSign;

}
