package dev.yxy.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * 数据源配置类
 *
 * @author atom
 * @create 2022/03/08 0:15
 * @update 2022/03/08 0:15
 * @origin aop-dynamic-ds-demo
 */
@Configuration(proxyBeanMethods = false)
public class DataSourceConfiguration {

    @Bean("db01")
    @ConfigurationProperties(prefix = "ds.db01")
    public DataSourceProperties db01() {
        return new DataSourceProperties();
    }

    @Bean("db02")
    @ConfigurationProperties(prefix = "ds.db02")
    public DataSourceProperties db02() {
        return new DataSourceProperties();
    }

    @Bean("db03")
    @ConfigurationProperties(prefix = "ds.db03")
    public DataSourceProperties db03() {
        return new DataSourceProperties();
    }

    // -------------------------------------------------- 数据源 --------------------------------------------------

    @Bean("db01Ds")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    HikariDataSource dataSource(@Qualifier("db01") DataSourceProperties properties) {
        HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

    @SuppressWarnings({"unchecked", "SameParameterValue"})
    protected static <T> T createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
        return (T) properties.initializeDataSourceBuilder().type(type).build();
    }
}
