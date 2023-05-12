package com.github.deeround.jdbc.plus.samples.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghao 913351190@qq.com
 * @create 2022/6/6 14:09
 */
@Configuration
@MapperScan(value = {"com.github.deeround.jdbc.plus.samples.mapper"})
public class MybatisPlusConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * 当前租户ID
             */
            @Override
            public Expression getTenantId() {
                String currentTenantId = "test_tenant_1";//可以从请求上下文中获取（cookie、session、header等）
                return new StringValue(currentTenantId);
            }

            /**
             * 租户字段名
             */
            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            /**
             * 根据表名判断是否忽略拼接多租户条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return false;
            }
        }));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
