package com.tuesdayma.middleware.redis;

import com.tuesdayma.middleware.redis.ext.delaymessage.DelayTask;
import com.tuesdayma.middleware.redis.ext.delaymessage.DelayTaskExecutor;
import lombok.extern.slf4j.Slf4j;
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
 * @Date: 2021/2/25 16:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DelayMessageTests {

    @Resource
    private DelayTaskExecutor delayTaskExecutor;

    @Test
    public void doTest() {
        for (int i = 0; i < 10; i++) {
            DelayTask delayTask = new DelayTask();
            delayTask.setK(i);
            if (i == 3) {
                delayTaskExecutor.addTask("123", delayTask, System.currentTimeMillis() + 5000);
            } else {
                delayTaskExecutor.addTask(UUID.randomUUID().toString().replaceAll("-", ""), delayTask, System.currentTimeMillis() + 5000);
            }
        }
        delayTaskExecutor.cancel("123");
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
