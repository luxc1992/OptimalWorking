package com.yst.im.imsdk.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 最近联系人
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/12.
 */
@Entity
public class ContactsEntity {
    @Id
    private Long id;
    private int type;
    private int event;
    private String version;
    private String senderId;
    private String accepteId;
    private String password;
    private String content;
    private String requestSourceSystem;
    private String nickName;
    private long occureTime;
    private String groupId;
    private String portrait;
    private String msgStatus;
    private String isRead;
    private int count;
    private int isStick;
    private int isShield;
    private long stickTime;
    private boolean groupChat;
    private boolean isReCall;
    private boolean isChecked;
    private String groupNum;
    @Unique
    private String contactsId;

    @Generated(hash = 98598491)
    public ContactsEntity(Long id, int type, int event, String version, String senderId, String accepteId, String password,
            String content, String requestSourceSystem, String nickName, long occureTime, String groupId, String portrait,
            String msgStatus, String isRead, int count, int isStick, int isShield, long stickTime, boolean groupChat,
            boolean isReCall, boolean isChecked, String groupNum, String contactsId) {
        this.id = id;
        this.type = type;
        this.event = event;
        this.version = version;
        this.senderId = senderId;
        this.accepteId = accepteId;
        this.password = password;
        this.content = content;
        this.requestSourceSystem = requestSourceSystem;
        this.nickName = nickName;
        this.occureTime = occureTime;
        this.groupId = groupId;
        this.portrait = portrait;
        this.msgStatus = msgStatus;
        this.isRead = isRead;
        this.count = count;
        this.isStick = isStick;
        this.isShield = isShield;
        this.stickTime = stickTime;
        this.groupChat = groupChat;
        this.isReCall = isReCall;
        this.isChecked = isChecked;
        this.groupNum = groupNum;
        this.contactsId = contactsId;
    }

    @Generated(hash = 900460154)
    public ContactsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
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

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public long getStickTime() {
        return stickTime;
    }

    public void setStickTime(long stickTime) {
        this.stickTime = stickTime;
    }

    public boolean isGroupChat() {
        return groupChat;
    }

    public void setGroupChat(boolean groupChat) {
        this.groupChat = groupChat;
    }

    public boolean isReCall() {
        return isReCall;
    }

    public void setReCall(boolean reCall) {
        isReCall = reCall;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public boolean getGroupChat() {
        return this.groupChat;
    }

    public boolean getIsReCall() {
        return this.isReCall;
    }

    public void setIsReCall(boolean isReCall) {
        this.isReCall = isReCall;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getContactsId() {
        return this.contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }
}
