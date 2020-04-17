package com.wise.config;

import com.wise.annotation.ApiVersionRequestMappingHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * MVC 配置
 *
 * @author lingyuwang
 * @date 2020-04-07 15:54
 * @since 1.0.9
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    /**
     * 注册请求的版本请求方法
     *
     * @param
     * @return org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
     * @author lingyuwang
     * @date 2020-04-07 15:54
     * @since 1.0.9
     */
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }

}