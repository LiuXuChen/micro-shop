package com.jetco.shop.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.jetco.shop.bean.Result;
import com.jetco.shop.enums.ResultCodeEnum;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * <p>
 *  全局异常处理和对于Sentinel中RESTFUL接口的异常处理
 * </p>
 *
 * @author lhw
 * @version 1.0
 * @since 2020-06-06
 */
@Component
@RestControllerAdvice
public class GlobalException implements BlockExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public <T extends Throwable> Result handleException(T e) {
        if (e instanceof AbstractException) {
            AbstractException baseException = (AbstractException) e;
            return Result.failure(baseException);
            // 此处为方法级的错误日志的处理
        } else if (e instanceof MethodArgumentNotValidException) {
            // MethodArgumentNotValidException为注解校验异常类
            // 获取注解校验异常信息
            StringBuilder sb = new StringBuilder("错误信息如下：").append("\r\n");
            List<FieldError> errorList = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
            for (FieldError error : errorList) {
                //验证错误信息
                String errorMsg = error.getDefaultMessage();
                sb.append("      ").append(errorMsg).append("\r\n");
            }
            String error = sb.substring(0, sb.length() - 1);
            return error != null ? Result.failure(ResultCodeEnum.PARAM_VALID_IS_BLANK.getCode(), error) : Result.failure(ResultCodeEnum.UNDEFINE.getCode(), ResultCodeEnum.UNDEFINE.getMessage());
            // 此处为属性级的错误日志的处理
        } else if (e instanceof ConstraintViolationException) {
            return Result.failure(e.getMessage());
        } else {
            return Result.failure(-1, e.getMessage());
        }
    }

    /**
     * RESTFUL异常信息处理器
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws Exception
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) {
        int code = ResultCodeEnum.UNDEFINE.getCode();
        String msg = ResultCodeEnum.UNDEFINE.getMessage();
        if (e instanceof FlowException) {
            code = ResultCodeEnum.INTERFACE_HAS_BEEN_THROTTLED.getCode();
            msg = ResultCodeEnum.INTERFACE_HAS_BEEN_THROTTLED.getMessage();
        } else if (e instanceof DegradeException) {
            code = ResultCodeEnum.INTERFACE_HAS_BEEN_BLOWN.getCode();
            msg = ResultCodeEnum.INTERFACE_HAS_BEEN_BLOWN.getMessage();
        } else if (e instanceof ParamFlowException) {
            code = ResultCodeEnum.HOTSPOT_PARAMETERS_HAVE_BEEN_RESTRICTED.getCode();
            msg = ResultCodeEnum.HOTSPOT_PARAMETERS_HAVE_BEEN_RESTRICTED.getMessage();
        } else if (e instanceof SystemBlockException) {
            code = ResultCodeEnum.SYSTEM_RULES_DO_NOT_MEET_THE_REQUIREMENTS.getCode();
            msg = ResultCodeEnum.SYSTEM_RULES_DO_NOT_MEET_THE_REQUIREMENTS.getMessage();
        } else if (e instanceof AuthorityException) {
            code = ResultCodeEnum.AUTHORIZATION_RULES_FAIL.getCode();
            msg = ResultCodeEnum.AUTHORIZATION_RULES_FAIL.getMessage();
        }
        throw new SentinelException(code, msg);
    }
}
