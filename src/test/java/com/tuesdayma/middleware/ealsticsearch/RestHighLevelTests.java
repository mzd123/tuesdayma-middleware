package com.tuesdayma.middleware.ealsticsearch;

import com.tuesdayma.middleware.elasticsearch.bean.Friend;
import com.tuesdayma.middleware.elasticsearch.bean.Person;
import com.tuesdayma.middleware.elasticsearch.client.RestHighLevelService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

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
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            Person person = new Person();
            person.setAddr(getString(5));
            person.setAge((int) (Math.random() * 80));
            person.setName(getString(2));
            List<Friend> list = new ArrayList<>();
            Friend friend = new Friend();
            friend.setName(getString(2));
            friend.setAge((int) (Math.random() * 80));
            list.add(friend);
            Friend friend1 = new Friend();
            friend1.setName(getString(2));
            friend1.setAge((int) (Math.random() * 80));
            list.add(friend1);
            person.setFriends(list);
            map.put(UUID.randomUUID().toString().replaceAll("-",""), person);
        }
        log.info(restHighLevelService.insertBulk("person", map));
    }
    public String getString(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
        }
        return result;
    }



    @Test
    public void search() {
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(new MatchQueryBuilder("name","职贬"));
        log.info(restHighLevelService.search("person",boolQueryBuilder));
    }


}
