package com.esther.fluxsync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.esther.fluxsync.ds.RoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.*;

/**
 * 数据源配置类。
 * <p>
 * 定义并管理应用的多数据源，支持基于 {@link RoutingDataSource} 的动态路由切换。
 * 本类配置了用户库数据源、默认数据源，以及对应的事务管理器。
 * </p>
 */
@Configuration
class DataSourceConfig {

    /**
     * 定义用户库数据源 Bean。
     * <p>
     * 使用配置前缀 <code>app.datasource.fluxsync-user</code> 加载数据源属性。
     * </p>
     *
     * @return 配置好的 {@link DataSource} 实例
     */
    @Bean("dsFluxsyncUser")
    @ConfigurationProperties(prefix = "app.datasource.fluxsync-user")
    public DataSource dsFluxsyncUser() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 定义动态数据源 Bean。
     * <p>
     * 将多个物理数据源映射到逻辑库名，并通过 {@link RoutingDataSource} 进行路由。
     * 默认库为 <code>fluxsync_user</code>。
     * </p>
     *
     * @param ds1 用户库数据源
     * @return 配置好的 {@link DataSource}（主数据源）
     */
    @Primary
    @Bean
    public DataSource dataSource(@Qualifier("dsFluxsyncUser") DataSource ds1
                                 ) {
        RoutingDataSource routing = new RoutingDataSource();
        Map<Object, Object> targets = new HashMap<>();

        // 库名
        targets.put("fluxsync_user", ds1);

        routing.setTargetDataSources(targets);
        routing.setDefaultTargetDataSource(ds1);    // 默认库
        return routing;
    }

    /**
     * 定义事务管理器 Bean。
     * <p>
     * 基于动态数据源，统一管理数据库事务。
     * </p>
     *
     * @param dataSource 注入的数据源
     * @return {@link DataSourceTransactionManager} 实例
     */
    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
