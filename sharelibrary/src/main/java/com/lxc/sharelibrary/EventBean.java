package com.lxc.sharelibrary;

import java.io.Serializable;

/**
 * 事件实体
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2017/3/3
 */
public class EventBean implements Serializable {
    private String msg;
    private int flag;

    public EventBean(String msg) {
        this.msg = msg;
    }

    public EventBean(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
