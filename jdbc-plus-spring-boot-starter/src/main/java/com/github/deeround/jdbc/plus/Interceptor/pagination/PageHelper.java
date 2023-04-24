/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.deeround.jdbc.plus.Interceptor.pagination;

import com.github.deeround.jdbc.plus.Interceptor.pagination.dialect.MySqlDialect;
import com.github.deeround.jdbc.plus.Interceptor.pagination.dialect.OracleDialect;
import com.github.deeround.jdbc.plus.Interceptor.pagination.dialect.PostgreSqlDialect;
import com.github.deeround.jdbc.plus.Interceptor.pagination.dialect.SqlServerDialect;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基础分页方法
 *
 * @author liuzh
 */
public abstract class PageHelper {
    private static final ThreadLocal<Page> LOCAL_PAGE = ThreadLocal.withInitial(() -> null);
    private static final boolean DEFAULT_COUNT = true;
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * 设置 Page 参数
     *
     * @param page
     */
    public static void setLocalPage(Page page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 获取 Page 参数
     *
     * @return
     */
    public static <T> Page<T> getLocalPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearPage() {
        LOCAL_PAGE.remove();
    }

    /**
     * 开始分页
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize) {
        return startPage(pageNum, pageSize, DEFAULT_COUNT);
    }

    /**
     * 开始分页
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @param count    是否进行count查询
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count) {
        return startPage(pageNum, pageSize, count, null, null);
    }


    /**
     * 开始分页
     *
     * @param pageNum      页码
     * @param pageSize     每页显示数量
     * @param count        是否进行count查询
     * @param reasonable   分页合理化,null时用默认配置
     * @param pageSizeZero true且pageSize=0时返回全部结果，false时分页,null时用默认配置
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count, Boolean reasonable, Boolean pageSizeZero) {
        Page<E> page = new Page<E>(pageNum, pageSize, count, reasonable, pageSizeZero);
        setLocalPage(page);
        return page;
    }


    /**
     * key:数据库别名，value：数据库方言实现类class
     */
    private static final Map<String, Class> DIALECT_MAP = new HashMap<String, Class>();
    /**
     * key:数据库别名，value:数据库方言实现类实例
     */
    private static final Map<String, Dialect> DIALECT_INSTANCE_MAP = new HashMap<String, Dialect>();

    static {

        registerDialectAlias("hsqldb", PostgreSqlDialect.class);
        registerDialectAlias("h2", PostgreSqlDialect.class);
        registerDialectAlias("postgresql", PostgreSqlDialect.class);

        registerDialectAlias("mysql", MySqlDialect.class);
        registerDialectAlias("mariadb", MySqlDialect.class);
        registerDialectAlias("sqlite", MySqlDialect.class);

        registerDialectAlias("oracle", OracleDialect.class);

        registerDialectAlias("sqlserver", SqlServerDialect.class);
        registerDialectAlias("sqlserver2012", SqlServerDialect.class);
    }

    public static void registerDialectAlias(String alias, Class clazz) {
        DIALECT_MAP.put(alias, clazz);
    }

    public static Dialect getDialect(JdbcTemplate jdbcTemplate) {
        String dialectName = getDialectName(getUrl(getDataSource(jdbcTemplate)));
        if (dialectName != null) {
            if (DIALECT_INSTANCE_MAP.containsKey(dialectName)) {
                return DIALECT_INSTANCE_MAP.get(dialectName);
            }
        }
        if (dialectName != null && DIALECT_MAP.containsKey(dialectName)) {
            lock.lock();
            try {
                Class dialectClass = null;
                Dialect dialect = null;
                try {
                    dialectClass = DIALECT_MAP.get(dialectName);
                    if (Dialect.class.isAssignableFrom(dialectClass)) {
                        dialect = (Dialect) dialectClass.newInstance();
                        DIALECT_INSTANCE_MAP.put(dialectName, dialect);
                        return dialect;
                    } else {
                        throw new RuntimeException("Dialect class must be implemented " + Dialect.class.getName() + " interface");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Initialize dialect class [" + dialectClass + "] error : " + e.getMessage(), e);
                }
            } finally {
                lock.unlock();
            }
        } else {
            throw new RuntimeException("Unsupported database : " + dialectName);
        }
    }

    private static DataSource getDataSource(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.getDataSource();
    }

    private static String getUrl(DataSource dataSource) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return conn.getMetaData().getURL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    private static String getDialectName(String url) {
        url = url.toLowerCase();
        for (String dialect : DIALECT_MAP.keySet()) {
            if (url.contains(":" + dialect + ":")) {
                return dialect;
            }
        }
        return null;
    }

}
