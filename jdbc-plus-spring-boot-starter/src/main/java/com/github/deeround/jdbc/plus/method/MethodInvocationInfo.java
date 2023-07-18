package com.github.deeround.jdbc.plus.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/23 14:24
 */
public class MethodInvocationInfo extends MethodInfo {

    private final Object[] args;

    private MethodType type;

    private boolean isSupport;

    private MethodActionInfo actionInfo;

    private final Map<String, Object> userAttributes = new HashMap<>(0);

    public MethodInvocationInfo(final Object[] args, Method method) {
        super(method);
        this.args = args;

        this.type = MethodType.UNKNOWN;
        this.isSupport = false;

        this.resolveMethod();

    }

    public Object[] getArgs() {
        return this.args;
    }

    public MethodType getType() {
        return this.type;
    }

    public boolean isSupport() {
        return this.isSupport;
    }

    public MethodActionInfo getActionInfo() {
        return this.actionInfo;
    }

    public Map<String, Object> getUserAttributes() {
        return this.userAttributes;
    }

    public void putUserAttribute(String key, Object value) {
        if (this.userAttributes != null) {
            this.userAttributes.put(key, value);
        }
    }

    public Object getUserAttribute(String key) {
        if (this.userAttributes != null) {
            return this.userAttributes.get(key);
        }
        return null;
    }


    //=================METHOD START================

    public void resolveSql(String sql) {
        this.resolveSql(new String[]{sql});
    }

    public void resolveSql(String[] batchSql) {
        if (this.actionInfo != null) {
            if (batchSql == null || batchSql.length == 0) {
                throw new RuntimeException("batchSql不能为空");
            }
            this.actionInfo.setBatchSql(batchSql);

            if (this.actionInfo.isSqlIsBatch()) {
                this.args[0] = this.actionInfo.getBatchSql();
            } else {
                this.args[0] = this.actionInfo.getSql();
            }

        }
    }

    public void resolveSql(int i, String sql) {
        if (this.actionInfo != null) {

            this.actionInfo.getBatchSql()[i] = sql;

            if (this.actionInfo.isSqlIsBatch()) {
                this.args[0] = this.actionInfo.getBatchSql();
            } else {
                this.args[0] = this.actionInfo.getSql();
            }

        }
    }

    public void resolveParameter(Object[] parameter) {
        List<Object[]> objects = new ArrayList<>();
        objects.add(parameter);
        this.resolveParameter(objects);
    }

    public void resolveParameter(List<Object[]> batchParameter) {
        if (this.actionInfo != null) {
            if (batchParameter == null || batchParameter.size() == 0) {
                throw new RuntimeException("batchParameter不能为空");
            }
            this.actionInfo.setBatchParameter(batchParameter);

            if (this.actionInfo.isHasParameter()) {
                if (!this.actionInfo.isParameterIsPss()) {
                    if (this.actionInfo.isParameterIsBatch()) {
                        this.args[this.actionInfo.getParameterIndex()] = this.actionInfo.getBatchParameter();
                    } else {
                        this.args[this.actionInfo.getParameterIndex()] = this.actionInfo.getParameter();
                    }
                }
            }
        }
    }

    //=================METHOD END================


    /**
     * 解析Method
     */
    private void resolveMethod() {
        if (this.getName().startsWith("execute")) {
            this.type = MethodType.EXECUTE;
        } else if (this.getName().startsWith("batchUpdate")) {
            this.type = MethodType.UPDATE;
        } else if (this.getName().startsWith("update")) {
            this.type = MethodType.UPDATE;
        } else if (this.getName().startsWith("query")) {
            this.type = MethodType.QUERY;
        }
        this.actionInfo = MethodActionRegister.getMethodActionInfo(this.getMethod(), this.args);
        if (this.actionInfo != null) {
            this.isSupport = true;
        }
    }

}
