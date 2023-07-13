package com.github.deeround.jdbc.plus.method;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/23 14:24
 */
public class MethodInvocationInfo extends MethodInfo {

    private String sql;

    private String[] batchSql;

    private Object[] parameters;

    private List<Object[]> batchParameters;

    private Object[] args;

    private Map<String, Object> userAttributes = new HashMap<>(0);

    public MethodInvocationInfo(final Object[] args, Method method) {
        super(method);
        this.args = args;
        if (this.isFirstParameterIsSql()) {
            if (this.isFirstParameterIsBatchSql()) {
                this.batchSql = (String[]) args[0];
            } else {
                this.batchSql = new String[]{args[0].toString()};
            }
        }
        if (this.isSecondParameterIsArgs()) {
            this.parameters = (Object[]) args[1];
        }
    }

    public String getSql() {
        if (this.batchSql == null || this.batchSql.length == 0) {
            return null;
        } else {
            return this.batchSql[0];
        }
    }

    public String[] getBatchSql() {
        return this.batchSql;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public Map<String, Object> getUserAttributes() {
        return this.userAttributes;
    }


    public void setSql(String sql) {
        if (this.batchSql == null || this.batchSql.length == 0) {
            this.batchSql = new String[]{sql};
        } else {
            this.batchSql[0] = sql;
        }
    }

    public void setBatchSql(String[] batchSql) {
        this.batchSql = batchSql;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public List<Object[]> getBatchParameters() {
        return this.batchParameters;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setUserAttributes(Map<String, Object> userAttributes) {
        this.userAttributes = userAttributes;
    }


    public void putUserAttribute(String key, Object value) {
        if (this.getUserAttributes() != null) {
            this.getUserAttributes().put(key, value);
        }
    }

    public Object getUserAttribute(String key) {
        if (this.getUserAttributes() != null) {
            return this.getUserAttributes().get(key);
        }
        return null;
    }
}
