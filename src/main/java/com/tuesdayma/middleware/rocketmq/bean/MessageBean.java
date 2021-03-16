package com.tuesdayma.middleware.rocketmq.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mzd
 * @Date: 2021/3/15 14:45
 */
@Data
public class MessageBean implements Serializable {
    private String id;
    private String content;
}
