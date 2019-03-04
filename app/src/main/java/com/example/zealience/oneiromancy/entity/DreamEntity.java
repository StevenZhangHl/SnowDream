package com.example.zealience.oneiromancy.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/21 10:34
 * @description 自定义
 */
public class DreamEntity implements Serializable {

    /**
     * id : 46b62fa308925d276b901d76e73c13a4
     * title : 杀人
     * des : 梦见杀人，通常是日常生活压力过大，精神过度紧张的表现。可能承受了巨大的情感痛苦，也有可能平时树敌过多，或有仇家。
     * list : ["梦见杀人，通常是日常生活压力过大，精神过度紧张的表现。可能承受了巨大的情感痛苦，也有可能平时树敌过多，或有仇家。","梦见激烈的战场、杀戮、流血场面等，还可能预示你会有意外的运气，找到办法解决生活中面临的难题。"]
     */

    private String id;
    private String title;
    private String des;
    private List<String> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
