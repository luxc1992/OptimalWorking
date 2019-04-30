package com.yst.onecity.bean;

import java.util.List;

/**
 * 系统通知列表实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/2
 */

public class NoticeBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"senderPhone":"15011552664","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png","isRead":1,"createdTime":"2018-03-06 11:29:17","comment":"再接再厉,更惊喜的礼物待您发现","id":11,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"15011552664","imgUrl":null,"isRead":1,"createdTime":"2018-03-06 11:21:02","comment":"再接再厉,更惊喜的礼物待您发现","id":10,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"15011552664","imgUrl":null,"isRead":1,"createdTime":"2018-03-06 11:17:23","comment":"再接再厉,更惊喜的礼物待您发现","id":9,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"15011552664","imgUrl":null,"isRead":1,"createdTime":"2018-03-06 11:09:28","comment":"再接再厉,更惊喜的礼物待您发现","id":8,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"15011552664","imgUrl":null,"isRead":1,"createdTime":"2018-03-06 11:07:53","comment":"再接再厉,更惊喜的礼物待您发现","id":7,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"15011552664","imgUrl":null,"isRead":1,"createdTime":"2018-03-06 10:51:45","comment":"再接再厉,更惊喜的礼物待您发现","id":6,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"15011552664","imgUrl":null,"isRead":1,"createdTime":"2018-03-06 10:49:32","comment":"再接再厉,更惊喜的礼物待您发现","id":5,"type":2,"title":"恭喜您成交一笔订单,系统将赠送您一份大奖","memberId":1},{"senderPhone":"12306","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180305/20180305195018206400611.jpeg","isRead":1,"createdTime":"2018-02-26 14:10:00","comment":"再接再厉，更惊喜礼物的待您发现","id":2,"type":2,"title":"恭喜您完成一笔订单，系统将赠送一份大奖！！","memberId":1},{"senderPhone":"12306","imgUrl":null,"isRead":1,"createdTime":"2018-02-26 14:10:00","comment":"再接再厉，更惊喜礼物的待您发现","id":3,"type":3,"title":"恭喜您成为服务专员，系统将为您分享的产品提供购买分润！！！","memberId":1},{"senderPhone":"12306","imgUrl":null,"isRead":1,"createdTime":"2018-02-26 14:10:00","comment":"再接再厉，更惊喜礼物的待您发现","id":4,"type":4,"title":"恭喜您成为猎头，您发布的产品计划已通过审核，请继续更新日记哟","memberId":1}]
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
         * senderPhone : 15011552664
         * imgUrl : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180208/20180208134635883392347.png
         * isRead : 1
         * createdTime : 2018-03-06 11:29:17
         * comment : 再接再厉,更惊喜的礼物待您发现
         * id : 11
         * type : 2
         * title : 恭喜您成交一笔订单,系统将赠送您一份大奖
         * memberId : 1
         */

        private String senderPhone;
        private String imgUrl;
        private int isRead;
        private String createdTime;
        private String COMMENT;
        private int id;
        private int type;
        private String title;
        private int memberId;
        private int orderState;
        private String orderId;
        private String msgType;
        private long ServiceOrderId;

        public long getServiceOrderId() {
            return ServiceOrderId;
        }

        public void setServiceOrderId(long serviceOrderId) {
            this.ServiceOrderId = serviceOrderId;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSenderPhone() {
            return senderPhone;
        }

        public void setSenderPhone(String senderPhone) {
            this.senderPhone = senderPhone;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getComment() {
            return COMMENT;
        }

        public void setComment(String comment) {
            this.COMMENT = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }
}
