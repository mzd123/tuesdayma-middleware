package com.tuesdayma.middleware.elasticsearch.client;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: mzd
 * @Date: 2021/1/19 17:42
 */
@Service
@Slf4j
public class RestHighLevelService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 初始化索引
     *
     * @param indexName 索引名称  名称不能有大写，不然会报错
     * @param mapping   索引结构  传入的mapping必须是JSON 格式的
     */
    public void initIndex(String indexName, String mapping) {
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            createIndexRequest.settings(Settings.builder().put("index.number_of_shards", 4)
                    .put("index.number_of_replicas", 1));
            createIndexRequest.mapping(mapping, XContentType.JSON);
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es索引初始化失败：", e);
        }
    }
}
