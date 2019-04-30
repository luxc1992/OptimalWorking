package com.yst.onecity.bean;

import java.io.Serializable;

/**
 * 商品列表实体类
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/9.
 */

public class GoodsBean implements Serializable {
    private String goodsName;
    private int goodsId;
    private boolean isChecked;
    /**
     * 上下架状态
     */
    private int manageState;
    /**
     * 点击批量管理
     */
    private boolean isclicked;
    /**
     * 关注状态
     */
    private int facusState;

    public int getFacusState() {
        return facusState;
    }

    public void setFacusState(int facusState) {
        this.facusState = facusState;
    }

    public int getManageState() {
        return manageState;
    }

    public void setManageState(int manageState) {
        this.manageState = manageState;
    }



    public boolean isIsclicked() {
        return isclicked;
    }

    public void setIsclicked(boolean isclicked) {
        this.isclicked = isclicked;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsId=" + goodsId +
                ", isChecked=" + isChecked +
                ", manageState=" + manageState +
                ", isclicked=" + isclicked +
                ", facusState=" + facusState +
                '}';
    }
}
