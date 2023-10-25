# **仓库已全部迁移到Gitee**
# Gitee地址：https://gitee.com/deeround/jdbc-plus


# 🚀 jdbc-plus简介

🚀 jdbc-plus是一款基于JdbcTemplate增强工具包，基于JdbcTemplate已实现分页、多租户、动态表名等插件，可与mybatis、mybatis-plus等混合使用，还可以十分简单的扩展自定义插件。

## 🍅  **特性**

- 使用简单，对代码入侵很小，可与mybatis、mybatis-plus等混合使用
- 已实现分页、多租户、动态表名等插件，还可以十分简单的扩展自定义插件
- 免费开源，可任意使用修改代码
- 只对ORM框架增强不做任何改变，当需要动态执行SQL不是很方面使用ORM框架执行SQL时，jdbc-plus就能发挥作用

## 🍆 **插件（持续扩展中）**

已内置以下插件，开箱即用，还可以自行扩展插件，扩展插件方法十分简单。

- **分页插件**：与PageHelper使用方法一致，还可以注册不支持的数据库
- **多租户插件**：与mybatis-plus多租户插件使用方法一致，理论上与mybatis-plus多租户插件支持度一样
- **动态表名插件**：与mybatis-plus动态表名插件使用方法一致
- **更多插件**：持续关注jdbc-plus仓库，仓库包含所有插件源代码以及使用示例

##  **🏠**  **项目开源地址**

https://github.com/deeround/jdbc-plus

https://gitee.com/deeround/jdbc-plus

# 快速开始

1. 引入jdbc-plus-spring-boot-starter

~~~ xml
<dependency>
    <groupId>com.github.deeround</groupId>
    <artifactId>jdbc-plus-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
~~~

2. 注入需要使用的插件（需要哪个注入哪个，不需要的注释掉即可）

~~~ java
@Configuration
public class JdbcPlusConfig {

