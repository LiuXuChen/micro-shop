package com.jetco.shop.exception;

/**
 * <p>
 * Sentinel处理异常
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2021-04-11
 */
public class SentinelException extends AbstractException {

    public SentinelException(int code, String message) {
        super(message, code);
    }

    public SentinelException() {
        super("Sentinel限流、熔断限制异常", 3000);
    }
}
