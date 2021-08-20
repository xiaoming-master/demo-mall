package com.ming.mall.common.exception;

import com.ming.mall.common.api.IErrorCode;

/**
 * 自定义API异常
 * @Author: ming
 * @create: 2021-08-18 13:12
 * @program: demo-mall
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
