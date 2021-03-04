package com.tuesdayma.middleware.dingding;

import com.taobao.api.ApiException;
import com.tuesdayma.middleware.dingding.bean.LinkMessage;
import com.tuesdayma.middleware.dingding.service.DingMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/3/4 14:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DingTests {

    @Resource
    private DingMessageService dingMessageService;

    @Test
    public void sendMessage() throws ApiException {
        LinkMessage dingMessage = new LinkMessage();
        dingMessage.setContent("测试连接");
        dingMessage.setMessageType("link");
        dingMessage.setMessageUrl("连接跳转地址");
        dingMessage.setTitle("钉钉通知页面跳转测试");
        dingMessage.setPicUrl("图片地址");
        dingMessageService.sendMessage("钉钉通知地址", dingMessage);
    }
}
