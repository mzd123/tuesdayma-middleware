package com.tuesdayma.middleware.redis.bitmap;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/1/8 11:32
 */
@Component
public class BitMapService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 赋值第几位
     *
     * @param key
     * @param offset
     * @param value
     */
    public void bitSet(String key, long offset, boolean value) {
        stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取第几位
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean bitGet(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 有多少个1
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long bitCount(String key, int start, int end) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    /**
     * 并且
     *
     * @param result
     * @param key1
     * @param key2
     * @return
     */
    public long bitOpAnd(String result, String key1, String key2) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.AND, result.getBytes(), key1.getBytes(), key2.getBytes()));
    }

    /**
     * 或
     *
     * @param result
     * @param key1
     * @param key2
     * @return
     */
    public long bitOpOr(String result, String key1, String key2) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.OR, result.getBytes(), key1.getBytes(), key2.getBytes()));
    }

    /**
     * 异或
     * 相同则0 不同则1
     *
     * @param result
     * @param key1
     * @param key2
     * @return
     */
    public long bitOpXor(String result, String key1, String key2) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.XOR, result.getBytes(), key1.getBytes(), key2.getBytes()));
    }
}
