package com.tuesdayma.middleware.redis.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/1/8 11:32
 */
@Configuration
public class RedisConfig {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(value = "redisTemplate")
    public RedisTemplate redisTemplate() {
        RedisTemplate temp = new RedisTemplate();
        temp.setKeySerializer(new StringRedisSerializer());
        temp.setValueSerializer(new GenericFastJsonRedisSerializer());
        temp.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        temp.setHashKeySerializer(new StringRedisSerializer());
        temp.setConnectionFactory(redisConnectionFactory);
        return temp;
    }

    @Bean(value = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate temp = new StringRedisTemplate();
        temp.setKeySerializer(new StringRedisSerializer());
        temp.setValueSerializer(new StringRedisSerializer());
        temp.setHashValueSerializer(new StringRedisSerializer());
        temp.setHashKeySerializer(new StringRedisSerializer());
        temp.setConnectionFactory(redisConnectionFactory);
        return temp;
    }
}
