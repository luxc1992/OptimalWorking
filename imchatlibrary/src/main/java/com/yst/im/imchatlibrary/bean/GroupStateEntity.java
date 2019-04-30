package com.yst.im.imchatlibrary.bean;

/**
 *  群状态保存
 * @author Lierpeng
 * @date 2018/5/26
 */

public class GroupStateEntity {
    private String userId;
    private int state;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
