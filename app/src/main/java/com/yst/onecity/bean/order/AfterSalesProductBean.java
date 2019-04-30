package com.yst.onecity.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * 售后商品实体类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/1
 */
public class AfterSalesProductBean implements Serializable {
    /**
     * payMoney : 1.11
     * merchantId : null
     * nickName : 小济
     * freight : 0.0
     * returnId : 25
     * merchantAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316100613194942862.png
     * detail : [{"productId":2085,"price":1.11,"num":1,"productAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316102205533066444.jpg","productName":"水果01","spec":"30二斤"}]
     * uuid : d968c07c446e485ab967e4e9d2b493d7
     * returnOrderNo : 20180316134840490
     * merchantName : 平台
     * status : 1
     * memberId : 23
     */

    private double payMoney;
    private String merchantId;
    private String nickName;
    private double freight;
    private int returnId;
    private String merchantAddress;
    private String uuid;
    private String returnOrderNo;
    private String merchantName;
    private int status;
    private int memberId;
    private List<DetailBean> detail;

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
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

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReturnOrderNo() {
        return returnOrderNo;
    }

    public void setReturnOrderNo(String returnOrderNo) {
        this.returnOrderNo = returnOrderNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean implements Serializable{
        /**
         * productId : 2085
         * price : 1.11
         * num : 1
         * productAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180316/20180316102205533066444.jpg
         * productName : 水果01
         * spec : 30二斤
         */

        private int productId;
        private double price;
        private int num;
        private String productAddress;
        private String productName;
        private String spec;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getProductAddress() {
            return productAddress;
        }

        public void setProductAddress(String productAddress) {
            this.productAddress = productAddress;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }

    /**
     * 退货单状态：0申请退款中 1 申请退货退款中 2同意退货 3已确认退款 4已拒绝退货 5已拒绝退款
     */


}
