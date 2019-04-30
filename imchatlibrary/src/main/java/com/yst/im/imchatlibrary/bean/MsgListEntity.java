package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 历史消息实体类
 *
 * @author  Lierpeng
 * @date 2018/04/24.
 * @version 1.0.0
 */
public class MsgListEntity {
    /**
     * code : 0
     * msg : 查询历史消息成功
     * content : [{"id":1060,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"11111111111","requestSourceSystem":null,"nickName":null,"occureTime":1512370677000,"groupChat":false},{"id":1061,"type":-1,"event":1,"version":null,"senderId":"tp0002","accepteId":"tp0001","password":"通权","content":"222222222222","requestSourceSystem":null,"nickName":null,"occureTime":1512370690000,"groupChat":false},{"id":1062,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"3333333","requestSourceSystem":null,"nickName":null,"occureTime":1512370701000,"groupChat":false},{"id":1063,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"44444444444","requestSourceSystem":null,"nickName":null,"occureTime":1512370708000,"groupChat":false},{"id":1064,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"5555555555","requestSourceSystem":null,"nickName":null,"occureTime":1512370714000,"groupChat":false},{"id":1065,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"666666666666","requestSourceSystem":null,"nickName":null,"occureTime":1512370719000,"groupChat":false},{"id":1066,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"7777777777777","requestSourceSystem":null,"nickName":null,"occureTime":1512370724000,"groupChat":false},{"id":1067,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"888888888888888888","requestSourceSystem":null,"nickName":null,"occureTime":1512370732000,"groupChat":false},{"id":1068,"type":-1,"event":1,"version":null,"senderId":"tp0001","accepteId":"tp0002","password":"通权","content":"9999999999","requestSourceSystem":null,"nickName":null,"occureTime":1512370737000,"groupChat":false}]
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
         * id : 1060
         * type : -1
         * event : 1
         * version : null
         * senderId : tp0001
         * accepteId : tp0002
         * password : 通权
         * content : 11111111111
         * requestSourceSystem : null
         * nickName : null
         * occureTime : 1512370677000
         * groupChat : false
         */

        private int id;
        private int type;
        private int event;
        private Object version;
        private String senderId;
        private String accepteId;
        private String password;
        private String content;
        private Object requestSourceSystem;
        private Object nickName;
        private long occureTime;
        private boolean groupChat;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getEvent() {
            return event;
        }

        public void setEvent(int event) {
            this.event = event;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getAccepteId() {
            return accepteId;
        }

        public void setAccepteId(String accepteId) {
            this.accepteId = accepteId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getRequestSourceSystem() {
            return requestSourceSystem;
        }

        public void setRequestSourceSystem(Object requestSourceSystem) {
            this.requestSourceSystem = requestSourceSystem;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public long getOccureTime() {
            return occureTime;
        }

        public void setOccureTime(long occureTime) {
            this.occureTime = occureTime;
        }

        public boolean isGroupChat() {
            return groupChat;
        }

        public void setGroupChat(boolean groupChat) {
            this.groupChat = groupChat;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", type=" + type +
                    ", event=" + event +
                    ", version=" + version +
                    ", senderId='" + senderId + '\'' +
                    ", accepteId='" + accepteId + '\'' +
                    ", password='" + password + '\'' +
                    ", content='" + content + '\'' +
                    ", requestSourceSystem=" + requestSourceSystem +
                    ", nickName=" + nickName +
                    ", occureTime=" + occureTime +
                    ", groupChat=" + groupChat +
                    '}';
        }
    }
}
