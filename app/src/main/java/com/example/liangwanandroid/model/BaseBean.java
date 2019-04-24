package com.example.liangwanandroid.model;

import com.google.gson.annotations.SerializedName;

/***
 * Bean的基类
 * @param <T>
 */
public class BaseBean<T> {

    @SerializedName("data")
    private T data;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMsg")
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
