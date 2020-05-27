package com.seal.sharding.common.ret;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;


/**
 * <p>
 * REST API 返回结果
 * </p>
 *
 * @author: panliu
 * @date: 2019-01-03 11:40
 * @description
 */

@Data
@Accessors(chain = true)
public class Rb<T> implements Serializable {

    public static final long SUCCESS = 0;
    public static final long ERROR = -1;
    /**
     * 状态
     */
    private long status;

    /**
     * 业务错误码
     */
    private long code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;

    public Rb() {
        // to do nothing
    }

    public Rb(IErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ApiErrorCode.FAILED);
        this.code = errorCode.getCode();
        this.status = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static <T> Rb ok(T data) {
        return restResult(data, Rb.SUCCESS, ApiErrorCode.SUCCESS);
    }

    public static <T> Rb ok(T data, String msg) {
        Rb rb = restResult(data, Rb.SUCCESS, ApiErrorCode.SUCCESS);
        rb.setMsg(msg);
        return rb;
    }


    public static <T> Rb failed(String msg) {
        return restResult(null, Rb.ERROR, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> Rb failed(IErrorCode errorCode) {
        return restResult(null, Rb.ERROR, errorCode);
    }

    public static <T> Rb<T> restResult(T data, long status, IErrorCode errorCode) {
        return restResult(data, status, errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> Rb<T> restResult(T data, long status, long code, String msg) {
        Rb<T> apiResult = new Rb<>();
        apiResult.setStatus(status);
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean ok() {
        return Rb.SUCCESS == status;
    }

    /**
     * 服务间调用非业务正常，异常直接释放
     */
    public T serviceData() {
        if (!ok()) {
            throw new ApiException(this.msg);
        }
        return data;
    }


}
