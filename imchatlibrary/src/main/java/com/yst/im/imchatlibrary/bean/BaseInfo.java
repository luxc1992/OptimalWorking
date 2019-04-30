package com.yst.im.imchatlibrary.bean;


/**
 * 通道 消息体
 *
 * @author Lierpeng
 * @date 2018/03/27.
 * @version 1.0.0
 */
public class BaseInfo extends BaseHeader {
    private int messageType;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * 发送者id   -1为系统
     */
    private String senderId;
    /**
     * 接收者id
     */
    private String accepteId;
    /**
     * 密码
     */
    private String password;
    /**
     * 具体的内容
     */
    private String content;
    /**
     * 请求来源
     */
    private String requestSourceSystem;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 发生时间
     */
    private long occureTime;
    /**
     * 是否群聊
     */
    private Boolean isGroupChat = false;

    public String getRequestSourceSystem() {
        return requestSourceSystem;
    }

    public void setRequestSourceSystem(String requestSourceSystem) {
        this.requestSourceSystem = requestSourceSystem;
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

    public Boolean getGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(Boolean groupChat) {
        isGroupChat = groupChat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "messageType=" + messageType +
                ", senderId='" + senderId + '\'' +
                ", accepteId='" + accepteId + '\'' +
                ", password='" + password + '\'' +
                ", content='" + content + '\'' +
                ", requestSourceSystem='" + requestSourceSystem + '\'' +
                ", nickName='" + nickName + '\'' +
                ", occureTime=" + occureTime +
                ", isGroupChat=" + isGroupChat +
                '}';
    }
}
