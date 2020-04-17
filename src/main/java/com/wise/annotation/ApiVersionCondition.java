package com.wise.annotation;

import com.wise.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * 版本控制
 *
 * @author lingyuwang
 * @date 2020-04-07 15:41
 * @since 1.0.9
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    /**
     * 版本号正则表达式
     *
     * @author lingyuwang
     * @date 2020-04-07 16:35
     * @since 1.0.9
     */
    private static final String VERSION_REGEX = "^(\\d{1,2})\\.(\\d{1,2})\\.(\\d{1,2})$";

    /**
     * 版本号分隔正则表达式
     *
     * @author lingyuwang
     * @date 2020-04-07 16:39
     * @since 1.0.9
     */
    private static final String VERSION_SPLIT_REGEX = "\\.";

    /**
     * Api 版本号
     *
     * @author lingyuwang
     * @date 2020-04-07 15:41
     * @since 1.0.9
     */
    private String apiVersion;

    public ApiVersionCondition(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * 将不同的筛选条件进行合并
     *
     * @param other
     * @return com.qiaoku.apiversion.ApiVersionCondition
     * @author lingyuwang
     * @date 2020-04-07 15:42
     * @since 1.0.9
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.getApiVersion());
    }

    /**
     * 版本比对，用于排序
     *
     * @param other
     * @param request
     * @return int
     * @author lingyuwang
     * @date 2020-04-07 15:42
     * @since 1.0.9
     */
    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新版本号
        return compareTo(other.getApiVersion(), this.apiVersion) ? 1: -1;
    }

    /**
     * 获取根据 request 的 header 版本号进行查找匹配的筛选条件
     *
     * @param request
     * @return com.qiaoku.apiversion.ApiVersionCondition
     * @author lingyuwang
     * @date 2020-04-07 15:45
     * @since 1.0.9
     */
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String version = request.getHeader(CommonConstant.VERSION_HEADER_NAME);
        if (StringUtils.isNotBlank(version)) {
            // 检查版本号是否合法
            if (!Pattern.matches(VERSION_REGEX, version)) {
                throw new IllegalArgumentException("请求头参数version的格式应该为x.y.z,其中x,y,z的范围都为0-99, 如:1.0.9");
            }

            if (compareTo(version, this.apiVersion)){
                return this;
            }
        }
        return null;
    }

    /**
     * 两版本号比较
     *
     * @param version1
     * @param version2
     * @return boolean
     * @author lingyuwang
     * @date 2020-04-07 16:57
     * @since 1.0.9
     */
    private boolean compareTo(String version1, String version2) {
        String[] split1 = version1.split(VERSION_SPLIT_REGEX);
        String[] split2 = version2.split(VERSION_SPLIT_REGEX);
        int len = split1.length;
        int versionSum1 = 0;
        int versionSum2 = 0;
        for (int i = 0; i < len; i++) {
            double pow = Math.pow(100, (len - 1 - i));
            versionSum1 += Integer.valueOf(split1[i]) * pow;
            versionSum2 += Integer.valueOf(split2[i]) * pow;
        }

        if (versionSum1 < versionSum2){
            return false;
        }

        return true;
    }

    /**
     * 获取注解的版本号
     *
     * @return java.lang.String
     * @author lingyuwang
     * @date 2020-04-07 16:57
     * @since 1.0.9
     */
    private String getApiVersion() {
        return apiVersion;
    }

}