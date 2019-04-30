package com.yst.onecity.bean.issue;

import java.util.List;

/**
 * @author zhaiyanwu
 * @version v1.0.1
 * @date 2018/3/14.
 */
public class CommodityStairClassifyBean {


    /**
     * code : 1
     * msg : 查询成功
     * content : [{"classifyLevel":1,"classifyName":"一级","description":"的说法都是对方","isDeleted":null,"parentId":null,"sort":1,"status":null,"attachmentId":null,"id":1,"createUser":null,"createdTime":null,"updateTime":null,"createdIp":null,"address":null}]
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
         * classifyLevel : 1
         * classifyName : 一级
         * description : 的说法都是对方
         * isDeleted : null
         * parentId : null
         * sort : 1
         * status : null
         * attachmentId : null
         * id : 1
         * createUser : null
         * createdTime : null
         * updateTime : null
         * createdIp : null
         * address : null
         */

        private int classifyLevel;
        private String classifyName;
        private String description;
        private Object isDeleted;
        private Object parentId;
        private int sort;
        private Object status;
        private Object attachmentId;
        private int id;
        private Object createUser;
        private Object createdTime;
        private Object updateTime;
        private Object createdIp;
        private Object address;

        public int getClassifyLevel() {
            return classifyLevel;
        }

        public void setClassifyLevel(int classifyLevel) {
            this.classifyLevel = classifyLevel;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Object isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(Object attachmentId) {
            this.attachmentId = attachmentId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }
    }
}
