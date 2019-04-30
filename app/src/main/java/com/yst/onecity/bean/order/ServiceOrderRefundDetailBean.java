package com.yst.onecity.bean.order;

/**
 * 获取服务订单退货售后详情
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/28
 */

public class ServiceOrderRefundDetailBean {

    /**
     * code : 1
     * msg : 查询服务订单申请售后详情成功
     * content : {"orderNo":"20180518174109157","num":1,"productImg":"img/img.jpge","remark":null,"returnOrderStatus":5,"returnPrice":1000,"productName":"我呢","returnOrderNo":"20180522132444805","refuseReason":null,"serviceOrderId":12}
     */

    private int code;
    private String msg;
    private ContentBean content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * orderNo : 20180518174109157
         * num : 1
         * productImg : img/img.jpge
         * remark : null
         * returnOrderStatus : 5
         * returnPrice : 1000
         * productName : 我呢
         * returnOrderNo : 20180522132444805
         * refuseReason : null
         * serviceOrderId : 12
         * returnReason:买错了
         */

        private String orderNo;
        private int num;
        private String productImg;
        private String remark;
        private int returnOrderStatus;
        private double returnPrice;
        private String productName;
        private String returnOrderNo;
        private String refuseReason;
        private int serviceOrderId;
        private String returnReason;

        public void setReturnReason(String returnReason) {
            this.returnReason = returnReason;
        }

        public String getReturnReason() {

            return returnReason;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public double getReturnPrice() {
            return returnPrice;
        }

        public void setReturnPrice(double returnPrice) {
            this.returnPrice = returnPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getReturnOrderNo() {
            return returnOrderNo;
        }

        public void setReturnOrderNo(String returnOrderNo) {
            this.returnOrderNo = returnOrderNo;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public int getServiceOrderId() {
            return serviceOrderId;
        }

        public void setServiceOrderId(int serviceOrderId) {
            this.serviceOrderId = serviceOrderId;
        }
    }
}
