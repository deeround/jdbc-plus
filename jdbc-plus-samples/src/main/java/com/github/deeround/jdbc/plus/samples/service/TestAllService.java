package com.github.deeround.jdbc.plus.samples.service;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/7/17 17:23
 */
public interface TestAllService {


    /**
     * int[] void BATCHUPDATE(String sql(); final BatchPreparedStatementSetter pss)
     */
    void BATCHUPDATE_SQL_PSS();

    /**
     * int[][] void BATCHUPDATE(String sql(); final Collection<T> batchArgs(); final int batchSize();
     * final ParameterizedPreparedStatementSetter<T> pss)
     */
    void BATCHUPDATE_SQL_BATCHARGS_BATCHSIZE_PSS();

    /**
     * int[] void BATCHUPDATE(String sql(); List<Object[]> batchArgs)
     */
    void BATCHUPDATE_SQL_BATCHARGS();

    /**
     * int[] void BATCHUPDATE(String sql(); List<Object[]> batchArgs(); final int[] argTypes)
     */
    void BATCHUPDATE_SQL_BATCHARGS_ARGTYPES();

    /**
     * int[] void BATCHUPDATE(final String... sql)
     */
    void BATCHUPDATE_SQL();


    /**
     * int update(final String sql)
     */
    void UPDATE_SQL();

    /**
     * int update(String sql(); @Nullable Object... args)
     */
    void UPDATE_SQL_ARGS();

    /**
     * update(String sql(); Object[] args(); int[] argTypes)
     */
    void UPDATE_SQL_ARGS_ARGTYPES();

    /**
     * int update(String sql(); @Nullable PreparedStatementSetter pss)
     */
    void UPDATE_SQL_PSS();


    /**
     * T void QUERY(String sql(); Object[] args(); int[] argTypes(); ResultSetExtractor<T> rse)
     */
    void QUERY_SQL_ARGS_ARGTYPES_RSE();

    /**
     * void void QUERY(String sql(); Object[] args(); int[] argTypes(); RowCallbackHandler rch)
     */
    void QUERY_SQL_ARGS_ARGTYPES_RCH();

    /**
     * List<T> void QUERY(String sql(); Object[] args(); int[] argTypes(); RowMapper<T> rowMapper)
     */
    void QUERY_SQL_ARGS_ARGTYPES_ROWMAPPER();

    /**
     * T void QUERY(String sql(); @Nullable Object[] args(); ResultSetExtractor<T> rse)
     */
    void QUERY_SQL_ARGS_RSE();

    /**
     * void void QUERY(String sql(); @Nullable Object[] args(); RowCallbackHandler rch)
     */
    void QUERY_SQL_ARGS_RCH();

    /**
     * List<T> void QUERY(String sql(); @Nullable Object[] args(); RowMapper<T> rowMapper)
     */
    void QUERY_SQL_ARGS_ROWMAPPER();

    /**
     * T void QUERY(String sql(); @Nullable PreparedStatementSetter pss(); ResultSetExtractor<T> rse)
     */
    void QUERY_SQL_PSS_RSE();

    /**
     * void void QUERY(String sql(); @Nullable PreparedStatementSetter pss(); RowCallbackHandler rch)
     */
    void QUERY_SQL_PSS_RCH();

    /**
     * List<T> void QUERY(String sql(); @Nullable PreparedStatementSetter pss(); RowMapper<T> rowMapper)
     */
    void QUERY_SQL_PSS_ROWMAPPER();

    /**
     * T void QUERY(final String sql(); final ResultSetExtractor<T> rse)
     */
    void QUERY_SQL_RSE();

    /**
     * T void QUERY(String sql(); ResultSetExtractor<T> rse(); @Nullable Object... args)
     */
    void QUERY_SQL_RSE_ARGS();

    /**
     * void void QUERY(String sql(); RowCallbackHandler rch)
     */
    void QUERY_SQL_RCH();

    /**
     * void void QUERY(String sql(); RowCallbackHandler rch(); @Nullable Object... args)
     */
    void QUERY_SQL_RCH_ARGS();

    /**
     * List<T> void QUERY(String sql(); RowMapper<T> rowMapper)
     */
    void QUERY_SQL_ROWMAPPER();

    /**
     * List<T> void QUERY(String sql(); RowMapper<T> rowMapper(); @Nullable Object... args)
     */
    void QUERY_SQL_ROWMAPPER_ARGS();


