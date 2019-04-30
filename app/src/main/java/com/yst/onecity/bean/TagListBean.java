package com.yst.onecity.bean;

import java.util.ArrayList;

/**
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/22
 */

public class TagListBean {
    public ArrayList<TagMsg> list = new ArrayList<>();
    private String name;

    public TagListBean() {
    }

    public TagListBean(ArrayList<TagMsg> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TagMsg> getList() {
        return list;
    }

    public void setList(ArrayList<TagMsg> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TagListBean{" +
                "name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}