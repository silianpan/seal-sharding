package com.seal.sharding.common.ret;

import lombok.Getter;

/**
 * @author: panliu
 * @date: 2018/7/16 10:48
 * @description 返回结果 code 枚举类
 * 10000 ~ 20000
 */
public enum ExceptionCodeEnum implements IMsgCodeEnum {

    // service exception
    DS_KEY_ENUM_NOT_FIND_BY_KEY(51001L, "不存在的数据源"),
    ;

    /**
     * 状态码
     */
    @Getter
    private Long code;
    /**
     * 消息
     */
    @Getter
    private String msg;

    ExceptionCodeEnum(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
