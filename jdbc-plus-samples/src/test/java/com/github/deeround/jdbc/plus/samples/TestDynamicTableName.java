package com.github.deeround.jdbc.plus.samples;

import com.github.deeround.jdbc.plus.samples.domain.TestLog;
import com.github.deeround.jdbc.plus.samples.service.JdbcTemplateTestService;
import com.github.deeround.jdbc.plus.samples.service.TestLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * 动态表名测试类
 *
 * @author wanghao 913351190@qq.com
 * @create 2023/3/1 9:34
 */
@Slf4j
@SpringBootTest
public class TestDynamicTableName {

    /**
     * jdbc-plus 测试service
     */
    @Autowired
    private JdbcTemplateTestService jdbcTemplateTestService;

    /**
     * mybatis-plus 测试service
     */
    @Autowired
    private TestLogService testLogService;

    //======================jdbc-plus和mybatis-plus使用对比==============================

    /**
     * 动态表名查询：jdbc-plus和mybatis-plus查询使用对比
     */
    @Test
    void testQueryWithMp() {
        List<Map<String, Object>> list1 = this.jdbcTemplateTestService.getTestLogList();
        List<TestLog> list2 = this.testLogService.list();
        System.out.println(list1);
        System.out.println(list2);
    }

    //======================jdbc-plus和mybatis-plus使用对比==============================

}
