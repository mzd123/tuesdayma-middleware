package com.tuesdayma.middleware.elasticsearch.client;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.elasticsearch.bean.Friend;
import com.tuesdayma.middleware.elasticsearch.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

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

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    public boolean existsIndex(String indexName) {
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("判断es索引是否存在失败：", e);
        }
        return false;
    }


    /**
     * 向es中插入数据
     *
     * @param id
     * @param indexName
     * @param object
     * @return
     */
    public String insert(String id, String indexName, Object object) {
        try {
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.id(id);
            indexRequest.source(JSON.toJSONString(object), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return JSON.toJSONString(indexResponse);
        } catch (IOException e) {
            log.error("向es中插入数据失败：", e);
            return null;
        }
    }


    public String insertBulk(String indexName, Map<String, Object> map) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            map.forEach((k, v) -> {
                IndexRequest indexRequest = new IndexRequest(indexName);
                indexRequest.id(k);
                indexRequest.source(JSON.toJSONString(v), XContentType.JSON);
                bulkRequest.add(indexRequest);
            });
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return JSON.toJSONString(bulkItemResponses);
        } catch (IOException e) {
            log.error("向es中批量插入数据失败：", e);
            return null;
        }
    }
}
