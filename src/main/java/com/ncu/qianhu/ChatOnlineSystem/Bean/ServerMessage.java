package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/8
 * @Modified by
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServerMessage {
    private String remark;
    private String location = "left";
    private String avatar;
    private String content;
    private String time;
    private String id;
}
