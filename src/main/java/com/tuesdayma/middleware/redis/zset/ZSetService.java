package com.tuesdayma.middleware.redis.zset;

import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: mzd
 * @Date: 2021/1/14 15:40
 * remove、intersectAndStore同set
 */
@Component
public class ZSetService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增一个元素
     *
     * @param key
     * @param value 值
     * @param score 分数
     * @return
     */
    public Boolean add(String key, String value, Double score) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 新增一个集合
     *
     * @param key
     * @param set
     * @return
     */
    public Long addTypedTuple(String key, Set<ZSetOperations.TypedTuple<String>> set) {
        return stringRedisTemplate.opsForZSet().add(key, set);
    }


    /**
     * 给key的value这个值的【分数】加上data，返回结果为 value最后的分数
     * 如果key中这个value不存在，则相当于是add
     * data 可以为负数
     *
     * @param key
     * @param value
     * @param data
     * @return
     */
    public Double incrementScore(String key, String value, double data) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, value, data);
    }

    /**
     * 返回key中，【分数】大于等于min，小于等于max的元素个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long count(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取【下标】范围内的元素集合，下标从0开始
     * start和end不能为负数
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> range(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 返回key中，【分数】大于等于min，小于等于max的元素
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> rangeByScore(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 在分数范围内进行分页返回对象（包含value和分数）
     * 排序：分数从小到大
     *
     * @param key
     * @param min
     * @param max
     * @param ofset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key, double min, double max, Long ofset, Long count) {
        if (ofset == null || count == null) {
            return stringRedisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        }
        return stringRedisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, ofset, count);
    }

    /**
     * 在分数范围内进行分页返回对象（包含value和分数）
     * 排序：分数从大到小
     *
     * @param key
     * @param min
     * @param max
     * @param ofset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(String key, double min, double max, Long ofset, Long count) {
        if (ofset == null || count == null) {
            return stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        }
        return stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, ofset, count);
    }

    /**
     * 返回value 在key中的下标
     * 如果value不在key中则返回null
     *
     * @param key
     * @param value
     * @return
     */
    public Long rank(String key, String value) {
        return stringRedisTemplate.opsForZSet().rank(key, value);
    }


    /**
     * todo 这个东西搞不清楚 range的大于，小于这些是在和谁在比
     * ???????????
     *
     * @param key
     * @param range
     * @param limit
     * @return
     */
    public Set<String> rangeByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        return stringRedisTemplate.opsForZSet().rangeByLex(key, range, limit);
    }

}
