package com.yst.onecity.bean.mine;

/**
 * (我的)个人中心
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/5/23
 */
public class PersonalCenterBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : {"score":137,"isHunter":0,"logoAttachmentAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524152027835519324.png","signCount":1,"signStatus":1,"nickname":"0545","id":558,"afterMoney":1370000,"isMerchant":0,"advisorName":""}
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
         * score : 137
         * isHunter : 0
         * logoAttachmentAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180524/20180524152027835519324.png
         * signCount : 1
         * signStatus : 1
         * nickname : 0545
         * id : 558
         * afterMoney : 1370000
         * isMerchant : 0
         * advisorName :
         */

        private String score;
        private int isHunter;
        private String logoAttachmentAddress;
        private String signCount;
        private int signStatus;
        private String nickname;
        private long id;
        private String afterMoney;
        private int isMerchant;
        private String advisorName;
        private int advisorId;
        private int examineStatus;

        public int getExamineStatus() {
            return examineStatus;
        }

        public void setExamineStatus(int examineStatus) {
            this.examineStatus = examineStatus;
        }

        public int getAdvisorId() {
            return advisorId;
        }

        public void setAdvisorId(int advisorId) {
            this.advisorId = advisorId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getIsHunter() {
            return isHunter;
        }

        public void setIsHunter(int isHunter) {
            this.isHunter = isHunter;
        }

        public String getLogoAttachmentAddress() {
            return logoAttachmentAddress;
        }

        public void setLogoAttachmentAddress(String logoAttachmentAddress) {
            this.logoAttachmentAddress = logoAttachmentAddress;
        }

        public String getSignCount() {
            return signCount;
        }

        public void setSignCount(String signCount) {
            this.signCount = signCount;
        }

        public int getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(int signStatus) {
            this.signStatus = signStatus;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAfterMoney() {
            return afterMoney;
        }

        public void setAfterMoney(String afterMoney) {
            this.afterMoney = afterMoney;
        }

        public int getIsMerchant() {
            return isMerchant;
        }

        public void setIsMerchant(int isMerchant) {
            this.isMerchant = isMerchant;
        }

        public String getAdvisorName() {
            return advisorName;
        }

        public void setAdvisorName(String advisorName) {
            this.advisorName = advisorName;
        }
    }
}
