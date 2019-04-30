package com.yst.onecity.bean.agent;

import java.io.Serializable;

/**
 * 课题列表实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */

public class CourseListBean implements Serializable {

    private String title;
    private boolean isClick;
    private int position;
    private String tabTitle;

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CourseListBean() {
    }

    public CourseListBean(String title, boolean isClick) {
        this.title = title;
        this.isClick = isClick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
