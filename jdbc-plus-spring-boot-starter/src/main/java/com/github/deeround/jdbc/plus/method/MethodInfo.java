package com.github.deeround.jdbc.plus.method;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/20 16:53
 */
public class MethodInfo {

    protected Method method;

    protected MethodType type;

    protected String name;

    protected Class<?>[] parameterTypes;

    protected Class<?> returnType;

    protected boolean firstParameterIsSql;

    protected boolean returnIsList;

    public MethodInfo(Method method) {

        this.method = method;
        this.name = method.getName();
        this.type = this.getMethodType();

        this.parameterTypes = method.getParameterTypes();
        this.returnType = method.getReturnType();


        if (this.parameterTypes != null && this.parameterTypes.length > 0) {
            if (this.parameterTypes[0] != null) {
                this.firstParameterIsSql = String.class.isAssignableFrom(this.parameterTypes[0]);
            }
        }

        if (this.returnType != null) {
            this.returnIsList = List.class.isAssignableFrom(this.returnType);
        }
    }

    protected MethodType getMethodType() {
        if (this.name != null || this.name.length() > 0) {
            if (this.name.equals("batchUpdate")) {
                return MethodType.BATCH_UPDATE;
            } else if (this.name.equals("execute")) {
                return MethodType.EXECUTE;
            } else if (this.name.equals("query") || this.name.equals("queryForList") || this.name.equals("queryForMap") || this.name.equals("queryForObject") || this.name.equals("queryForRowSet") || this.name.equals("queryForStream")) {
                return MethodType.QUERY;
            } else if (this.name.equals("update")) {
                return MethodType.UPDATE;
            }
        }
        return MethodType.UNKNOWN;
    }


    public Method getMethod() {
        return this.method;
    }

    public MethodType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }

    public Class<?> getReturnType() {
        return this.returnType;
    }

    public boolean isFirstParameterIsSql() {
        return this.firstParameterIsSql;
    }

    public boolean isReturnIsList() {
        return this.returnIsList;
    }
}
