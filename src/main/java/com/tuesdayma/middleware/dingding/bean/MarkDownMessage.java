package com.tuesdayma.middleware.dingding.bean;

import lombok.Data;

/**
 * @Author: mzd
 * @Date: 2021/3/4 14:13
 */
@Data
public class MarkDownMessage extends DingMessage {
    /**
     * 标题
     */
    private String title;
}
