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
package com.github.deeroud.jdbc.plus.aop;

import com.github.deeroud.jdbc.plus.Interceptor.IInterceptor;
import com.github.deeroud.jdbc.plus.method.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
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
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        log.debug("before prepare ......");

        Object[] args = methodInvocation.getArguments();
        log.debug("==========origin args start==========");
        for (Object arg : args) {
            log.debug("{}", arg);
        }
        log.debug("==========origin args end============");


        Method method = methodInvocation.getMethod();
        log.debug("method==>{}", method);

        JdbcTemplate jdbcTemplate = (JdbcTemplate) methodInvocation.getThis();
        log.debug("jdbcTemplate==>{}", jdbcTemplate);

        MethodInfo methodInfo = new MethodInfo(method);

        //逻辑处理
        if (this.interceptors != null && this.interceptors.size() > 0) {
            for (IInterceptor interceptor : this.interceptors) {
                if (interceptor.supportMethod(methodInfo)) {
                    interceptor.beforePrepare(args, methodInfo, jdbcTemplate);
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
                    result = interceptor.beforeFinish(result, args, methodInfo, jdbcTemplate);
                }
            }
        }
        log.debug("finish result==>{}", result);

        return result;
    }
}
