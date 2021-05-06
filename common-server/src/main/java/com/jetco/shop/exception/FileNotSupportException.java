package com.jetco.shop.exception;

/**
 * <p>
 *    文件类型不支持上传异常
 * </p>
 *
 * @author liuhongwei
 * @version 1.0
 * @since 2019-12-24
 */
public class FileNotSupportException extends AbstractException {

    public FileNotSupportException(String message) {
        super(message, 2000);
    }

    public FileNotSupportException() {
        super("该类型的文件不支持上传 ", 2000);
    }

}
