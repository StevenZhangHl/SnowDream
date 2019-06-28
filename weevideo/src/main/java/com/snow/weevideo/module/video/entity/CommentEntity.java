package com.snow.weevideo.module.video.entity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/6/28 11:05
 * @description 评论
 */
public class CommentEntity {
    private long id;
    private int userId;
    private String userName;
    private String userheadImg;
    private String commentContent;
    private boolean isPraise;
    private int praiseCount;
    private List<ReplyCommentEntity>replyLists;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserheadImg() {
        return userheadImg;
    }

    public void setUserheadImg(String userheadImg) {
        this.userheadImg = userheadImg;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public List<ReplyCommentEntity> getReplyLists() {
        return replyLists;
    }

    public void setReplyLists(List<ReplyCommentEntity> replyLists) {
        this.replyLists = replyLists;
    }
}
