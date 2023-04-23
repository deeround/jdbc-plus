package com.github.deeround.jdbc.plus.method;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/23 14:24
 */
public class MethodInvocationInfo extends MethodInfo {

    private String sql;

    private Object[] args;

    private Map<String, Object> userAttributes = new HashMap<>(0);

    public MethodInvocationInfo(final Object[] args, Method method) {
        super(method);
        this.args = args;
        if (this.isFirstParameterIsSql()) {
            this.sql = args[0].toString();
        }
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Map<String, Object> getUserAttributes() {
        return this.userAttributes;
    }

    public void setUserAttributes(Map<String, Object> userAttributes) {
        this.userAttributes = userAttributes;
    }
}
