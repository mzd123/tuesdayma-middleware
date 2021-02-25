package com.tuesdayma.middleware.redis.ext.delaymessage;


import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mzd
 * @Date: 2021/2/19 16:32
 */
@Slf4j
public class DelayTaskExecutor {

    TaskThread taskThread;
    private boolean isRun = false;

    /**
     * 初始化
     *
     * @param threadPoolConfig
     * @param stringRedisTemplate
     */
    public void initThreadPool(ThreadPoolConfig threadPoolConfig, StringRedisTemplate stringRedisTemplate) {
        taskThread = new TaskThread();
        taskThread.initThreadPool(threadPoolConfig, stringRedisTemplate);
    }


    /**
     * 加入延迟队列
     *
     * @param id
     * @param thread
     * @param time
     */
    public void addTask(String id, Thread thread, Long time) {
        if (id == null || id.length() <= 0) {
            return;
        }
        synchronized (DelayTaskExecutor.class) {
            if (taskThread == null) {
                log.error("taskThread is null,maybe DelayTaskExecutor  not init....");
            }
            if (!isRun) {
                taskThread.start();
                isRun = true;
            }
        }
        taskThread.addTask(id, thread, time);
    }

    /**
     * 移除任务
     *
     * @param id
     */
    public void cancel(String id) {
        taskThread.cancel(id);
    }

    class TaskThread extends Thread {
        ThreadPoolExecutor executor;
        private StringRedisTemplate stringRedisTemplate;
        private String delayTaskRedisIdTime = "delay-task-id-time";
        private String delayTaskRedisIdThread = "delay-task-id-thread";
        private String delayTaskRedisCount = "delay-task-count";
        private String delayTaskRedisIdWork = "delay-task-id-work";
        private String delayTaskRedis = "delay-task";

        public void initThreadPool(ThreadPoolConfig threadPoolConfig, StringRedisTemplate stringRedisTemplate) {
            executor = new ThreadPoolExecutor(threadPoolConfig.getCorePoolSize(),
                    threadPoolConfig.getMaxPoolSize(),
                    threadPoolConfig.getKeepAliveSeconds(),
                    TimeUnit.SECONDS,
                    threadPoolConfig.getWorkQueue(),
                    threadPoolConfig.getRejectedExecutionHandler());
            this.stringRedisTemplate = stringRedisTemplate;
        }

        public void addTask(String id, Thread thread, Long time) {
            //与当前时间比较
            if (time <= System.currentTimeMillis()) {
                //立即执行,不进行持久化
                executor.execute(thread);
                return;
            }
            //加入延迟队列
            stringRedisTemplate.opsForHash().put(delayTaskRedisIdTime, id, String.valueOf(time));
            stringRedisTemplate.opsForHash().put(delayTaskRedisIdThread, id, JSON.toJSONString(thread));
            stringRedisTemplate.opsForValue().increment(delayTaskRedisCount);
        }

        public void cancel(String id) {
            log.info("cancel task,id:{}", id);
            if (stringRedisTemplate.opsForSet().isMember(delayTaskRedisIdWork, id)) {
                log.error("cancel fail,id:{} task is running", id);
                return;
            }
            if (stringRedisTemplate.opsForHash().delete(delayTaskRedisIdTime, id) <= 0) {
                log.error("cancel fail,id:{} task is undefined", id);
                return;
            }
            if (stringRedisTemplate.opsForHash().delete(delayTaskRedisIdThread, id) <= 0) {
                log.error("cancel fail,id:{} task is undefined", id);
                return;
            }
            stringRedisTemplate.opsForValue().decrement(delayTaskRedisCount);
        }

        @Override
        public void run() {
            log.info("delay task start ...");
            while (true) {
                try {
                    if (stringRedisTemplate.opsForValue().setIfAbsent(delayTaskRedis, delayTaskRedis, 1, TimeUnit.MINUTES)) {
                        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(delayTaskRedisIdTime);
                        map.forEach((k, v) -> {
                            if (v == null || k == null) {
                                return;
                            }
                            Long time = Long.valueOf(v.toString());
                            if (time <= System.currentTimeMillis()) {
                                execute(k.toString());
                            }
                        });
                    }
                } catch (Exception e) {
                    log.error("delay task execute error:{}", e);
                } finally {
                    stringRedisTemplate.delete(delayTaskRedis);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }

        private void execute(String id) {
            //先把线程改成执行状态
            stringRedisTemplate.opsForSet().add(delayTaskRedisIdWork, id);
            try {
                //启动任务
                Object threadObj = stringRedisTemplate.opsForHash().get(delayTaskRedisIdThread, id);
                if (threadObj == null) {
                    return;
                }
                DelayTask delayTask = JSON.parseObject(threadObj.toString(), DelayTask.class);
                if (delayTask != null) {
                    //执行线程
                    log.info("start execute:{}", id);
                    executor.execute(delayTask);
                    //移除任务
                    stringRedisTemplate.opsForHash().delete(delayTaskRedisIdTime, id);
                    stringRedisTemplate.opsForHash().delete(delayTaskRedisIdThread, id);
                    stringRedisTemplate.opsForValue().decrement(delayTaskRedisCount);
                }
            } catch (Exception e) {
                log.error("delay task execute error:{}", e);
            } finally {
                stringRedisTemplate.opsForSet().remove(delayTaskRedisIdWork, id);
            }
        }

    }
}
