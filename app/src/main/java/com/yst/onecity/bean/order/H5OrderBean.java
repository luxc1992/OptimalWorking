package com.yst.onecity.bean.order;

import java.io.Serializable;

/**
 * H5订单
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/6
 */
public class H5OrderBean implements Serializable{
    private String phone;
    private String uuid;
    private String orderNo;
    private String data;
    private String merchantId;
    private String Img;
    private String nickName;
    private String userId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getImg() {
        return Img;
    }

    public String getNickName() {
        return nickName;
    }

    public String getUserId() {
        return userId;
    }
}
