package com.yst.onecity.bean.order;

/**
 * 查看评价详情bean
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/26
 */
public class ServiceOrderEvaluateDetailBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"address":"123","price":1000,"startNum":3,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417102544771770878.jpg","salesNum":12,"createUser":null,"title":"我呢","content":"654321"}
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
         * address : 123
         * price : 1000
         * startNum : 3
         * imageAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417102544771770878.jpg
         * salesNum : 12
         * createUser : null
         * title : 我呢
         * content : 654321
         * commentId : 评价id
         * nickName : 评价人
         * anonymity ：0匿名  1不匿名
         */

        private String address;
        private double price;
        private int startNum;
        private String imageAddress;
        private int salesNum;
        private Object createUser;
        private String title;
        private String content;
        private String commentId;
        private int anonymity;
        private String nickName;
        private int source;
        private int isPicture;

        public void setAnonymity(int anonymity) {
            this.anonymity = anonymity;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getAnonymity() {

            return anonymity;
        }

        public String getNickName() {
            return nickName;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStartNum() {
            return startNum;
        }

        public void setStartNum(int startNum) {
            this.startNum = startNum;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public int getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(int salesNum) {
            this.salesNum = salesNum;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
