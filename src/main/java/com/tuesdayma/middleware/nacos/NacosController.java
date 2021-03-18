package com.tuesdayma.middleware.nacos;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: mzd
 * @Date: 2021/3/8 16:37
 */
@Profile(value = "nacos")
@RestController
public class NacosController {
    @Resource
    private NacosPropertiesConfig nacosPropertiesConfig;

    @Resource
    private NacosJsonConfig nacosJsonConfig;

    @Resource
    private NacosHtmlConfig nacosHtmlConfig;

    @RequestMapping("/get/config/properties")
    public String getConfigProperties() {
        return nacosPropertiesConfig.getAa();
    }

    @RequestMapping("/get/config/json")
    public String getConfigJson() {
        return JSON.toJSONString(nacosJsonConfig.getNacosJsonBean());
    }

    @RequestMapping("/get/config/html")
    public String getConfigHtml() {
        return nacosHtmlConfig.getIndex();
    }
}
