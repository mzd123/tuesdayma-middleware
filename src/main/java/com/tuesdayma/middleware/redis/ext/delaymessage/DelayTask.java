package com.tuesdayma.middleware.redis.ext.delaymessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: mzd
 * @Date: 2021/2/25 16:43
 */
@Slf4j
public class DelayTask extends Thread {
    private int k;

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName() + "执行：" + k);
    }
}
