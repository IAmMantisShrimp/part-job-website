package com.yue.excetion;

import com.yue.common.Result;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 捕获全局异常进行处理
 */
@ControllerAdvice
@ResponseBody
public class PartJobExceptionHandler {

    @ExceptionHandler(PartJobException.class)
    public Result handleException(PartJobException e){
        ExceptionEnum em = e.getExceptionEnum();
        return Result.fail(em.getCode(), em.getMsg());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e) {
        return Result.fail(401, "权限异常，请重新登录");
    }

}
