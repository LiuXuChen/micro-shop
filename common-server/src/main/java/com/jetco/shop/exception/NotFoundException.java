package com.jetco.shop.exception;

/**
 * <p>
 *     未找到异常
 * </p>
 * @author liuhongwei
 * @version 1.0
 * @since 2019-12-21
 */
public class NotFoundException extends AbstractException {

    public NotFoundException(String message) {
        super(message, 999);
    }

    public NotFoundException() {
        super("未找到相应的资源", 999);
    }
}
