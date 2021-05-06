package com.jetco.shop.exception;

/**
 * @author liuhongwei
 * @since 2019-12-24
 * @version 1.0
 */
public class PermissionException extends AbstractException {

    public PermissionException(String message) {
        super(message, 401);
    }

    public PermissionException() {
        super("没有权限", 401);
    }

}
