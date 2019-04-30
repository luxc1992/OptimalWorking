package com.yst.im.imchatlibrary.bean;

/**
 * 登录实体类
 *
 * @author Lierpeng
 * @date 2018/4/11.
 * @version 1.0.0
 */
public class LoginEntity {

    /**
     * code : 0
     * msg : 登录成功
     * content : {"address":"河北 石家庄","phone":"13439223424","nickName":"葫芦娃","sex":"男","updateTime":1523426469000,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","userId":"F36AE2459B5F4A0EB8C2AA505D76E0F7","token":"3b265b8c5c4a47b3a59cae0e10a3366a"}
     */

    private int code;
    private String msg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * address : 河北 石家庄
         * phone : 13439223424
         * nickName : 葫芦娃
         * sex : 男
         * updateTime : 1523426469000
         * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg
         * userId : F36AE2459B5F4A0EB8C2AA505D76E0F7
         * token : 3b265b8c5c4a47b3a59cae0e10a3366a
         */

        private String address;
        private String phone;
        private String nickName;
        private String sex;
        private String updateTime;
        private String userIcon;
        private String userId;
        private String token;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", sex='" + sex + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", userIcon='" + userIcon + '\'' +
                    ", userId='" + userId + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }
}
