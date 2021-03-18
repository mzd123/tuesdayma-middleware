package com.tuesdayma.middleware.redis.list;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mzd
 * @Date: 2021/1/12 16:05
 */
@Profile(value = "redis")
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
     * 将key中第index个值设置为value，key必须要存在，不然会报错
     * index 不能超过list的长度-1，不然会报错
     *
     * @param key
     * @param index
     * @param value
     */
    public void set(String key, long index, String value) {
        stringRedisTemplate.opsForList().set(key, index, value);
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
     * 如果index传入的是符数，则从右往左数，下标从-1开始
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

    /**
     * key1右出栈一个value，再将这个value左进栈到key2中
     * 如果key1不存在则，什么事情都不会发生，key2也不会多一个值
     * 如果key2不存在，key1存在，则key1会右出栈一个value，并且会新建一个key2
     * 应用场景：
     * 安全的队列：key1为等待处理的事件队列，key2为处理中的事件队列，当事件处理完之后，再删除key2中的值
     *
     * @param key1
     * @param key2
     * @param timeout
     */
    public void rightPopAndLeftPush(String key1, String key2, Long timeout) {
        if (timeout == null) {
            stringRedisTemplate.opsForList().rightPopAndLeftPush(key1, key2);
        } else {
            stringRedisTemplate.opsForList().rightPopAndLeftPush(key1, key2, timeout, TimeUnit.SECONDS);
        }
    }

}
