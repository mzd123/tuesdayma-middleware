package com.tuesdayma.middleware.redis.set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
     * 如果删除成功，则返回1  删除步成功返回0
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
     * 将key1中的值，与keys中每个key的集合进行做并集，最后得到的交集结果赋值给key2
     * 如果key1不存在，则key2也将会被制为keys中所有key的并集
     * 如果keys中没有一个key，则等于key1的值全部赋值给key2
     *
     * @param key1
     * @param keys
     * @param key2
     * @return
     */
    public Long unionAndStore(String key1, Collection<String> keys, String key2) {
        return stringRedisTemplate.opsForSet().unionAndStore(key1, keys, key2);
    }

    /**
     * 查看value是不是key中的元素，如果是则返回true，如果不是则返回false
     * 如果key本身就是不存在的，则直接返回false
     * 类同与hash数据结构中的hasKey
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 随机返回key中得count个元素
     * 如果key不存在，则返回一个空的set
     * 如果count的数目大于key中总元素的个数，则返回key中的所有元素
     * count必须大于0，不然就会报错
     *
     * @param key
     * @param count
     * @return
     */
    public Set<String> distinctRandomMembers(String key, long count) {
        return stringRedisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * distinctRandomMembers  和  randomMembers 对于set来说好像是一样的，set中也允许出现重复的value
     *
     * @param key
     * @param count
     * @return
     */
    public List<String> randomMembers(String key, Long count) {
        List<String> result = new ArrayList<>();
        if (count != null && count > 0) {
            result = stringRedisTemplate.opsForSet().randomMembers(key, count);
        } else {
            String value = stringRedisTemplate.opsForSet().randomMember(key);
            result.add(value);
        }
        return result;
    }

    /**
     * 从key中随机取出count个元素
     * 这里要区别于distinctRandomMembers，distinctRandomMembers是返回count个元素，但是这些元素任然在key中
     * 而pop则是直接把元素取了出来，即key中将不再有这些元素
     *
     * @param key
     * @param count
     * @return
     */
    public List<String> pop(String key, Long count) {
        List<String> result = new ArrayList<>();
        if (count != null && count > 0) {
            result = stringRedisTemplate.opsForSet().pop(key, count);
        } else {
            String value = stringRedisTemplate.opsForSet().pop(key);
            result.add(value);
        }
        return result;
    }

    /**
     * 将key1中的value拿出来放到key2中，key1将失去value，key2将多一个value
     * 如果key1中value不存在，则key2不会得到任何东西
     * 如果key1不存在，则key2不会得到任何东西
     * 如果key2不存在，value在key1中，则会新建一个key2，并拿到value
     * 如果key2不存在，value也不在key1中，则不会新建一个key2
     *
     * @param key1
     * @param value
     * @param key2
     * @return
     */
    public Boolean move(String key1, String value, String key2) {
        return stringRedisTemplate.opsForSet().move(key1, value, key2);
    }


}
