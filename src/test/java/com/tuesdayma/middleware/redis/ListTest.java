package com.tuesdayma.middleware.redis;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.redis.list.ListService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: mzd
 * @Date: 2021/1/12 16:09
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ListTest {
    @Resource
    private ListService listService;

    @Test
    public void push() {
        listService.leftPush("javaList", "333");
    }

    @Test
    public void push2() {
        listService.leftPush("javaList", "444", "222");
    }

    @Test
    public void leftPushIfPresent() {
        log.info(String.valueOf(listService.leftPushIfPresent("javaList", "444")));
    }


    @Test
    public void leftPushAll() {
        List<String> collection = new ArrayList<>();
        collection.add("222");
        collection.add("555");
        collection.add("111");
        collection.add("444");
        collection.add("333");
        collection.add("444");
        collection.add("222");
        collection.add("222");
        collection.add("555");
        collection.add("222");
        collection.add("111");
        collection.add("444");
        collection.add("333");
        collection.add("444");
        listService.leftPushAll("javaList", collection);
//        Set<String> collection = new HashSet<>();
//        collection.add("手动阀第三方了");
//        collection.add("啊的司法大厦");
//        collection.add("打发打法是");
//        collection.add("地方都是");
        // listService.pushAll("javaList", collection);
    }

    @Test
    public void set() {
        listService.set("javaList1", 10L, "7777");
    }

    @Test
    public void leftPop() {
        log.info(listService.leftPop("javaList"));
    }

    @Test
    public void index() {
        log.info(listService.index("javaList", 0L));
    }

    @Test
    public void range() {
        log.info(JSON.toJSONString(listService.range("javaList", 0, 1)));
    }

    @Test
    public void remove() {
        log.info(String.valueOf(listService.remove("javaList", 0, "222")));
    }

    @Test
    public void rightPopAndLeftPush() {
        listService.rightPopAndLeftPush("javaList", "javaRightPush2", null);
    }
}
