package com.yst.im.imchatlibrary.bean;

/**
 * 聊天详情，查群好友信息
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/20.
 */

public class SelectFriendEntity {

    /**
     * code : 0
     * msg : 查询用户信息成功
     * content : {"userChat":{"id":13,"createTime":null,"updateTime":null,"userId":"553E047F64BC41CC82DD2DB82EC18AD8","nickName":"Sas","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105155211045029.png","userType":"0","requestSourceSystem":"im","address":null,"phone":"13804051995","sex":"男","userPassword":"123456","remark":null},"isStick":0,"isShield":1}
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
         * userChat : {"id":13,"createTime":null,"updateTime":null,"userId":"553E047F64BC41CC82DD2DB82EC18AD8","nickName":"Sas","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105155211045029.png","userType":"0","requestSourceSystem":"im","address":null,"phone":"13804051995","sex":"男","userPassword":"123456","remark":null}
         * isStick : 0
         * isShield : 1
         */

        private UserChatBean userChat;
        private int isStick;
        private int isShield;

        public UserChatBean getUserChat() {
            return userChat;
        }

        public void setUserChat(UserChatBean userChat) {
            this.userChat = userChat;
        }

        public int getIsStick() {
            return isStick;
        }

        public void setIsStick(int isStick) {
            this.isStick = isStick;
        }

        public int getIsShield() {
            return isShield;
        }

        public void setIsShield(int isShield) {
            this.isShield = isShield;
        }

        public static class UserChatBean {
            /**
             * id : 13
             * createTime : null
             * updateTime : null
             * userId : 553E047F64BC41CC82DD2DB82EC18AD8
             * nickName : Sas
             * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105155211045029.png
             * userType : 0
             * requestSourceSystem : im
             * address : null
             * phone : 13804051995
             * sex : 男
             * userPassword : 123456
             * remark : null
             */

            private int id;
            private String createTime;
            private String updateTime;
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

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
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
}
