package com.ab;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@EnableCaching
@EnableScheduling
@MapperScan({"com.ab.*.mapper","com.ab.websocket.chat.*.mapper"})
public class BookServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServerApplication.class, args);
    }

}
