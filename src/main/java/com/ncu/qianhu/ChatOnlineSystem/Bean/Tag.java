package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:17
 * @Modified by
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Tag {
    private int tagId;
    private String tagSenderId;
    private String tagReceiverId;
    private String tagContent;
}
