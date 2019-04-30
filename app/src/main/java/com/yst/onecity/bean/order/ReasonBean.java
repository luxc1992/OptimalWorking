package com.yst.onecity.bean.order;

/**
 * 退货原因实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/3
 */
public class ReasonBean {
    private String reasonTypeName;
    private String id;
    private boolean isChoose;

    public ReasonBean() {
    }

    public String getReasonTypeName() {
        return reasonTypeName;
    }

    public void setReasonTypeName(String reasonTypeName) {
        this.reasonTypeName = reasonTypeName;
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
