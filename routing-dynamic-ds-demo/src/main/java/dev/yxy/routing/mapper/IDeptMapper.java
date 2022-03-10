package dev.yxy.routing.mapper;

import dev.yxy.routing.annotation.DataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 部门表
 *
 * @author atom
 * @create 2022/03/08 0:35
 * @update 2022/03/08 0:35
 * @origin aop-dynamic-ds-demo
 */
@DataSource("master")
@Mapper
public interface IDeptMapper {

    /**
     * 在从库查询一条数据
     */
    @DataSource("slave")
    Map<String, String> querySlave();

    /**
     * 在主库查询一条数据
     */
    Map<String, String> queryMaster();
}
