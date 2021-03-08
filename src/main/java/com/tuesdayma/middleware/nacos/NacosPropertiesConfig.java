package com.tuesdayma.middleware.nacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: mzd
 * @Date: 2021/3/8 16:00
 * 作为服务端的配置中心,支持properties形式的配置文件
 */
@Component
@Data
@NacosPropertySource(dataId = "nacos_test", autoRefreshed = true)
public class NacosPropertiesConfig {
    /**
     * 只要nacos上的aa一发生变化，服务端的变量将立马发生改变
     */
    @NacosValue(value = "${aa}", autoRefreshed = true)
    private String aa;
}
