package dev.yxy.simple.mapper;

import java.util.Map;

/**
 * 部门表
 *
 * @author atom
 * @create 2022/03/08 0:35
 * @update 2022/03/08 0:35
 * @origin aop-dynamic-ds-demo
 */
public interface IDeptMapper {

    /**
     * 查询一条数据
     */
    Map<String, String> queryOne();
}
