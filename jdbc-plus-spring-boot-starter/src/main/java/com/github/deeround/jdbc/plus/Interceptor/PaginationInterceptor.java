package com.github.deeround.jdbc.plus.Interceptor;

import com.github.deeround.jdbc.plus.Interceptor.pagination.Dialect;
import com.github.deeround.jdbc.plus.Interceptor.pagination.Page;
import com.github.deeround.jdbc.plus.Interceptor.pagination.PageHelper;
import com.github.deeround.jdbc.plus.method.MethodInvocationInfo;
import com.github.deeround.jdbc.plus.method.MethodType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/19 9:30
 */
public class PaginationInterceptor implements IInterceptor {

    @Override
    public boolean supportMethod(final MethodInvocationInfo methodInfo) {
        if (methodInfo.isFirstParameterIsSql() && MethodType.QUERY.equals(methodInfo.getType()) && methodInfo.isReturnIsList()) {
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
            Dialect dialect = PageHelper.getDialect(jdbcTemplate);
            String sql = methodInfo.getSql();

            //查询汇总
            if (localPage.isCount()) {
                Object cnt = jdbcTemplate.queryForMap(dialect.getCountSql(sql)).get("PG_COUNT");
                localPage.setTotal(Long.parseLong(cnt.toString()));
            }

            //生成分页SQL
            sql = dialect.getPageSql(sql, localPage.getPageNum(), localPage.getPageSize());

            methodInfo.setSql(sql);
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
            if (result != null) {
                localPage.addAll((Collection<?>) result);
            }
            return localPage;
        } finally {
            PageHelper.clearPage();
        }
    }
}
