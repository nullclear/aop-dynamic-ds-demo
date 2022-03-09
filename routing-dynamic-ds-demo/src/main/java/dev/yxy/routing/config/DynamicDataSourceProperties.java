package dev.yxy.routing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 动态数据源配置类
 *
 * @author atom
 * @create 2022/03/10 3:32
 * @update 2022/03/10 3:32
 * @origin aop-dynamic-ds-demo
 */
@Getter
@Setter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "demo")
public class DynamicDataSourceProperties {

    private String primary = "master";

    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();
}
