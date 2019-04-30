package com.yst.onecity.bean.order;

import java.io.Serializable;

/**
 * 订单商品信息实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/6
 */
public class OrderChildBean implements Serializable {

    /**
     * orderNo : 20180228113110476
     * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
     * nickName : qqq
     * price : 1
     * num : 1
     * name : 砍价商品测试3
     * id : 15
     * sPrice : 2
     * orderSubNo : 20180228113110476_3
     * content : 12
     * status : 2
     */

    private String orderNo;
    private String address;
    private String nickName;
    private String price;
    private int num;
    private int pNum;
    private String name;
    private String id;
    private String pId;
    private String sPrice;
    private String orderSubNo;
    private String content;
    private int status;
    private String salePrice;
    private boolean isChoose;
    private String productType;
    private int afterSale;
    private String pImg;
    private String createdTime;

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    private String oId;
    private String commentId;
    private String psName;

    public OrderChildBean() {
    }

    public OrderChildBean(String pImg, String oId, String name, String price) {
        this.pImg = pImg;
        this.oId = oId;
        this.name = name;
        this.price = price;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getAfterSale() {
        return afterSale;
    }

    public void setAfterSale(int afterSale) {
        this.afterSale = afterSale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSPrice() {
        return sPrice;
    }

    public void setSPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getpImg() {
        return pImg;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getoId() {
        return oId;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }
}
