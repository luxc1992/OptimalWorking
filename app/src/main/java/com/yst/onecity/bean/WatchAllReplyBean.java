package com.yst.onecity.bean;

import java.util.List;

/**
 * 查看评论全部回复列表 实体类
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/29
 */
public class WatchAllReplyBean {

    /**
     * code : 1
     * msg : 查询成功
     * id : null
     * createUser : null
     * createdTime : null
     * updateTime : null
     * createdIp : null
     * commentId : 1897
     * content : freed
     * fabulousNum : 30
     * isDeleted : null
     * replyNum : null
     * commentInfoId : null
     * consultationId : null
     * memberId : null
     * memberName : null
     * parent : null
     * planId : null
     * type : null
     * operationId : null
     * nickName : 2107
     * logoAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417144622397175343.png
     * replyList : [{"id":null,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"commentId":1897,"content":"freed","fabulousNum":30,"isDeleted":null,"replyNum":null,"commentInfoId":null,"consultationId":null,"memberId":null,"memberName":null,"parent":null,"planId":null,"type":null,"operationId":null,"nickName":"赵禹","logoAddress":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417144622397175343.png","replyList":null,"consultationCommentFabulousStatus":0,"bCount":null,"createTime":"2018-04-18 11:13:53.0"}]
     * consultationCommentFabulousStatus : 0
     * bCount : 8
     * createTime : 2018-04-18 11:13:53.0
     */

    private int code;
    private String msg;
    private Object id;
    private Object createUser;
    private Object createdTime;
    private Object updateTime;
    private Object createdIp;
    private int commentId;
    private String content;
    private int fabulousNum;
    private Object isDeleted;
    private Object replyNum;
    private Object commentInfoId;
    private Object consultationId;
    private Object memberId;
    private Object memberName;
    private Object parent;
    private Object planId;
    private Object type;
    private Object operationId;
    private String nickName;
    private String logoAddress;
    private int consultationCommentFabulousStatus;
    private int bCount;
    private String createTime;
    private List<ReplyListBean> replyList;

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

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Object createUser) {
        this.createUser = createUser;
    }

    public Object getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Object createdTime) {
        this.createdTime = createdTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(Object createdIp) {
        this.createdIp = createdIp;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFabulousNum() {
        return fabulousNum;
    }

    public void setFabulousNum(int fabulousNum) {
        this.fabulousNum = fabulousNum;
    }

    public Object getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Object isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Object replyNum) {
        this.replyNum = replyNum;
    }

    public Object getCommentInfoId() {
        return commentInfoId;
    }

    public void setCommentInfoId(Object commentInfoId) {
        this.commentInfoId = commentInfoId;
    }

    public Object getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Object consultationId) {
        this.consultationId = consultationId;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getMemberName() {
        return memberName;
    }

    public void setMemberName(Object memberName) {
        this.memberName = memberName;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getPlanId() {
        return planId;
    }

    public void setPlanId(Object planId) {
        this.planId = planId;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getOperationId() {
        return operationId;
    }

    public void setOperationId(Object operationId) {
        this.operationId = operationId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLogoAddress() {
        return logoAddress;
    }

    public void setLogoAddress(String logoAddress) {
        this.logoAddress = logoAddress;
    }

    public int getConsultationCommentFabulousStatus() {
        return consultationCommentFabulousStatus;
    }

    public void setConsultationCommentFabulousStatus(int consultationCommentFabulousStatus) {
        this.consultationCommentFabulousStatus = consultationCommentFabulousStatus;
    }

    public int getBCount() {
        return bCount;
    }

    public void setBCount(int bCount) {
        this.bCount = bCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ReplyListBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyListBean> replyList) {
        this.replyList = replyList;
    }

    public static class ReplyListBean {
        /**
         * id : null
         * createUser : null
         * createdTime : null
         * updateTime : null
         * createdIp : null
         * commentId : 1897
         * content : freed
         * fabulousNum : 30
         * isDeleted : null
         * replyNum : null
         * commentInfoId : null
         * consultationId : null
         * memberId : null
         * memberName : null
         * parent : null
         * planId : null
         * type : null
         * operationId : null
         * nickName : 赵禹
         * logoAddress : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417144622397175343.png
         * replyList : null
         * consultationCommentFabulousStatus : 0
         * bCount : null
         * createTime : 2018-04-18 11:13:53.0
         */

        private Object id;
        private Object createUser;
        private Object createdTime;
        private Object updateTime;
        private Object createdIp;
        private int commentId;
        private String content;
        private int fabulousNum;
        private Object isDeleted;
        private Object replyNum;
        private Object commentInfoId;
        private Object consultationId;
        private Object memberId;
        private Object memberName;
        private Object parent;
        private Object planId;
        private Object type;
        private Object operationId;
        private String nickName;
        private String logoAddress;
        private Object replyList;
        private int consultationCommentFabulousStatus;
        private Object bCount;
        private String createTime;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(Object createdTime) {
            this.createdTime = createdTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCreatedIp() {
            return createdIp;
        }

        public void setCreatedIp(Object createdIp) {
            this.createdIp = createdIp;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public Object getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Object isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(Object replyNum) {
            this.replyNum = replyNum;
        }

        public Object getCommentInfoId() {
            return commentInfoId;
        }

        public void setCommentInfoId(Object commentInfoId) {
            this.commentInfoId = commentInfoId;
        }

        public Object getConsultationId() {
            return consultationId;
        }

        public void setConsultationId(Object consultationId) {
            this.consultationId = consultationId;
        }

        public Object getMemberId() {
            return memberId;
        }

        public void setMemberId(Object memberId) {
            this.memberId = memberId;
        }

        public Object getMemberName() {
            return memberName;
        }

        public void setMemberName(Object memberName) {
            this.memberName = memberName;
        }

        public Object getParent() {
            return parent;
        }

        public void setParent(Object parent) {
            this.parent = parent;
        }

        public Object getPlanId() {
            return planId;
        }

        public void setPlanId(Object planId) {
            this.planId = planId;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getOperationId() {
            return operationId;
        }

        public void setOperationId(Object operationId) {
            this.operationId = operationId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLogoAddress() {
            return logoAddress;
        }

        public void setLogoAddress(String logoAddress) {
            this.logoAddress = logoAddress;
        }

        public Object getReplyList() {
            return replyList;
        }

        public void setReplyList(Object replyList) {
            this.replyList = replyList;
        }

        public int getConsultationCommentFabulousStatus() {
            return consultationCommentFabulousStatus;
        }

        public void setConsultationCommentFabulousStatus(int consultationCommentFabulousStatus) {
            this.consultationCommentFabulousStatus = consultationCommentFabulousStatus;
        }

        public Object getBCount() {
            return bCount;
        }

        public void setBCount(Object bCount) {
            this.bCount = bCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
