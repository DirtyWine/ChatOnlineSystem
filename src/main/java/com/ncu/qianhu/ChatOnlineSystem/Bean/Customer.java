package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/5/28
 * @Modified by
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Customer {
    private String name;
    private String password;
    private String gender;
}
