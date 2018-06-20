package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/14 0:53
 * @Modified by
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendResult {
    private int type;
    private String id;
    private String avatar;
    private String nickname;
}
