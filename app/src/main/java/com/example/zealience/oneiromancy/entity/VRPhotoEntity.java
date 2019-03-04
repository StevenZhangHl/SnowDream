package com.example.zealience.oneiromancy.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @user steven
 * @createDate 2019/2/27 13:32
 * @description 自定义
 */
public class VRPhotoEntity implements MultiItemEntity {
    private int type;
    private String title;
    private String desc;
    private String assetName;
    private int resourceName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getResourceName() {
        return resourceName;
    }

    public void setResourceName(int resourceName) {
        this.resourceName = resourceName;
    }

    public VRPhotoEntity(int type, String title, String desc, String assetName, int resourceName) {
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.assetName = assetName;
        this.resourceName = resourceName;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
