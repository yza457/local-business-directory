package com.yza457.o2o.dto;

/**
 * the type to return to frontend
 * encapsulate JSON object
 * @param <T>
 */
public class Result<T> {
    private boolean success;
    private T data;
    private String errMsg;
    private int errCode;

    // default constructor
    public Result() {
    }

    // constructor for success case
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    // constructor for failure case
    public Result(boolean success, int errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
