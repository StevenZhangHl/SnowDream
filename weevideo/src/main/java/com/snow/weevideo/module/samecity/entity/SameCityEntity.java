package com.snow.weevideo.module.samecity.entity;

/**
 * @user steven
 * @createDate 2019/6/25 13:52
 * @description 同城
 */
public class SameCityEntity {
    private long id;
    private String userHeadUrl;
    private double distance;
    private boolean isLiving;
    private String firstTitle;
    private String sencondTitle;
    private String previewUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    public String getFirstTitle() {
        return firstTitle;
    }

    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public String getSencondTitle() {
        return sencondTitle;
    }

    public void setSencondTitle(String sencondTitle) {
        this.sencondTitle = sencondTitle;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
