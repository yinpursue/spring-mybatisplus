package com.example.demo;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class HelloWorldController {

    @GetMapping("/helloWorld")
    public String helloWorld(){
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        snowflake.nextId();
        ReentrantLock lock = new ReentrantLock();
        lock.lockInterruptibly();
        lock.lock();
        ExecutorService executorService = Executors.newFixedThreadPool();
        executorService.execute();

        return "hello world";
    }
}
