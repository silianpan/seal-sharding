package com.seal.sharding.common.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.seal.sharding.common.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author: hsc
 * @date: 2018/7/26 16:16
 * @description mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 * @see
 */
@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("meta object insert fill ");
        // mybatis-plus版本2.0.9+
        Object crtTime = getFieldValByName("crtTime", metaObject);
        Object crtUser = getFieldValByName("crtUser", metaObject);

        if (crtTime == null) {
            // mybatis-plus版本3.0.2
            setFieldValByName("crtTime", LocalDateTime.now(), metaObject);
        }
        if (crtUser == null) {
            setFieldValByName("crtUser", BaseContextHandler.getUserID(), metaObject);
        }

        log.debug("crtOn {} crtBy {}", getFieldValByName("crtOn", metaObject), getFieldValByName("crtBy", metaObject));


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("meta object update fill");
        setFieldValByName("updTime", LocalDateTime.now(), metaObject);
        setFieldValByName("updUser", BaseContextHandler.getUserID(), metaObject);

        log.debug("updOn {} updBy {}", getFieldValByName("updOn", metaObject), getFieldValByName("updBy", metaObject));

    }
}
