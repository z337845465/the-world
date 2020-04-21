package com.jerry.theworld.exception;

/**
 * Author:Jerry
 * 2020/4/16
 */
public class GolbleHandelException  extends Exception{
    private String msg;
    private Integer errorCode;

    public GolbleHandelException(String msg, Integer errorCode) {
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public GolbleHandelException setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public GolbleHandelException setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
