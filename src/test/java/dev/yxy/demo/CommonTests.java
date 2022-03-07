package dev.yxy.demo;

import dev.yxy.demo.mapper.IDeptMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class CommonTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Map<String, DataSourceProperties> dbMap;

    @Autowired
    private IDeptMapper deptMapper;

    @Test
    public void testConnect() {
        Map<String, String> map = deptMapper.queryOne();
        if (logger.isInfoEnabled()) {
            logger.info("[testConnect] - {}", map);
        }
    }

    @Test
    public void testDbMap() {
        if (dbMap != null) {
            for (Map.Entry<String, DataSourceProperties> entry : dbMap.entrySet()) {
                if (logger.isInfoEnabled()) {
                    logger.info("[testDsList] - {}", entry.getKey());
                }
            }
        }
    }
}
