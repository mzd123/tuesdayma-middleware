package com.tuesdayma.middleware.redis.channel;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author: mzd
 * @date: 2021-01-17 16:43
 **/
@Component
@Slf4j
public class MyListener implements MessageListener {
    /**
     * 特点：
     * 只要是监听了这个主题的机器，都会收到消息
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("{},收到消息为：{}", new String(pattern), new String(message.getBody()));
        if (new String(message.getBody()).equals("1111")){
            try {
                Thread.sleep(1000000000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
