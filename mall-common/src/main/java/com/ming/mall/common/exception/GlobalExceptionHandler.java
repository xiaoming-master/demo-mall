package com.ming.mall.common.exception;

import com.ming.mall.common.api.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 *
 * @Author: ming
 * @create: 2021-08-18 13:16
 * @program: demo-mall
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理ApiException
     * @param apiException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public CommonResult handle(ApiException apiException) {
        if (apiException.getErrorCode() != null) {
            return CommonResult.failed(apiException.getErrorCode());
        }
        return CommonResult.failed(apiException.getMessage());
    }
}
