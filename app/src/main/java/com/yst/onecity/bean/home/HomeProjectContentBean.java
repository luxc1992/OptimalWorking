package com.yst.onecity.bean.home;

import com.yst.onecity.bean.MsgBean;

import java.util.List;

/**
 * 首页实体类
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class HomeProjectContentBean extends MsgBean {
    private List<HomeProjectBean> content;


    public List<HomeProjectBean> getContent() {
        return content;
    }

    public void setContent(List<HomeProjectBean> content) {
        this.content = content;
    }

    private String groupTitle;
    private List<HomeProjectNewsBean> childList;

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public List<HomeProjectNewsBean> getChildList() {
        return childList;
    }

    public void setChildList(List<HomeProjectNewsBean> childList) {
        this.childList = childList;
    }
}
