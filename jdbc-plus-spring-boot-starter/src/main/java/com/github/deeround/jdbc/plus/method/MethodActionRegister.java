package com.github.deeround.jdbc.plus.method;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/7/17 15:24
 */
@Slf4j
public class MethodActionRegister {

    private final static Map<Method, MethodActionInfo> Method_MAP = new HashMap<>();

    static {
        Class<JdbcTemplate> clazz = JdbcTemplate.class;

        //int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss)
        register(clazz, new MethodActionInfo(-1, true, false), "batchUpdate", String.class, BatchPreparedStatementSetter.class);
        //int[][] batchUpdate(String sql, final Collection<T> batchArgs, final int batchSize, final ParameterizedPreparedStatementSetter<T> pss)
        register(clazz, new MethodActionInfo(-1, true, false), "batchUpdate", String.class, Collection.class, int.class, ParameterizedPreparedStatementSetter.class);
        //int[] batchUpdate(String sql, List<Object[]> batchArgs)
        register(clazz, new MethodActionInfo(1, false, true, true, false), "batchUpdate", String.class, List.class);
        //int[] batchUpdate(String sql, List<Object[]> batchArgs, final int[] argTypes)
        register(clazz, new MethodActionInfo(1, false, true, true, false), "batchUpdate", String.class, List.class, int[].class);
        //int[] batchUpdate(final String... sql)
        register(clazz, new MethodActionInfo(-1, true, false, true, false), "batchUpdate", String[].class);


        //int update(final String sql)
        register(clazz, new MethodActionInfo(-1, true, false), "update", String.class);
        //int update(String sql, @Nullable Object... args)
        register(clazz, new MethodActionInfo(1, true, false), "update", String.class, Object[].class);
        //update(String sql, Object[] args, int[] argTypes)
        register(clazz, new MethodActionInfo(false, 1, 2, true, false), "update", String.class, Object[].class, int[].class);
        //int update(String sql, @Nullable PreparedStatementSetter pss)
        register(clazz, new MethodActionInfo(true, 1, -1, true, false), "update", String.class, PreparedStatementSetter.class);


        //T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
        register(clazz, new MethodActionInfo(false, 1, 2, true, false), "query", String.class, Object[].class, int[].class, ResultSetExtractor.class);
        //void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch)
        register(clazz, new MethodActionInfo(false, 1, 2, false, false), "query", String.class, Object[].class, int[].class, RowCallbackHandler.class);
        //List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(false, 1, 2, true, true), "query", String.class, Object[].class, int[].class, RowMapper.class);
        //T query(String sql, @Nullable Object[] args, ResultSetExtractor<T> rse)
        register(clazz, new MethodActionInfo(false, 1, -1, true, false), "query", String.class, Object[].class, ResultSetExtractor.class);
        //void query(String sql, @Nullable Object[] args, RowCallbackHandler rch)
        register(clazz, new MethodActionInfo(false, 1, -1, false, false), "query", String.class, Object[].class, RowCallbackHandler.class);
        //List<T> query(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(false, 1, -1, true, true), "query", String.class, Object[].class, RowMapper.class);
        //T query(String sql, @Nullable PreparedStatementSetter pss, ResultSetExtractor<T> rse)
        register(clazz, new MethodActionInfo(true, 1, -1, true, false), "query", String.class, PreparedStatementSetter.class, ResultSetExtractor.class);
        //void query(String sql, @Nullable PreparedStatementSetter pss, RowCallbackHandler rch)
        register(clazz, new MethodActionInfo(true, 1, -1, false, false), "query", String.class, PreparedStatementSetter.class, RowCallbackHandler.class);
        //List<T> query(String sql, @Nullable PreparedStatementSetter pss, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(true, 1, -1, true, true), "query", String.class, PreparedStatementSetter.class, RowMapper.class);
        //T query(final String sql, final ResultSetExtractor<T> rse)
        register(clazz, new MethodActionInfo(false, -1, -1, true, false), "query", String.class, ResultSetExtractor.class);
        //T query(String sql, ResultSetExtractor<T> rse, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 2, -1, true, false), "query", String.class, ResultSetExtractor.class, Object[].class);
        //void query(String sql, RowCallbackHandler rch)
        register(clazz, new MethodActionInfo(false, -1, -1, false, false), "query", String.class, RowCallbackHandler.class);
        //void query(String sql, RowCallbackHandler rch, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 2, -1, false, false), "query", String.class, RowCallbackHandler.class, Object[].class);
        //List<T> query(String sql, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(false, -1, -1, true, true), "query", String.class, RowMapper.class);
        //List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 2, -1, true, true), "query", String.class, RowMapper.class, Object[].class);


        //List<Map<String, Object>> queryForList(String sql)
        register(clazz, new MethodActionInfo(false, -1, -1, true, true), "queryForList", String.class);
        //List<T> queryForList(String sql, Class<T> elementType)
        register(clazz, new MethodActionInfo(false, -1, -1, true, true), "queryForList", String.class, Class.class);
        //List<T> queryForList(String sql, Class<T> elementType, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 2, -1, true, true), "queryForList", String.class, Class.class, Object[].class);
        //List<Map<String, Object>> queryForList(String sql, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 1, -1, true, true), "queryForList", String.class, Object[].class);
        //List<T> queryForList(String sql, @Nullable Object[] args, Class<T> elementType)
        register(clazz, new MethodActionInfo(false, 1, -1, true, true), "queryForList", String.class, Object[].class, Class.class);
        //List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes)
        register(clazz, new MethodActionInfo(false, 1, 2, true, true), "queryForList", String.class, Object[].class, int[].class);
        //List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
        register(clazz, new MethodActionInfo(false, 1, 2, true, true), "queryForList", String.class, Object[].class, int[].class, Class.class);


        //Map<String, Object> queryForMap(String sql)
        register(clazz, new MethodActionInfo(false, -1, -1, true, false), "queryForMap", String.class);
        //Map<String, Object> queryForMap(String sql, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 1, -1, true, false), "queryForMap", String.class, Object[].class);
        //Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes)
        register(clazz, new MethodActionInfo(false, 1, 2, true, false), "queryForMap", String.class, Object[].class, int[].class);


        //T queryForObject(String sql, Class<T> requiredType)
        register(clazz, new MethodActionInfo(false, -1, -1, true, false), "queryForObject", String.class, Class.class);
        //T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 2, -1, true, false), "queryForObject", String.class, Class.class, Object[].class);
        //T queryForObject(String sql, @Nullable Object[] args, Class<T> requiredType)
        register(clazz, new MethodActionInfo(false, 1, -1, true, false), "queryForObject", String.class, Object[].class, Class.class);
        //T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
        register(clazz, new MethodActionInfo(false, 1, 2, true, false), "queryForObject", String.class, Object[].class, int[].class, Class.class);
        //T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(false, 1, 2, true, false), "queryForObject", String.class, Object[].class, int[].class, RowMapper.class);
        //T queryForObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(false, 1, -1, true, false), "queryForObject", String.class, Object[].class, RowMapper.class);
        //T queryForObject(String sql, RowMapper<T> rowMapper)
        register(clazz, new MethodActionInfo(false, -1, -1, true, false), "queryForObject", String.class, RowMapper.class);
        //T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
        register(clazz, new MethodActionInfo(false, 2, -1, true, false), "queryForObject", String.class, RowMapper.class, Object[].class);

    }

