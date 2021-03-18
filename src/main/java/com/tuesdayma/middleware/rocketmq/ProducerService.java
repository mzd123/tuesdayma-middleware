package com.tuesdayma.middleware.rocketmq;

import com.tuesdayma.middleware.rocketmq.bean.MessageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/3/15 13:58
 */
@Profile(value = "rocketmq")
@Service
@Slf4j
public class ProducerService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送同步消息
     *
     * @param topic      topic:tag
     * @param msg
     * @param timeout    不填或者null默认为30000 单位毫秒
     * @param delayLevel 延迟等级 1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
     *                   填1表示延迟1s  填2表示延迟5s  填0或者不填表示不需要延迟
     * @return
     */
    public SendResult syncSend(String topic, MessageBean msg, Integer timeout, Integer delayLevel) {
        if (timeout == null) {
            timeout = 30000;
        }
        if (delayLevel == null) {
            delayLevel = 0;
        }
        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        return rocketMQTemplate.syncSend(topic, message, timeout, delayLevel);
    }


    public SendResult syncSendOrderly(String topic, MessageBean msg) {
        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        return rocketMQTemplate.syncSendOrderly(topic, message, msg.getId());
    }

    /**
     * 发送异步消息
     *
     * @param topic      topic:tag
     * @param msg
     * @param timeout    不填或者null默认为30000 单位毫秒
     * @param delayLevel 延迟等级 1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
     *                   填1表示延迟1s  填2表示延迟5s  填0或者不填表示不需要延迟
     * @return
     */
    public void asyncSend(String topic, MessageBean msg, Integer timeout, Integer delayLevel) {
        if (timeout == null) {
            timeout = 30000;
        }
        if (delayLevel == null) {
            delayLevel = 0;
        }
        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        //如果消息发送成功就会执行
                        log.info("消息发送成功");
                        stringRedisTemplate.opsForValue().set("1", "1");
                    }

                    @Override
                    public void onException(Throwable e) {
                        //如果消息发送出现了异常就会执行
                        log.info("消息发送失败：", e);
                    }
                },
                timeout, delayLevel);
    }

    /**
     * 此方法不会等存储的结果
     * 它有最大的吞吐量，但潜在的消息丢失。不建议使用
     *
     * @param topic
     * @param msg
     */
    public void sendOneWay(String topic, MessageBean msg) {
        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.sendOneWay(topic, message);
    }

}
