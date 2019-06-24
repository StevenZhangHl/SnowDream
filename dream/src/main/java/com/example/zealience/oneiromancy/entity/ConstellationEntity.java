package com.example.zealience.oneiromancy.entity;

/**
 * @user steven
 * @createDate 2019/4/1 11:18
 * @description 星座实体
 */
public class ConstellationEntity {
    private String title;
    private int drawableId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public ConstellationEntity(String title, int drawableId) {
        this.title = title;
        this.drawableId = drawableId;
    }
}
