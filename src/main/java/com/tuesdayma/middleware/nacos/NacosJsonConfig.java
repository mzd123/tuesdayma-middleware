package com.tuesdayma.middleware.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.tuesdayma.middleware.nacos.bean.NacosJsonBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: mzd
 * @Date: 2021/3/8 16:00
 * 作为服务端的配置中心,支持json形式的配置文件
 * 这种形式的配置文件注入，需要注意 项目启动的拉取 和 远程配置更改的时候本地的更新
 */
@Component
@Data
@Slf4j
@NacosPropertySource(dataId = "nacos_json", autoRefreshed = true)
public class NacosJsonConfig {
    @NacosInjected
    private ConfigService configService;

    private NacosJsonBean nacosJsonBean;

    public NacosJsonBean getNacosJsonBean() {
        return nacosJsonBean;
    }

    /**
     * 该对象被创建的时候执行，这样就可以减少项目启动初始化拉取远程的配置文件
     */
    @PostConstruct
    public void init() {
        try {
            String string = configService.getConfig("nacos_json", "DEFAULT_GROUP", 3000);
            log.info("配置文件初始化:{}", string);
            nacosJsonBean = JSON.parseObject(string, NacosJsonBean.class);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 远程配置文件发生了更改
     *
     * @param conf
     */
    @NacosConfigListener(dataId = "nacos_json", timeout = 3000)
    public void onChange(String conf) {
        log.info("配置文件发生改变:{}", conf);
        nacosJsonBean = JSON.parseObject(conf, NacosJsonBean.class);
    }

}
