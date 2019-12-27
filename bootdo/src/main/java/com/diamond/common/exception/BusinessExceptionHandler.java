package com.diamond.common.exception;

import com.diamond.common.utils.HttpServletUtils;
import com.diamond.common.base.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return CustomResponse.error(403, "未授权");
        }
        return new ModelAndView("error/403");
    }


    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return new BusinessException("服务器错误，请联系管理员", 500, e);
        }
        return new ModelAndView("error/500");
    }

    @ExceptionHandler({BusinessException.class})
    public Object handleBusinessException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return new BusinessException(e.getMessage(), 500, e);
    }
}
