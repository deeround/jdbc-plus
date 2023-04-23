package com.github.deeround.jdbc.plus.samples.config;

import com.github.deeround.jdbc.plus.Interceptor.IInterceptor;
import com.github.deeround.jdbc.plus.Interceptor.PaginationInterceptor;
import com.github.deeround.jdbc.plus.Interceptor.TenantLineInterceptor;
import com.github.deeround.jdbc.plus.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/18 16:41
 */
@Configuration
public class JdbcPlusConfig {

    /**
     * PaginationInterceptor是内置的分页插件（分页插件一定要注入在TenantLineHandler之后，可以通过Order来控制顺序）
     */
    @Bean
    @Order(9)
    public IInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * TenantLineHandler是内置的多租户插件插件
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
        });
    }

    /**
     * 自定义插件注入，注入位置按实际情况
     */
    @Bean
    @Order(0)
    public IInterceptor myStatInterceptor() {
        return new MyStatInterceptor();
    }
}
