package com.wise.controller;

import com.wise.annotation.ApiVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1.0.6 版本控制器（最新版）
 *
 * @author lingyuwang
 * @date 2020-04-17 22:10
 * @since 1.0.9
 */
@RestController
@RequestMapping("/common")
@ApiVersion(value = "1.0.6")
public class CommonControllerV106 {

    @Value("${spring.profiles.active:dev}")
    private String env;

    /**
     * 获取服务环境
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-17 22:09
     * @since 1.0.9
     */
    @GetMapping("/env")
    public String env() {
        return String.format("%s_%s", env, "1.0.6");
    }

}
