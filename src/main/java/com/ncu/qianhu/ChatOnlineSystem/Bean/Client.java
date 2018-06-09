package com.ncu.qianhu.ChatOnlineSystem.Bean;

import java.security.Principal;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/9
 * @Modified by
 */
public class Client implements Principal {

    private final String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
