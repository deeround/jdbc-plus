package com.github.deeround.jdbc.plus.Interceptor;

import com.github.deeround.jdbc.plus.Interceptor.pagination.Dialect;
import com.github.deeround.jdbc.plus.Interceptor.pagination.Page;
import com.github.deeround.jdbc.plus.Interceptor.pagination.PageHelper;
import com.github.deeround.jdbc.plus.method.MethodActionInfo;
import com.github.deeround.jdbc.plus.method.MethodInvocationInfo;
import com.github.deeround.jdbc.plus.method.MethodType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/19 9:30
 */
public class PaginationInterceptor implements IInterceptor {

    @Override
    public boolean supportMethod(final MethodInvocationInfo methodInfo) {

        if (!methodInfo.isSupport()) {
            return false;
        }

        if (MethodType.QUERY.equals(methodInfo.getType())) {
            return true;
        }
        return false;
    }

    @Override
    public void beforePrepare(final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        Page<Object> localPage = PageHelper.getLocalPage();
        if (localPage == null) {
            return;
        }

        try {
            MethodActionInfo actionInfo = methodInfo.getActionInfo();
            Dialect dialect = PageHelper.getDialect(jdbcTemplate);
            String sql = actionInfo.getSql();

            //查询汇总
            if (localPage.isCount() && methodInfo.getActionInfo().isReturnIsList()) {

                if (actionInfo.isHasParameter()) {
                    if (actionInfo.isParameterIsPss()) {
                        Object cnt = jdbcTemplate.query(dialect.getCountSql(sql), (PreparedStatementSetter) methodInfo.getArgs()[actionInfo.getParameterIndex()], new ResultSetExtractor<Map>() {
                            @Override
                            public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
                                while (rs.next()) {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("PG_COUNT", rs.getLong("PG_COUNT"));
                                    return map;
                                }
                                return new HashMap<>();
                            }
                        }).get("PG_COUNT");
                        localPage.setTotal(Long.parseLong(cnt.toString()));
                    } else {
                        if (actionInfo.isHasParameterType()) {
                            Object cnt = jdbcTemplate.queryForMap(dialect.getCountSql(sql), actionInfo.getParameter(), actionInfo.getParameterType()).get("PG_COUNT");
                            localPage.setTotal(Long.parseLong(cnt.toString()));
                        } else {
                            Object cnt = jdbcTemplate.queryForMap(dialect.getCountSql(sql), actionInfo.getParameter()).get("PG_COUNT");
                            localPage.setTotal(Long.parseLong(cnt.toString()));
                        }
                    }
                } else {
                    Object cnt = jdbcTemplate.queryForMap(dialect.getCountSql(sql)).get("PG_COUNT");
                    localPage.setTotal(Long.parseLong(cnt.toString()));
                }
            }

            //生成分页SQL
            sql = dialect.getPageSql(sql, localPage.getPageNum(), localPage.getPageSize());

            methodInfo.resolveSql(sql);
        } catch (Exception e) {
            PageHelper.clearPage();
            throw e;
        }
    }

    @Override
    public Object beforeFinish(Object result, final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        Page<Object> localPage = PageHelper.getLocalPage();
        if (localPage == null) {
            return result;
        }

        try {
            if (methodInfo.getActionInfo().isReturnIsList()) {
                if (result != null) {
                    localPage.addAll((Collection<?>) result);
                }
                return localPage;
            } else {
                return result;
            }
        } finally {
            PageHelper.clearPage();
        }
    }
}
