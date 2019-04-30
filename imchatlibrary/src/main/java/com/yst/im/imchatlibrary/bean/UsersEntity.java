package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 查询用户信息
 *
 * @author Lierpeng
 * @version 1.0.1
 * @date 2018/2/23.
 */

public class UsersEntity {

    /**
     * code : 0
     * msg : 查询用户信息成功
     * content : [{"id":94,"createTime":1516774690000,"updateTime":1516774690000,"userId":"71fd40fdffe449569a21d4422aab3724","nickName":"zyh","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180124/20180124141754609100385.jpeg","userType":"-1","requestSourceSystem":"xinchangcheng","address":null,"phone":"xcc020","companyName":"易商通","job":"测试","industry":null,"userPassword":"123456"}]
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

    public static class ContentBean implements Serializable {
        /**
         * id : 94
         * createTime : 1516774690000
         * updateTime : 1516774690000
         * userId : 71fd40fdffe449569a21d4422aab3724
         * nickName : zyh
         * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180124/20180124141754609100385.jpeg
         * userType : -1
         * requestSourceSystem : xinchangcheng
         * address : null
         * phone : xcc020
         * companyName : 易商通
         * job : 测试
         * industry : null
         * userPassword : 123456
         */

        private int id;
        private long createTime;
        private long updateTime;
        private String userId;
        private String nickName;
        private String userIcon;
        private String userType;
        private String requestSourceSystem;
        private Object address;
        private String phone;
        private String companyName;
        private String job;
        private Object industry;
        private String userPassword;
        private boolean isGroup = false;

        public boolean isGroup() {
            return isGroup;
        }

        public void setGroup(boolean group) {
            isGroup = group;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getRequestSourceSystem() {
            return requestSourceSystem;
        }

        public void setRequestSourceSystem(String requestSourceSystem) {
            this.requestSourceSystem = requestSourceSystem;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Object getIndustry() {
            return industry;
        }

        public void setIndustry(Object industry) {
            this.industry = industry;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }
    }
}
