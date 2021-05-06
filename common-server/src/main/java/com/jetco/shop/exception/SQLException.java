package com.jetco.shop.exception;

/**
 * <p>
 *     SQL异常
 * </p>
 * @author liuhongwei
 * @version 1.0
 * @since 2019-12-24
 */
public class SQLException extends AbstractException {

    public SQLException(String message) {
        super(message, 4000);
    }

    public SQLException() {
        super("执行SQL出现错误，请查看日志", 4000);
    }

}
