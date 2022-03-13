package dev.yxy.advance;

import dev.yxy.advance.service.IDeptService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdvanceTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDeptService deptService;

    @Test
    public void testSlave() {
        logger.info("[testSlave] - {}", deptService.querySlave());
    }

    @Test
    public void testMaster() {
        logger.info("[testMaster] - {}", deptService.queryMaster());
    }

    @Test
    public void testTransactional() {
        try {
            deptService.insertRand();
        } catch (Exception e) {
            logger.warn("[testTransactional] - {}", e.getMessage());
        }
        logger.info("[testTransactional] - {}", deptService.queryMaster());
    }
}
