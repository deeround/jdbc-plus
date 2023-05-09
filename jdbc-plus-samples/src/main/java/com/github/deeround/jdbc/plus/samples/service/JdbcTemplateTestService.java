package com.github.deeround.jdbc.plus.samples.service;

import com.github.deeround.jdbc.plus.Interceptor.pagination.PageHelper;
import com.github.deeround.jdbc.plus.Interceptor.pagination.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/3/1 9:06
 */
@Service
public class JdbcTemplateTestService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public PageInfo<Map<String, Object>> page1() {
        PageHelper.startPage(1, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");//最终执行SQL：select * from test_user LIMIT 0，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);//PageInfo对象包含了分页信息（总行数等）
        return page;
    }

    public PageInfo<Map<String, Object>> page2() {
        PageHelper.startPage(2, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");//最终执行SQL：select * from test_user LIMIT 2，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);//PageInfo对象包含了分页信息（总行数等）
        return page;
    }

    public PageInfo<Map<String, Object>> page3() {
        PageHelper.startPage(3, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");//最终执行SQL：select * from test_user LIMIT 4，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);//PageInfo对象包含了分页信息（总行数等）
        return page;
    }


    public void insert() {
        this.jdbcTemplate.update("insert into test_user(id,name) values('1','wangwu')");
        //最终执行SQL：insert into test_user(id,name,tenant_id) values('1','wangwu','test_tenant_1')
    }

    public void delete() {
        this.jdbcTemplate.update("delete from test_user where id='1'");
        //最终执行SQL：delete from test_user where id='1' and tenant_id='test_tenant_1'
    }

    public void update() {
        this.jdbcTemplate.update("update test_user set name='lisi' where id='1'");
        //最终执行SQL：update test_user set name='lisi' where id='1' and tenant_id='test_tenant_1'
    }

    public List<Map<String, Object>> query() {
        return this.jdbcTemplate.queryForList("select * from test_user where name=?", "3");
        //最终执行SQL：select * from test_user where tenant_id='test_tenant_1'
    }

    public void createTable() {
        String sql = "CREATE TABLE test_user\n" +
                "(\n" +
                "    name      varchar(100) DEFAULT NULL,\n" +
                "    tenant_id varchar(32)  DEFAULT NULL,\n" +
                "    id        varchar(32) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ")";
        this.jdbcTemplate.execute(sql);
    }

    public void batchUpdate() {
        this.jdbcTemplate.batchUpdate("insert into test_user(id,name) values('1','wangwu')", "insert into test_user(id,name) values('2','wangwu2')");
    }


}
