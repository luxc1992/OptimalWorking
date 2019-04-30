package com.yst.onecity.bean;

import com.yst.onecity.bean.agent.ByIdBean;

import java.io.Serializable;
import java.util.List;

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
    private boolean isEdit;
    private List<ByIdBean.ContentBean> list;
    private String tab;

    public EventBean(String msg, int flag) {
        this.msg = msg;
        this.flag = flag;
    }

    public EventBean(String msg, boolean isEdit) {
        this.msg = msg;
        this.isEdit = isEdit;
    }

    public EventBean(String msg, List<ByIdBean.ContentBean> list) {
        this.msg = msg;
        this.list = list;
    }

    public EventBean(String msg, List<ByIdBean.ContentBean> list, String tab) {
        this.msg = msg;
        this.list = list;
        this.tab = tab;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public List<ByIdBean.ContentBean> getList() {
        return list;
    }

    public void setList(List<ByIdBean.ContentBean> list) {
        this.list = list;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

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
