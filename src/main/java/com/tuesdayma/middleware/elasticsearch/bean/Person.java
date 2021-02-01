package com.tuesdayma.middleware.elasticsearch.bean;

import lombok.Data;

import java.util.List;

/**
 * @author: mzd
 * @date: 2021-02-01 22:10
 **/
@Data
public class Person {
    private String name;
    private Integer age;
    private String addr;
    private List<Friend> friends;
}
