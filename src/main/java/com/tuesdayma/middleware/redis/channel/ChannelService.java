package com.tuesdayma.middleware.redis.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: mzd
 * @date: 2021-01-17 16:27
 **/
@Profile(value = "redis")
@Component
@Slf4j
public class ChannelService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向主题为channelName的队列中发送消息为message
     *
     * @param channelName
     * @param message
     */
    public void publishMessage(String channelName, String message) {
        stringRedisTemplate.convertAndSend(channelName, message);
    }
}
