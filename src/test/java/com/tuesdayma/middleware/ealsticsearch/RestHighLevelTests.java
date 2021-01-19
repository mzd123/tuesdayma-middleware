package com.tuesdayma.middleware.ealsticsearch;

import com.tuesdayma.middleware.elasticsearch.client.RestHighLevelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/1/19 18:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestHighLevelTests {

    @Resource
    private RestHighLevelService restHighLevelService;

    @Test
    public void initIndex() {
        String mapping = "{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"name\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        restHighLevelService.initIndex("test_index", mapping);
    }
}
