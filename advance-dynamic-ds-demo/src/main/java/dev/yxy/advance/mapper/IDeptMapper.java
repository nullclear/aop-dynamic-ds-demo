package dev.yxy.advance.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
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
@DS("master")
@Mapper
public interface IDeptMapper {

    /**
     * 在从库查询一条数据
     */
    @DS("slave")
    Map<String, String> querySlave();

    /**
     * 在主库查询一条数据
     */
    Map<String, String> queryMaster();

    /**
     * 在主库随机插入
     */
    int insertRand();
}
