package com.yst.onecity.bean;

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/17.
 */

public class ServerTeamClassifyBean implements Serializable {
    private String name;
    private int id;
    private boolean isCheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
