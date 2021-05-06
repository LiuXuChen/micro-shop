package com.jetco.shop.bean;

import com.jetco.shop.enums.ResultCodeEnum;
import com.jetco.shop.exception.AbstractException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  统一结果返回
 * </p>
 * @author lhw
 * @version 1.0
 * @since 2019-05-01
 *
 */
public class Result<T> implements Serializable {

    private String message;
    private Integer code;
    private transient T data;
    private boolean success;

    protected Result() {
        this.success = Boolean.TRUE;
        this.code = ResultCodeEnum.SUCCESS.getCode();
    }

    protected Result(int code, String message, boolean success, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", this.code);
        map.put("message", this.message);
        map.put("result", this.data);
        map.put("success", this.success);
        return map;
    }

    public static Result failure(Integer code, String message) {
        return failure(new Result(), code, message);
    }

    public static Result failure(AbstractException sandException) {
        return failure(new Result(), sandException.getCode(), sandException.getMessage());
    }

    public static Result failure(Result result, Integer code, String message) {
        return result.setSuccess(Boolean.FALSE).setCode(code).setMessage(message);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> Result<T> failure(int errorCode, String message) {
        return failure(errorCode, message);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> Result<T> failure(String message) {
        return failure(ResultCodeEnum.FAILED.getCode(), message);
    }

    /**
     * 失败返回结果
     * @return
     */
    public static <T> Result<T> failure() {
        return failure(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> paramValidateFailed() {
        return failure(ResultCodeEnum.PARAM_VALID_IS_BLANK.getCode(), ResultCodeEnum.PARAM_VALID_IS_BLANK.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> paramValidateFailed(String message) {
        return failure(ResultCodeEnum.PARAM_VALID_IS_BLANK.getCode(), message);
    }

    /**
     * 方法未找到失败返回结果
     * @return
     */
    public static <T> Result<T> methodNotFoundFailed() {
        return failure(ResultCodeEnum.METHOD_NOT_FOUND.getCode(), ResultCodeEnum.METHOD_NOT_FOUND.getMessage());
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized(T data) {
        return new Result<>(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getMessage(), Boolean.FALSE, data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden(T data) {
        return new Result<>(ResultCodeEnum.FORBIDDEN.getCode(), ResultCodeEnum.FORBIDDEN.getMessage(), Boolean.FALSE, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), Boolean.TRUE, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), Boolean.TRUE, data);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), message, Boolean.TRUE, data);
    }
    
}
