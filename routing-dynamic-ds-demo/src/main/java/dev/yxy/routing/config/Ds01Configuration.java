package dev.yxy.routing.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源01配置类
 *
 * @author yuanxy
 * @create 2022/3/9 17:40
 * @update 2022/3/9 17:40
 * @origin aop-dynamic-ds-demo
 */
@Configuration(proxyBeanMethods = false)
public class Ds01Configuration extends AbstractDataSourceConfiguration {

    @Bean("db01")
    @ConfigurationProperties(prefix = "ds.db01")
    @Override
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @Bean("ds01")
    @Override
    public DataSource dataSource(@Qualifier("db01") DataSourceProperties properties) {
        return super.dataSource(properties);
    }

    @Bean("tx01")
    @Override
    public DataSourceTransactionManager transactionManager(@Qualifier("ds01") DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        return super.transactionManager(dataSource, transactionManagerCustomizers);
    }
}