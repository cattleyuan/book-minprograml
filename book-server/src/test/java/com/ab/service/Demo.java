package com.ab.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author cattleYuan
 * @date 2024/4/15
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Demo {
    @Resource
    public ExecutorService pool;
    @Test
    public void excu() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    downLatch.countDown();
                    System.out.println("test:"+Thread.currentThread().getName());
                }
            });
        }

        downLatch.await();

    }

}
