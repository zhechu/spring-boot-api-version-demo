package com.wise.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Api 版本号
 *
 * @author lingyuwang
 * @date 2020-04-17 21:27
 * @since 1.0.9
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    /**
     * 标识版本号，如:1.0.0
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-07 15:38
     * @since 1.0.9
     */
    String value();

}