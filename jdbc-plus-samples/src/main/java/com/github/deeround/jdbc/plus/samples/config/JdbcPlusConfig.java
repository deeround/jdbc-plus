package com.github.deeround.jdbc.plus.samples.config;

import com.github.deeround.jdbc.plus.Interceptor.DynamicTableNameInterceptor;
import com.github.deeround.jdbc.plus.Interceptor.IInterceptor;
import com.github.deeround.jdbc.plus.Interceptor.PaginationInterceptor;
import com.github.deeround.jdbc.plus.Interceptor.TenantLineInterceptor;
import com.github.deeround.jdbc.plus.handler.TableNameHandler;
import com.github.deeround.jdbc.plus.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/18 16:41
 */
@Configuration
public class JdbcPlusConfig {

    /**
     * TenantLineInterceptor是内置的多租户插件
     */
    @Bean
    @Order(1)
    public IInterceptor tenantLineInterceptor() {
        return new TenantLineInterceptor(new TenantLineHandler() {
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
                return TenantLineHandler.super.ignoreTable(tableName);
            }
        });
    }

    /**
     * DynamicTableNameInterceptor是内置的动态表名插件
     */
    @Bean
    @Order(2)
    public IInterceptor dynamicTableNameInterceptor() {
        return new DynamicTableNameInterceptor(new TableNameHandler() {
            @Override
            public String dynamicTableName(String sql, String tableName) {
                if ("test_log".equals(tableName)) {
                    return tableName + "_" + LocalDateTime.now().getYear();
                }
                return tableName;
            }
        });
    }

    /**
     * PaginationInterceptor是内置的分页插件（分页插件一般情况放置最后）
     */
    @Bean
    @Order(9)
    public IInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * MyStatInterceptor是自定义扩展的SQL监控插件（注入位置按实际情况）
     */
    @Bean
    @Order(0)
    public IInterceptor myStatInterceptor() {
        return new MyStatInterceptor();
    }
}
