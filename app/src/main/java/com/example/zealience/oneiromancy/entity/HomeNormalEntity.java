package com.example.zealience.oneiromancy.entity;

/**
 * @user steven
 * @createDate 2019/3/20 15:16
 * @description 首页数据正常的实体
 */
public class HomeNormalEntity {
    private String userName;
    private String userImage;
    private String photoUrl;
    private String content;

    public HomeNormalEntity() {
    }

    public HomeNormalEntity(String photoUrl, String userImage, String userName, String content) {
        this.photoUrl = photoUrl;
        this.userImage = userImage;
        this.userName = userName;
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
