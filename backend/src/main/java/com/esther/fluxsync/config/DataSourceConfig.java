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

@Configuration
class DataSourceConfig {

    // 用户库
    @Bean("dsFluxsyncUser")
    @ConfigurationProperties(prefix = "app.datasource.fluxsync-user")
    public DataSource dsFluxsyncUser() {
        return DataSourceBuilder.create().build();
    }

    // 数据源
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

    // 事务管理器
    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
