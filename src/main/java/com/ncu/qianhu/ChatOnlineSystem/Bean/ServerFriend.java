package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/13
 * @Modified by
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServerFriend {
    private String id;
    private String avatar;
    private String remark;
    private String gender;
    private String sign;
    private String home;
}
