package dev.yxy.advance;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.baomidou.dynamic.datasource.aop.DynamicLocalTransactionInterceptor;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.ds.AbstractRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.GroupDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.YmlDynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.strategy.LoadBalanceDynamicDataSourceStrategy;
import com.baomidou.dynamic.datasource.tx.ConnectionFactory;
import com.baomidou.dynamic.datasource.tx.ConnectionProxy;
import com.baomidou.dynamic.datasource.tx.LocalTxUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * NOTE - 2022/03/12 进阶动态数据源参考
 * https://blog.csdn.net/w57685321/article/details/106823660
 * <p>
 * NOTE - 2022/03/13 数据源创建
 * 生成连接池的Creator {@link DynamicDataSourceCreatorAutoConfiguration}
 * 注入生成默认Creator {@link DynamicDataSourceCreatorAutoConfiguration#dataSourceCreator(List)}
 * 注入生成Provider {@link YmlDynamicDataSourceProvider#defaultDataSourceCreator}
 * 注入生成路由数据源 {@link DynamicRoutingDataSource#providers}（可注入自定义数据库、配置中心等提供的数据源Provider）
 * 后置启动 {@link DynamicRoutingDataSource#afterPropertiesSet()}
 * 加载数据源 {@link YmlDynamicDataSourceProvider#loadDataSources()}
 * 生成数据源映射 {@link AbstractDataSourceProvider#createDataSourceMap(Map)}
 * 循环创建数据源 {@link DefaultDataSourceCreator#createDataSource(DataSourceProperty)}
 * 判断可用Creator {@link DataSourceCreator#support(DataSourceProperty)}
 * 创建数据源 {@link HikariDataSourceCreator#createDataSource(DataSourceProperty)}
 * 实际创建 {@link HikariDataSourceCreator#doCreateDataSource(DataSourceProperty)}
 * <p>
 * NOTE - 2022/03/13 事务管理
 * 注册事务AOP {@link DynamicDataSourceAutoConfiguration#dynamicTransactionAdvisor()}
 * 事务拦截流程 {@link DynamicLocalTransactionInterceptor#invoke(MethodInvocation)}
 * 开始事务 {@link LocalTxUtil#startTransaction()}
 * 判断是否持有事务 {@link AbstractRoutingDataSource#getConnection()}
 * 持有事务则代理连接 {@link AbstractRoutingDataSource#getConnectionProxy(String, Connection)}
 * 关闭自动提交 {@link ConnectionFactory#putConnection(String, ConnectionProxy)}
 * 根据执行情况选择方法 {@link LocalTxUtil#commit()} 或 {@link LocalTxUtil#rollback()}
 * 通知所有连接进行结束操作 {@link ConnectionFactory#notify(Boolean)}（观察者模式）
 * <p>
 * NOTE - 2022/03/14 数据源切换
 * 生成动态数据源 {@link DynamicDataSourceAutoConfiguration#dataSource()}
 * 获取每个连接 {@link DynamicRoutingDataSource#getConnection()}
 * 注解拦截设置数据源 {@link DynamicDataSourceAnnotationInterceptor#invoke(MethodInvocation)}
 * 组数据源获取Key {@link GroupDataSource#determineDsKey()}
 * 负载均衡策略 {@link LoadBalanceDynamicDataSourceStrategy#determineKey(List)}
 * <p>
 * NOTE - 2022/03/14 Spring事务管理
 * 事务自动配置 {@link TransactionAutoConfiguration}
 * 事务拦截器 {@link TransactionInterceptor#invoke(MethodInvocation)}
 * 事务拦截 {@link TransactionInterceptor#invokeWithinTransaction(Method, Class, TransactionAspectSupport.InvocationCallback)}
 * 创建事务 {@link TransactionInterceptor#createTransactionIfNecessary(PlatformTransactionManager, TransactionAttribute, String)}
 * 获取事务 {@link DataSourceTransactionManager#getTransaction(TransactionDefinition)}
 * 处理已存在的事务 {@link DataSourceTransactionManager#handleExistingTransaction(TransactionDefinition, Object, boolean)}
 * 或者开启新事务 {@link DataSourceTransactionManager#startTransaction(TransactionDefinition, Object, boolean, AbstractPlatformTransactionManager.SuspendedResourcesHolder)}
 * 挂起事务 {@link DataSourceTransactionManager#suspend(Object)}
 * 开始事务 {@link DataSourceTransactionManager#doBegin(Object, TransactionDefinition)}
 * 提交事务 {@link DataSourceTransactionManager#doCommit(DefaultTransactionStatus)}
 * 回滚事务 {@link DataSourceTransactionManager#doRollback(DefaultTransactionStatus)}
 */
@SuppressWarnings("ALL")
@SpringBootApplication
public class AdvanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvanceApplication.class, args);
    }
}
