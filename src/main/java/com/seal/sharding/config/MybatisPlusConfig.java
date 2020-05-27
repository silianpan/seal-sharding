package com.seal.sharding.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.seal.sharding.common.ret.RetBack;
import io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@Configuration
// 这个注解，作用相当于下面的@Bean MapperScannerConfigurer，2者配置1份即可
@MapperScan("com.seal.sharding.**.mapper")
@EnableConfigurationProperties(MybatisPlusProper.class)
@AutoConfigureAfter(SpringBootConfiguration.class)
public class MybatisPlusConfig {

    @Autowired
    private MybatisPlusProper mybatisPlusProper;

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /*
         * 【测试多租户】 SQL 解析处理拦截器<br>
         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
         */
        List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {
                try {
                    // 获取当前登录用户租户ID
                    String tenantId = "1";
                    if (!StrUtil.isEmpty(tenantId)) {
                        return new LongValue(tenantId);
                    }
                    throw new Exception(RetBack.errorJson(4001, "该租户不存在"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new LongValue(-1L);
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 这里可以判断是否过滤表
                /*if ("user".equals(tableName)) {
                    return true;
                }*/
                return false;
            }
        });

        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
//        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
//            @Override
//            public boolean doFilter(MetaObject metaObject) {
//                MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
//                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
//                if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
//                    return true;
//                }
//                return false;
//            }
//        });
        return paginationInterceptor;
    }

    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.baomidou.springboot.mapper*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
     */
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
//        scannerConfigurer.setBasePackage("com.gcs.cloud.biz.pms.**.mapper");
//        return scannerConfigurer;
//    }

//    @Bean
//    public H2KeyGenerator getH2KeyGenerator() {
//        return new H2KeyGenerator();
//    }

    /**
     * 性能分析拦截器，不建议生产使用
     */
//    @Bean
//    @Profile({"dev","test"})
//    public PerformanceInterceptor performanceInterceptor(){
//        return new PerformanceInterceptor();
//    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setPlugins(new Interceptor[]{this.paginationInterceptor()});
        if (this.mybatisPlusProper.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(this.mybatisPlusProper.getConfigurationProperties());
        }
        if (StringUtils.hasLength(this.mybatisPlusProper.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(this.mybatisPlusProper.getTypeAliasesPackage());
        }
        // TODO 自定义枚举包
        if (StringUtils.hasLength(this.mybatisPlusProper.getTypeEnumsPackage())) {
            factory.setTypeEnumsPackage(this.mybatisPlusProper.getTypeEnumsPackage());
        }
        if (this.mybatisPlusProper.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(this.mybatisPlusProper.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(this.mybatisPlusProper.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(this.mybatisPlusProper.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.mybatisPlusProper.resolveMapperLocations())) {
            factory.setMapperLocations(this.mybatisPlusProper.resolveMapperLocations());
        }
        // TODO 此处必为非 NULL
        GlobalConfig globalConfig = this.mybatisPlusProper.getGlobalConfig();
        factory.setGlobalConfig(globalConfig);
        return factory.getObject();
    }
}
