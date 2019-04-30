package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 查询好友列表
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11
 */

public class FriendListEntity {

    /**
     * code : 0
     * msg : 查询好友列表成功
     * content : [{"id":3,"createTime":null,"updateTime":null,"userId":"EE1B5DF9633644BDAA6555A56CE7066D","nickName":"huangfan","userIcon":"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","userType":"0","requestSourceSystem":null,"address":null,"phone":"18514046695","sex":null,"userPassword":"789456"},{"id":7,"createTime":null,"updateTime":null,"userId":"BD3779D0EA14472EAFED54493C4D4BC8","nickName":"洒洒水","userIcon":"abcdefg","userType":"0","requestSourceSystem":null,"address":null,"phone":"2222222222222","sex":null,"userPassword":"123456"}]
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
         * id : 3
         * createTime : null
         * updateTime : null
         * userId : EE1B5DF9633644BDAA6555A56CE7066D
         * nickName : huangfan
         * userIcon : aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
         * userType : 0
         * requestSourceSystem : null
         * address : null
         * phone : 18514046695
         * sex : null
         * userPassword : 789456
         *
         "remark":null,
         "state":1
         */

        private int id;
        private long createTime;
        private long updateTime;
        private String userId;
        private String nickName;
        private String userIcon;
        private String userType;
        private String requestSourceSystem;
        private String address;
        private String phone;
        private String sex;
        private String userPassword;
        private String remark;
        private String state;
        private boolean isGroup = false;
        private boolean isChecked;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", userId='" + userId + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", userIcon='" + userIcon + '\'' +
                    ", userType='" + userType + '\'' +
                    ", requestSourceSystem='" + requestSourceSystem + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", sex='" + sex + '\'' +
                    ", userPassword='" + userPassword + '\'' +
                    ", remark='" + remark + '\'' +
                    ", isGroup=" + isGroup +
                    ", isChecked=" + isChecked +
                    '}';
        }
    }
}
