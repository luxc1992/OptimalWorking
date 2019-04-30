package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 注册实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/10
 */
public class RegisterEntity {

    /**
     * code : 0
     * msg : 注册成功
     * content : [{"id":12,"createTime":null,"updateTime":null,"userId":"EE3683BF85CA43A5A27E3B188351D265","nickName":null,"userIcon":null,"userType":"0","requestSourceSystem":null,"address":null,"phone":"15632635839","sex":null,"userPassword":"123456"}]
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
         * id : 12
         * createTime : null
         * updateTime : null
         * userId : EE3683BF85CA43A5A27E3B188351D265
         * nickName : null
         * userIcon : null
         * userType : 0
         * requestSourceSystem : null
         * address : null
         * phone : 15632635839
         * sex : null
         * userPassword : 123456
         */

        private int id;
        private Object createTime;
        private Object updateTime;
        private String userId;
        private Object nickName;
        private Object userIcon;
        private String userType;
        private Object requestSourceSystem;
        private Object address;
        private String phone;
        private Object sex;
        private String userPassword;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public Object getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(Object userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Object getRequestSourceSystem() {
            return requestSourceSystem;
        }

        public void setRequestSourceSystem(Object requestSourceSystem) {
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

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }
    }
}
