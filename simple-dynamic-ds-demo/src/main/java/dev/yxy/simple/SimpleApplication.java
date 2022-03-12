package dev.yxy.simple;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * NOTE - 2022/03/12 简单动态数据源参考
 * https://blog.csdn.net/w57685321/article/details/84143469
 */
@EnableConfigurationProperties(MybatisPlusProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class SimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
