package com.tuesdayma.middleware.redis.ext.delaymessage;

import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: mzd
 * @Date: 2021/2/19 16:42
 */
@Data
public class ThreadPoolConfig {
    /**
     * 核心线程
     */
    private int corePoolSize = 1;
    /**
     * 最大线程数
     */
    private int maxPoolSize = 2;
    /**
     * 等待时间
     */
    private int keepAliveSeconds = 60;
    /**
     * 最大等待队列
     */
    private int queueCapacity = 2;
    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
    /**
     * 线程池名字
     */
    private String threadPoolName = "tuesday-ma-thread-schedule-task";
    /**
     * 队列
     */
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
}
