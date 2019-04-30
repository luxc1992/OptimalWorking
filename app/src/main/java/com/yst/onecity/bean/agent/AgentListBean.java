package com.yst.onecity.bean.agent;

import java.util.List;

/**
 * 经纪人中心页面列表实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/25
 */
public class AgentListBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"id":32,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"AA","content":"111","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":31,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"","title":"哈哈","content":"oo","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":30,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"哈哈","content":"oo","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":28,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":27,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":26,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":25,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":24,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":23,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null},{"id":22,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"imageAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg","title":"11","content":"11","topicClassifyId":null,"isHot":null,"hunterId":null,"collectionNum":null,"commentNum":null,"readNum":0,"priseNum":0,"isCheck":null,"isDeleted":null,"feedback":null}]
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
         * id : 32
         * createUser : null
         * createdTime : null
         * updateTime : null
         * createdIp : null
         * imageAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417114530685659858.jpg
         * title : AA
         * content : 111
         * topicClassifyId : null
         * isHot : null
         * hunterId : null
         * collectionNum : null
         * commentNum : null
         * readNum : 0
         * priseNum : 0
         * isCheck : null
         * isDeleted : null
         * feedback : null
         */

        private int id;
        private String createUser;
        private String createdTime;
        private String updateTime;
        private String createdIp;
        private String imageAddress;
        private String title;
        private String content;
        private int topicClassifyId;
        private String isHot;
        private String hunterId;
        private String collectionNum;
        private String commentNum;
        private int readNum;
        private int priseNum;
        private boolean isCheck;
        private boolean isDeleted;
        private String feedback;
        private boolean isClick;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreatedIp() {
            return createdIp;
        }

        public void setCreatedIp(String createdIp) {
            this.createdIp = createdIp;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
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

        public int getTopicClassifyId() {
            return topicClassifyId;
        }

        public void setTopicClassifyId(int topicClassifyId) {
            this.topicClassifyId = topicClassifyId;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public String getHunterId() {
            return hunterId;
        }

        public void setHunterId(String hunterId) {
            this.hunterId = hunterId;
        }

        public String getCollectionNum() {
            return collectionNum;
        }

        public void setCollectionNum(String collectionNum) {
            this.collectionNum = collectionNum;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public int getReadNum() {
            return readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }

        public int getPriseNum() {
            return priseNum;
        }

        public void setPriseNum(int priseNum) {
            this.priseNum = priseNum;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }
    }
}
