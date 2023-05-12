package com.github.deeround.jdbc.plus.samples;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.deeround.jdbc.plus.Interceptor.pagination.PageInfo;
import com.github.deeround.jdbc.plus.samples.domain.TestUser;
import com.github.deeround.jdbc.plus.samples.service.JdbcTemplateTestService;
import com.github.deeround.jdbc.plus.samples.service.TestUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/3/1 9:34
 */
@Slf4j
@SpringBootTest
public class Tests {

    /**
     * jdbc-plus 测试service
     */
    @Autowired
    private JdbcTemplateTestService jdbcTemplateTestService;

    /**
     * mybatis-plus 测试service
     */
    @Autowired
    private TestUserService testUserService;

    @Test
    void testPage123() {
        PageInfo<Map<String, Object>> page1 = this.jdbcTemplateTestService.page1();
        PageInfo<Map<String, Object>> page2 = this.jdbcTemplateTestService.page2();
        PageInfo<Map<String, Object>> page3 = this.jdbcTemplateTestService.page3();
        System.out.println(page1);
        System.out.println(page2);
        System.out.println(page3);
    }

    /**
     * 分页查询：jdbc-plus和mybatis-plus查询使用对比
     */
    @Test
    void testPage() {
        PageInfo<Map<String, Object>> page1 = this.jdbcTemplateTestService.page1();
        Page<TestUser> page2 = this.testUserService.page(new Page<TestUser>(1, 2));
        log.info("total:{},records:{},page1:{}", page1.getTotal(), page1.getList().size(), page1.getList());
        log.info("total:{},records:{},page2:{}", page2.getTotal(), page2.getRecords().size(), page2.getRecords());
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

    /**
     * 普通查询：jdbc-plus和mybatis-plus查询使用对比
     */
    @Test
    void testTenantQuery() {
        List<Map<String, Object>> list1 = this.jdbcTemplateTestService.query();
        List<TestUser> list2 = this.testUserService.list();
        System.out.println(list1);
        System.out.println(list2);
    }

    @Test
    void testCreateTable() {
        this.jdbcTemplateTestService.createTable();
    }

    @Test
    void testBatchUpdate() {
        this.jdbcTemplateTestService.batchUpdate();
    }


    /**
     * 条件查询：jdbc-plus和mybatis-plus查询使用对比
     * xml中编写条件查询语句
     */
    @Test
    void testQueryByCondition() {
        List<Map<String, Object>> list1 = this.jdbcTemplateTestService.getListByCondition("3");
        List<TestUser> list2 = this.testUserService.getListByCondition("3");
        System.out.println(list1);
        System.out.println(list2);
    }

}
