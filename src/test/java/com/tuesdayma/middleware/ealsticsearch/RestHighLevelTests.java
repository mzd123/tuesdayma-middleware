package com.tuesdayma.middleware.ealsticsearch;

import com.tuesdayma.middleware.elasticsearch.bean.Friend;
import com.tuesdayma.middleware.elasticsearch.bean.Person;
import com.tuesdayma.middleware.elasticsearch.client.RestHighLevelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: mzd
 * @Date: 2021/1/19 18:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RestHighLevelTests {

    @Resource
    private RestHighLevelService restHighLevelService;

    @Test
    public void initIndex() {
        String mapping = "{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"name\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"age\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"addr\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"classCode\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"friends\": {\n" +
                "\t\t\t\"properties\": {\n" +
                "\t\t\t\t\"name\": {\n" +
                "\t\t\t\t\t\"type\": \"text\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"age\": {\n" +
                "\t\t\t\t\t\"type\": \"integer\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\n" +
                "\t}\n" +
                "}";
        restHighLevelService.initIndex("person", mapping);
    }

    @Test
    public void existsIndex() {
        log.info(String.valueOf(restHighLevelService.existsIndex("test_index1")));
    }

    @Test
    public void insert() {
        Person person = new Person();
        person.setAddr("测试地址");
        person.setAge(20);
        person.setName("小明");
        List<Friend> list = new ArrayList<>();
        Friend friend = new Friend();
        friend.setName("sfasd");
        friend.setAge(1);
        list.add(friend);
        Friend friend1 = new Friend();
        friend1.setName("gsdgfg");
        friend1.setAge(2);
        list.add(friend1);
        person.setFriends(list);
        log.info(restHighLevelService.insert("111", "person", person));
    }


    @Test
    public void insertBulk() {
        Person person1 = new Person();
        person1.setAddr("测试地址");
        person1.setAge(21);
        person1.setName("小百");
        List<Friend> list1 = new ArrayList<>();
        Friend friend2 = new Friend();
        friend2.setName("dsfjsdlfjas");
        friend2.setAge(1);
        list1.add(friend2);
        Friend friend3 = new Friend();
        friend3.setName("sdfnjldskflsk");
        friend3.setAge(2);
        list1.add(friend3);
        person1.setFriends(list1);

        Person person = new Person();
        person.setAddr("测试地址");
        person.setAge(19);
        person.setName("小黄");
        List<Friend> list = new ArrayList<>();
        Friend friend = new Friend();
        friend.setName("电风扇");
        friend.setAge(1);
        list.add(friend);
        Friend friend1 = new Friend();
        friend1.setName("fdsfjl");
        friend1.setAge(2);
        list.add(friend1);
        person.setFriends(list);

        Map<String,Object> map=new HashMap<>();
        map.put("456",person);
        map.put("789",person1);

        log.info(restHighLevelService.insertBulk( "person", map));
    }
}
