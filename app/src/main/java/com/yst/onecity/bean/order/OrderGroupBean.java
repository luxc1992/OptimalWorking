package com.yst.onecity.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * 订单店铺信息实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/6
 */
public class OrderGroupBean implements Serializable{
    private int type;
    private String mechantImg;
    private String orderNo;
    private String total_price;
    private String merchantId;
    private String nickName;
    private String id;
    private int status;
    private String productFreight;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {

        this.uuid = uuid;
    }

    private List<OrderChildBean> son;
    private int isConfirmSendButton;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMechantImg() {
        return mechantImg;
    }

    public void setMechantImg(String mechantImg) {
        this.mechantImg = mechantImg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProductFreight() {
        return productFreight;
    }

    public void setProductFreight(String productFreight) {
        this.productFreight = productFreight;
    }

    public List<OrderChildBean> getSon() {
        return son;
    }

    public void setSon(List<OrderChildBean> son) {
        this.son = son;
    }

    public int getIsConfirmSendButton() {
        return isConfirmSendButton;
    }

    public void setIsConfirmSendButton(int isConfirmSendButton) {
        this.isConfirmSendButton = isConfirmSendButton;
    }
}
