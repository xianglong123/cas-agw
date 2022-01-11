package com.cas.enums;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/10 3:42 下午
 * @desc
 */
public enum CodeAndMsg {
    /**
     *
     */
    SUCCESS(1000, "SUCCESS"),
    FAIL(2000, "FAIL"),
    UNKNOWEXCEPTION(3000, "UNKNOWEXCEPTION"),
    VERIFYFAIL(4000, "验签失败"),
    SIGN_IS_BLANK(5000, "签名为空"),
    ;

    private int code;
    private String msg;

    CodeAndMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
