package com.steven.base.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @user steven
 * @createDate 2019/2/20 13:53
 * @description 自定义
 */
public class BaseEntity<T> implements Serializable {
    private String reason;
    private int error_code;
    private T result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean success() {
        if ("successed".equals(reason)) {
            return true;
        } else {
            return false;
        }
    }
}
