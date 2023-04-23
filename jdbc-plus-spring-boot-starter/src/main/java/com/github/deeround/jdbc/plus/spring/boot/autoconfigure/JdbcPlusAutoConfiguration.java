package com.github.deeround.jdbc.plus.spring.boot.autoconfigure;

import com.github.deeround.jdbc.plus.Interceptor.IInterceptor;
import com.github.deeround.jdbc.plus.aop.JdbcTemplateMethodAdvisor;
import com.github.deeround.jdbc.plus.aop.JdbcTemplateMethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/2/23 16:52
 */
@Configuration
@EnableConfigurationProperties(JdbcPlusProperties.class)
@ConditionalOnProperty(prefix = JdbcPlusProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class JdbcPlusAutoConfiguration {

    @Autowired
    private JdbcPlusProperties jdbcPlusProperties;

    @Bean
    public Advisor jdbcTemplateMethodAdvisor(List<IInterceptor> interceptors) {
        JdbcTemplateMethodInterceptor interceptor = new JdbcTemplateMethodInterceptor(interceptors);
        return new JdbcTemplateMethodAdvisor(interceptor);
    }

}
