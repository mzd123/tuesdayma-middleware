package com.tuesdayma.middleware.rocketmq;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.rocketmq.bean.MessageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: mzd
 * @Date: 2021/3/15 14:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RocketMqTests {
    @Resource
    private ProducerService producerService;

    @Test
    public void syncSend() {
        MessageBean messageBean = new MessageBean();
        messageBean.setContent("测试消息" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        messageBean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        SendResult sendResult = producerService.syncSend("tuesdayma-rocketmq:sync", messageBean, null, 3);
        log.info(JSON.toJSONString(sendResult));
    }


    @Test
    public void asyncSend() {
        MessageBean messageBean = new MessageBean();
        messageBean.setContent("测试消息" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        messageBean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        producerService.asyncSend("tuesdayma-rocketmq:async", messageBean, null, 3);
    }

    @Test
    public void sendOneWay() {
        MessageBean messageBean = new MessageBean();
        messageBean.setContent("测试消息" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        messageBean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        producerService.sendOneWay("tuesdayma-rocketmq:sendOneWay", messageBean);
    }

    @Test
    public void syncSendOrderly() {

//        MessageBean messageBean = new MessageBean();
//        messageBean.setContent("测试消息" + "   " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        messageBean.setId("1111");
//        producerService.syncSendOrderly("tuesdayma-rocketmq:sync", messageBean);

        for (int i = 0; i < 100; i++) {
            MessageBean messageBean = new MessageBean();
            messageBean.setContent("测试消息" + i + "   " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            messageBean.setId("1111");
            producerService.asyncSend("tuesdayma-rocketmq:sync", messageBean,null,null);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
