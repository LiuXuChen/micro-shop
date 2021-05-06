package com.jetco.shop.enums;

/**
 * <p>
 *     返回状态码枚举类
 * </p>
 *
 * @author lhw
 * @since 2019-05-01
 * @version 1.0
 *
 */
public enum ResultCodeEnum {

    /**
     * 100—199 信息，服务器收到请求，需要请求者继续执行操作
     * 200—299 成功，操作被成功被接收并处理
     * 300—399 重定向，需要进一步的操作以完成请求
     */

    // 成功状态码
    SUCCESS(200, "成功"),

    // 失败状态码
    /**
     * 400—499 客户端错误，请求包含语法错误或无法完成请求
     * 500—599 服务器错误，服务器在处理请求的过程中发生了错误
     */
    UNAUTHORIZED(401, "未登录或token已过期"),
    FORBIDDEN(403, "没有相关权限"),
    METHOD_NOT_FOUND(404, "方法未找到"),
    FAILED(500, "操作失败"),

    /**
     * 参数错误： 1001—1999
     */
    PARAM_IS_INVALID(1001, "参数无效") ,
    PARAM_VALID_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_PATTERN_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),

    /**
     * 用户错误： 2001—2999
     */
    USER_NOT_LOGGED_IN(2001, "用户未登录，访问的路径需要验证，请登录"),
    USER_LOGIN_ERROR(2002, "用户不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003, "账户已被禁用，请先解锁"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在"),
    USER_HAS_LOCKED(2006, "用户被锁定"),
    EDIT_PASSWORD_INCORRECT(2007, "原始密码错误"),
    USER_WILL_LOCKED(2008, "密码输入错误5次,账户锁定五分钟"),
    USER_HAS_LOCKED_WITH_TIME(2009, "账户被锁定,剩余时间:"),
    USER_PSW_WRONG(2010, "用户名或密码错误"),
    USER_HAS_EXPIRED(2011, "账号已过期"),
    USER_DISABLED(2012, "账号已停用"),
    PASSWORD_DISABLED(2013, "密码已过期"),
    CAPTCHA_IS_VALID(2014, "验证码已失效"),
    CAPTCHA_IS_INCORRECT(2015, "验证码不正确"),

    /**
     * 接口异常： 3001—3999
     */
    INTERFACE_HAS_BEEN_THROTTLED(3001, "接口已被限流！"),
    INTERFACE_HAS_BEEN_BLOWN(3002, "接口已被熔断，请稍后再试！"),
    HOTSPOT_PARAMETERS_HAVE_BEEN_RESTRICTED(3003, "热点参数已被限流！"),
    SYSTEM_RULES_DO_NOT_MEET_THE_REQUIREMENTS(3004, "系统规则（负载/...不满足要求！）"),
    AUTHORIZATION_RULES_FAIL(3005, "授权规则不通过！"),

    //公共异常
    UNDEFINE(-1, "未定义异常信息"),


    /**
     * 业务异常信息 4001-4999
     */
    CANT_REMOVE_BECAUSE_CHILD(4001, "对象存在下级，不允许删除"),
    CANT_ADD_BECAUSE_EXIST(4002, "对象已存在"),
    QUERY_INFO_FAILED(4003, "获取信息失败"),
    NOTIFY_FAILED(4004, "通知异常");
    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static String getMessage(Integer code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.getCode().equals(code)) {
                return resultCodeEnum.getMessage();
            }
        }
        return UNDEFINE.getMessage();
    }

}
