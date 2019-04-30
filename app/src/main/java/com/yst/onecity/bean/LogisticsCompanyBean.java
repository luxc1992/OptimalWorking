package com.yst.onecity.bean;

import java.util.List;

/**
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/1
 */

public class LogisticsCompanyBean {

    /**
     * code : 1
     * msg : 查询货成功
     * content : [{"id":1,"createUser":null,"createdTime":"2018-02-25 13:44:33","updateTime":"2018-02-28 18:31:39","createdIp":null,"companyName":"32323","status":0,"isDelete":0,"number":100001},{"id":36,"createUser":null,"createdTime":"2018-02-28 17:15:13","updateTime":"2018-02-28 18:31:29","createdIp":null,"companyName":"556","status":0,"isDelete":0,"number":100001},{"id":37,"createUser":null,"createdTime":"2018-02-28 17:16:31","updateTime":null,"createdIp":null,"companyName":"88","status":0,"isDelete":0,"number":100001},{"id":38,"createUser":null,"createdTime":"2018-02-28 19:06:10","updateTime":null,"createdIp":null,"companyName":"vukvhkh","status":0,"isDelete":0,"number":100001}]
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
         * id : 1
         * createUser : null
         * createdTime : 2018-02-25 13:44:33
         * updateTime : 2018-02-28 18:31:39
         * createdIp : null
         * companyName : 32323
         * status : 0
         * isDelete : 0
         * number : 100001
         */

        private String id;
        private Object createUser;
        private String createdTime;
        private String updateTime;
        private Object createdIp;
        private String companyName;
        private int status;
        private int isDelete;
        private int number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
