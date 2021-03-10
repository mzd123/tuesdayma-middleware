package com.tuesdayma.middleware.sharding.controller;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.sharding.service.ShardingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/3/9 15:36
 */
@RestController
public class ShardingController {
    @Resource
    private ShardingService shardingService;

    /**
     * 分表分库
     */
    @RequestMapping("/sharding/insert")
    public void insert() {
        shardingService.insert();
    }

    /**
     * 分表分库
     */
    @RequestMapping("/sharding/get")
    public String get() {
        return shardingService.get();
    }

    /**
     * 读写分离
     */
    @RequestMapping("/sharding/insert/goods")
    public void insertGoods() {
        shardingService.insertGoods();
    }

    /**
     * 读写分离
     */
    @RequestMapping("/sharding/get/goods")
    public String getGoods() {
        return shardingService.getGoods();
    }
}
