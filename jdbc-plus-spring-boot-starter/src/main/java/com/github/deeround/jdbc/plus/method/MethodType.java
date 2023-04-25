package com.github.deeround.jdbc.plus.method;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/20 16:47
 */
public enum MethodType {
    /**
     * 未知执行方法
     */
    UNKNOWN,
    /**
     * 新增、修改、删除 SQL语句
     */
    UPDATE,
    /**
     * 查询 SQL语句
     */
    QUERY,
    /**
     * DDL、存储过程 SQL语句
     */
    EXECUTE
}
