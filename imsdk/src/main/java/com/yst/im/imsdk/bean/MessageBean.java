package com.yst.im.imsdk.bean;


import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 消息实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9.
 */
@Entity
public class MessageBean {
    private String accepteId;
    private String content;
    private int event;
    private boolean groupChat = false;
    @Id
    private Long id;
    private String nickName;
    private long occureTime;
    private String password;
    private String requestSourceSystem;
    private String senderId;
    private int type;
    @Unique
    private String version;
    private int messageType;
    private String newsId;
    private String path;
    private int second;
    private int sendState;
    /**
     * 是否已经播放过
     */
    private boolean isPlayed;
    /**
     * 是否正在播放
     */
    private boolean isPlaying;
    private int isRead;
    private String groupId;
    private String portrait;
    private String contactsId;

    @Generated(hash = 835506230)
    public MessageBean(String accepteId, String content, int event,
                       boolean groupChat, Long id, String nickName, long occureTime,
                       String password, String requestSourceSystem, String senderId, int type,
                       String version, int messageType, String newsId, String path, int second,
                       int sendState, boolean isPlayed, boolean isPlaying, int isRead,
                       String groupId, String portrait, String contactsId) {
        this.accepteId = accepteId;
        this.content = content;
        this.event = event;
        this.groupChat = groupChat;
        this.id = id;
        this.nickName = nickName;
        this.occureTime = occureTime;
        this.password = password;
        this.requestSourceSystem = requestSourceSystem;
        this.senderId = senderId;
        this.type = type;
        this.version = version;
        this.messageType = messageType;
        this.newsId = newsId;
        this.path = path;
        this.second = second;
        this.sendState = sendState;
        this.isPlayed = isPlayed;
        this.isPlaying = isPlaying;
        this.isRead = isRead;
        this.groupId = groupId;
        this.portrait = portrait;
        this.contactsId = contactsId;
    }

    @Generated(hash = 1588632019)
    public MessageBean() {
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

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
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

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
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

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public boolean getGroupChat() {
        return this.groupChat;
    }

    public boolean getIsPlayed() {
        return this.isPlayed;
    }

    public void setIsPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }

    public boolean getIsPlaying() {
        return this.isPlaying;
    }


    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
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
                ", sendState=" + sendState +
                ", isPlayed=" + isPlayed +
                ", isPlaying=" + isPlaying +
                ", groupId='" + groupId + '\'' +
                ", portrait='" + portrait + '\'' +
                ", contactsId='" + contactsId + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
