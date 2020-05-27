/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.seal.sharding.common.kit;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.seal.sharding.common.page.LegaPage;
import com.seal.sharding.common.query.QueryCondition;
import com.seal.sharding.common.query.QueryItem;
import com.seal.sharding.common.utils.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * SQL 辅助类
 * </p>
 *
 * @author hubin
 * @Date 2016-11-06
 */
@Slf4j
public final class LegaSqlHelper {

    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     *
     * @param clazz 实体类
     * @return SqlSession
     */
    public static SqlSession sqlSessionBatch(Class<?> clazz) {
        return SqlSessionUtils.getSqlSession(GlobalConfigUtils.currentSessionFactory(clazz), ExecutorType.BATCH, null);
    }

    /**
     * <p>
     * 获取sqlSession
     * </p>
     *
     * @param clazz 对象类
     * @return
     */
    private static SqlSession getSqlSession(Class<?> clazz) {
        return SqlSessionUtils.getSqlSession(GlobalConfigUtils.currentSessionFactory(clazz));
    }

    /**
     * <p>
     * 获取Session
     * </p>
     *
     * @param clazz 实体类
     * @return SqlSession
     */
    public static SqlSession sqlSession(Class<?> clazz) {
        return getSqlSession(clazz);
    }

    /**
     * <p>
     * 获取TableInfo
     * </p>
     *
     * @param clazz 对象类
     * @return TableInfo 对象表信息
     */
    public static TableInfo table(Class<?> clazz) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(tableInfo, "Error: Cannot execute table Method, ClassGenricType not found .");
        return tableInfo;
    }

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

    /**
     * <p>
     * 删除不存在的逻辑上属于成功
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean delBool(Integer result) {
        return null != result && result >= 0;
    }

    /**
     * <p>
     * 返回SelectCount执行结果
     * </p>
     *
     * @param result
     * @return int
     */
    public static int retCount(Integer result) {
        return (null == result) ? 0 : result;
    }

    /**
     * <p>
     * 从list中取第一条数据返回对应List中泛型的单个结果
     * </p>
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> E getObject(List<E> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            int size = list.size();
            if (size > 1) {
                log.warn(String.format("Warn: execute Method There are  %s results.", size));
            }
            return list.get(0);
        }
        return null;
    }

    /**
     * <p>
     * 填充Wrapper
     * </p>
     *
     * @param page    分页对象
     * @param wrapper SQL包装对象
     */
    @SuppressWarnings("unchecked")
    public static Wrapper<?> fillWrapper(IPage<?> page, Wrapper<?> wrapper) {
        if (null == page) {
            return wrapper;
        }
        LegaPage lp = (LegaPage) page;
        if (ArrayUtils.isEmpty(lp.ascs())
                && ArrayUtils.isEmpty(lp.descs())
                && ObjectUtils.isEmpty(lp.getConditions())) {
            return wrapper;
        }
        QueryWrapper qw;
        if (null == wrapper) {
            qw = new QueryWrapper<>();
        } else {
            qw = (QueryWrapper) wrapper;
        }
        // 排序
        if (ArrayUtils.isNotEmpty(lp.ascs())) {
            qw.orderByAsc(lp.ascs());
        }
        if (ArrayUtils.isNotEmpty(lp.descs())) {
            qw.orderByDesc(lp.descs());
        }
        // QueryCondition 参数查询
        if (ObjectUtils.isNotEmpty(lp.getConditions())) {
            qw = fillWrapperWithConditions(lp.getConditions(), qw);
        }
        return qw;
    }

    public static Wrapper<?> fillWrapper(QueryCondition queryCondition, Wrapper<?> wrapper) {
        if (null == queryCondition) {
            return wrapper;
        }
        if (ObjectUtils.isEmpty(queryCondition.getAscs()) && ObjectUtils.isEmpty(queryCondition.getDescs()) && ObjectUtils.isEmpty(queryCondition.getConditions())) {
            return wrapper;
        }
        QueryWrapper qw;
        if (null == wrapper) {
            qw = new QueryWrapper<>();
        } else {
            qw = (QueryWrapper) wrapper;
        }
        // 排序
        if (ObjectUtils.isNotEmpty(queryCondition.getAscs())) {
            qw.orderByAsc(queryCondition.getAscs().toArray());
        }
        if (ObjectUtils.isNotEmpty(queryCondition.getDescs())) {
            qw.orderByDesc(queryCondition.getDescs().toArray());
        }
        // QueryCondition 参数查询
        if (ObjectUtils.isNotEmpty(queryCondition.getConditions())) {
            qw = fillWrapperWithConditions(queryCondition.getConditions(), qw);
        }
        return qw;
    }


    /**
     * @param conditions
     * @param wrapper
     * @return com.baomidou.mybatisplus.mapper.com.seal.sharding.auth.server.mapper.Wrapper<?>
     * @description
     * @author: liupan
     * @date: 2018/8/10 10:53
     */
    public static QueryWrapper<?> fillWrapperWithConditions(List<QueryItem> conditions, QueryWrapper<?> wrapper) {

        // wrapper 不存创建一个 Condition
        if (wrapper == null) {
            wrapper = new QueryWrapper<>();
        }
        //  参数查询 这里提供 5种 方式查询
        if (CollectionUtils.isNotEmpty(conditions)) {
            for (QueryItem condition : conditions) {
                switch (condition.getQueryType()) {
                    case "like":
                        wrapper.like(condition.getField(), String.format("%s", condition.getValue()));
                        break;
                    case "neq":
                        wrapper.ne(condition.getField(), condition.getValue());
                        break;
                    case "llike":
                        wrapper.likeLeft(condition.getField(), String.format("%s", condition.getValue()));
                        break;
                    case "rlike":
                        wrapper.likeRight(condition.getField(), String.format("%s", condition.getValue()));
                        break;
                    case "eq":
                        wrapper.eq(condition.getField(), condition.getValue());
                        break;
                    case "ge":
                        wrapper.ge(condition.getField(), condition.getValue());
                        break;
                    case "le":
                        wrapper.le(condition.getField(), condition.getValue());
                        break;
                    case "isnull":
                        wrapper.isNull(condition.getField());
                        break;
                    case "in":
                        wrapper.in(condition.getField(), Arrays.stream(Optional.ofNullable(condition.getValue().toString().split(",")).orElseGet(() -> new String[]{})).toArray());
                        break;
                    case "between":
                        //
                        String valueStr = StringHelper.getObjectValue(condition.getValue());
                        String[] values = valueStr.split(",");
                        if (values.length == 2) {
                            wrapper.between(condition.getField(), values[0], values[1]);
                        }
                        break;
                    default:
                        // 默认为 eq
                        wrapper.eq(condition.getField(), condition.getValue());
                }
            }
        }
        return wrapper;
    }


    /**
     * @param records
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @description
     * @author: liupan
     * @date: 2018/10/12 10:17
     */
    public static List<Map<String, Object>> convertMapKeyToCamel(List<Map<String, Object>> records) {

        List<Map<String, Object>> newRecords = Lists.newArrayList();
        if (records != null && records.size() > 0) {
            for (Map<String, Object> record : records) {
                Map<String, Object> newRecord = Maps.newHashMap();
                record.entrySet().forEach((entry) -> {
                    newRecord.put(StringUtils.underlineToCamel(entry.getKey()), entry.getValue());
                });
                newRecords.add(newRecord);
            }
        }
        return newRecords;
    }

    /**
     * @param record
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @description
     * @author: hsc
     * @date: 2018/10/12 10:17
     */
    public static Map<String, Object> convertMapKeyToCamel(Map<String, Object> record) {

        Map<String, Object> newRecord = Maps.newHashMap();
        if (record != null) {
            record.entrySet().forEach((entry) -> {
                newRecord.put(StringUtils.underlineToCamel(entry.getKey()), entry.getValue());
            });
        }
        return newRecord;
    }

}
