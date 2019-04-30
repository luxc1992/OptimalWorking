package com.yst.onecity.bean.home;

/**
 * 课题分类
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/24
 */
public class ProjectClassifyBean {
    private String descriptionName;
    private String id;
    private boolean isChoose;

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
