package dev.yxy.simple.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * 数据源配置抽象类
 * <p>
 * 数据源自动配置类 {@link DataSourceAutoConfiguration}
 * Hikari 连接池 自动配置类 {@link org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari}
 * 数据源事务自动配置类 {@link DataSourceTransactionManagerAutoConfiguration}
 * Mybatis Plus 自动配置类 {@link MybatisPlusAutoConfiguration#sqlSessionFactory(DataSource)}
 * Mybatis Plus 自动配置类 {@link MybatisPlusAutoConfiguration#sqlSessionTemplate(SqlSessionFactory)}
 *
 * @author atom
 * @create 2022/03/08 0:15
 * @update 2022/03/08 0:15
 * @origin aop-dynamic-ds-demo
 */
@SuppressWarnings("JavadocReference")
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
     * SQL 工厂
     */
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        return this.createMybatisSqlSessionFactoryBean(dataSource, "**");
    }

    /**
     * SQL 模板
     */
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = this.properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
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

    /**
     * 创建 SQL 工厂
     */
    protected final SqlSessionFactory createMybatisSqlSessionFactoryBean(DataSource dataSource, String packageName) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/" + packageName + "/*.xml"));
        return factory.getObject();
    }
}
