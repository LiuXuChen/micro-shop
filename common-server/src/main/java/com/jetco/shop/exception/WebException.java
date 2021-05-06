package com.jetco.shop.exception;

/**
 * <p>
 *     WEB请求相关异常
 * </p>
 * @author liuhongwei
 * @version 1.0
 * @since 2020-01-17
 */
public class WebException extends AbstractException {

    public WebException(String message) {
        super(message, 400);
    }

    public WebException() {
        super("请求失败", 400);
    }
}