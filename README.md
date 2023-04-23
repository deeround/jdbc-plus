#  🚀 jdbc-plus简介

 🚀 jdbc-plus是一款基于JdbcTemplate增强工具包， 基于JdbcTemplate已实现分页、多租户等插件，可自定义扩展插件。项目地址： https://github.com/deeround/jdbc-plus



# 快速开始



1. 引入jdbc-plus-spring-boot-starter

~~~ xml
<dependency>
    <groupId>com.github.deeround</groupId>
    <artifactId>jdbc-plus-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
~~~

2. 注入需要使用的插件

~~~ java
@Configuration
public class JdbcPlusConfig {

    /**
     * PaginationInterceptor是内置的分页插件（分页插件一定要注入在TenantLineHandler之后，可以通过Order来控制顺序）
     */
    @Bean
    @Order(9)
    public IInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * TenantLineHandler是内置的多租户插件插件
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
}
~~~



# 多租户插件



1. 注入多租户插件

~~~ java
    /**
     * TenantLineHandler是内置的多租户插件插件
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
    @Autowired
    JdbcTemplate jdbcTemplate;

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
     * PaginationInterceptor是内置的分页插件（分页插件一定要注入在TenantLineHandler之后，可以通过Order来控制顺序）
     */
    @Bean
    @Order(9)
    public IInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
~~~

2. service层执行SQL时自动对SQL进行分页查询

~~~ java
    @Autowired
    JdbcTemplate jdbcTemplate;

    public PageInfo<Map<String, Object>> page1() {
        PageHelper.startPage(1, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");//最终执行SQL：select * from test_user LIMIT 0，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);//PageInfo对象包含了分页信息（总行数等）
        return page;
    }

    public PageInfo<Map<String, Object>> page2() {
        PageHelper.startPage(2, 2);
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select * from test_user");//最终执行SQL：select * from test_user LIMIT 2，2
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);//PageInfo对象包含了分页信息（总行数等）
        return page;
    }
~~~

3. 自定义分页

当插件不支持的数据库分页，可以通过`PageHelper.registerDialectAlias(String alias, Class clazz) `注册一个自己分页实现类即可，也可以覆盖已支持的数据库分页。



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
        log.info("原始SQL：{}", methodInfo.getSql());
        log.info("入参：{}", Arrays.toString(methodInfo.getArgs()));
        log.info("执行SQL开始时间：{}", LocalDateTime.now());
        methodInfo.getUserAttributes().put("startTime", LocalDateTime.now());
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
        LocalDateTime startTime = (LocalDateTime) methodInfo.getUserAttributes().get("startTime");
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



# ★ 鸣谢 ★



https://github.com/baomidou/mybatis-plus

https://github.com/pagehelper/Mybatis-PageHelper

https://github.com/deeround/jdbc-plus





