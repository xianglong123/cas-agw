package com.cas.config.advice;

import com.cas.enums.CodeAndMsg;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/10 2:34 下午
 * @desc
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 6304501072268270030L;

    private CodeAndMsg exception;

    public BusinessException(CodeAndMsg exception) {
        super(exception.getMsg());
        this.exception = exception;
    }

    public CodeAndMsg getException() {
        return exception;
    }

    public void setException(CodeAndMsg exception) {
        this.exception = exception;
    }
}
