package com.example.zealience.oneiromancy.entity;

import java.io.Serializable;

/**
 * @user steven
 * @createDate 2019/3/28 13:38
 * @description 自定义
 */
public class UserDynamicEntity implements Serializable {
    private String date;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
