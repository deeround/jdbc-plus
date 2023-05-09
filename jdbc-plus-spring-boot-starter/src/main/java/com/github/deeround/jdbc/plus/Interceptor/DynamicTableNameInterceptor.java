package com.github.deeround.jdbc.plus.Interceptor;/*
 * Copyright (c) 2011-2022, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.deeround.jdbc.plus.handler.TableNameHandler;
import com.github.deeround.jdbc.plus.method.MethodInvocationInfo;
import com.github.deeround.jdbc.plus.method.MethodType;
import com.github.deeround.jdbc.plus.util.TableNameParser;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * 动态表名
 *
 * @author wanghao
 */
public class DynamicTableNameInterceptor implements IInterceptor {
    /**
     * 表名处理器，是否处理表名的情况都在该处理器中自行判断
     */
    private TableNameHandler tableNameHandler;

    public DynamicTableNameInterceptor(TableNameHandler tableNameHandler) {
        this.tableNameHandler = tableNameHandler;
    }

    @Override
    public boolean supportMethod(MethodInvocationInfo methodInfo) {
        if (methodInfo.isFirstParameterIsSql() && (MethodType.UPDATE.equals(methodInfo.getType()) || MethodType.QUERY.equals(methodInfo.getType()))) {
            return true;
        }
        return false;
    }

    @Override
    public void beforePrepare(final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        for (int i = 0; i < methodInfo.getBatchSql().length; i++) {
            methodInfo.getBatchSql()[i] = this.changeTable(methodInfo.getBatchSql()[i]);
        }
    }

    protected String changeTable(String sql) {
        TableNameParser parser = new TableNameParser(sql);
        List<TableNameParser.SqlToken> names = new ArrayList<>();
        parser.accept(names::add);
        StringBuilder builder = new StringBuilder();
        int last = 0;
        for (TableNameParser.SqlToken name : names) {
            int start = name.getStart();
            if (start != last) {
                builder.append(sql, last, start);
                builder.append(this.tableNameHandler.dynamicTableName(sql, name.getValue()));
            }
            last = name.getEnd();
        }
        if (last != sql.length()) {
            builder.append(sql.substring(last));
        }
        return builder.toString();
    }
}
