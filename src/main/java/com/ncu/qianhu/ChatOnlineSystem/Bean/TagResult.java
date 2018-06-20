package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

import java.util.ArrayList;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/14
 * @Modified by
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagResult {
    private String remark;
    private ArrayList<String> tag;
}
