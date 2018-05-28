package com.ncu.qianhu.ChatOnlineSystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ncu.qianhu.ChatOnlineSystem.Mapper")
public class ChatOnlineSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatOnlineSystemApplication.class, args);
	}
}
