package com.tuesdayma.middleware.redis;

import com.tuesdayma.middleware.redis.scan.ScanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: mzd
 * @Date: 2021/1/11 10:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ScanTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ScanService scanService;

    @Test
    public void init() {
        for (int i = 0; i < 10000000; i++) {
            stringRedisTemplate.opsForValue().set("k" + i, String.valueOf(i) + "小明");
        }
    }

    @Test
    public void doScanKey() {
//        Set<String> set = scanService.doScanKey(null, null);
//        log.info("长度为：{}", set.size());
        Set<String> set = scanService.doScanKey(20L, null);
        log.info("长度为：{}", set.size());
    }


    @Test
    public void doScanKeyCompareScan() {
        for (int i = 0; i < 2; i++) {
            ScanKeyThread scanKeyThread = new ScanKeyThread();
            scanKeyThread.setScanService(scanService);
            scanKeyThread.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(stringRedisTemplate.opsForValue().get("k1"));
        //增加一下线程的存活时间
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doScanKeyCompareKeys() {
        for (int i = 0; i < 2; i++) {
            KeysThread keysThread = new KeysThread();
            keysThread.setStringRedisTemplate(stringRedisTemplate);
            keysThread.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(stringRedisTemplate.opsForValue().get("k1"));
        //增加一下线程的存活时间
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class KeysThread extends Thread {
        private StringRedisTemplate stringRedisTemplate;

        public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
            this.stringRedisTemplate = stringRedisTemplate;
        }

        @Override
        public void run() {
            Set<String> set = stringRedisTemplate.keys("k*");
            log.info("end......KeysThread,{}", set.size());
        }
    }

    class ScanKeyThread extends Thread {
        private ScanService scanService;

        public void setScanService(ScanService scanService) {
            this.scanService = scanService;
        }

        @Override
        public void run() {
            Set<String> set = scanService.doScanKey(1000L, "k*");
            log.info("end......ScanKeyThread,{}", set.size());
        }
    }


}
