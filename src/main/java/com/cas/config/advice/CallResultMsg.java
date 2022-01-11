package com.cas.config.advice;

import com.cas.enums.CodeAndMsg;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/10 3:44 下午
 * @desc
 */
public class CallResultMsg<T> {

    private boolean result;
    private int code;
    private String message;
    private T data;

    public CallResultMsg(boolean result, CodeAndMsg codeAndMsg, T data) {
        this.result = result;
        this.code = codeAndMsg.getCode();
        this.message = codeAndMsg.getMsg();
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
