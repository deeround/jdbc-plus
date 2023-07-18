package com.github.deeround.jdbc.plus.samples.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/7/18 9:19
 */
@SpringBootTest
public class PageServiceTest {

    @Autowired
    TestAllService testAllService;

    @Test
    void test() {
        this.testAllService.BATCHUPDATE_SQL_PSS();
        this.testAllService.BATCHUPDATE_SQL_BATCHARGS();
        this.testAllService.BATCHUPDATE_SQL_BATCHARGS_ARGTYPES();
        this.testAllService.BATCHUPDATE_SQL();
        this.testAllService.UPDATE_SQL();
        this.testAllService.UPDATE_SQL_ARGS();
        this.testAllService.UPDATE_SQL_ARGS();
        this.testAllService.UPDATE_SQL_PSS();
        this.testAllService.QUERY_SQL_ARGS_ARGTYPES_RSE();
        this.testAllService.QUERY_SQL_ARGS_ARGTYPES_RCH();
        this.testAllService.QUERY_SQL_ARGS_ARGTYPES_ROWMAPPER();
        this.testAllService.QUERY_SQL_ARGS_RSE();
        this.testAllService.QUERY_SQL_ARGS_RCH();
        this.testAllService.QUERY_SQL_ARGS_ROWMAPPER();
        this.testAllService.QUERY_SQL_PSS_RSE();
        this.testAllService.QUERY_SQL_PSS_RCH();
        this.testAllService.QUERY_SQL_PSS_ROWMAPPER();
        this.testAllService.QUERY_SQL_RSE();
        this.testAllService.QUERY_SQL_RSE_ARGS();
        this.testAllService.QUERY_SQL_RCH();
        this.testAllService.QUERY_SQL_RCH_ARGS();
        this.testAllService.QUERY_SQL_ROWMAPPER();
        this.testAllService.QUERY_SQL_ROWMAPPER_ARGS();
        this.testAllService.QUERYFORLIST_SQL();
        this.testAllService.QUERYFORSTREAM_SQL_ROWMAPPER_ARGS();
    }

}
