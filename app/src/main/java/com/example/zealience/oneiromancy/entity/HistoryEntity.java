package com.example.zealience.oneiromancy.entity;

import java.io.Serializable;

/**
 * @user steven
 * @createDate 2019/3/8 13:13
 * @description 历史上的今天实体
 */
public class HistoryEntity implements Serializable {

    /**
     * day : 1/1
     * date : 前45年01月01日
     * title : 罗马共和国开始使用儒略历
     * e_id : 1
     */

    private String day;
    private String date;
    private String title;
    private String e_id;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }
}
