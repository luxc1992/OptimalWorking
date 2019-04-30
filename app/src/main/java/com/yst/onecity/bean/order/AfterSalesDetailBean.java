package com.yst.onecity.bean.order;

import com.yst.onecity.bean.ImageBean;

import java.util.List;

/**
 * 售后详情实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/3
 */
public class AfterSalesDetailBean {

    /**
     * returnReason : 质量问题
     * num : 4
     * productImg : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
     * orderStatus : 7
     * remark : 备注1
     * returnOrderStatus : 3
     * returnPrice : 4.00
     * productName : 11111
     */
    private String orderNo;
    private String orderId;
    private String returnReason;
    private int num;
    private String productImg;
    private int orderStatus;
    private String remark;
    private int returnOrderStatus;
    private String returnPrice;
    private String productName;
    private String refuseReason;
    private int returnOrderType;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;

    private List<ImageBean> attachment;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getReturnOrderStatus() {
        return returnOrderStatus;
    }

    public void setReturnOrderStatus(int returnOrderStatus) {
        this.returnOrderStatus = returnOrderStatus;
    }

    public String getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(String returnPrice) {
        this.returnPrice = returnPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public List<ImageBean> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<ImageBean> attachment) {
        this.attachment = attachment;
    }

    public int getReturnOrderType() {
        return returnOrderType;
    }

    public void setReturnOrderType(int returnOrderType) {
        this.returnOrderType = returnOrderType;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }
}
