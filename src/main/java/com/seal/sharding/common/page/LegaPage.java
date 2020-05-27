package com.seal.sharding.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Maps;
import com.seal.sharding.common.query.QueryItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: liupan
 * @date: 2018/8/8 16:44
 * @description 改造 page
 */
@ApiModel(value = "分页查询参数", description = "分页对象(可增加conditions:{queryType:'',field:'',value}参数)")
public class LegaPage<T> implements IPage<T> {
    private static final long serialVersionUID = 1L;
    /**
     * 查询参数（ 不会传入到 xml 层，这里是 Controller 层与 service 层传递参数预留 ）
     */
    @Setter
    @Getter
    private List<QueryItem> conditions;

    /**
     * 查询数据列表
     */
    @ApiModelProperty(value = "返回的数据", hidden = true)
    private List<T> records = Collections.emptyList();

    /**
     * 总数，当 total 为 null 或者大于 0 分页插件不在查询总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;
    /**
     * 当前页
     */
    private long current = 1;
    /**
     * <p>
     * SQL 排序 ASC 数组
     * </p>
     */
    private String[] ascs;
    /**
     * <p>
     * SQL 排序 DESC 数组
     * </p>
     */
    private String[] descs;
    /**
     * <p>
     * 自动优化 COUNT SQL
     * </p>
     */
    private boolean optimizeCountSql = true;

    public LegaPage() {
        // to do nothing
    }

    /**
     * <p>
     * 分页构造函数
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public LegaPage(long current, long size) {
        this(current, size, 0L);
    }

    public LegaPage(long current, long size, Long total) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
    }

    /**
     * <p>
     * 是否存在上一页
     * </p>
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * <p>
     * 是否存在下一页
     * </p>
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    @ApiModelProperty(value = "总行数", hidden = true)
    public long getTotal() {
        return this.total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    @ApiModelProperty(value = "总页数", hidden = true)
    public long getSize() {
        return this.size;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String[] ascs() {
        return ascs;
    }

    public IPage<T> setAscs(List<String> ascs) {
        if (CollectionUtils.isNotEmpty(ascs)) {
            this.ascs = ascs.toArray(new String[0]);
        }
        return this;
    }


    /**
     * <p>
     * 升序
     * </p>
     *
     * @param ascs 多个升序字段
     * @return
     */
    public IPage<T> setAsc(String... ascs) {
        this.ascs = ascs;
        return this;
    }

    @Override
    public String[] descs() {
        return descs;
    }

    public IPage<T> setDescs(List<String> descs) {
        if (CollectionUtils.isNotEmpty(descs)) {
            this.descs = descs.toArray(new String[0]);
        }
        return this;
    }

    /**
     * <p>
     * 降序
     * </p>
     *
     * @param descs 多个降序字段
     * @return
     */
    public IPage<T> setDesc(String... descs) {
        this.descs = descs;
        return this;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public IPage<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder pg = new StringBuilder();
        pg.append(" Page:{ [").append(super.toString()).append("], ");
        if (records != null) {
            pg.append("records-size:").append(records.size());
        } else {
            pg.append("records is null");
        }
        return pg.append(" }").toString();
    }

    /**
     * 从 legaPage 中 获取 查询 参数的 Map 结构
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description
     * @author: liupan
     * @date: 2018-12-04 10:52
     */
    @Transient
    public Map<String, Object> getQueryMaps() {

        Map<String, Object> queryMaps = Maps.newHashMap();
        if (this.getConditions() != null && this.getConditions().size() > 0) {
            List<QueryItem> queryItems = this.getConditions();
            queryItems.forEach(item -> queryMaps.put(item.getField(), item.getValue()));
        }
        return queryMaps;
    }

}
