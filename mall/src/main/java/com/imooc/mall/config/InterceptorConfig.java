package com.imooc.mall.config;

import com.imooc.mall.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Greated by Guo
 *
 * @date2020/5/8 12:47
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private FilterInterceptorConfig filterInterceptorConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(filterInterceptorConfig.getPath());
    }
}
