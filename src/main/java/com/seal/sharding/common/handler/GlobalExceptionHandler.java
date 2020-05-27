package com.seal.sharding.common.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.seal.sharding.common.ret.Rb;
import com.seal.sharding.common.ret.RetBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: liupan
 * @date: 2018/7/16 10:51
 * @description 全局的异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @param request
     * @param e
     * @return com.seal.sharding.common.ret.RetBack
     * @description 处理业务逻辑中发生的错误
     * @author: liupan
     * @date: 2018/8/1 10:43
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RetBack error500Handler(HttpServletRequest request, Exception e) {
        String errorContent = e.getMessage();
        log.error(errorContent);
        return RetBack.parse(errorContent).set("url", request.getRequestURL());
    }

    /**
     * @param request
     * @param e
     * @return com.seal.sharding.common.ret.RetBack
     * @description 处理 404错误
     * @author: liupan
     * @date: 2018/8/1 10:43
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public RetBack error404Handler(HttpServletRequest request, Exception e) {

        String errorContent = e.getMessage();
        log.error(errorContent);
        return RetBack.fail()
                .set("msg", "未找到资源")
                .set("code", HttpStatus.NOT_FOUND.value())
                .set("url", request.getRequestURL());
    }

    @ExceptionHandler(value = {MybatisPlusException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Rb errorMybatisPlusExceptionHandler(Exception e) {
        String errorContent = e.getMessage();
        return Rb.failed(errorContent);
    }

//    @ExceptionHandler(value = {HystrixRuntimeException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public Rb errorHystrixRuntimeExceptionHandler(Exception e) {
//        String errorContent = e.getCause().getMessage();
//        return Rb.failed(errorContent);
//    }

}
