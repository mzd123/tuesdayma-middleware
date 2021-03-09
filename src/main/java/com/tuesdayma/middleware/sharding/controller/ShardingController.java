package com.tuesdayma.middleware.sharding.controller;

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

    @RequestMapping("/sharding/insert")
    public void insert() {
        shardingService.insert();
    }
}
