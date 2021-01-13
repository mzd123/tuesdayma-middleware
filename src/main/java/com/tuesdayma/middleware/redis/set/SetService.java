package com.tuesdayma.middleware.redis.set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: mzd
 * @Date: 2021/1/13 16:08
 */
@Component
public class SetService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向key中add一个value，乱序的
     * 如果这个value已经存在，则不再新增
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        stringRedisTemplate.opsForSet().add(key, value);
    }

    /**
     * 返回key中的元素个数
     * 当key不存在时返回0
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 获取key中的所有元素
     *
     * @param key
     * @return
     */
    public Set<String> members(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * 删除key中的value元素
     *
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, String value) {
        return stringRedisTemplate.opsForSet().remove(key, value);
    }


    /**
     * 将key1中的值，去除keys中set集合的所有元素，得到一个set集合赋值到key2中
     * 如果key1不存在，则key2也将会被制为null
     * 如果keys中没有一个key，则等于key1的值全部赋值给key2
     *
     * @param key1
     * @param keys
     * @param key2
     * @return
     */
    public Long differenceAndStore(String key1, Collection<String> keys, String key2) {
        return stringRedisTemplate.opsForSet().differenceAndStore(key1, keys, key2);
    }

    /**
     * 将key1中的值，与keys中每个key的集合进行做交集，最后得到的交集结果赋值给key2
     * 如果key1不存在，则key2也将会被制为null
     * 如果keys中没有一个key，则等于key1的值全部赋值给key2
     *
     * @param key1
     * @param keys
     * @param key2
     * @return
     */
    public Long intersectAndStore(String key1, Collection<String> keys, String key2) {
        return stringRedisTemplate.opsForSet().intersectAndStore(key1, keys, key2);
    }

    /**
     * 查看value是不是key中的元素，如果是则返回true，如果不是则返回false
     * 如果key本身就是不存在的，则直接返回false
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }
}
