package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:19
 * @Modified by
 */


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private int messageId;
    private String messageSenderId;
    private String messageReceiverId;
    private String messageTime;
    private String messageContent;
    private int messageStat;
    private String senderAvatar;

}
