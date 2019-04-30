package com.yst.onecity.bean;

/**
 * 切换专员返回数据实体类
 *
 * @author chejainqi
 * @version 1.0.1
 * @date 2018/3/8.
 */

public class ChangeSpeciallyPersonBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"address":null,"phone":"15011552664","groupId":null,"nickname":"高歌","chatGroup":"美食家族二代","id":1,"provinceName":null,"uuid":"123456789","token":null}
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
         * address : null
         * phone : 15011552664
         * groupId : null
         * nickname : 高歌
         * chatGroup : 美食家族二代
         * id : 1
         * provinceName : null
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
    }
}
