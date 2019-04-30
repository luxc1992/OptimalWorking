package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 产品计划实体类
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/24
 */

public class ProjectPlanBean implements Serializable{

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"id":21,"title":"多少","fund":null,"describes":"11第三方","planClassifyId":null,"attachmentId":94,"isRecommend":null,"createdTime":null,"status":0,"browserNum":null,"shareNum":0,"address":"多发的生","attachmentaddress":"dd","content":null},{"id":11,"title":"wer","fund":null,"describes":"we","planClassifyId":null,"attachmentId":45,"isRecommend":null,"endTime":"2018-03-01 10:18:34","status":0,"browserNum":null,"shareNum":0,"address":"w","attachmentaddress":"qqw"}]
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

    public static class ContentBean implements Serializable{
        /**
         * id : 21
         * title : 多少
         * fund : null
         * describes : 11第三方
         * planClassifyId : null
         * attachmentId : 94
         * isRecommend : null
         * createdTime : null
         * status : 0
         * browserNum : null
         * shareNum : 0
         * address : 多发的生
         * attachmentaddress : dd
         * content : null
         * endTime : 2018-03-01 10:18:34
         */

        private int id;
        private String title;
        private Object fund;
        private String describes;
        private Object planClassifyId;
        private int attachmentId;
        private Object isRecommend;
        private String createdTime;
        private int status;
        private Object browserNum;
        private int shareNum;
        private String address;
        private String attachmentaddress;
        private String content;
        private String endTime;

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

        public Object getFund() {
            return fund;
        }

        public void setFund(Object fund) {
            this.fund = fund;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public Object getPlanClassifyId() {
            return planClassifyId;
        }

        public void setPlanClassifyId(Object planClassifyId) {
            this.planClassifyId = planClassifyId;
        }

        public int getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(int attachmentId) {
            this.attachmentId = attachmentId;
        }

        public Object getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(Object isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getBrowserNum() {
            return browserNum;
        }

        public void setBrowserNum(Object browserNum) {
            this.browserNum = browserNum;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAttachmentaddress() {
            return attachmentaddress;
        }

        public void setAttachmentaddress(String attachmentaddress) {
            this.attachmentaddress = attachmentaddress;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
