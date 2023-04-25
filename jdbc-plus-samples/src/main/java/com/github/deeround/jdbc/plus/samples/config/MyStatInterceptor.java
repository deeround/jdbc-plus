package com.github.deeround.jdbc.plus.samples.config;

import com.github.deeround.jdbc.plus.Interceptor.IInterceptor;
import com.github.deeround.jdbc.plus.method.MethodInvocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * SQL监控插件
 *
 * @author wanghao 913351190@qq.com
 * @create 2023/4/23 16:16
 */
@Slf4j
public class MyStatInterceptor implements IInterceptor {
    /**
     * 自定义插件是否支持
     */
    @Override
    public boolean supportMethod(final MethodInvocationInfo methodInfo) {
        return IInterceptor.super.supportMethod(methodInfo);
    }

    /**
     * SQL执行前方法（主要用于对SQL进行修改）
     */
    @Override
    public void beforePrepare(final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        log.info("执行SQL开始时间：{}", LocalDateTime.now());
        log.info("原始SQL：{}", Arrays.toString(methodInfo.getBatchSql()));
        log.info("调用方法名称：{}", methodInfo.getName());
        log.info("调用方法入参：{}", Arrays.toString(methodInfo.getArgs()));

        methodInfo.getUserAttributes().put("startTime", LocalDateTime.now());
    }

    /**
     * SQL执行完成后方法（主要用于对返回值修改）
     *
     * @param result 原始返回对象
     * @return 处理后的返回对象
     */
    @Override
    public Object beforeFinish(Object result, final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        log.info("执行SQL结束时间：{}", LocalDateTime.now());
        LocalDateTime startTime = (LocalDateTime) methodInfo.getUserAttributes().get("startTime");
        log.info("执行SQL耗时：{}毫秒", Duration.between(startTime, LocalDateTime.now()).toMillis());
        return result;
    }
}
