package dev.yxy.routing.config;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源
 *
 * @author yuanxy
 * @create 2022/3/9 17:31
 * @update 2022/3/9 17:31
 * @origin aop-dynamic-ds-demo
 */
@Primary
@Component
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Resource(name = "ds01")
    private DataSource ds01;

    @Resource(name = "ds02")
    private DataSource ds02;

    @PostConstruct
    private void init() {
        Map<Object, Object> map = new HashMap<>();
        map.put("ds01", ds01);
        map.put("ds02", ds02);
        super.setTargetDataSources(map);
        super.setDefaultTargetDataSource(ds01);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return "ds02";
    }
}
