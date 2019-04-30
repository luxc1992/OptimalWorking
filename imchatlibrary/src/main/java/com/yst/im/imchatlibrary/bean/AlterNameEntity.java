package com.yst.im.imchatlibrary.bean;

import com.yst.im.imchatlibrary.enumclass.AlterNameEnum;

import java.io.Serializable;

/**
 * 跳转修改标题栏及提示文字
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/19
 */
public class AlterNameEntity implements Serializable {
    private String titleName;
    private String rightName;
    private String leftName;
    private String tips;
    private String id;
    private String name;
    private AlterNameEnum alterNameEnum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlterNameEnum getAlterNameEnum() {
        return alterNameEnum;
    }

    public void setAlterNameEnum(AlterNameEnum alterNameEnum) {
        this.alterNameEnum = alterNameEnum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
