package com.imooc.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Greated by Guo
 *
 * @date2020/5/8 13:44
 */
@Component
@ConfigurationProperties(prefix = "loginterceptorconfig")
@Data
public class FilterInterceptorConfig {

        private String[] path;
}
