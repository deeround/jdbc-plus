package com.github.deeround.jdbc.plus.samples;

import com.github.deeround.jdbc.plus.samples.service.JdbcTemplateTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/3/1 9:34
 */
@SpringBootTest
public class Tests {

    @Autowired
    JdbcTemplateTestService jdbcTemplateTestService;

    @Test
    void testPage1() {
        System.out.println(this.jdbcTemplateTestService.page1());
    }

}
