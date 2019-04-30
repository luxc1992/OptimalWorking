package com.yst.im.imchatlibrary.bean;

import com.yst.im.imchatlibrary.enumclass.GroupMemberEnum;
import com.yst.im.imsdk.ChatType;

import java.io.Serializable;

/**
 * 群成员操作
 *
 * @author Lierpeng
 * @date 2018/4/20
 * @version 1.0.0
 */

public class IntentGroupMemberEntity implements Serializable {
    private String groupId;
    private GroupMemberEnum groupMemberEnum;
    private String titleName;
    private String memberId;
    private ChatType chatType;

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupMemberEnum getGroupMemberEnum() {
        return groupMemberEnum;
    }

    public void setGroupMemberEnum(GroupMemberEnum groupMemberEnum) {
        this.groupMemberEnum = groupMemberEnum;
    }
}
