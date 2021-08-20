package com.ming.mall.common.exception;

import com.ming.mall.common.api.IErrorCode;

/**
 * @Author: ming
 * @create: 2021-08-18 13:15
 * @program: demo-mall
 * 断言处理类，用于抛出各种API异常
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
