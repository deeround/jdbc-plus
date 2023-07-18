package com.github.deeround.jdbc.plus.method;

import java.lang.reflect.Method;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/20 16:53
 */
public class MethodInfo {

    private Method method;

    private String name;

    private Class<?>[] parameterTypes;

    private Class<?> returnType;

    public Method getMethod() {
        return this.method;
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

    public MethodInfo(Method method) {
        this.method = method;
        this.name = method.getName();
        this.parameterTypes = method.getParameterTypes();
        this.returnType = method.getReturnType();
    }

}
