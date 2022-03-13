package dev.yxy.advance.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import dev.yxy.advance.mapper.IDeptMapper;
import dev.yxy.advance.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 部门服务
 *
 * @author atom
 * @create 2022/03/13 17:45
 * @update 2022/03/13 17:45
 * @origin aop-dynamic-ds-demo
 */
@Service
public class DeptService implements IDeptService {

    @Autowired
    private IDeptMapper deptMapper;

    @Override
    public Map<String, String> querySlave() {
        return deptMapper.querySlave();
    }

    @DSTransactional
    @Override
    public Map<String, String> queryMaster() {
        return deptMapper.queryMaster();
    }

    @DSTransactional
    @Override
    public int insertRand() {
        for (int i = 0; i < 10; i++) {
            int rows = deptMapper.insertRand();
        }
        throw new RuntimeException("rollback");
    }
}
