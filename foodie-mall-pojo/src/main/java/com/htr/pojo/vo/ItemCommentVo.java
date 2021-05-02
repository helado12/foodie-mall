package com.htr.pojo.vo;

import java.util.Date;

/**
 * @Author: T. He
 * @Date: 2021/5/2
 */
public class ItemCommentVo {
    private Integer commentLevelCREATEDtIME;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;

    public Integer getCommentLevelCREATEDtIME() {
        return commentLevelCREATEDtIME;
    }

    public void setCommentLevelCREATEDtIME(Integer commentLevelCREATEDtIME) {
        this.commentLevelCREATEDtIME = commentLevelCREATEDtIME;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
