package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;

/**
 * 好友信息中转类
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/10.
 */

public class FriendInfoEntity implements Serializable{

    private String userGroupApplyId;
    private String groupChatId;
    private String groupName;
    private String imageUrl;
    private String userChatId;
    private String currentMemberNumber;
    private String describ;
    private String friendId;
    private String friendName;
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
    private String state;

    public String getUserGroupApplyId() {
        return userGroupApplyId;
    }

    public void setUserGroupApplyId(String userGroupApplyId) {
        this.userGroupApplyId = userGroupApplyId;
    }

    public String getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(String groupChatId) {
        this.groupChatId = groupChatId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(String userChatId) {
        this.userChatId = userChatId;
    }

    public String getCurrentMemberNumber() {
        return currentMemberNumber;
    }

    public void setCurrentMemberNumber(String currentMemberNumber) {
        this.currentMemberNumber = currentMemberNumber;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FriendInfoEntity{" +
                "userGroupApplyId='" + userGroupApplyId + '\'' +
                ", groupChatId='" + groupChatId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userChatId='" + userChatId + '\'' +
                ", currentMemberNumber='" + currentMemberNumber + '\'' +
                ", describ='" + describ + '\'' +
                ", friendId='" + friendId + '\'' +
                ", friendName='" + friendName + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userType='" + userType + '\'' +
                ", requestSourceSystem='" + requestSourceSystem + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", remark='" + remark + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
