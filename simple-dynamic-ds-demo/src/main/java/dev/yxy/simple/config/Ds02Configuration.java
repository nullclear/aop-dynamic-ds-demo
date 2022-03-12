package dev.yxy.simple.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
 * 数据源02配置类
 *
 * @author yuanxy
 * @create 2022/3/8 16:54
 * @update 2022/3/8 16:54
 * @origin aop-dynamic-ds-demo
 */
// 为不同的包指定不同的会话
@MapperScan(basePackages = "dev.yxy.simple.mapper.db02", sqlSessionTemplateRef = "sqlSessionTemplate02")
@Configuration
public class Ds02Configuration extends AbstractDataSourceConfiguration {

    @Bean("db02")
    @ConfigurationProperties(prefix = "ds.db02")
    @Override
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @Bean("ds02")
    @Override
    public DataSource dataSource(@Qualifier("db02") DataSourceProperties properties) {
        return super.dataSource(properties);
    }

    @Bean("sqlSessionFactory02")
    @Override
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ds02") DataSource dataSource) throws Exception {
        return super.createMybatisSqlSessionFactoryBean(dataSource, "db02");
    }

    @Bean("sqlSessionTemplate02")
    @Override
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory02") SqlSessionFactory sqlSessionFactory) {
        return super.sqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("tx02")
    @Override
    public DataSourceTransactionManager transactionManager(@Qualifier("ds02") DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        return super.transactionManager(dataSource, transactionManagerCustomizers);
    }
}
