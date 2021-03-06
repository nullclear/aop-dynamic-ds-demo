package dev.yxy.routing;

import dev.yxy.routing.mapper.IDeptMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoutingTests {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IDeptMapper deptMapper;

    @Test
    public void testSlave() {
        logger.info("[testSlave] - {}", deptMapper.querySlave());
    }

    @Test
    public void testMaster() {
        logger.info("[testMaster] - {}", deptMapper.queryMaster());
    }
}
