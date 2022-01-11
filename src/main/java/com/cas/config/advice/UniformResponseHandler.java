package com.cas.config.advice;

import com.cas.enums.CodeAndMsg;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


/**
 * 控制器Controller异常处理
 * @author wunaozai
 * @Date 2020-03-05
 */
@RestControllerAdvice
public class UniformResponseHandler<T> {

    /**
     * 应用到所有@RequestMapper 注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    @ResponseStatus(HttpStatus.OK)
    public CallResultMsg sendSuccessResponse() {
        return new CallResultMsg(true, CodeAndMsg.SUCCESS, null);
    }

    @ResponseStatus(HttpStatus.OK)
    public CallResultMsg sendSuccessResponse(T data) {
        return new CallResultMsg(true, CodeAndMsg.SUCCESS, data);
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapper可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("Server", "Wunaozai");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public CallResultMsg sendErrorResponse_business(Exception e) {
        return new CallResultMsg(false, ((BusinessException)e).getException(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CallResultMsg sendErrorResponse_System(Exception e) {
        if (e instanceof BusinessException) {
            return this.sendErrorResponse_business(e);
        }
        return new CallResultMsg(false,CodeAndMsg.UNKNOWEXCEPTION, null);
    }
}