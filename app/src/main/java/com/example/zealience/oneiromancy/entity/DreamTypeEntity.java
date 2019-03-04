package com.example.zealience.oneiromancy.entity;

import java.io.Serializable;

/**
 * @user steven
 * @createDate 2019/2/20 14:05
 * @description 自定义
 */
public class DreamTypeEntity implements Serializable {

    /**
     * id : 89
     * name : 其它动物
     * fid : 3
     */

    private String id;
    private String name;
    private String fid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
