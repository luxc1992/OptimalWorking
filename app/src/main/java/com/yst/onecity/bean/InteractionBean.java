package com.yst.onecity.bean;

import java.util.List;

/**
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/5/26.
 */

public class InteractionBean extends MsgBean {

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * member_id : 507
         * created_time : 2018-05-14 10:30:14
         * idType : 1
         * productId : null
         * head_img : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180418/20180418151840862981453.png
         * type : 3
         * consultationId : 2170
         * content : 分享资讯
         * consultationImgUrl : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417115819961568903.png,https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417115826713634299.png
         * operation_object_id : 2279
         * consultationDescription : 5656<img src="https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417113827151387410.jpg" alt="" />
         * consultationTitle : 此次
         * object_name : null
         * nickname : 一休哥
         * id : 5798
         * serviceProjectId : null
         * consultationModelType : 2
         * productMinprice : 0.09
         * productName : 水果拼盘
         * productTitle : 21312
         * productMaxprice : 0.11
         * productAttachment : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180419/20180419113157340340458.jpg
         * serviceTitle : 我呢122
         * price : 5000000
         * imgAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180425/20180425154744299457863.png
         */

        private int member_id;
        private String created_time;
        private int idType;
        private String productId;
        private String head_img;
        private int type;
        private int consultationId;
        private String content;
        private String consultationImgUrl;
        private int operation_object_id;
        private String consultationDescription;
        private String consultationTitle;
        private String object_name;
        private String nickname;
        private int id;
        private String serviceProjectId;
        private int consultationModelType;
        private String productMinprice;
        private String productName;
        private String productTitle;
        private String productMaxprice;
        private String productAttachment;
        private String serviceTitle;
        private String price;
        private String imgAddress;
        private String userId;
        private String userName;
        private String replyContent;

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public int getIdType() {
            return idType;
        }

        public void setIdType(int idType) {
            this.idType = idType;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getConsultationId() {
            return consultationId;
        }

        public void setConsultationId(int consultationId) {
            this.consultationId = consultationId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getConsultationImgUrl() {
            return consultationImgUrl;
        }

        public void setConsultationImgUrl(String consultationImgUrl) {
            this.consultationImgUrl = consultationImgUrl;
        }

        public int getOperation_object_id() {
            return operation_object_id;
        }

        public void setOperation_object_id(int operation_object_id) {
            this.operation_object_id = operation_object_id;
        }

        public String getConsultationDescription() {
            return consultationDescription;
        }

        public void setConsultationDescription(String consultationDescription) {
            this.consultationDescription = consultationDescription;
        }

        public String getConsultationTitle() {
            return consultationTitle;
        }

        public void setConsultationTitle(String consultationTitle) {
            this.consultationTitle = consultationTitle;
        }

        public String getObject_name() {
            return object_name;
        }

        public void setObject_name(String object_name) {
            this.object_name = object_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getServiceProjectId() {
            return serviceProjectId;
        }

        public void setServiceProjectId(String serviceProjectId) {
            this.serviceProjectId = serviceProjectId;
        }

        public int getConsultationModelType() {
            return consultationModelType;
        }

        public void setConsultationModelType(int consultationModelType) {
            this.consultationModelType = consultationModelType;
        }

        public String getProductMinprice() {
            return productMinprice;
        }

        public void setProductMinprice(String productMinprice) {
            this.productMinprice = productMinprice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public String getProductMaxprice() {
            return productMaxprice;
        }

        public void setProductMaxprice(String productMaxprice) {
            this.productMaxprice = productMaxprice;
        }

        public String getProductAttachment() {
            return productAttachment;
        }

        public void setProductAttachment(String productAttachment) {
            this.productAttachment = productAttachment;
        }

        public String getServiceTitle() {
            return serviceTitle;
        }

        public void setServiceTitle(String serviceTitle) {
            this.serviceTitle = serviceTitle;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImgAddress() {
            return imgAddress;
        }

        public void setImgAddress(String imgAddress) {
            this.imgAddress = imgAddress;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }
    }
}
