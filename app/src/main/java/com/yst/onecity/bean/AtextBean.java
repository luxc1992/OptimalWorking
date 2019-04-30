package com.yst.onecity.bean;

import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/18
 */
public class AtextBean {
    private String time;
    private List<MsgBean> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<MsgBean> getList() {
        return list;
    }

    public void setList(List<MsgBean> list) {
        this.list = list;
    }
}
