package com.tuesdayma.middleware.sharding.service;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.sharding.dal.entity.Goods;
import com.tuesdayma.middleware.sharding.dal.entity.MyOrder;
import com.tuesdayma.middleware.sharding.dal.entity.MyOrderCriteria;
import com.tuesdayma.middleware.sharding.dal.mapper.GoodsMapper;
import com.tuesdayma.middleware.sharding.dal.mapper.MyOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: mzd
 * @Date: 2021/3/9 15:37
 */
@Service
public class ShardingService {
    @Resource
    private MyOrderMapper myOrderMapper;
    @Resource
    private GoodsMapper goodsMapper;

    public void insert() {
        MyOrder order = new MyOrder();
        order.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setPrice((int) (Math.random() * 100));
        order.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        myOrderMapper.insert(order);
    }

    public String get() {
        MyOrderCriteria myOrderCriteria = new MyOrderCriteria();
        MyOrderCriteria.Criteria criteria = myOrderCriteria.createCriteria();
        criteria.andOrderNoEqualTo("3fbeb0181c5747aa8efc5066c9b06c5f");
        return JSON.toJSONString(myOrderMapper.selectByExample(myOrderCriteria));
    }

    public void insertGoods() {
        Goods goods = new Goods();
        goods.setGoodsId(UUID.randomUUID().toString().replaceAll("-", ""));
        goods.setGoodsName("测试");
        goodsMapper.insert(goods);
    }

    public String getGoods() {
        return JSON.toJSONString(goodsMapper.selectByPrimaryKey(1));
    }

}
