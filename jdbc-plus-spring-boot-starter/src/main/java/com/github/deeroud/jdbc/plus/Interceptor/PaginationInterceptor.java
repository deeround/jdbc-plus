package com.github.deeroud.jdbc.plus.Interceptor;

import com.github.deeroud.jdbc.plus.Interceptor.pagination.Dialect;
import com.github.deeroud.jdbc.plus.Interceptor.pagination.Page;
import com.github.deeroud.jdbc.plus.Interceptor.pagination.PageHelper;
import com.github.deeroud.jdbc.plus.method.MethodInfo;
import com.github.deeroud.jdbc.plus.method.MethodType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/19 9:30
 */
public class PaginationInterceptor implements IInterceptor {

    @Override
    public boolean supportMethod(MethodInfo methodInfo) {
        if (methodInfo.isFirstParameterIsString() && MethodType.QUERY.equals(methodInfo.getType()) && methodInfo.isReturnIsList()) {
            return true;
        }
        return false;
    }

    @Override
    public void beforePrepare(final Object[] args, MethodInfo methodInfo, JdbcTemplate jdbcTemplate) {
        try {
            Dialect dialect = PageHelper.getDialect(jdbcTemplate);
            Page<Object> localPage = PageHelper.getLocalPage();
            String sql = args[0].toString();

            //查询汇总
            if (localPage.isCount()) {
                Object cnt = jdbcTemplate.queryForMap(dialect.getCountSql(sql)).get("PG_COUNT");
                localPage.setTotal(Long.parseLong(cnt.toString()));
            }

            //生成分页SQL
            sql = dialect.getPageSql(sql, localPage.getPageNum(), localPage.getPageSize());

            args[0] = sql;
        } catch (Exception e) {
            PageHelper.clearPage();
            throw e;
        }
    }

    @Override
    public Object beforeFinish(Object result, final Object[] args, MethodInfo methodInfo, JdbcTemplate jdbcTemplate) {
        try {
            Page<Object> localPage = PageHelper.getLocalPage();
            for (Object o : (List<?>) result) {
                localPage.add(o);
            }
            return localPage;
        } finally {
            PageHelper.clearPage();
        }
    }
}
