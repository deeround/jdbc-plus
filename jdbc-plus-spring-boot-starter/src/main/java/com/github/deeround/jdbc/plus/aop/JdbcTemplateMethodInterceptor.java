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
import java.util.List;

@Slf4j
public class JdbcTemplateMethodInterceptor implements MethodInterceptor {

    private final List<IInterceptor> interceptors;

    public JdbcTemplateMethodInterceptor(List<IInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("before prepare ......");

        ReflectiveMethodInvocation methodInvocation = (ReflectiveMethodInvocation) invocation;

        Object[] args = methodInvocation.getArguments();
        log.debug("==========origin args start==========");
        for (Object arg : args) {
            log.debug("{}", arg);
        }
        log.debug("==========origin args end============");

        Method method = methodInvocation.getMethod();
        JdbcTemplate jdbcTemplate = (JdbcTemplate) methodInvocation.getThis();
        log.debug("method==>{}", method);

        final MethodInvocationInfo methodInfo = new MethodInvocationInfo(args, method);

        //逻辑处理
        if (this.interceptors != null && this.interceptors.size() > 0) {
            for (IInterceptor interceptor : this.interceptors) {
                if (interceptor.supportMethod(methodInfo)) {
                    log.debug("==========" + interceptor.getClass().getName() + " interceptor prepare start==========");
                    interceptor.beforePrepare(methodInfo, jdbcTemplate);
                    //将入参回写（SQL语句和入参）
                    if (methodInfo.getSql() != null && methodInfo.getSql().length() > 0 && methodInfo.isFirstParameterIsSql()) {
                        //将SQL写入到第一个入参
                        if (methodInfo.getArgs() != null && methodInfo.getArgs().length > 0) {
                            methodInfo.getArgs()[0] = methodInfo.getSql();
                        }
                    }
                    if (methodInfo.getArgs() != null && methodInfo.getArgs().length > 0) {
                        args = methodInfo.getArgs();
                        for (Object arg : args) {
                            methodInvocation.setArguments(arg);
                        }
                    }
                    log.debug("==========" + interceptor.getClass().getName() + " interceptor prepare end============");
                } else {
                    log.debug("==========" + interceptor.getClass().getName() + " interceptor prepare unsupported============");
                }
            }
        }


        log.debug("==========finish args start==========");
        for (Object arg : args) {
            log.debug("{}", arg);
        }
        log.debug("==========finish args end============");

        log.debug("finish prepare ......");

        Object result = methodInvocation.proceed();
        log.debug("origin result==>{}", result);

        //逻辑处理
        if (this.interceptors != null && this.interceptors.size() > 0) {
            for (IInterceptor interceptor : this.interceptors) {
                if (interceptor.supportMethod(methodInfo)) {
                    log.debug("==========" + interceptor.getClass().getName() + " interceptor finish start==========");
                    result = interceptor.beforeFinish(result, methodInfo, jdbcTemplate);
                    log.debug("==========" + interceptor.getClass().getName() + " interceptor finish end============");
                } else {
                    log.debug("==========" + interceptor.getClass().getName() + " interceptor finish unsupported============");
                }
            }
        }
        log.debug("finish result==>{}", result);

        return result;
    }
}
