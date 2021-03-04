package com.tuesdayma.middleware.dingding.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import com.tuesdayma.middleware.dingding.bean.DingMessage;
import com.tuesdayma.middleware.dingding.bean.LinkMessage;
import com.tuesdayma.middleware.dingding.bean.MarkDownMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @Author: mzd
 * @Date: 2021/3/4 13:37
 */
@Service
@Slf4j
public class DingMessageService {
    /**
     * 发送钉钉消息
     *
     * @param url
     * @param dingMessage
     * @throws ApiException
     */
    public void sendMessage(String url, DingMessage dingMessage) throws ApiException {
        log.info("收到需要钉钉发送的内容为：{},{}", url, JSON.toJSONString(dingMessage));
        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(dingMessage.getMessageType());
        if ("text".equals(dingMessage.getMessageType())) {
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(dingMessage.getContent());
            request.setText(text);
        } else if ("link".equals(dingMessage.getMessageType())) {
            LinkMessage linkMessage = (LinkMessage) dingMessage;
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setMessageUrl(linkMessage.getMessageUrl());
            link.setPicUrl(linkMessage.getPicUrl());
            link.setTitle(linkMessage.getTitle());
            link.setText(linkMessage.getContent());
            request.setLink(link);
        } else if ("markdown".equals(dingMessage.getMessageType())) {
            MarkDownMessage markDownMessage = (MarkDownMessage) dingMessage;
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(markDownMessage.getTitle());
            markdown.setText(markDownMessage.getContent());
            request.setMarkdown(markdown);
        }
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        if (dingMessage.getAtMobileList() != null && dingMessage.getAtMobileList().size() > 0) {
            at.setAtMobiles(dingMessage.getAtMobileList());
        }
        if (dingMessage.getIsAtAll() != null) {
            at.setIsAtAll(dingMessage.getIsAtAll());
        }
        request.setAt(at);
        OapiRobotSendResponse response = client.execute(request);
        log.info("钉钉发送结果为：{}", JSON.toJSONString(response));
    }
}
