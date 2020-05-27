package com.seal.sharding.common.query;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author: panliu
 * @date: 2018/8/10 10:06
 * @description 搜索条件
 */
@Data
@ApiModel(value = "普通查询条件", description = "参数对象")
public final class QueryCondition {

    /**
     * 查询条件项
     */
    @ApiModelProperty(value = "查询条件项", name = "conditions", example = "[\"created_on\"]")
    private List<QueryItem> conditions;

    /**
     * <p>
     * SQL 排序 ASC 集合
     * </p>
     */
    @ApiModelProperty(value = "SQL 排序 ASC 集合", name = "ascs")
    private List<String> ascs;
    /**
     * <p>
     * SQL 排序 DESC 集合
     * </p>
     */
    @ApiModelProperty(value = "SQL 排序 DESC 集合", name = "descs")
    private List<String> descs;

    @Transient
    public Map<String, Object> getQueryMaps() {
        Map<String, Object> queryMaps = Maps.newHashMap();
        if (this.conditions != null && this.conditions.size() > 0) {
            List<QueryItem> queryItems = this.conditions;
            queryItems.forEach(item -> queryMaps.put(item.getField(), item.getValue()));
        }
        return queryMaps;
    }
}
