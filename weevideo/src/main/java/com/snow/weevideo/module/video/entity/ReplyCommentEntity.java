package com.snow.weevideo.module.video.entity;

/**
 * @user steven
 * @createDate 2019/6/28 11:10
 * @description 自定义
 */
public class ReplyCommentEntity {
    private long id;
    private int commentUserId;
    private String commentUserName;
    private String commentUserheadImg;
    private String commentContent;
    private int replyUserId;
    private String replyContent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserheadImg() {
        return commentUserheadImg;
    }

    public void setCommentUserheadImg(String commentUserheadImg) {
        this.commentUserheadImg = commentUserheadImg;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
