package com.tuesdayma.middleware.rocketmq;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.rocketmq.bean.MessageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * @Author: mzd
 * @Date: 2021/3/15 14:20
 */
@Profile(value = "rocketmq")
@Component
@RocketMQMessageListener(topic = "tuesdayma-rocketmq", consumerGroup = "tuesdayma-rocketmq", consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class ConsumerService implements RocketMQListener<MessageBean> {
    /**
     * 默认consumeMode = ConsumeMode.CONCURRENTLY
     * 无论发送的是顺序消息还是普通消息，都会启用20个线程去消费消息
     *
     * consumeMode = ConsumeMode.ORDERLY
     * 无论是发送顺序消息还是普通消息，最多起20个线程去消费消息，最小取决于配置了多少个broker，一个broker配置了多少个队列
     *
     * @param message
     */
    @Override
    public void onMessage(MessageBean message) {
        log.info("收到消息：{}", JSON.toJSONString(message));
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
