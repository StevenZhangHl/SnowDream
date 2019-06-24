package com.example.zealience.oneiromancy.entity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/7 17:29
 * @description 自定义
 */
public class BaseNewsEntity {
    private String stat;
    private List<NewsEntity>data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<NewsEntity> getData() {
        return data;
    }

    public void setData(List<NewsEntity> data) {
        this.data = data;
    }
}
