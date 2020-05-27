package com.seal.sharding.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: panliu
 * @date: 2018/8/8 15:14
 * @description
 */
public interface BaseMapper<M> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<M> {


    /**
     * 根据id 物理删除
     *
     * @param id
     * @return int
     * @description
     * @author: panliu
     * @date: 2018/8/8 10:54
     */
    Integer delPhysicalById(Serializable id);

    /**
     * 查询所有数据，包括del_flag=1逻辑删除的数据
     *
     * @param params
     * @return
     */
    List<M> listAll(@Param("mp") Map<String, Object> params);
}
