package com.htr.pojo.vo;

import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: T. He
 * @Date: 2021/8/8
 */
public class UsersVo {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    /**
     * 用户名 用户名
     */
    private String username;


    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 头像 头像
     */
    private String face;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    private String userUniqueToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getUserUniqueToken() {
        return userUniqueToken;
    }

    public void setUserUniqueToken(String userUniqueToken) {
        this.userUniqueToken = userUniqueToken;
    }
}
