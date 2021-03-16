package com.tuesdayma.middleware.rocketmq;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.rocketmq.bean.MessageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * @Author: mzd
 * @Date: 2021/3/15 14:20
 */
@Component
@RocketMQMessageListener(topic = "tuesdayma-rocketmq", consumerGroup = "tuesdayma-rocketmq", consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class ConsumerService implements RocketMQListener<MessageBean> {
    /**
     * 默认consumeMode = ConsumeMode.CONCURRENTLY 启用20个线程消费，即普通消息同时能消费20个消息
     * consumeMode = ConsumeMode.ORDERLY 即顺序消费，启用4个线程进行消费，因为
     *
     * @param message
     */
    @Override
    public void onMessage(MessageBean message) {
        log.info("收到消息：{}", JSON.toJSONString(message));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
