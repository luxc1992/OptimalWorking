package com.yst.onecity.bean;

import java.util.List;

/**
 * 服务评价列表的bean
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/25
 */

public class ServiceCommentBean {

    /**
     * code : 1
     * msg : 服务评价列表成功！
     * content : {"lista":[{"returnContent":"111","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-08 16:09:44","content":"不好吃","imgUrl":"","commentMemberId":492,"isPicture":0,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":1,"nickname":"小猴子","commentId":1904,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":null,"payTime":1526535437000,"commentTime":"2018-05-11 15:29:50","content":"111","imgUrl":null,"commentMemberId":null,"isPicture":null,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":4,"nickname":null,"commentId":1905,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":null,"payTime":1526535437000,"commentTime":"2018-05-11 15:30:57","content":"4333 ","imgUrl":null,"commentMemberId":null,"isPicture":null,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":null,"commentId":1906,"serviceId":2},{"returnContent":"hha","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-19 17:30:31","content":"真不错！  就是不错！","imgUrl":"123,456,789","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":1916,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 14:13:23","content":"哈哈哈哈哈","imgUrl":null,"commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":1960,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:38:21","content":"ggg","imgUrl":"2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2033,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:39:10","content":"ggg","imgUrl":",2,2,2,2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2034,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:43:45","content":"ggg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829165646841.jpg,https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829165676508.jpg,2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2035,"serviceId":2},{"returnContent":"ggg","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:45:42","content":"ggg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829226024651.jpg","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2036,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:49:15","content":"ggg","imgUrl":"2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2038,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 16:01:50","content":"ggg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829301354966.jpg,2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2039,"serviceId":2}],"countAll":11,"countNo":8,"countLittle":2,"countBest":9}
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
         * lista : [{"returnContent":"111","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-08 16:09:44","content":"不好吃","imgUrl":"","commentMemberId":492,"isPicture":0,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":1,"nickname":"小猴子","commentId":1904,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":null,"payTime":1526535437000,"commentTime":"2018-05-11 15:29:50","content":"111","imgUrl":null,"commentMemberId":null,"isPicture":null,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":4,"nickname":null,"commentId":1905,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":null,"payTime":1526535437000,"commentTime":"2018-05-11 15:30:57","content":"4333 ","imgUrl":null,"commentMemberId":null,"isPicture":null,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":null,"commentId":1906,"serviceId":2},{"returnContent":"hha","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-19 17:30:31","content":"真不错！  就是不错！","imgUrl":"123,456,789","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":1916,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 14:13:23","content":"哈哈哈哈哈","imgUrl":null,"commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":1960,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:38:21","content":"ggg","imgUrl":"2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2033,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:39:10","content":"ggg","imgUrl":",2,2,2,2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2034,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:43:45","content":"ggg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829165646841.jpg,https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829165676508.jpg,2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2035,"serviceId":2},{"returnContent":"ggg","logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:45:42","content":"ggg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829226024651.jpg","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2036,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 15:49:15","content":"ggg","imgUrl":"2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2038,"serviceId":2},{"returnContent":null,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png","payTime":1526535437000,"commentTime":"2018-05-22 16:01:50","content":"ggg","imgUrl":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180508/20180508160829301354966.jpg,2","commentMemberId":492,"isPicture":1,"readNum":0,"serviceTitle":"我呢","servicePrice":0.1,"fabulousNum":0,"startNum":5,"nickname":"小猴子","commentId":2039,"serviceId":2}]
         * countAll : 11
         * countNo : 8
         * countLittle : 2
         * countBest : 9
         */

        private int countAll;
        private int countNo;
        private int countLittle;
        private int countBest;
        private List<ListaBean> lista;

        public int getCountAll() {
            return countAll;
        }

        public void setCountAll(int countAll) {
            this.countAll = countAll;
        }

        public int getCountNo() {
            return countNo;
        }

        public void setCountNo(int countNo) {
            this.countNo = countNo;
        }

        public int getCountLittle() {
            return countLittle;
        }

        public void setCountLittle(int countLittle) {
            this.countLittle = countLittle;
        }

        public int getCountBest() {
            return countBest;
        }

        public void setCountBest(int countBest) {
            this.countBest = countBest;
        }

        public List<ListaBean> getLista() {
            return lista;
        }

        public void setLista(List<ListaBean> lista) {
            this.lista = lista;
        }

        public static class ListaBean {
            /**
             * returnContent : 111
             * logoAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105355605103929.png
             * payTime : 1526535437000
             * commentTime : 2018-05-08 16:09:44
             * content : 不好吃
             * imgUrl :
             * commentMemberId : 492
             * isPicture : 0
             * readNum : 0
             * serviceTitle : 我呢
             * servicePrice : 0.1
             * fabulousNum : 0
             * startNum : 1
             * nickname : 小猴子
             * commentId : 1904
             * serviceId : 2
             */

            private String returnContent;
            private String logoAttachmentAddress;
            private long payTime;
            private String commentTime;
            private String content;
            private String imgUrl;
            private int commentMemberId;
            private int isPicture;
            private int readNum;
            private String serviceTitle;
            private double servicePrice;
            private int fabulousNum;
            private int startNum;
            private String nickname;
            private int commentId;
            private int serviceId;

            public String getReturnContent() {
                return returnContent;
            }

            public void setReturnContent(String returnContent) {
                this.returnContent = returnContent;
            }

            public String getLogoAttachmentAddress() {
                return logoAttachmentAddress;
            }

            public void setLogoAttachmentAddress(String logoAttachmentAddress) {
                this.logoAttachmentAddress = logoAttachmentAddress;
            }

            public long getPayTime() {
                return payTime;
            }

            public void setPayTime(long payTime) {
                this.payTime = payTime;
            }

            public String getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getCommentMemberId() {
                return commentMemberId;
            }

            public void setCommentMemberId(int commentMemberId) {
                this.commentMemberId = commentMemberId;
            }

            public int getIsPicture() {
                return isPicture;
            }

            public void setIsPicture(int isPicture) {
                this.isPicture = isPicture;
            }

            public int getReadNum() {
                return readNum;
            }

            public void setReadNum(int readNum) {
                this.readNum = readNum;
            }

            public String getServiceTitle() {
                return serviceTitle;
            }

            public void setServiceTitle(String serviceTitle) {
                this.serviceTitle = serviceTitle;
            }

            public double getServicePrice() {
                return servicePrice;
            }

            public void setServicePrice(double servicePrice) {
                this.servicePrice = servicePrice;
            }

            public int getFabulousNum() {
                return fabulousNum;
            }

            public void setFabulousNum(int fabulousNum) {
                this.fabulousNum = fabulousNum;
            }

            public int getStartNum() {
                return startNum;
            }

            public void setStartNum(int startNum) {
                this.startNum = startNum;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                this.serviceId = serviceId;
            }
        }
    }
}
