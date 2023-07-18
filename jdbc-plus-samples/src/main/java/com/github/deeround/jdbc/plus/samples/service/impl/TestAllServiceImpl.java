package com.github.deeround.jdbc.plus.samples.service.impl;

import com.github.deeround.jdbc.plus.Interceptor.pagination.PageHelper;
import com.github.deeround.jdbc.plus.Interceptor.pagination.PageInfo;
import com.github.deeround.jdbc.plus.samples.domain.TestLog;
import com.github.deeround.jdbc.plus.samples.service.TestAllService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

/**
 * @author wanghao 913351190@qq.com
 * @create 2023/7/18 8:57
 */
@Slf4j
@Service
public class TestAllServiceImpl implements TestAllService {

    @SuppressWarnings("all")
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> hm = new HashMap<String, Object>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();// 获取列的数量
        for (int i = 1; i <= count; i++) {
            String key = rsmd.getColumnLabel(i);
            Object value = rs.getObject(i);
            hm.put(key, value);
        }
        return hm;
    }

    /**
     * int[] void BATCHUPDATE(String sql, final BatchPreparedStatementSetter pss)
     */
    @Override
    public void BATCHUPDATE_SQL_PSS() {

        String sql = "insert into test_log(name,id) values(?,?)";

        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                if (i >= 10) {
                    return;
                }
                ps.setString(1, String.valueOf(i));
                ps.setString(2, UUID.randomUUID().toString().replaceAll("-", ""));
            }

            @Override
            public int getBatchSize() {
                return 10;
            }
        });


    }

    /**
     * int[][] void BATCHUPDATE(String sql, final Collection<T> batchArgs, final int batchSize(){}
     * final ParameterizedPreparedStatementSetter<T> pss)
     */
    @Override
    public void BATCHUPDATE_SQL_BATCHARGS_BATCHSIZE_PSS() {
    }

    /**
     * int[] void BATCHUPDATE(String sql, List<Object[]> batchArgs)
     */
    @Override
    public void BATCHUPDATE_SQL_BATCHARGS() {
        String sql = "insert into test_log(name,id) values(?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"a", UUID.randomUUID().toString().replaceAll("-", "")});
        batchArgs.add(new Object[]{"b", UUID.randomUUID().toString().replaceAll("-", "")});
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * int[] void BATCHUPDATE(String sql, List<Object[]> batchArgs, final int[] argTypes)
     */
    @Override
    public void BATCHUPDATE_SQL_BATCHARGS_ARGTYPES() {
        String sql = "insert into test_log(name,id) values(?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"a1", UUID.randomUUID().toString().replaceAll("-", "")});
        batchArgs.add(new Object[]{"b1", UUID.randomUUID().toString().replaceAll("-", "")});
        int[] argTypes = new int[]{
                Types.VARCHAR,
                Types.VARCHAR
        };
        this.jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
    }

    /**
     * int[] void BATCHUPDATE(final String... sql)
     */
    @Override
    public void BATCHUPDATE_SQL() {
        this.jdbcTemplate.batchUpdate("insert into test_log(name,id) values('a2','" + UUID.randomUUID().toString().replaceAll("-", "") + "')",
                "insert into test_log(name,id) values('b2','" + UUID.randomUUID().toString().replaceAll("-", "") + "')");
    }


    /**
     * int update(final String sql)
     */
    @Override
    public void UPDATE_SQL() {
        this.jdbcTemplate.update("insert into test_log(name,id) values('a3','" + UUID.randomUUID().toString().replaceAll("-", "") + "')");
    }

    /**
     * int update(String sql, @Nullable Object... args)
     */
    @Override
    public void UPDATE_SQL_ARGS() {
        String sql = "insert into test_log(name,id) values(?,?)";
        Object[] objects = {"a4", UUID.randomUUID().toString().replaceAll("-", "")};
        this.jdbcTemplate.update(sql, objects);
    }

    /**
     * update(String sql, Object[] args, int[] argTypes)
     */
    @Override
    public void UPDATE_SQL_ARGS_ARGTYPES() {
        String sql = "insert into test_log(name,id) values(?,?)";
        Object[] objects = {"a5", UUID.randomUUID().toString().replaceAll("-", "")};
        int[] argTypes = new int[]{
                Types.VARCHAR,
                Types.VARCHAR
        };
        this.jdbcTemplate.update(sql, objects, argTypes);
    }

    /**
     * int update(String sql, @Nullable PreparedStatementSetter pss)
     */
    @Override
    public void UPDATE_SQL_PSS() {
        String sql = "insert into test_log(name,id) values(?,?)";
        this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "a6");
                ps.setString(2, UUID.randomUUID().toString().replaceAll("-", ""));
            }
        });
    }


    /**
     * T void QUERY(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
     */
    @Override
    public void QUERY_SQL_ARGS_ARGTYPES_RSE() {
        String sql = "select * from test_log where name=?";
        Object[] objects = {"0"};
        int[] argTypes = new int[]{
                Types.VARCHAR
        };
        Map query = this.jdbcTemplate.query(sql, objects, argTypes, new ResultSetExtractor<Map>() {
            @Override
            public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    return TestAllServiceImpl.toMap(rs);
                }
                return null;
            }
        });
    }

    /**
     * void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch)
     */
    @Override
    public void QUERY_SQL_ARGS_ARGTYPES_RCH() {

        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log where tenant_id=?";
        Object[] objects = {"test_tenant_4"};
        int[] argTypes = new int[]{
                Types.VARCHAR
        };
        List<Object> query = new ArrayList<>();
        final int[] rowNum = {0};
        this.jdbcTemplate.query(sql, objects, argTypes, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                log.info("rowNum==>{}", rowNum[0]++);
                query.add(TestAllServiceImpl.toMap(rs));
            }
        });

    }

    /**
     * List<T> void QUERY(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERY_SQL_ARGS_ARGTYPES_ROWMAPPER() {

        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log where tenant_id=?";
        Object[] objects = {"test_tenant_4"};
        int[] argTypes = new int[]{
                Types.VARCHAR
        };
        List<Object> query = this.jdbcTemplate.query(sql, objects, argTypes, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.info("rowNum==>{}", rowNum);
                return TestAllServiceImpl.toMap(rs);
            }
        });
    }

    /**
     * T void QUERY(String sql, @Nullable Object[] args, ResultSetExtractor<T> rse)
     */
    @Override
    public void QUERY_SQL_ARGS_RSE() {
        String sql = "select * from test_log where name=?";
        Object[] objects = {"0"};
        int[] argTypes = new int[]{
                Types.VARCHAR
        };
        Map query = this.jdbcTemplate.query(sql, objects, argTypes, new ResultSetExtractor<Map>() {
            @Override
            public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    return TestAllServiceImpl.toMap(rs);
                }
                return null;
            }
        });
    }

    /**
     * void QUERY(String sql, @Nullable Object[] args, RowCallbackHandler rch)
     */
    @Override
    public void QUERY_SQL_ARGS_RCH() {
        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log where tenant_id=?";
        Object[] objects = {"test_tenant_4"};
        List<Object> query = new ArrayList<>();
        final int[] rowNum = {0};
        this.jdbcTemplate.query(sql, objects, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                log.info("rowNum==>{}", rowNum[0]++);
                query.add(TestAllServiceImpl.toMap(rs));
            }
        });
    }

    /**
     * List<T> void QUERY(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERY_SQL_ARGS_ROWMAPPER() {

        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log where tenant_id=?";
        Object[] objects = {"test_tenant_4"};
        List<Object> query = this.jdbcTemplate.query(sql, objects, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.info("rowNum==>{}", rowNum);
                return TestAllServiceImpl.toMap(rs);
            }
        });
    }

    /**
     * T void QUERY(String sql, @Nullable PreparedStatementSetter pss, ResultSetExtractor<T> rse)
     */
    @Override
    public void QUERY_SQL_PSS_RSE() {
        String sql = "select * from test_log where tenant_id=?";
        List<Object> list = this.jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "test_tenant_4");
            }
        }, new ResultSetExtractor<List<Object>>() {
            @Override
            public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Object> query = new ArrayList<>();
                int rowNum = 0;
                while (rs.next()) {
                    log.info("rowNum==>{}", rowNum++);
                    query.add(TestAllServiceImpl.toMap(rs));
                }
                return query;
            }
        });
    }

    /**
     * void QUERY(String sql, @Nullable PreparedStatementSetter pss, RowCallbackHandler rch)
     */
    @Override
    public void QUERY_SQL_PSS_RCH() {
        List<Object> query = new ArrayList<>();
        final int[] rowNum = {0};
        String sql = "select * from test_log where tenant_id=?";
        this.jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "test_tenant_4");
            }
        }, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                log.info("rowNum==>{}", rowNum[0]++);
                query.add(TestAllServiceImpl.toMap(rs));
            }
        });
    }

    /**
     * List<T> void QUERY(String sql, @Nullable PreparedStatementSetter pss, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERY_SQL_PSS_ROWMAPPER() {
        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log where tenant_id=?";
        List<Object> query = this.jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "test_tenant_4");
            }
        }, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.info("rowNum==>{}", rowNum);
                return TestAllServiceImpl.toMap(rs);
            }
        });

        PageInfo<Object> page = new PageInfo<>(query);
    }

    /**
     * T void QUERY(final String sql, final ResultSetExtractor<T> rse)
     */
    @Override
    public void QUERY_SQL_RSE() {
        String sql = "select * from test_log";
        List<Object> list = this.jdbcTemplate.query(sql, new ResultSetExtractor<List<Object>>() {
            @Override
            public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Object> query = new ArrayList<>();
                int rowNum = 0;
                while (rs.next()) {
                    log.info("rowNum==>{}", rowNum++);
                    query.add(TestAllServiceImpl.toMap(rs));
                }
                return query;
            }
        });
    }

    /**
     * T void QUERY(String sql, ResultSetExtractor<T> rse, @Nullable Object... args)
     */
    @Override
    public void QUERY_SQL_RSE_ARGS() {
        String sql = "select * from test_log where tenant_id=?";
        List<Object> list = this.jdbcTemplate.query(sql, new ResultSetExtractor<List<Object>>() {
            @Override
            public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Object> query = new ArrayList<>();
                int rowNum = 0;
                while (rs.next()) {
                    log.info("rowNum==>{}", rowNum++);
                    query.add(TestAllServiceImpl.toMap(rs));
                }
                return query;
            }
        }, "test_tenant_4");
    }

    /**
     * void QUERY(String sql, RowCallbackHandler rch)
     */
    @Override
    public void QUERY_SQL_RCH() {
        List<Object> query = new ArrayList<>();
        final int[] rowNum = {0};
        String sql = "select * from test_log";
        this.jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                log.info("rowNum==>{}", rowNum[0]++);
                query.add(TestAllServiceImpl.toMap(rs));
            }
        });
    }

    /**
     * void QUERY(String sql, RowCallbackHandler rch, @Nullable Object... args)
     */
    @Override
    public void QUERY_SQL_RCH_ARGS() {
        List<Object> query = new ArrayList<>();
        final int[] rowNum = {0};
        String sql = "select * from test_log where tenant_id=?";
        this.jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                log.info("rowNum==>{}", rowNum[0]++);
                query.add(TestAllServiceImpl.toMap(rs));
            }
        }, "test_tenant_4");
    }

    /**
     * List<T> void QUERY(String sql, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERY_SQL_ROWMAPPER() {
        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log";
        List<Object> query = this.jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.info("rowNum==>{}", rowNum);
                return TestAllServiceImpl.toMap(rs);
            }
        });

        PageInfo<Object> page = new PageInfo<>(query);
    }

    /**
     * List<T> void QUERY(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    @Override
    public void QUERY_SQL_ROWMAPPER_ARGS() {
        //分页测试
        PageHelper.startPage(1, 5);

        String sql = "select * from test_log where tenant_id=?";
        List<Object> query = this.jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.info("rowNum==>{}", rowNum);
                return TestAllServiceImpl.toMap(rs);
            }
        }, "test_tenant_4");

        PageInfo<Object> page = new PageInfo<>(query);
    }


    /**
     * List<Map<String, Object>> void QUERYForList(String sql)
     */
    @Override
    public void QUERYFORLIST_SQL() {
        String sql = "select id,name,tenant_id as tenantId from test_log where tenant_id=?";
        List bean = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<TestLog>(TestLog.class), "test_tenant_4");
    }

    /**
     * List<T> void QUERYForList(String sql, Class<T> elementType)
     */
    @Override
    public void QUERYFORLIST_SQL_ELEMENTTYPE() {
    }

    /**
     * List<T> void QUERYForList(String sql, Class<T> elementType, @Nullable Object... args)
     */
    @Override
    public void QUERYFORLIST_SQL_ELEMENTTYPE_ARGS() {
    }

    /**
     * List<Map<String, Object>> void QUERYForList(String sql, @Nullable Object... args)
     */
    @Override
    public void QUERYFORLIST_SQL_ARGS() {
    }

    /**
     * List<T> void QUERYForList(String sql, @Nullable Object[] args, Class<T> elementType)
     */
    @Override
    public void QUERYFORLIST_SQL_ARGS_ELEMENTTYPE() {
    }

    /**
     * List<Map<String, Object>> void QUERYForList(String sql, Object[] args, int[] argTypes)
     */
    @Override
    public void QUERYFORLIST_SQL_ARGS_ARGTYPES() {
    }

    /**
     * List<T> void QUERYForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
     */
    @Override
    public void QUERYFORLIST_SQL_ARGS_ARGTYPES_ELEMENTTYPE() {
    }


    /**
     * Map<String, Object> void QUERYForMap(String sql)
     */
    @Override
    public void QUERYFORMAP_SQL() {
    }

    /**
     * Map<String, Object> void QUERYForMap(String sql, @Nullable Object... args)
     */
    @Override
    public void QUERYFORMAP_SQL_ARGS() {
    }

    /**
     * Map<String, Object> void QUERYForMap(String sql, Object[] args, int[] argTypes)
     */
    @Override
    public void QUERYFORMAP_SQL_ARGS_ARGTYPES() {
    }


    /**
     * T void QUERYForObject(String sql, Class<T> requiredType)
     */
    @Override
    public void QUERYFOROBJECT_SQL_REQUIREDTYPE() {
    }

    /**
     * T void QUERYForObject(String sql, Class<T> requiredType, @Nullable Object... args)
     */
    @Override
    public void QUERYFOROBJECT_SQL_REQUIREDTYPE_ARGS() {
    }

    /**
     * T void QUERYForObject(String sql, @Nullable Object[] args, Class<T> requiredType)
     */
    @Override
    public void QUERYFOROBJECT_SQL_ARGS_REQUIREDTYPE() {
    }

    /**
     * T void QUERYForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
     */
    @Override
    public void QUERYFOROBJECT_SQL_ARGS_ARGTYPES_REQUIREDTYPE() {
    }

    /**
     * T void QUERYForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERYFOROBJECT_SQL_ARGS_ARGTYPES_ROWMAPPER() {
    }

    /**
     * T void QUERYForObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERYFOROBJECT_SQL_ARGS_ROWMAPPER() {
    }

    /**
     * T void QUERYForObject(String sql, RowMapper<T> rowMapper)
     */
    @Override
    public void QUERYFOROBJECT_SQL_ROWMAPPER() {
    }

    /**
     * T void QUERYForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    @Override
    public void QUERYFOROBJECT_SQL_ROWMAPPER_ARGS() {
    }
}
