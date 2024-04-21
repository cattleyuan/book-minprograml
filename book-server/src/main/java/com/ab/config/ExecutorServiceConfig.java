package com.ab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;


/**
 * @author cattleYuan
 * @date 2024/2/6
 */
@Configuration
public class ExecutorServiceConfig {

    private static Integer corePoolSize=5;
    private static Integer maximumPoolSize=10;
    private static Integer keepAliveMinutes=5;
    private static Integer queueCapacity=5;

    @Bean("pool")
    ExecutorService executorService(){
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveMinutes, TimeUnit.SECONDS,new ArrayBlockingQueue<>(queueCapacity));
    }
}
