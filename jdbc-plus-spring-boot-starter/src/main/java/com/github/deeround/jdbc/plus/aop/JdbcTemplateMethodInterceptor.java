/*
 * Copyright © 2018 organization baomidou
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
package com.github.deeround.jdbc.plus.aop;

import com.github.deeround.jdbc.plus.Interceptor.IInterceptor;
import com.github.deeround.jdbc.plus.method.MethodInvocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JdbcTemplateMethodInterceptor implements MethodInterceptor {

    private final List<IInterceptor> interceptors;

    public JdbcTemplateMethodInterceptor(List<IInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ReflectiveMethodInvocation methodInvocation = (ReflectiveMethodInvocation) invocation;

        Object[] args = methodInvocation.getArguments();
        Method method = methodInvocation.getMethod();
        JdbcTemplate jdbcTemplate = (JdbcTemplate) methodInvocation.getThis();

        final MethodInvocationInfo methodInfo = new MethodInvocationInfo(args, method);
        log.debug("method==>name:{},actionType:{}", methodInfo.getName(), methodInfo.getActionInfo().getActionType());
        log.debug("origin sql==>{}", this.toStr(methodInfo.getActionInfo().getBatchSql()));
        log.debug("origin parameters==>{}", this.toStr(methodInfo.getActionInfo().getBatchParameter()));

        //逻辑处理（核心方法：主要处理SQL和SQL参数）
        if (this.interceptors != null && this.interceptors.size() > 0) {
            for (IInterceptor interceptor : this.interceptors) {
                if (interceptor.supportMethod(methodInfo)) {
                    interceptor.beforePrepare(methodInfo, jdbcTemplate);
                    //插件允许修改原始SQL以及入参
                    if (methodInfo.getArgs() != null && methodInfo.getArgs().length > 0) {
                        //回写参数
                        methodInvocation.setArguments(methodInfo.getArgs());
                    }
                }
            }
        }
        log.debug("finish sql==>{}", this.toStr(methodInfo.getActionInfo().getBatchSql()));
        log.debug("finish parameters==>{}", this.toStr(methodInfo.getActionInfo().getBatchParameter()));


        Object result = methodInvocation.proceed();
        log.debug("origin result==>{}", result);

        //逻辑处理
        if (this.interceptors != null && this.interceptors.size() > 0) {
            for (int i = this.interceptors.size() - 1; i >= 0; i--) {
                IInterceptor interceptor = this.interceptors.get(i);
                if (interceptor.supportMethod(methodInfo)) {
                    result = interceptor.beforeFinish(result, methodInfo, jdbcTemplate);
                }
            }
        }
        log.debug("finish result==>{}", result);

        return result;
    }

    private String toStr(Object[] objs) {
        if (objs == null) {
            return null;
        }
        return Arrays.toString(objs);
    }

    private String toStr(List<Object[]> list) {
        if (list == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < list.size(); i++) {
            str.append(Arrays.toString(list.get(i)));
            if (i < list.size() - 1) {
                str.append(",");
            }
        }
        return str.append("]").toString();
    }
}
