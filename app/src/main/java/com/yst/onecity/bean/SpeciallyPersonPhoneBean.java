package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 专员列表通讯录好友
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/5
 */

public class SpeciallyPersonPhoneBean implements Serializable {

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"address":"https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180305/20180305181255491063930.png","phone":"15011552664","groupId":null,"nickname":"测试","chatGroup":"我是你的眼","id":1,"provinceName":"河北省","uuid":"123456789","token":null}]
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
         * address : https://yst-pjyc-server.oss-cn-beijing.aliyuncs.com/datas/image/20180305/20180305181255491063930.png
         * phone : 15011552664
         * groupId : null
         * nickname : 测试
         * chatGroup : 我是你的眼
         * id : 1
         * provinceName : 河北省
         * uuid : 123456789
         * token : null
         */

        private String address;
        private String phone;
        private String groupId;
        private String nickname;
        private String chatGroup;
        private int id;
        private String provinceName;
        private String uuid;
        private String token;
        boolean ischeck;

        public boolean ischeck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
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

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getChatGroup() {
            return chatGroup;
        }

        public void setChatGroup(String chatGroup) {
            this.chatGroup = chatGroup;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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
                    ", groupId='" + groupId + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", chatGroup='" + chatGroup + '\'' +
                    ", id=" + id +
                    ", provinceName='" + provinceName + '\'' +
                    ", uuid='" + uuid + '\'' +
                    ", token='" + token + '\'' +
                    ", ischeck=" + ischeck +
                    '}';
        }
    }

}
