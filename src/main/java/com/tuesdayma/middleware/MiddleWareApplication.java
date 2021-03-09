package com.tuesdayma.middleware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: mzd
 * @Date: 2021/1/8 11:32
 */
@SpringBootApplication
@MapperScan("com.tuesdayma.middleware.sharding.dal.mapper")
public class MiddleWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleWareApplication.class, args);
    }

}
