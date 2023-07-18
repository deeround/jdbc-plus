package com.github.deeround.jdbc.plus.method;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/7/17 9:23
 */
public enum MethodActionType {

    /**
     * 未知执行方法
     */
    UNKNOWN,

    /**
     * 动态方法
     */
    DYNAMIC,


    /**
     * int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss)
     */
    BATCHUPDATE_SQL_PSS,

    /**
     * int[][] batchUpdate(String sql, final Collection<T> batchArgs, final int batchSize,
     * final ParameterizedPreparedStatementSetter<T> pss)
     */
    BATCHUPDATE_SQL_BATCHARGS_BATCHSIZE_PSS,

    /**
     * int[] batchUpdate(String sql, List<Object[]> batchArgs)
     */
    BATCHUPDATE_SQL_BATCHARGS,

    /**
     * int[] batchUpdate(String sql, List<Object[]> batchArgs, final int[] argTypes)
     */
    BATCHUPDATE_SQL_BATCHARGS_ARGTYPES,

    /**
     * int[] batchUpdate(final String... sql)
     */
    BATCHUPDATE_SQL,


    /**
     * int update(final String sql)
     */
    UPDATE_SQL,

    /**
     * int update(String sql, @Nullable Object... args)
     */
    UPDATE_SQL_ARGS,

    /**
     * update(String sql, Object[] args, int[] argTypes)
     */
    UPDATE_SQL_ARGS_ARGTYPES,

    /**
     * int update(String sql, @Nullable PreparedStatementSetter pss)
     */
    UPDATE_SQL_PSS,


    /**
     * T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
     */
    QUERY_SQL_ARGS_ARGTYPES_RSE,

    /**
     * void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch)
     */
    QUERY_SQL_ARGS_ARGTYPES_RCH,

    /**
     * List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
     */
    QUERY_SQL_ARGS_ARGTYPES_ROWMAPPER,

    /**
     * T query(String sql, @Nullable Object[] args, ResultSetExtractor<T> rse)
     */
    QUERY_SQL_ARGS_RSE,

    /**
     * void query(String sql, @Nullable Object[] args, RowCallbackHandler rch)
     */
    QUERY_SQL_ARGS_RCH,

    /**
     * List<T> query(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
     */
    QUERY_SQL_ARGS_ROWMAPPER,

    /**
     * T query(String sql, @Nullable PreparedStatementSetter pss, ResultSetExtractor<T> rse)
     */
    QUERY_SQL_PSS_RSE,

    /**
     * void query(String sql, @Nullable PreparedStatementSetter pss, RowCallbackHandler rch)
     */
    QUERY_SQL_PSS_RCH,

    /**
     * List<T> query(String sql, @Nullable PreparedStatementSetter pss, RowMapper<T> rowMapper)
     */
    QUERY_SQL_PSS_ROWMAPPER,

    /**
     * T query(final String sql, final ResultSetExtractor<T> rse)
     */
    QUERY_SQL_RSE,

    /**
     * T query(String sql, ResultSetExtractor<T> rse, @Nullable Object... args)
     */
    QUERY_SQL_RSE_ARGS,

    /**
     * void query(String sql, RowCallbackHandler rch)
     */
    QUERY_SQL_RCH,

    /**
     * void query(String sql, RowCallbackHandler rch, @Nullable Object... args)
     */
    QUERY_SQL_RCH_ARGS,

    /**
     * List<T> query(String sql, RowMapper<T> rowMapper)
     */
    QUERY_SQL_ROWMAPPER,

    /**
     * List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    QUERY_SQL_ROWMAPPER_ARGS,


    /**
     * List<Map<String, Object>> queryForList(String sql)
     */
    QUERYFORLIST_SQL,

    /**
     * List<T> queryForList(String sql, Class<T> elementType)
     */
    QUERYFORLIST_SQL_ELEMENTTYPE,

    /**
     * List<T> queryForList(String sql, Class<T> elementType, @Nullable Object... args)
     */
    QUERYFORLIST_SQL_ELEMENTTYPE_ARGS,

    /**
     * List<Map<String, Object>> queryForList(String sql, @Nullable Object... args)
     */
    QUERYFORLIST_SQL_ARGS,

    /**
     * List<T> queryForList(String sql, @Nullable Object[] args, Class<T> elementType)
     */
    QUERYFORLIST_SQL_ARGS_ELEMENTTYPE,

    /**
     * List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes)
     */
    QUERYFORLIST_SQL_ARGS_ARGTYPES,

    /**
     * List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
     */
    QUERYFORLIST_SQL_ARGS_ARGTYPES_ELEMENTTYPE,


    /**
     * Map<String, Object> queryForMap(String sql)
     */
    QUERYFORMAP_SQL,

    /**
     * Map<String, Object> queryForMap(String sql, @Nullable Object... args)
     */
    QUERYFORMAP_SQL_ARGS,

    /**
     * Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes)
     */
    QUERYFORMAP_SQL_ARGS_ARGTYPES,


    /**
     * T queryForObject(String sql, Class<T> requiredType)
     */
    QUERYFOROBJECT_SQL_REQUIREDTYPE,

    /**
     * T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)
     */
    QUERYFOROBJECT_SQL_REQUIREDTYPE_ARGS,

    /**
     * T queryForObject(String sql, @Nullable Object[] args, Class<T> requiredType)
     */
    QUERYFOROBJECT_SQL_ARGS_REQUIREDTYPE,

    /**
     * T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
     */
    QUERYFOROBJECT_SQL_ARGS_ARGTYPES_REQUIREDTYPE,

    /**
     * T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
     */
    QUERYFOROBJECT_SQL_ARGS_ARGTYPES_ROWMAPPER,

    /**
     * T queryForObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
     */
    QUERYFOROBJECT_SQL_ARGS_ROWMAPPER,

    /**
     * T queryForObject(String sql, RowMapper<T> rowMapper)
     */
    QUERYFOROBJECT_SQL_ROWMAPPER,

    /**
     * T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    QUERYFOROBJECT_SQL_ROWMAPPER_ARGS,

}
