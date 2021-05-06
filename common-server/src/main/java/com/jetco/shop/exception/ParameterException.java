package com.jetco.shop.exception;

/**
 *
 * <p>
 *     接口入参异常
 * </p>
 * @author liuhongwei
 * @version 1.0
 * @since 2019-12-24
 */
public class ParameterException extends AbstractException {

    public ParameterException(String message) {
        super(message, 3000);
    }

    public ParameterException() {
        super("参数错误", 3000);
    }

}
