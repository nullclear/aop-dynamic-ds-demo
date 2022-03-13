package dev.yxy.advance.service;

import java.util.Map;

/**
 * 部门服务
 *
 * @author atom
 * @create 2022/03/13 17:44
 * @update 2022/03/13 17:44
 * @origin aop-dynamic-ds-demo
 */
public interface IDeptService {

    /**
     * 在从库查询一条数据
     */
    Map<String, String> querySlave();

    /**
     * 在主库查询一条数据
     */
    Map<String, String> queryMaster();

    /**
     * 随机插入
     */
    int insertRand();
}