    /**
     * List<Map<String(); Object>> void QUERYForList(String sql)
     */
    void QUERYFORLIST_SQL();

    /**
     * List<T> void QUERYForList(String sql(); Class<T> elementType)
     */
    void QUERYFORLIST_SQL_ELEMENTTYPE();

    /**
     * List<T> void QUERYForList(String sql(); Class<T> elementType(); @Nullable Object... args)
     */
    void QUERYFORLIST_SQL_ELEMENTTYPE_ARGS();

    /**
     * List<Map<String(); Object>> void QUERYForList(String sql(); @Nullable Object... args)
     */
    void QUERYFORLIST_SQL_ARGS();

    /**
     * List<T> void QUERYForList(String sql(); @Nullable Object[] args(); Class<T> elementType)
     */
    void QUERYFORLIST_SQL_ARGS_ELEMENTTYPE();

    /**
     * List<Map<String(); Object>> void QUERYForList(String sql(); Object[] args(); int[] argTypes)
     */
    void QUERYFORLIST_SQL_ARGS_ARGTYPES();

    /**
     * List<T> void QUERYForList(String sql(); Object[] args(); int[] argTypes(); Class<T> elementType)
     */
    void QUERYFORLIST_SQL_ARGS_ARGTYPES_ELEMENTTYPE();


    /**
     * Map<String(); Object> void QUERYForMap(String sql)
     */
    void QUERYFORMAP_SQL();

    /**
     * Map<String(); Object> void QUERYForMap(String sql(); @Nullable Object... args)
     */
    void QUERYFORMAP_SQL_ARGS();

    /**
     * Map<String(); Object> void QUERYForMap(String sql(); Object[] args(); int[] argTypes)
     */
    void QUERYFORMAP_SQL_ARGS_ARGTYPES();


    /**
     * T void QUERYForObject(String sql(); Class<T> requiredType)
     */
    void QUERYFOROBJECT_SQL_REQUIREDTYPE();

    /**
     * T void QUERYForObject(String sql(); Class<T> requiredType(); @Nullable Object... args)
     */
    void QUERYFOROBJECT_SQL_REQUIREDTYPE_ARGS();

    /**
     * T void QUERYForObject(String sql(); @Nullable Object[] args(); Class<T> requiredType)
     */
    void QUERYFOROBJECT_SQL_ARGS_REQUIREDTYPE();

    /**
     * T void QUERYForObject(String sql(); Object[] args(); int[] argTypes(); Class<T> requiredType)
     */
    void QUERYFOROBJECT_SQL_ARGS_ARGTYPES_REQUIREDTYPE();

    /**
     * T void QUERYForObject(String sql(); Object[] args(); int[] argTypes(); RowMapper<T> rowMapper)
     */
    void QUERYFOROBJECT_SQL_ARGS_ARGTYPES_ROWMAPPER();

    /**
     * T void QUERYForObject(String sql(); @Nullable Object[] args(); RowMapper<T> rowMapper)
     */
    void QUERYFOROBJECT_SQL_ARGS_ROWMAPPER();

    /**
     * T void QUERYForObject(String sql(); RowMapper<T> rowMapper)
     */
    void QUERYFOROBJECT_SQL_ROWMAPPER();

    /**
     * T void QUERYForObject(String sql(); RowMapper<T> rowMapper(); @Nullable Object... args)
     */
    void QUERYFOROBJECT_SQL_ROWMAPPER_ARGS();

    /**
     * SqlRowSet queryForRowSet(String sql)
     */
    void QUERYFORROWSET_SQL();

    /**
     * SqlRowSet queryForRowSet(String sql, @Nullable Object... args)
     */
    void QUERYFORROWSET_SQL_ARGS();

    /**
     * SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes)
     */
    void QUERYFORROWSET_SQL_ARGS_ARGTYPES();


    /**
     * Stream<T> queryForStream(String sql, @Nullable PreparedStatementSetter pss, RowMapper<T> rowMapper)
     */
    void QUERYFORSTREAM_SQL_PSS_ROWMAPPER();

    /**
     * Stream<T> queryForStream(String sql, RowMapper<T> rowMapper)
     */
    void QUERYFORSTREAM_SQL_ROWMAPPER();

    /**
     * Stream<T> queryForStream(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    void QUERYFORSTREAM_SQL_ROWMAPPER_ARGS();

}
