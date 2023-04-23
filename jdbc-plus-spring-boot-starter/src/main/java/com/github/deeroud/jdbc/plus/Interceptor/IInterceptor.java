/*
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
package com.github.deeroud.jdbc.plus.Interceptor;

import com.github.deeroud.jdbc.plus.method.MethodInfo;
import com.github.deeroud.jdbc.plus.method.MethodType;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author miemie
 * @since 3.4.0
 */
public interface IInterceptor {

    default boolean supportMethod(MethodInfo methodInfo) {
        if (methodInfo.isFirstParameterIsString() && !MethodType.UNKNOWN.equals(methodInfo.getType())) {
            return true;
        }
        return false;
    }

    default void beforePrepare(final Object[] args, MethodInfo methodInfo, JdbcTemplate jdbcTemplate) {
        // do nothing
    }

    default Object beforeFinish(Object result, Object[] args, MethodInfo methodInfo, JdbcTemplate jdbcTemplate) {
        // do nothing
        return result;
    }
}
