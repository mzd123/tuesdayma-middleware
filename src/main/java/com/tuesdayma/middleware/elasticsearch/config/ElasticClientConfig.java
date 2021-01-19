package com.tuesdayma.middleware.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: mzd
 * @Date: 2021/1/19 17:27
 */
@Component
public class ElasticClientConfig {

    @Bean("restHighLevelClient")
    public RestHighLevelClient getRestHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200)));
        return restHighLevelClient;
    }
}
