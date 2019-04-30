package com.yst.onecity.bean.order;

import java.util.List;

/**
 * 订单详情实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class OrderDetailsBean {

    /**
     * receivePhone : 18710181319
     * finishTime : 2018年02月28日 11:30
     * orderNo : 20180228113110476
     * receiveAddress : 北京市 市辖区嘉里大通
     * payTime : null
     * totalPrice : 0
     * sendTime : null
     * iMid : null
     * receiveTime : null
     * receiveName : 王凤娇
     * createTime : 2018年02月28日 11:30
     * cancelTime : null
     * totalNum : 1
     */
    private int isConfirmSendButton;
    private String receivePhone;
    private String finishTime;
    private String orderNo;
    private String orderFreight;
    private String receiveAddress;
    private String payTime;
    private String totalPrice;
    private String sendTime;
    private String iMid;
    private String receiveTime;
    private String receiveName;
    private String createTime;
    private String cancelTime;
    private int totalNum;
    private String payType;
    private String logisticsCompanyName;
    private String logisticsNo;
    private List<OrderChildBean> productList;

    public int getIsConfirmSendButton() {
        return isConfirmSendButton;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getiMid() {
        return iMid;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public String getOrderFreight() {
        return orderFreight;
    }

    public String getPayType() {
        return payType;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public List<OrderChildBean> getProductList() {
        return productList;
    }
}
