package com.tuesdayma.middleware.redis;

import com.tuesdayma.middleware.redis.channel.ChannelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: mzd
 * @date: 2021-01-17 16:31
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChannelTests {
    @Resource
    private ChannelService channelService;

    @Test
    public void publishMessage() {
        channelService.publishMessage("aa", "javaMessage");
    }
}