    public static MethodActionInfo getMethodActionInfo(Method method) {
        if (Method_MAP.containsKey(method)) {
            return Method_MAP.get(method);
        } else {
            return null;
        }
    }

    public static MethodActionInfo getMethodActionInfo(Method method, Object[] args) {
        if (Method_MAP.containsKey(method)) {
            MethodActionInfo actionInfo = Method_MAP.get(method);

            //SQL语句
            if (actionInfo.isSqlIsBatch()) {
                actionInfo.setBatchSql((String[]) args[0]);
            } else {
                actionInfo.setBatchSql(new String[]{(String) args[0]});
            }
            //SQL语句参数
            if (actionInfo.isHasParameter()) {
                if (!actionInfo.isParameterIsPss()) {
                    if (actionInfo.isParameterIsBatch()) {
                        actionInfo.setBatchParameter((List<Object[]>) args[actionInfo.getParameterIndex()]);
                    } else {
                        List<Object[]> batchParameter = new ArrayList<>();
                        batchParameter.add((Object[]) args[actionInfo.getParameterIndex()]);
                        actionInfo.setBatchParameter(batchParameter);
                    }
                }
                if (actionInfo.isHasParameterType()) {
                    actionInfo.setParameterType((int[]) args[actionInfo.getParameterTypeIndex()]);
                }
            }

            return actionInfo;

        } else {
            return null;
        }
    }

    static void register(Class<JdbcTemplate> clazz, MethodActionInfo actionInfo, String name, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getMethod(name, parameterTypes);
            Method_MAP.put(method, actionInfo);
        } catch (NoSuchMethodException e) {
            log.error("未找到方法：name={},parameterTypes={}", name, parameterTypes, e);
        }
    }

}
