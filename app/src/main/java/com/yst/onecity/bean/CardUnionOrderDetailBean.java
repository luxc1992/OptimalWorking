package com.yst.onecity.bean;

import java.util.List;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/6/5
 */
public class CardUnionOrderDetailBean {

    /**
     * code : 1
     * msg : 查询服务订单详情成功
     * content : [{"serviceType":null,"orderNo":"PJGK20180604144959575833260","cardName":null,"payTime":null,"num":22,"discountPrice":"0","cardType":null,"cardPrice":null,"serviceTime":1528041600000,"linkphone":"18101249503","linkman":null,"detailAdd":null,"price":"0.01","payPrice":"0.01","createdTime":1528094879000,"imageAddress":null,"id":439,"serviceAdd":null,"status":7}]
     */

    private int code;
    private String msg;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * serviceType : null
         * orderNo : PJGK20180604144959575833260
         * cardName : null
         * payTime : null
         * num : 22
         * discountPrice : 0
         * cardType : null
         * cardPrice : null
         * serviceTime : 1528041600000
         * linkphone : 18101249503
         * linkman : null
         * detailAdd : null
         * price : 0.01
         * payPrice : 0.01
         * createdTime : 1528094879000
         * imageAddress : null
         * id : 439
         * serviceAdd : null
         * status : 7
         */

        private Object serviceType;
        private String orderNo;
        private String cardName;
        private Object payTime;
        private String clubCardId;
        private int num;
        private String discountPrice;
        private String cardType;
        private String cardPrice;
        private long serviceTime;
        private String linkphone;
        private Object linkman;
        private Object detailAdd;
        private String price;
        private String payPrice;
        private String createdTime;
        private String imageAddress;
        private int id;
        private Object serviceAdd;
        private int status;

        public String getClubCardId() {
            return clubCardId;
        }

        public void setClubCardId(String clubCardId) {
            this.clubCardId = clubCardId;
        }

        public Object getServiceType() {
            return serviceType;
        }

        public void setServiceType(Object serviceType) {
            this.serviceType = serviceType;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardPrice() {
            return cardPrice;
        }

        public void setCardPrice(String cardPrice) {
            this.cardPrice = cardPrice;
        }

        public long getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(long serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getLinkphone() {
            return linkphone;
        }

        public void setLinkphone(String linkphone) {
            this.linkphone = linkphone;
        }

        public Object getLinkman() {
            return linkman;
        }

        public void setLinkman(Object linkman) {
            this.linkman = linkman;
        }

        public Object getDetailAdd() {
            return detailAdd;
        }

        public void setDetailAdd(Object detailAdd) {
            this.detailAdd = detailAdd;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getServiceAdd() {
            return serviceAdd;
        }

        public void setServiceAdd(Object serviceAdd) {
            this.serviceAdd = serviceAdd;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
