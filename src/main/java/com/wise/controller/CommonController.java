package com.wise.controller;

import com.wise.annotation.ApiVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器内兼容示例
 *
 * @author lingyuwang
 * @date 2020-04-17 21:59
 * @since 1.0.9
 */
@RestController
public class CommonController {

    @Value("${spring.profiles.active:dev}")
    private String env;

    /**
     * 获取服务环境
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-17 22:00
     * @since 1.0.9
     * @deprecated
     * @see CommonController#envV107
     */
    @GetMapping("/env")
    @Deprecated
    public String env() {
        return env;
    }

    /**
     * 获取服务环境
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-17 22:00
     * @since 1.0.9
     * @deprecated
     * @see CommonController#envV108
     */
    @GetMapping("/env")
    @ApiVersion(value = "1.0.7")
    @Deprecated
    public String envV107() {
        return String.format("%s_%s", env, "1.0.7");
    }

    /**
     * 获取服务环境
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-17 22:02
     * @since 1.0.9
     * @deprecated
     * @see CommonController#envV109
     */
    @GetMapping("/env")
    @ApiVersion(value = "1.0.8")
    @Deprecated
    public String envV108() {
        return String.format("%s_%s", env, "1.0.8");
    }

    /**
     * 获取服务环境
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-17 22:04
     * @since 1.0.9
     */
    @GetMapping("/env")
    @ApiVersion(value = "1.0.9")
    public String envV109() {
        return String.format("%s_%s", env, "1.0.9");
    }

}
