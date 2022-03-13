package dev.yxy.advance;

import com.baomidou.dynamic.datasource.aop.DynamicLocalTransactionInterceptor;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.tx.ConnectionFactory;
import com.baomidou.dynamic.datasource.tx.ConnectionProxy;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import java.util.List;
import java.util.Map;

/**
 * NOTE - 2022/03/12 进阶动态数据源参考
 * https://blog.csdn.net/w57685321/article/details/106823660
 * <p>
 * NOTE - 2022/03/13 创建数据源流程
 * 生成各种数据池的创建器 {@link DynamicDataSourceCreatorAutoConfiguration}
 * 注入到默认创建器 {@link DynamicDataSourceCreatorAutoConfiguration#dataSourceCreator(List)}
 * 循环判断是否支持选择使用的创建器 {@link DefaultDataSourceCreator#createDataSource(DataSourceProperty)}
 * Hikari创建器 {@link HikariDataSourceCreator#doCreateDataSource(DataSourceProperty)}
 * 创建数据源Map {@link AbstractDataSourceProvider#createDataSourceMap(Map)}
 * <p>
 * NOTE - 2022/03/13 事务管理
 * 注册AOP {@link DynamicDataSourceAutoConfiguration#dynamicTransactionAdvisor()}
 * 拦截流程 {@link DynamicLocalTransactionInterceptor#invoke(MethodInvocation)}
 * 开始事务 {@link DataSourceTransactionManager#doBegin(Object, TransactionDefinition)}
 * 关闭自动提交 {@link ConnectionFactory#putConnection(String, ConnectionProxy)}
 */
@SuppressWarnings("ALL")
@SpringBootApplication
public class AdvanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvanceApplication.class, args);
    }
}
