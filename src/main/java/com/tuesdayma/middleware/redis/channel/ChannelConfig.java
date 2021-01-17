package com.tuesdayma.middleware.redis.channel;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author: mzd
 * @date: 2021-01-17 16:46
 **/
@Component
public class ChannelConfig {
    /**
     * 注册监听器
     *
     * @param connectionFactory
     * @param myListener
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MyListener myListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 可以添加多个 messageListener，配置不同的交换机
        container.addMessageListener(myListener, new PatternTopic("aa"));
        return container;
    }
}
