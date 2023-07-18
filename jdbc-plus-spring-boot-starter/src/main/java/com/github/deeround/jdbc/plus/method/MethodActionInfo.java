package com.github.deeround.jdbc.plus.method;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/7/17 15:48
 */
@Data
@Slf4j
public class MethodActionInfo {

    private MethodActionType actionType = MethodActionType.UNKNOWN;

    //以下是针对入参-SQL


    /**
     * SQL语句
     */
    private String sql;
    /**
     * 批量SQL语句
     */
    private String[] batchSql;
    /**
     * 是否批量SQL语句
     */
    private boolean sqlIsBatch;


    //以下针对入参-SQL入参

    /**
     * 是否有入参
     */
    private boolean hasParameter;
    /**
     * 入参是否是pss
     */
    private boolean parameterIsPss;
    /**
     * 入参Object[]
     */
    private Object[] parameter;
    /**
     * 批量入参List<Object[]>
     */
    private List<Object[]> batchParameter;
    /**
     * 参数是否是批量
     */
    private boolean parameterIsBatch;
    /**
     * 入参索引
     */
    private int parameterIndex;
    /**
     * 是否有入参类型定义
     */
    private boolean hasParameterType;
    /**
     * 入参类型定义
     */
    private int[] parameterType;
    /**
     * 入参类型定义索引
     */
    private int parameterTypeIndex;

    public String getSql() {
        if (this.batchSql != null && this.batchSql.length > 0) {
            return this.batchSql[0];
        }
        return null;
    }

    private void setSql(String sql) {
    }

    public Object[] getParameter() {
        if (this.batchParameter != null && this.batchParameter.size() > 0) {
            return this.batchParameter.get(0);
        }
        return null;
    }

    private void setParameter(Object[] parameter) {
    }

    //以下是针对出参分分析

    /**
     * 是否有返回值
     */
    private boolean hasReturn;
    /**
     * 返回值类型是否是LIST
     */
    private boolean returnIsList;

    public MethodActionInfo(boolean parameterIsPss, int parameterIndex, int parameterTypeIndex,
                            boolean hasReturn, boolean returnIsList) {
        this.parameterIsPss = parameterIsPss;
        this.parameterIndex = parameterIndex;
        if (parameterIndex >= 0) {
            this.hasParameter = true;
        }
        this.parameterTypeIndex = parameterTypeIndex;
        if (parameterTypeIndex >= 0) {
            this.hasParameterType = true;
        }
        this.hasReturn = hasReturn;
        this.returnIsList = returnIsList;
    }

    public MethodActionInfo(int parameterIndex,
                            boolean hasReturn, boolean returnIsList) {
        this(false, parameterIndex, -1,
                hasReturn, returnIsList);
    }

    public MethodActionInfo(int parameterIndex,
                            boolean sqlIsBatch, boolean parameterIsBatch,
                            boolean hasReturn, boolean returnIsList) {
        this(false, parameterIndex, -1,
                hasReturn, returnIsList);

        this.sqlIsBatch = sqlIsBatch;
        this.parameterIsBatch = parameterIsBatch;
    }
}
