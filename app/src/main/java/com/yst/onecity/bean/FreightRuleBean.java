package com.yst.onecity.bean;

import java.util.List;

/**
 * 运费模板实体类
 * @author zhaiyanwu
 * @version v3.0.1
 * @date 2018/3/2.
 */
public class FreightRuleBean {


    /**
     * code : 1
     * msg : 获取成功
     * content : [{"id":8,"createUser":null,"createdTime":"2018-02-27 16:31:14","updateTime":"2018-02-27 16:31:14","createdIp":null,"name":"ceshi","userType":2,"userId":36},{"id":9,"createUser":null,"createdTime":"2018-02-27 16:35:27","updateTime":"2018-02-27 16:35:27","createdIp":null,"name":"ceshi","userType":2,"userId":36}]
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
         * id : 8
         * createUser : null
         * createdTime : 2018-02-27 16:31:14
         * updateTime : 2018-02-27 16:31:14
         * createdIp : null
         * name : ceshi
         * userType : 2
         * userId : 36
         */

        private int id;
        private Object createUser;
        private String createdTime;
        private String updateTime;
        private Object createdIp;
        private String name;
        private int userType;
        private int userId;

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

        public Object getCreatedIp() {
            return createdIp;
        }

        public void setCreatedIp(Object createdIp) {
            this.createdIp = createdIp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}

