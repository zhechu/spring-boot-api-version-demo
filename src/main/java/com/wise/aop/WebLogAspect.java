package com.wise.aop;


import com.wise.annotation.ApiVersion;
import com.wise.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution( * com.wise.controller.*.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 版本号参数
        String versionParam = request.getHeader(CommonConstant.VERSION_HEADER_NAME);
        if (versionParam == null) {
            versionParam = StringUtils.EMPTY;
        }

        Signature signature = pjp.getSignature();

        // 获取匹配接口的版本号
        String version = "default";
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            // 优先从方法查找版本号
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            if (apiVersion != null) {
                version = apiVersion.value();
            }
            // 方法没有版本号注解，则查找类版本号
            else {
                Class clazz = methodSignature.getDeclaringType();
                apiVersion = AnnotationUtils.findAnnotation(clazz, ApiVersion.class);
                if (apiVersion != null) {
                    version = apiVersion.value();
                }
            }
        }

        long beginTime = System.currentTimeMillis();

        // 执行方法,ob为方法的返回值
        Object ob;

        try {
            ob = pjp.proceed();
        } finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;

            log.info("\n开始-------------------------------------------------------\n\t"
                            + "耗时: {}ms\n\t"
                            + "链路: {} {}\n\t"
                            + "请求版本: {}\n\t"
                            + "匹配版本: {}\n\t"
                            + "参数: {}\n-------------------------------------------------------结束",
                    time,
                    request.getRequestURL(),
                    signature.getDeclaringTypeName() + "." + signature.getName(),
                    versionParam,
                    version,
                    Arrays.toString(pjp.getArgs()));
        }

        return ob;
    }

}
