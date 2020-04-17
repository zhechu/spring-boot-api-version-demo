package com.wise.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认版本控制器
 *
 * @author lingyuwang
 * @date 2020-04-17 22:07
 * @since 1.0.9
 */
@RestController
@RequestMapping("/common")
@Deprecated
public class CommonControllerVdefault {

    @Value("${spring.profiles.active:dev}")
    private String env;

    /**
     * 获取服务环境
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-17 22:07
     * @since 1.0.9
     */
    @GetMapping("/env")
    public String env() {
        return String.format("%s_%s", env, "default");
    }

}
