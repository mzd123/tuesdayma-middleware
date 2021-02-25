package com.tuesdayma.middleware.redis.ext.delaymessage;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/2/25 16:04
 */
@Component
public class InitDelayTask {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean(name = "delayTaskExecutor")
    public DelayTaskExecutor initDelayTaskExecutor() {
        DelayTaskExecutor delayTaskExecutor = new DelayTaskExecutor();
        delayTaskExecutor.initThreadPool(new ThreadPoolConfig(), stringRedisTemplate);
        return delayTaskExecutor;
    }
}
