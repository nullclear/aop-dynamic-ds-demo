package dev.yxy.routing.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * 数据源配置抽象类
 *
 * @author atom
 * @create 2022/03/08 0:15
 * @update 2022/03/08 0:15
 * @origin aop-dynamic-ds-demo
 */
public abstract class AbstractDataSourceConfiguration {

    @Autowired
    private MybatisPlusProperties properties;

    /**
     * 数据源属性
     */
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 数据源
     */
    public DataSource dataSource(DataSourceProperties properties) {
        return this.createHikariDataSource(properties);
    }

    /**
     * 数据源事务
     */
    public DataSourceTransactionManager transactionManager(DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }

    // -------------------------------------------------- 内部方法 --------------------------------------------------

    /**
     * 创建 Hikari 数据源
     */
    private HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }
}
