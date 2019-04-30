package com.yst.im.imchatlibrary.bean;

import java.util.List;

/**
 * 历史消息实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class HistoryMsgEntity {

    /**
     * code : 0
     * msg : 查询历史消息成功
     * content : [{"id":70,"type":0,"event":1,"version":"1515034889568","senderId":"xcc001","accepteId":"xcc000","password":null,"content":"22","requestSourceSystem":"xcc","nickName":"xcc001","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":71,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"222","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":72,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"22","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":73,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"22","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":74,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"22","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":75,"type":0,"event":1,"version":"1515034889568","senderId":"xcc001","accepteId":"xcc000","password":null,"content":"22","requestSourceSystem":"xcc","nickName":"xcc001","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":76,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"333","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":77,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"333","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":78,"type":0,"event":1,"version":"1515034889568","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"3","requestSourceSystem":"xcc","nickName":"xcc000","occureTime":1515034890000,"groupId":null,"portrait":"","msgStatus":null,"heartbeatStatus":null,"groupChat":false},{"id":81,"type":0,"event":3,"version":"1515834524384","senderId":"xcc000","accepteId":"xcc001","password":null,"content":"hiuyhiuyoiho","requestSourceSystem":REQUEST_SS,"nickName":"zhangsan","occureTime":1515834524000,"groupId":"1","portrait":null,"msgStatus":null,"heartbeatStatus":null,"groupChat":false}]
     */
    private int code;
    private String msg;
    private List<MessageBean> content;

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

    public List<MessageBean> getContent() {
        return content;
    }

    public void setContent(List<MessageBean> content) {
        this.content = content;
    }

    public static class MessageBean {
        /**
         * id : 70
         * type : 0
         * event : 1
         * version : 1515034889568
         * senderId : xcc001
         * accepteId : xcc000
         * password : null
         * content : 22
         * requestSourceSystem : xcc
         * nickName : xcc001
         * occureTime : 1515034890000
         * groupId : null
         * portrait :
         * msgStatus : null
         * heartbeatStatus : null
         * groupChat : false
         */

        private String accepteId;
        private String content;
        private int event;
        private boolean groupChat = false;
        private Long id;
        private String nickName;
        private long occureTime;
        private String password;
        private String requestSourceSystem;
        private String senderId;
        private int type;
        private String version;
        private int messageType;
        private String newsId;
        private String path;
        private int second;
        private boolean isPlayed=false;
        private boolean isPlaying=false;
        private String groupId;
        private String portrait;

        public String getAccepteId() {
            return accepteId;
        }

        public void setAccepteId(String accepteId) {
            this.accepteId = accepteId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getEvent() {
            return event;
        }

        public void setEvent(int event) {
            this.event = event;
        }

        public boolean isGroupChat() {
            return groupChat;
        }

        public void setGroupChat(boolean groupChat) {
            this.groupChat = groupChat;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getOccureTime() {
            return occureTime;
        }

        public void setOccureTime(long occureTime) {
            this.occureTime = occureTime;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRequestSourceSystem() {
            return requestSourceSystem;
        }

        public void setRequestSourceSystem(String requestSourceSystem) {
            this.requestSourceSystem = requestSourceSystem;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public boolean isPlayed() {
            return isPlayed;
        }

        public void setPlayed(boolean played) {
            isPlayed = played;
        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public void setPlaying(boolean playing) {
            isPlaying = playing;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "accepteId='" + accepteId + '\'' +
                    ", content='" + content + '\'' +
                    ", event=" + event +
                    ", groupChat=" + groupChat +
                    ", id=" + id +
                    ", nickName='" + nickName + '\'' +
                    ", occureTime=" + occureTime +
                    ", password='" + password + '\'' +
                    ", requestSourceSystem='" + requestSourceSystem + '\'' +
                    ", senderId='" + senderId + '\'' +
                    ", type=" + type +
                    ", version='" + version + '\'' +
                    ", messageType=" + messageType +
                    ", newsId='" + newsId + '\'' +
                    ", path='" + path + '\'' +
                    ", second=" + second +
                    ", isPlayed=" + isPlayed +
                    ", isPlaying=" + isPlaying +
                    ", groupId='" + groupId + '\'' +
                    ", portrait='" + portrait + '\'' +
                    '}';
        }
    }
}
