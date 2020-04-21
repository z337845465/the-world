package com.jerry.theworld.controller.respone;

import java.io.Serializable;

/**
 * Author:Jerry
 * 2020/4/15
 */
public class ResponseResult<T> implements Serializable {

    private Integer code;

    private String msg;

    private T responseData;

    private ResponseResult(){
        this.code=200;
        this.msg="Success";
    }

    private ResponseResult(T data){
        this.code=200;
        this.msg="Success";
        this.responseData=data;
    }

    private ResponseResult(String msg){
        this.code=-1;
        this.msg=msg;
    }

    private ResponseResult(Integer code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.responseData=data;
    }

    public static<T> ResponseResult<T> success(){
        return new ResponseResult();
    }

    public static<T> ResponseResult<T> success(T data){
        return new ResponseResult(data);
    }

    public static<T> ResponseResult<T> result(Integer code,String msg,T data){
        return new ResponseResult(code,msg,data);
    }

    public static<T> ResponseResult<T> fail(String msg){
        return new ResponseResult(msg);
    }



    public Integer getCode() {
        return code;
    }

    public ResponseResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getResponseData() {
        return responseData;
    }

    public ResponseResult<T> setResponseData(T responseData) {
        this.responseData = responseData;
        return this;
    }
}
