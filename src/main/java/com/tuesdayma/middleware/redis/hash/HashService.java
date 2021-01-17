package com.tuesdayma.middleware.redis.hash;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: mzd
 * @date: 2021-01-17 11:33
 **/
@Component
@Slf4j
public class HashService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向key的map对象中的hKey赋值为hValue
     *
     * @param key
     * @param hKey
     * @param hValue
     */
    public void put(String key, String hKey, String hValue) {
        stringRedisTemplate.opsForHash().put(key, hKey, hValue);
    }

    /**
     * 添加一个map
     *
     * @param key
     * @param hash
     */
    public void putAll(String key, Map<String, String> hash) {
        stringRedisTemplate.opsForHash().putAll(key, hash);
    }

    /**
     * 向key中的hKey赋值，如果hKey已存在，则赋值失败，返回false；如果不存在，则赋值成功，并返回true
     *
     * @param key
     * @param hKey
     * @param hValue
     * @return
     */
    public Boolean putIfAbsent(String key, String hKey, String hValue) {
        return stringRedisTemplate.opsForHash().putIfAbsent(key, hKey, hValue);
    }


    /**
     * 获取key中hashKey的value
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object get(String key, String hashKey) {
        return stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 一次性获取key对应的map中多个元素
     * 如果collection中的有hashKey不存在，则返回的list中将会有null对象
     *
     * @param key
     * @param collection
     * @return
     */
    public List<Object> multiGet(String key, Collection<Object> collection) {
        return stringRedisTemplate.opsForHash().multiGet(key, collection);
    }

    /**
     * 返回key中所有元素
     *
     * @param key
     * @return
     */
    public List<Object> values(String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

    /**
     * 获取key对应的map
     *
     * @param key
     * @return
     */
    public Map<Object, Object> entries(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }


    /**
     * 返回key中map的元素个数
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForHash().size(key);
    }

    /**
     * 获取key中hashKey元素对应的value的长度
     * hashkey不存在则返回0
     *
     * @param key
     * @param hasKey
     * @return
     */
    public Long lengthOfValue(String key, String hasKey) {
        return stringRedisTemplate.opsForHash().lengthOfValue(key, hasKey);
    }


    /**
     * 删除key中hashKey的value值
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Long delete(String key, String hashKey) {
        return stringRedisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断key中hashKey是否存在
     * 类同与set数据结构中的isMember
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hasKey(String key, String hashKey) {
        return stringRedisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 遍历key这个map中的所有hashKey
     *
     * @param key
     * @return
     */
    public Set<Object> keys(String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    /**
     * 给key对应的map中hashKey的value加值
     * 前提hashKey的value必须是能转integer的，不然就会报错 ERR hash value is not an integer
     *
     * @param key
     * @param hasKey
     * @param addValue
     * @return
     */
    public Long increment(String key, String hasKey, Long addValue) {
        return stringRedisTemplate.opsForHash().increment(key, hasKey, addValue);
    }


}
