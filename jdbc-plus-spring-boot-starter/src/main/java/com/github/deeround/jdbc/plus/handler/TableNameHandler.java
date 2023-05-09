package com.github.deeround.jdbc.plus.handler;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/26 10:10
 */
public interface TableNameHandler {

    /**
     * 生成动态表名
     *
     * @param sql       当前执行 SQL
     * @param tableName 表名
     * @return String
     */
    String dynamicTableName(String sql, String tableName);
}