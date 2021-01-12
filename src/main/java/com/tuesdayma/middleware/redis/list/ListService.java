package com.tuesdayma.middleware.redis.list;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author: mzd
 * @Date: 2021/1/12 16:05
 */
@Slf4j
@Component
public class ListService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 往 key 这个list放入一个value
     *
     * @param key
     * @param value
     */
    public void leftPush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将value放在key这个list中targetValue的右侧
     *
     * @param key
     * @param targetValue
     * @param value
     */
    public void leftPush(String key, String targetValue, String value) {
        stringRedisTemplate.opsForList().leftPush(key, targetValue, value);
    }

    /**
     * 当key不存在时，直接返回0
     * 当key存在时，左入栈value，并返回key中总共的元素长度
     *
     * @param key
     * @param value
     * @return
     */
    public long leftPushIfPresent(String key, String value) {
        return stringRedisTemplate.opsForList().leftPushIfPresent(key, value);
    }


    /**
     * 一下子给key这个list放入一个集合
     *
     * @param key
     * @param collection
     */
    public void leftPushAll(String key, Collection<String> collection) {
        stringRedisTemplate.opsForList().leftPushAll(key, collection);
    }

    /**
     * 返回最后一个进栈的
     * 无论是左入栈左出栈，还是右入栈右出栈，都是后进先出
     *
     * @param key
     * @return
     */
    public String leftPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    /**
     * 获取key这个list中第几个元素，下标从0开始
     *
     * @param key
     * @param index
     * @return
     */
    public String index(String key, long index) {
        return stringRedisTemplate.opsForList().index(key, index);
    }

    /*无论是index还是range，下标都是从左往右数的*/

    /**
     * 获取key从start到end之间的元素,下标从0开始
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * count>0时，删除key这个list中从左到右，count个出现value的值
     * count<0时，删除key这个list中从右到左，|count|个出现value的值
     * count=0时，删除key这个list中所有value的值
     *
     * @param key
     * @param count
     * @param value
     * @return 返回值为删除的元素个数
     */
    public long remove(String key, long count, String value) {
        return stringRedisTemplate.opsForList().remove(key, count, value);
    }

}
