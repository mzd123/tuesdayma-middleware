package com.tuesdayma.middleware.redis;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.redis.zset.ZSetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: mzd
 * @Date: 2021/1/14 16:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ZSetTests {
    @Resource
    private ZSetService zSetService;

    @Test
    public void add() {
        zSetService.add("javaZSet", "1111", 1.0);
    }

    @Test
    public void addTypedTuple() {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("222", 1.1);
        ZSetOperations.TypedTuple<String> typedTuple1 = new DefaultTypedTuple<>("444", 2.1);
        ZSetOperations.TypedTuple<String> typedTuple2 = new DefaultTypedTuple<>("111", 3.3);
        set.add(typedTuple);
        set.add(typedTuple1);
        set.add(typedTuple2);
        set.add(new DefaultTypedTuple<>("333", 4.1));
        set.add(new DefaultTypedTuple<>("asdfsd", 10.1));
        set.add(new DefaultTypedTuple<>("dsggfsg", 7.1));
        zSetService.addTypedTuple("javaZSet1", set);
    }

    @Test
    public void range() {
        log.info(JSON.toJSONString(zSetService.range("javaZSet1", 0, 2)));
    }

    @Test
    public void incrementScore() {
        log.info(String.valueOf(zSetService.incrementScore("javaZSet1", "手动阀手动阀法大师傅", -1.1)));
    }

    @Test
    public void count() {
        log.info(String.valueOf(zSetService.count("javaZSet1", -1.1, 1.1)));
    }

    @Test
    public void rangeByScore() {
        log.info(JSON.toJSONString(zSetService.rangeByScore("javaZSet1", -1.1, 1.1)));
    }

    @Test
    public void rangeByLex() {
        Set<String> set = zSetService.rangeByLex("javaZSet1",
                new RedisZSetCommands.Range().gte("1").lte("222"), new RedisZSetCommands.Limit().count(10).offset(0));
        log.info(JSON.toJSONString(set));
    }

    @Test
    public void rangeByScoreWithScores() {
        Set<ZSetOperations.TypedTuple<String>> set = zSetService.rangeByScoreWithScores("javaZSet1", -1.1, 10.1, 1L, 2L);
        log.info(JSON.toJSONString(set));
    }

    @Test
    public void reverseRangeByScoreWithScores() {
        Set<ZSetOperations.TypedTuple<String>> set = zSetService.reverseRangeByScoreWithScores("javaZSet1", -1.1, 10.1, 1L, 2L);
        log.info(JSON.toJSONString(set));
    }

    @Test
    public void rank() {
        log.info(String.valueOf(zSetService.rank("javaZSet1", "aefadsfdsafsfadsfadsfsa")));
    }
}
