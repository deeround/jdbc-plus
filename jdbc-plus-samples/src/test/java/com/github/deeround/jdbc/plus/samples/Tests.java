package com.github.deeround.jdbc.plus.samples;

import com.github.deeround.jdbc.plus.Interceptor.pagination.PageInfo;
import com.github.deeround.jdbc.plus.samples.service.JdbcTemplateTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/3/1 9:34
 */
@SpringBootTest
public class Tests {

    @Autowired
    JdbcTemplateTestService jdbcTemplateTestService;

    @Test
    void testPage() {
        PageInfo<Map<String, Object>> page1 = this.jdbcTemplateTestService.page1();
        PageInfo<Map<String, Object>> page2 = this.jdbcTemplateTestService.page2();
        PageInfo<Map<String, Object>> page3 = this.jdbcTemplateTestService.page3();
        System.out.println(page1);
        System.out.println(page2);
        System.out.println(page3);
    }

    @Test
    void testTenantInsert() {
        this.jdbcTemplateTestService.insert();
    }

    @Test
    void testTenantUpdate() {
        this.jdbcTemplateTestService.update();
    }

    @Test
    void testTenantDelete() {
        this.jdbcTemplateTestService.delete();
    }

    @Test
    void testTenantQuery() {
        this.jdbcTemplateTestService.query();
    }

}
