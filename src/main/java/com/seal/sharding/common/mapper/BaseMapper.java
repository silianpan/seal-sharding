package com.seal.sharding.common.mapper;

import java.io.Serializable;

/**
 * @author: hsc
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
     * @author: hsc
     * @date: 2018/8/8 10:54
     */
    Integer delPhysicalById(Serializable id);
}
