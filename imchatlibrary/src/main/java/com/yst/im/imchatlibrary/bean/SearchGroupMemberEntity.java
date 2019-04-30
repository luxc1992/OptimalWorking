package com.yst.im.imchatlibrary.bean;

import java.util.List;

/**
 * 搜索群组成员
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/24.
 */

public class SearchGroupMemberEntity {

    /**
     * code : 0
     * msg : 搜索成功
     * content : [{"userId":"BD3779D0EA14472EAFED54493C4D4BC8","nickName":"发给","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","userType":"0","requestSourceSystem":"im","address":"北京","phone":"im001","sex":null,"userPassword":null,"remark":null,"id":7,"createTime":null,"updateTime":1523179441000,"state":null}]
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
         * userId : BD3779D0EA14472EAFED54493C4D4BC8
         * nickName : 发给
         * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg
         * userType : 0
         * requestSourceSystem : im
         * address : 北京
         * phone : im001
         * sex : null
         * userPassword : null
         * remark : null
         * id : 7
         * createTime : null
         * updateTime : 1523179441000
         * state : null
         */

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
        private int id;
        private String createTime;
        private long updateTime;
        private String state;
        private boolean isCheck;

        public boolean getCheck() {
            return isCheck;
        }

        public void setCheck(boolean isCheck) {
            this.isCheck = isCheck;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
