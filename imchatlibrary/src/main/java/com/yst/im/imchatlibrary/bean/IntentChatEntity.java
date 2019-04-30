package com.yst.im.imchatlibrary.bean;

import com.yst.im.imsdk.ChatType;

import java.io.Serializable;

/**
 * 聊天框挑转实体
 *
 * @author Lierpeng
 * @date 2018/5/8
 * @version 1.0.0
 */

public class IntentChatEntity implements Serializable {
    private ChatType chatType;
    private String acceptId;
    private String acceptName;
    private String groupNum;

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }
}
