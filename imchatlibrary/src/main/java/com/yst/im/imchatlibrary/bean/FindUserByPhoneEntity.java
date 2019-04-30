package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 根据手机号查询好友信息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11
 */

public class FindUserByPhoneEntity implements Serializable{

    /**
     * code : 0
     * msg : 查询成功
     * content : [{"id":30,"createTime":null,"updateTime":1523528292000,"userId":"74071692F18A41D98B5021C432ECC2C8","nickName":"呵呵呵","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180412/20180412181812685935608.jpeg","userType":"0","requestSourceSystem":"im","address":"辽宁 辽阳","phone":"15910422500","sex":"女","userPassword":"111nnn","remark":null}]
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
         * id : 30
         * createTime : null
         * updateTime : 1523528292000
         * userId : 74071692F18A41D98B5021C432ECC2C8
         * nickName : 呵呵呵
         * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180412/20180412181812685935608.jpeg
         * userType : 0
         * requestSourceSystem : im
         * address : 辽宁 辽阳
         * phone : 15910422500
         * sex : 女
         * userPassword : 111nnn
         * remark : null
         */

        private int id;
        private String createTime;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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
    }
}
