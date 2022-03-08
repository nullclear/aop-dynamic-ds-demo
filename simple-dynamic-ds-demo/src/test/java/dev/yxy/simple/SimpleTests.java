package dev.yxy.simple;

import dev.yxy.simple.mapper.IDeptMapper;
import dev.yxy.simple.mapper.db01.Db01DeptMapper;
import dev.yxy.simple.mapper.db02.Db02DeptMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class SimpleTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private List<IDeptMapper> list;

    @Autowired
    private Db01DeptMapper db01DeptMapper;

    @Autowired
    private Db02DeptMapper db02DeptMapper;

    @Test
    public void testList() {
        for (IDeptMapper mapper : list) {
            if (logger.isWarnEnabled()) {
                logger.warn("[testList] - {}", mapper.queryOne());
            }
        }
    }

    @Commit
    @Transactional(transactionManager = "tx01")
    @Test
    public void testDb01() {
        if (logger.isWarnEnabled()) {
            logger.warn("[testDb01] - {}", db01DeptMapper.queryOne());
        }
    }

    @Rollback
    @Transactional(transactionManager = "tx02")
    @Test
    public void testDb02() {
        if (logger.isWarnEnabled()) {
            logger.warn("[testDb02] - {}", db02DeptMapper.queryOne());
        }
    }
}
