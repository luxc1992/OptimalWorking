package com.yst.onecity.bean;

/**
 * 选择分类实体类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/18
 */

public class SelectClassifyBean {
    String name;
    boolean flag;

    public SelectClassifyBean() {
    }

    public SelectClassifyBean(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
