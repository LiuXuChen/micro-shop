package com.jetco.shop.exception;

/**
 * <p>
 *    公共抽象异常
 * </p>
 *
 * @author liuhongwei
 * @version 1.0
 * @since 2019-12-21
 */
public abstract class AbstractException extends RuntimeException {

    protected final int code;

    protected AbstractException(String message, int code) {
        super(message != null && message.trim().length() != 0 ? message : "错误");
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
