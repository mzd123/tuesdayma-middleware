package com.tuesdayma.middleware.dingding.bean;

import lombok.Data;

/**
 * @Author: mzd
 * @Date: 2021/3/4 13:59
 */
@Data
public class LinkMessage extends DingMessage {
    /**
     * 跳转连接地址
     */
    private String messageUrl;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 标题
     */
    private String title;

}
