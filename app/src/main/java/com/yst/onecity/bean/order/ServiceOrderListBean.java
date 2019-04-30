package com.yst.onecity.bean.order;

import java.util.List;

/**
 * 服务订单列表
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/24
 */
public class ServiceOrderListBean {

    /**
     * code : 1
     * msg : 查询成功！
     * content : [{"phone":"13712341234","price":0.1,"name":"博洛尼装修2","salesNum":12,"imageAddress":"img/img.jpge","id":12,"title":"我呢","status":1}]
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
         * phone : 13712341234
         * price : 0.1
         * name : 博洛尼装修2
         * salesNum : 12
         * imageAddress : img/img.jpge
         * id : 12
         * title : 我呢
         * status : 1
         * uuid：买家服务列表反 卖家uuid  卖家服务列表反  买家uuid
         * createdTime: 订单创建时间
         * orderNo：订单号
         */

        private String phone;
        private double price;
        private String name;
        private int salesNum;
        private String imageAddress;
        private int id;
        private String title;
        private int status;
        private String uuid;
        private String createdTime;
        private String orderNo;

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderNo() {

            return orderNo;
        }

        public void setCreatedTime(String createdTime) {

            this.createdTime = createdTime;
        }

        public String getCreatedTime() {

            return createdTime;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {

            return uuid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(int salesNum) {
            this.salesNum = salesNum;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
