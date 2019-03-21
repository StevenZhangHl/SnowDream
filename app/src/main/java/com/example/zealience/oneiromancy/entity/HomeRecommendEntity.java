package com.example.zealience.oneiromancy.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;


/**
 * @user steven
 * @createDate 2019/3/20 15:13
 * @description 自定义
 */
public class HomeRecommendEntity implements MultiItemEntity {
    private List<HomeNormalEntity> horizontalEntity;
    private HomeNormalEntity portraitEntity;
    private VideoEntity videoEntity;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<HomeNormalEntity> getHorizontalEntity() {
        return horizontalEntity;
    }

    public void setHorizontalEntity(List<HomeNormalEntity> horizontalEntity) {
        this.horizontalEntity = horizontalEntity;
    }

    public HomeNormalEntity getPortraitEntity() {
        return portraitEntity;
    }

    public void setPortraitEntity(HomeNormalEntity portraitEntity) {
        this.portraitEntity = portraitEntity;
    }

    public VideoEntity getVideoEntity() {
        return videoEntity;
    }

    public void setVideoEntity(VideoEntity videoEntity) {
        this.videoEntity = videoEntity;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
