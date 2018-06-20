package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/8
 * @Modified by
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientMessage {
    private String location = "left";
    private String avatar;
    private String content;
    private String time;
    private String id;
}
