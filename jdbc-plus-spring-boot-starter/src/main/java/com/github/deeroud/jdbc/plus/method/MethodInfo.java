package com.github.deeroud.jdbc.plus.method;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/4/20 16:53
 */
@Data
public class MethodInfo {

    private Method method;

    private MethodType type;

    private String name;

    private Class<?>[] parameterTypes;

    private Class<?> returnType;

    private boolean firstParameterIsString;

    private boolean returnIsList;

    public MethodInfo(Method method) {

        this.method = method;
        this.name = method.getName();
        this.type = this.getMethodType();

        this.parameterTypes = method.getParameterTypes();
        this.returnType = method.getReturnType();


        if (this.parameterTypes != null && this.parameterTypes.length > 0) {
            if (this.parameterTypes[0] != null) {
                this.firstParameterIsString = String.class.isAssignableFrom(this.parameterTypes[0]);
            }
        }

        if (this.returnType != null) {
            this.returnIsList = List.class.isAssignableFrom(this.returnType);
        }
    }


    private MethodType getMethodType() {
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

}
