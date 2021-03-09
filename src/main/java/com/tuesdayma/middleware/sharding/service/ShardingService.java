package com.tuesdayma.middleware.sharding.service;

import com.tuesdayma.middleware.sharding.dal.entity.MyOrder;
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

    public void insert() {
        MyOrder order = new MyOrder();
        order.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setPrice((int) (Math.random() * 100));
        order.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        myOrderMapper.insert(order);
    }
}
