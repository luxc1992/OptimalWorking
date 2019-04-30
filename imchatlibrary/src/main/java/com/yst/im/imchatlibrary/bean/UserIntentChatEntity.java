package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;

/**
 * 跳转实体
 *
 * @author Lierpeng
 * @date 2018/1/23.
 * @version 1.0.1
 */
public class UserIntentChatEntity implements Serializable {
    private String userId;
    private String nickName;
    private String imgUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "UserIntentChatEntity{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
