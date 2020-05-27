package com.seal.sharding.common.ret;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: panliu
 * @date: 2018/7/3 22:52
 * @description RetBack 用于返回值封装，也用于服务端与客户端的 json 数据通信
 * 服务器不会返回异常 只会返回一个code 用于标识 异常
 */
public class RetBack extends HashMap {

    public static final String STATUS_NAME = "status";
    public static final long SUCCESS = 0;
    public static final long ERROR = -1;
    private static final long serialVersionUID = 7138263523012252024L;

    public static RetBack ok() {
        return ok("操作成功");
    }


    public RetBack() {
        this.put("timestamp", DateTime.now());
    }

    /**
     * @param msg
     * @return com.seal.sharding.common.ret.RetBack
     * @description 成功的结果集
     * @author: panliu
     * @date: 2018/7/16 23:25
     */
    public static RetBack ok(String msg) {
        return new RetBack().setOk().set("msg", msg);
    }

    public static RetBack ok(Object key, Object value) {
        return ok().set(key, value);
    }


    public static RetBack by() {
        return new RetBack();
    }

    public static RetBack create() {
        return new RetBack();
    }

    public static RetBack create(Object key, Object value) {
        return new RetBack().set(key, value);
    }

    public static RetBack fail() {
        return new RetBack().setFail();
    }

    public static RetBack fail(Object key, Object value) {
        return fail().set(key, value);
    }

    /**
     * @param code
     * @param key
     * @param value
     * @return com.seal.sharding.common.ret.RetBack
     * @description 返回一个状态为失败的结果集
     * @author: panliu
     * @date: 2018/7/17 00:04
     */
    public RetBack fail(int code, Object key, Object value) {
        this.setFail().set(key, value).set("code", code);
        return this;
    }

    public RetBack fail(int code, String msg) {
        this.setFail().set("msg", msg).set("code", code);
        return this;
    }

    /**
     * @return
     */
    public RetBack setOk() {
        super.put(RetBack.STATUS_NAME, RetBack.SUCCESS);
        return this;
    }

    public RetBack setFail() {
        super.put(RetBack.STATUS_NAME, RetBack.ERROR);
        return this;
    }

    /**
     * @param key
     * @param value
     * @return com.seal.sharding.common.ret.RetBack
     * @description 设置键值对
     * @author: panliu
     * @date: 2018/7/16 23:20
     */
    public RetBack set(Object key, Object value) {

        super.put(key, value);
        return this;
    }

    public RetBack set(Map map) {
        super.putAll(map);
        return this;
    }

    public RetBack set(RetBack ret) {
        super.putAll(ret);
        return this;
    }

    public RetBack delete(Object key) {
        super.remove(key);
        return this;
    }

    public String getStr(Object key) {
        Object s = get(key);
        return s != null ? s.toString() : null;
    }

    public Integer getInt(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.intValue() : null;
    }

    public Long getLong(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.longValue() : null;
    }

    public Number getNumber(Object key) {
        return (Number) get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean) get(key);
    }

    /**
     * @param key
     * @return boolean
     * @description key 存在，并且 value 不为 null
     * @author: panliu
     * @date: 2018/7/17 00:04
     */
    public boolean notNull(Object key) {

        return get(key) != null;
    }

    /**
     * @param key
     * @return boolean
     * @description key 不存在，或者 key 存在但 value 为null
     * @author: panliu
     * @date: 2018/7/17 00:03
     */
    public boolean isNull(Object key) {

        return get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == true));
    }

    /**
     * @param key
     * @return boolean
     * @description key 存在，并且 value 为 false，则返回 true
     * @author: panliu
     * @date: 2018/7/16 23:21
     */
    public boolean isFalse(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == false));
    }

    /**
     * @return java.lang.String
     * @description 转化成json 对象
     * @author: panliu
     * @date: 2018/7/16 23:22
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * @param ret
     * @return boolean
     * @description
     * @author: panliu
     * @date: 2018/7/16 23:24
     */
    @Override
    public boolean equals(Object ret) {
        return ret instanceof RetBack && super.equals(ret);
    }

    public boolean isOk() {
        int status = getInt(STATUS_NAME);
        return SUCCESS == status;

    }

    /**
     * @param
     * @return boolean
     * @description 判断是否是失败消息
     * @author: panliu
     * @date: 2018/7/16 23:24
     */
    public boolean isFail() {
        int status = getInt(STATUS_NAME);
        return !(SUCCESS == status);
    }

    /**
     * @param source
     * @return com.seal.sharding.common.ret.RetBack
     * @description 将字符串解析成 RetBack 对象
     * @author: panliu
     * @date: 2018/7/16 23:20
     */
    public static RetBack parse(String source) {

        if (!StringUtils.isEmpty(source)) {
            try {
                RetBack retBack = JSON.parseObject(source, new TypeReference<RetBack>() {
                });
                return retBack;
            } catch (Exception e) {
                return fail("msg", source);
            }
        } else {
            return fail("msg", source);
        }
    }

    /**
     * @param code
     * @param msg
     * @return com.seal.sharding.common.ret.RetBack
     * @description 跑出异常时调用此方法 封装异常信息
     * @author: panliu
     * @date: 2018/7/16 23:19
     */
    public static RetBack error(long code, String msg) {
        RetBack retBack = fail().set("code", code).set("msg", msg);
        return retBack;
    }

    /**
     * @param code
     * @param msg
     * @return java.lang.String
     * @description 跑出异常时调用此方法 封装异常信息
     * @author: panliu
     * @date: 2018/7/16 23:10
     */
    public static String errorJson(long code, String msg) {
        RetBack retBack = fail().set("code", code).set("msg", msg);
        return retBack.toJson();
    }

    /**
     * @param retEnum
     * @return java.lang.String
     * @description 抛出异常时调用此方法 封装异常信息
     * @author: panliu
     * @date: 2018/7/16 23:13
     */

    public static String errorJson(IMsgCodeEnum retEnum) {

        RetBack retBack = fail().set("code", retEnum.getCode()).set("msg", retEnum.getMsg());
        return retBack.toJson();
    }

    /**
     * @param retEnum
     * @return com.seal.sharding.common.ret.RetBack
     * @description 抛出异常时调用此方法 封装异常信息
     * @author: panliu
     * @date: 2018/7/16 23:19
     */
    public static RetBack error(IMsgCodeEnum retEnum) {

        RetBack retBack = fail().set("code", retEnum.getCode()).set("msg", retEnum.getMsg());
        return retBack;
    }
}
