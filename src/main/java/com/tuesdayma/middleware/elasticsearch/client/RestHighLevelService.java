package com.tuesdayma.middleware.elasticsearch.client;

import com.alibaba.fastjson.JSON;
import com.tuesdayma.middleware.elasticsearch.bean.Friend;
import com.tuesdayma.middleware.elasticsearch.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
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
     * 如果索引indexName已存在，则会报错
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
     * 删除索引
     * indexName这个索引必须存在不然就会报错
     *
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName) {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            log.error("删除索引失败：", e);
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

    /**
     * 批量插入es
     *
     * @param indexName
     * @param map
     * @return
     */
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

    /**
     * 删除es中指定id的数据,该删除为物理删除
     *
     * @param indexName
     * @param id
     * @return
     */
    public boolean delete(String indexName, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest();
            deleteRequest.index(indexName);
            deleteRequest.id(id);
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            // 无论是update还是delete，es都有1s的延迟时间，只能说是准实时
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            SearchRequest request = new SearchRequest();
//            request.indices(indexName);
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//            boolQueryBuilder.must(new TermQueryBuilder("_id", id));
//            searchSourceBuilder.query(boolQueryBuilder);
//            request.source(searchSourceBuilder);
//            SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            log.info("查询结果为：{}" , JSON.toJSONString(searchResponse));
        } catch (IOException e) {
            log.error("删除es中的数据失败：", e);
            return false;
        }
        return true;
    }

    /**
     * 更新指定id的数据
     *
     * @param indexName
     * @param id
     * @param hashMap
     * @return
     */
    public boolean updateById(String indexName, String id, HashMap<String, Object> hashMap) {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(indexName);
            updateRequest.id(id);
            updateRequest.doc(hashMap);
            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            // 无论是update还是delete，es都有1s的延迟时间，只能说是准实时
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            SearchRequest request = new SearchRequest();
//            request.indices(indexName);
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//            boolQueryBuilder.must(new TermQueryBuilder("_id", id));
//            searchSourceBuilder.query(boolQueryBuilder);
//            request.source(searchSourceBuilder);
//            SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            log.info("查询结果为：{}", JSON.toJSONString(searchResponse));
        } catch (IOException e) {
            log.error("修改es中的数据失败：", e);
            return false;
        }
        return true;
    }

    /**
     * 搜索
     *
     * @param indexName
     * @param boolQueryBuilder
     * @return
     */
    public String search(String indexName, BoolQueryBuilder boolQueryBuilder) {
        try {
            SearchRequest request = new SearchRequest();
            request.indices(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(boolQueryBuilder);
            request.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            return JSON.toJSONString(searchResponse);
        } catch (Exception e) {
            log.error("查询es失败：", e);
            return null;
        }
    }
}
