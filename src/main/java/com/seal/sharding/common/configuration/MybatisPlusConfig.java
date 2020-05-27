package com.seal.sharding.common.configuration;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author: liupan
 * @date: 2018/7/16 22:11
 * @description mybatis-plus 配置
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * @param
     * @return com.baomidou.mybatisplus.plugins.PaginationInterceptor
     * @description mybatis-plus分页插件 文档：http://mp.baomidou.com<br>
     * @author: liupan
     * @date: 2018/7/16 23:25
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

    /**
     * 乐观锁
     * 当要更新一条记录的时候，希望这条记录没有被别人更新
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * @param ds
     * @return org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @description 动态数据源 事务
     * @author: liupan
     * @date: 2018/7/16 23:26
     */
//    @Bean
//    @ConditionalOnBean(DynamicDataSource.class)
//    public DataSourceTransactionManager transactionManager(DynamicDataSource ds) {
//        return new DataSourceTransactionManager(ds);
//    }

    /**
     * @param
     * @return com.baomidou.mybatisplus.mapper.com.seal.sharding.auth.server.mapper.MetaObjectHandler
     * @description mybatisplus自定义填充公共字段
     * @author: liupan
     * @date: 2018/7/28 00:41
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisMetaObjectHandler();
    }

    @Bean
    public ISqlInjector logicSqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * @param
     * @return com.baomidou.mybatisplus.plugins.PerformanceInterceptor
     * @description plus 的性能优化
     * SQL执行效率插件【生产环境可以关闭】
     * @author: liupan
     * @date: 2018/7/16 23:26
     */
    @Bean
    @Profile({"dev", "test"}) // 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
        performanceInterceptor.setMaxTime(1000);
        /*<!--SQL是否格式化 默认false-->*/
        performanceInterceptor.setFormat(true);
        performanceInterceptor.setWriteInLog(true);
        return performanceInterceptor;
    }
}
