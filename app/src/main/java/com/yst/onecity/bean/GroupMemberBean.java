package com.yst.onecity.bean;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/26.
 */

public class GroupMemberBean {
    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 显示数据拼音的首字母
     */
    private String sortLetters;

    /**
     * 是否选中
     */
    private boolean isChecked;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean check) {
        isChecked = check;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}

