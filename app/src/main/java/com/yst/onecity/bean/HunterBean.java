package com.yst.onecity.bean;

/**
 * 我的  猎头个人主页Bean
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/5
 */

public class HunterBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"attentionNum":3,"waitSendGoodsNum":1,"sendGoodsNum":0,"isHunter":0,"fansNum":4,"headImg":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180410/20180410113719669979361.png","waitPaymentNum":0,"groupId":null,"isMerchant":1,"score":375,"money":0.11,"chatHunterId":380,"returnNum":3,"shopCartNum":0,"waitCommentNum":0,"id":381,"connect":2,"username":"孙悟空"}
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
         * attentionNum : 3
         * waitSendGoodsNum : 1
         * sendGoodsNum : 0
         * isHunter : 0
         * fansNum : 4
         * headImg : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180410/20180410113719669979361.png
         * waitPaymentNum : 0
         * groupId : null
         * isMerchant : 1
         * score : 375
         * money : 0.11
         * chatHunterId : 380
         * returnNum : 3
         * shopCartNum : 0
         * waitCommentNum : 0
         * id : 381
         * connect : 2
         * username : 孙悟空
         */

        private int attentionNum;
        private int waitSendGoodsNum;
        private int sendGoodsNum;
        private int isHunter;
        private int fansNum;
        private String headImg;
        private int waitPaymentNum;
        private int groupId;
        private int isMerchant;
        private int score;
        private double money;
        private int chatHunterId;
        private int returnNum;
        private int shopCartNum;
        private int waitCommentNum;
        private int id;
        private int connect;
        private String username;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public int getWaitSendGoodsNum() {
            return waitSendGoodsNum;
        }

        public void setWaitSendGoodsNum(int waitSendGoodsNum) {
            this.waitSendGoodsNum = waitSendGoodsNum;
        }

        public int getSendGoodsNum() {
            return sendGoodsNum;
        }

        public void setSendGoodsNum(int sendGoodsNum) {
            this.sendGoodsNum = sendGoodsNum;
        }

        public int getIsHunter() {
            return isHunter;
        }

        public void setIsHunter(int isHunter) {
            this.isHunter = isHunter;
        }

        public int getFansNum() {
            return fansNum;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getWaitPaymentNum() {
            return waitPaymentNum;
        }

        public void setWaitPaymentNum(int waitPaymentNum) {
            this.waitPaymentNum = waitPaymentNum;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getIsMerchant() {
            return isMerchant;
        }

        public void setIsMerchant(int isMerchant) {
            this.isMerchant = isMerchant;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getChatHunterId() {
            return chatHunterId;
        }

        public void setChatHunterId(int chatHunterId) {
            this.chatHunterId = chatHunterId;
        }

        public int getReturnNum() {
            return returnNum;
        }

        public void setReturnNum(int returnNum) {
            this.returnNum = returnNum;
        }

        public int getShopCartNum() {
            return shopCartNum;
        }

        public void setShopCartNum(int shopCartNum) {
            this.shopCartNum = shopCartNum;
        }

        public int getWaitCommentNum() {
            return waitCommentNum;
        }

        public void setWaitCommentNum(int waitCommentNum) {
            this.waitCommentNum = waitCommentNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getConnect() {
            return connect;
        }

        public void setConnect(int connect) {
            this.connect = connect;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
