package com.tuesdayma.middleware.redis;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.redis.hash.HashService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author: mzd
 * @date: 2021-01-17 11:49
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class HashTests {
    @Resource
    private HashService hashService;

    @Test
    public void put() {
        hashService.put("javaHash", "put2", "222");
        hashService.put("javaHash", "put1", "111");
        hashService.put("javaHash", "put4", "444");
        hashService.put("javaHash", "put3", "333");
    }

    @Test
    public void putAll() {
        HashMap hashMap = new HashMap();
        hashMap.put("putAll1", "1111");
        hashMap.put("putAll2", "2222");
        hashMap.put("putAll3", "3333");
        hashMap.put("putAll4", "4444");
        hashService.putAll("javaHashAll", hashMap);
    }


    @Test
    public void putIfAbsent() {
        log.info(String.valueOf(hashService.putIfAbsent("javaHash", "put2", "12321")));
    }


    @Test
    public void get() {
        log.info(JSON.toJSONString(hashService.get("javaHashAll", "aaa")));
    }

    @Test
    public void multiGet() {
        Collection<Object> collection = new HashSet<>();
        collection.add("putAll3");
        collection.add("putAll1");
        collection.add("putAll4");
        log.info(JSON.toJSONString(hashService.multiGet("javaHashAll", collection)));
    }

    @Test
    public void values() {
        log.info(JSON.toJSONString(hashService.values("javaHashAll22")));
    }

    @Test
    public void entries() {
        log.info(JSON.toJSONString(hashService.entries("javaHashAll")));
    }

    @Test
    public void size() {
        log.info(JSON.toJSONString(hashService.size("javaHashAll")));
    }

    @Test
    public void lengthOfValue() {
        log.info(String.valueOf(hashService.lengthOfValue("javaHashAll", "putAll1111")));
    }

    @Test
    public void delete() {
        log.info(String.valueOf(hashService.delete("javaHashAll", "putAll4")));
    }

    @Test
    public void hasKey() {
        log.info(String.valueOf(hashService.hasKey("javaHashAll", "putAll2")));
    }

    @Test
    public void keys() {
        log.info(JSON.toJSONString(hashService.keys("javaHashAll1")));
    }
    @Test
    public void increment(){
        log.info(JSON.toJSONString(hashService.increment("javaHashAll","putAllString",11L)));
    }
}
