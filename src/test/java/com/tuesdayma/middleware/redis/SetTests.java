package com.tuesdayma.middleware.redis;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.redis.set.SetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: mzd
 * @Date: 2021/1/13 16:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SetTests {

    @Resource
    private SetService setService;

    @Test
    public void add() {
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "3333");
        setService.add("javaSet5", "3333");
    }

    @Test
    public void size() {
        log.info(String.valueOf(setService.size("javaSet1")));
    }

    @Test
    public void members() {
        log.info(JSON.toJSONString(setService.members("javaSet2")));
    }

    @Test
    public void remove() {
        log.info(JSON.toJSONString(setService.remove("javaSet4","1111")));
    }

    @Test
    public void differenceAndStore() {
        Collection<String> keys = new ArrayList<>();
        log.info(String.valueOf(setService.differenceAndStore("javaSet3", keys, "javaSet2")));
    }

    @Test
    public void intersectAndStore() {
        List<String> keys = new ArrayList<>();
        //keys.add("javaSet1");
        log.info(String.valueOf(setService.intersectAndStore("javaSet", keys, "javaSet2")));
    }

    @Test
    public void isMember() {
        log.info(String.valueOf(setService.isMember("javaSet6", "112")));
    }
}
