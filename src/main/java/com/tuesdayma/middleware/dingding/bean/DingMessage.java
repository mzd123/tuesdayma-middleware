package com.tuesdayma.middleware.dingding.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author: mzd
 * @Date: 2021/3/4 13:49
 */
@Data
public class DingMessage {
    /**
     * 消息类型
     * text，link，markdown
     */
    private String messageType;
    /**
     * 是否@ 所有人
     */
    private Boolean isAtAll;
    /**
     * 需要被@ 人的电话号码
     */
    private List<String> atMobileList;
    /**
     * 发送的文本内容
     */
    private String content;
}
