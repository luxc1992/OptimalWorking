package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;

/**
 *
 * @author wangj
 * @date 2018/4/25
 */

public class ForWardingEntity implements Serializable{
    private String acceptId;
    private String event;

    public String getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
