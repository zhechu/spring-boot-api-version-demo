package com.wise.annotation;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Api 版本匹配处理器
 *
 * @author lingyuwang
 * @date 2020-04-17 21:27
 * @since 1.0.9
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    /**
     * 根据版本号创建条件
     *
     * @param apiVersion
     * @return org.springframework.web.servlet.mvc.condition.RequestCondition<com.wise.annotation.ApiVersionCondition>
     * @author lingyuwang
     * @date 2020-04-17 21:27
     * @since 1.0.9
     */
    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
    }

}