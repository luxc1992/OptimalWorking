package com.yst.onecity.bean;

import java.io.Serializable;


/**
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/22
 */

public class TagMsg implements Serializable {
    private int productL;
    private int productT;
    private String productId;
    private String labelY;
    private String labelX;
    private String productName;

    public String getLabelY() {
        return labelY;
    }

    public void setLabelY(String labelY) {
        this.labelY = labelY;
    }

    public String getLabelX() {
        return labelX;
    }

    public void setLabelX(String labelX) {
        this.labelX = labelX;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public int getProductL() {
        return productL;
    }

    public void setProductL(int productL) {
        this.productL = productL;
    }

    public int getProductT() {
        return productT;
    }

    public void setProductT(int productT) {
        this.productT = productT;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