    /**
     * TenantLineInterceptor是内置的多租户插件
     */
    @Bean
    @Order(1)
    public IInterceptor tenantLineInterceptor() {
        return new TenantLineInterceptor(new TenantLineHandler() {
            /**
             * 当前租户ID
             */
            @Override
            public Expression getTenantId() {
                String currentTenantId = "test_tenant_1";//可以从请求上下文中获取（cookie、session、header等）
                return new StringValue(currentTenantId);
            }

            /**
             * 租户字段名
             */
            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            /**
             * 根据表名判断是否忽略拼接多租户条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return TenantLineHandler.super.ignoreTable(tableName);
            }
        });
    }

    /**
     * DynamicTableNameInterceptor是内置的动态表名插件
     */
    @Bean
    @Order(2)
    public IInterceptor dynamicTableNameInterceptor() {
        return new DynamicTableNameInterceptor(new TableNameHandler() {
            @Override
            public String dynamicTableName(String sql, String tableName) {
                if ("test_log".equals(tableName)) {
                    return tableName + "_" + LocalDateTime.now().getYear();
                }
                return tableName;
            }
        });
    }

    /**
     * PaginationInterceptor是内置的分页插件（分页插件一般情况放置最后）
     */
    @Bean
    @Order(9)
    public IInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 自定义插件注入，注入位置按实际情况
     */
    @Bean
    @Order(0)
    public IInterceptor myStatInterceptor() {
        return new MyStatInterceptor();
    }
}
~~~

3. 正常使用JdbcTemplate执行SQL语句，代码零入侵，使用体验超棒

~~~ java
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert() {
        this.jdbcTemplate.update("insert into test_user(id,name) values('1','wangwu')");
        //最终执行SQL：insert into test_user(id,name,tenant_id) values('1','wangwu','test_tenant_1')
    }

    public void delete() {
        this.jdbcTemplate.update("delete from test_user where id='1'");
        //最终执行SQL：delete from test_user where id='1' and tenant_id='test_tenant_1'
    }

    public void update() {
        this.jdbcTemplate.update("update test_user set name='lisi' where id='1'");
        //最终执行SQL：update test_user set name='lisi' where id='1' and tenant_id='test_tenant_1'
    }

    public List<Map<String, Object>> query() {
        return this.jdbcTemplate.queryForList("select * from test_user");
        //最终执行SQL：select * from test_user where tenant_id='test_tenant_1'
    }

    public PageInfo<Map<String, Object>> page1() {
        PageHelper.startPage(1, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");
        //最终执行SQL：select * from test_user LIMIT 0，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        //PageInfo对象包含了分页信息（总行数等）
        return page;
    }
~~~

# 多租户插件

1. 注入多租户插件

~~~ java
    /**
     * TenantLineInterceptor是内置的多租户插件插件
     */
    @Bean
    @Order(1)
    public IInterceptor tenantLineInterceptor() {
        return new TenantLineInterceptor(new TenantLineHandler() {
            /**
             * 当前租户ID
             */
            @Override
            public Expression getTenantId() {
                String currentTenantId = "test_tenant_1";//可以从请求上下文中获取（cookie、session、header等）
                return new StringValue(currentTenantId);
            }

            /**
             * 租户字段名
             */
            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            /**
             * 根据表名判断是否忽略拼接多租户条件
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return TenantLineHandler.super.ignoreTable(tableName);
            }
        });
    }
~~~

2. service层执行SQL时自动添加租户字段

~~~ java
    public void insert() {
        this.jdbcTemplate.update("insert into test_user(id,name) values('1','wangwu')");
        //最终执行SQL：insert into test_user(id,name,tenant_id) values('1','wangwu','test_tenant_1')
    }

    public void delete() {
        this.jdbcTemplate.update("delete from test_user");
        //最终执行SQL：delete from test_user where tenant_id='test_tenant_1'
    }

    public void update() {
        this.jdbcTemplate.update("update test_user set name='lisi' where id='1'");
        //最终执行SQL：update test_user set name='lisi' where id='1' and tenant_id='test_tenant_1'
    }

    public List<Map<String, Object>> query() {
        return this.jdbcTemplate.queryForList("select * from test_user");
        //最终执行SQL：select * from test_user where tenant_id='test_tenant_1'
    }
~~~

# 分页插件

1. 注入分页插件

~~~ java
    /**
     * PaginationInterceptor是内置的分页插件（分页插件一般情况放置最后）
     */
    @Bean
    @Order(9)
    public IInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
~~~

2. service层执行SQL时自动对SQL进行分页查询

~~~ java
    public PageInfo<Map<String, Object>> page1() {
        PageHelper.startPage(1, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");
        //最终执行SQL：select * from test_user LIMIT 0，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        //PageInfo对象包含了分页信息（总行数等）
        return page;
    }

    public PageInfo<Map<String, Object>> page2() {
        PageHelper.startPage(2, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");
        //最终执行SQL：select * from test_user LIMIT 2，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        //PageInfo对象包含了分页信息（总行数等）
        return page;
    }
~~~

3. 自定义分页

当插件不支持的数据库分页，可以通过`PageHelper.registerDialectAlias(String alias, Class clazz) `注册一个自己分页实现类即可，也可以覆盖已支持的数据库分页。

# 动态表名插件

1. 注入分页插件

~~~ java
    /**
     * DynamicTableNameInterceptor是内置的动态表名插件
     */
    @Bean
    @Order(2)
    public IInterceptor dynamicTableNameInterceptor() {
        return new DynamicTableNameInterceptor(new TableNameHandler() {
            @Override
            public String dynamicTableName(String sql, String tableName) {
                if ("test_log".equals(tableName)) {
                    return tableName + "_" + LocalDateTime.now().getYear();
                }
                return tableName;
            }
        });
    }
~~~

2. service层执行SQL时自动对SQL进行分页查询

~~~ java
    public List<Map<String, Object>> getTestLogList() {
        return this.jdbcTemplate.queryForList("select * from test_log");
        //最终执行SQL：select * from test_log_2023
    }
~~~

# 自定义插件

示例：写一个打印SQL语句、执行参数、以及执行SQL耗时的监控插件。

1. 编写MyStatInterceptor插件

~~~ java
/**
 * SQL监控插件
 */
@Slf4j
public class MyStatInterceptor implements IInterceptor {
    /**
     * 自定义插件是否支持
     */
    @Override
    public boolean supportMethod(final MethodInvocationInfo methodInfo) {
        return IInterceptor.super.supportMethod(methodInfo);
    }

    /**
     * SQL执行前方法（主要用于对SQL进行修改）
     */
    @Override
    public void beforePrepare(final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        log.info("执行SQL开始时间：{}", LocalDateTime.now());
        log.info("原始SQL：{}", Arrays.toString(methodInfo.getBatchSql()));
        log.info("调用方法名称：{}", methodInfo.getName());
        log.info("调用方法入参：{}", Arrays.toString(methodInfo.getArgs()));

        methodInfo.putUserAttribute("startTime", LocalDateTime.now());
    }

    /**
     * SQL执行完成后方法（主要用于对返回值修改）
     *
     * @param result 原始返回对象
     * @return 处理后的返回对象
     */
    @Override
    public Object beforeFinish(Object result, final MethodInvocationInfo methodInfo, JdbcTemplate jdbcTemplate) {
        log.info("执行SQL结束时间：{}", LocalDateTime.now());
        LocalDateTime startTime = (LocalDateTime) methodInfo.getUserAttribute("startTime");
        log.info("执行SQL耗时：{}毫秒", Duration.between(startTime, LocalDateTime.now()).toMillis());
        return result;
    }
}
~~~

2. 注入自定义插件

~~~ java
    /**
     * 自定义插件注入，注入位置按实际情况
     */
    @Bean
    @Order(0)
    public IInterceptor myStatInterceptor() {
        return new MyStatInterceptor();
    }
~~~

3. 查看效果（查看打印日志）

~~~ log
c.g.d.j.p.s.config.MyStatInterceptor     : 原始SQL：select * from test_user
c.g.d.j.p.s.config.MyStatInterceptor     : 入参：[select * from test_user]
c.g.d.j.p.s.config.MyStatInterceptor     : 执行SQL开始时间：2023-04-23T16:35:58.151
c.g.d.j.p.s.config.MyStatInterceptor     : 执行SQL结束时间：2023-04-23T16:35:58.655
c.g.d.j.p.s.config.MyStatInterceptor     : 执行SQL耗时：503毫秒
~~~



# JdbcTemplate支持

- 所有batchUpdate、update、query、queryForList、queryForMap、queryForObject、queryForRowSet、queryForStream都支持（但是方法第一个入参必须是sql语句的才支持）
- 所有query开头的方法都支持分页插件（但是只有返回类型是List<?>的才支持查询汇总信息）
- 如果有其他方法需要支持的，可以联系作者
- 也可以手动调用`MethodActionRegister.register(Class<JdbcTemplate> clazz, MethodActionInfo actionInfo, String name, Class<?>... parameterTypes)`方法进行注册使jdbc-plus支持该方法

支持的方法明细（里面注释内容就是支持的JdbcTemlate方法）：

~~~ java
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

/**
     * SqlRowSet queryForRowSet(String sql)
     */
    QUERYFORROWSET_SQL,

    /**
     * SqlRowSet queryForRowSet(String sql, @Nullable Object... args)
     */
    QUERYFORROWSET_SQL_ARGS,

    /**
     * SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes)
     */
    QUERYFORROWSET_SQL_ARGS_ARGTYPES,


    /**
     * Stream<T> queryForStream(String sql, @Nullable PreparedStatementSetter pss, RowMapper<T> rowMapper)
     */
    QUERYFORSTREAM_SQL_PSS_ROWMAPPER,

    /**
     * Stream<T> queryForStream(String sql, RowMapper<T> rowMapper)
     */
    QUERYFORSTREAM_SQL_ROWMAPPER,

    /**
     * Stream<T> queryForStream(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    QUERYFORSTREAM_SQL_ROWMAPPER_ARGS,
~~~





# 联系我



方式一：Git上提Issue





# 鸣谢

欢迎各路好汉一起来参与完善 jdbc-plus，感兴趣的可以在github点个 ⭐ ，有任何问题和建议欢迎提交 Issue ！

感谢以下开源项目：

* mybatis-plus

* Mybatis-PageHelper
