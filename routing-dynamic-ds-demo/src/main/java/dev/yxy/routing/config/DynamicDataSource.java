package dev.yxy.routing.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 动态数据源
 *
 * @author yuanxy
 * @create 2022/3/9 17:31
 * @update 2022/3/9 17:31
 * @origin aop-dynamic-ds-demo
 */
@Configuration(proxyBeanMethods = false)
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    public DynamicDataSource(DynamicDataSourceProperties dynamicDataSourceProperties) {
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
        Map<Object, Object> dsMap = new HashMap<>();
        this.dynamicDataSourceProperties.getDatasource().forEach((k, v) -> dsMap.put(k, this.createHikariDataSource(v)));
        super.setTargetDataSources(dsMap);
        super.setDefaultTargetDataSource(dsMap.get(this.dynamicDataSourceProperties.getPrimary()));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return Optional.ofNullable(DynamicDataSourceHolder.getContext()).orElse(dynamicDataSourceProperties.getPrimary());
    }

    private HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }
}
