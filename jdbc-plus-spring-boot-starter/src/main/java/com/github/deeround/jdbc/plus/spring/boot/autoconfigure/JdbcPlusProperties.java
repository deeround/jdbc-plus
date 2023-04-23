package com.github.deeround.jdbc.plus.spring.boot.autoconfigure;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/2/23 17:29
 */
@ConfigurationProperties(prefix = JdbcPlusProperties.PREFIX)
@Slf4j
@Data
public class JdbcPlusProperties {

    public static final String PREFIX = "jdbc.plus";

}
