package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索结果
 *
 * @author Lierpeng
 * @date 2018/4/13
 * @version 1.0.0
 */

public class SearchResultEntity implements Serializable{

    /**
     * id : 8
     * createTime : 1523502957000
     * updateTime : null
     * userId : 25
     * groupNumberByMax : 500
     * groupNumberByCurrent : null
     * groupType : null
     * groupName : 葫不好
     * descrbe : 回家睡不好
     * topic : null
     * imageUrl : ["https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg"]
     * groupUserType : null
     * prohibitUserSpeak : null
     * requestSourceSystem : im
     * groups : []
     * applyUser : []
     * nickName : 不好鹏
     * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg
     * userType : 0
     * address : 北京
     * phone : im001
     * sex : 男
     * userPassword : 123456
     * remark : null
     */

    private String id;
    private String createTime;
    private String updateTime;
    private String userId;
    private String groupNumberByMax;
    private String groupNumberByCurrent;
    private String groupType;
    private String groupName;
    private String descrbe;
    private String topic;
    private String imageUrl;
    private String groupUserType;
    private String prohibitUserSpeak;
    private String requestSourceSystem;
    private String nickName;
    private String userIcon;
    private String userType;
    private String address;
    private String phone;
    private String sex;
    private String userPassword;
    private String remark;
    private String state;
    private boolean isGroup;
    private boolean isChecked = false;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupNumberByMax() {
        return groupNumberByMax;
    }

    public void setGroupNumberByMax(String groupNumberByMax) {
        this.groupNumberByMax = groupNumberByMax;
    }

    public String getGroupNumberByCurrent() {
        return groupNumberByCurrent;
    }

    public void setGroupNumberByCurrent(String groupNumberByCurrent) {
        this.groupNumberByCurrent = groupNumberByCurrent;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescrbe() {
        return descrbe;
    }

    public void setDescrbe(String descrbe) {
        this.descrbe = descrbe;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGroupUserType() {
        return groupUserType;
    }

    public void setGroupUserType(String groupUserType) {
        this.groupUserType = groupUserType;
    }

    public String getProhibitUserSpeak() {
        return prohibitUserSpeak;
    }

    public void setProhibitUserSpeak(String prohibitUserSpeak) {
        this.prohibitUserSpeak = prohibitUserSpeak;
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

    @Override
    public String toString() {
        return "SearchResultEntity{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", userId='" + userId + '\'' +
                ", groupNumberByMax='" + groupNumberByMax + '\'' +
                ", groupNumberByCurrent='" + groupNumberByCurrent + '\'' +
                ", groupType='" + groupType + '\'' +
                ", groupName='" + groupName + '\'' +
                ", descrbe='" + descrbe + '\'' +
                ", topic='" + topic + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", groupUserType='" + groupUserType + '\'' +
                ", prohibitUserSpeak='" + prohibitUserSpeak + '\'' +
                ", requestSourceSystem='" + requestSourceSystem + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userType='" + userType + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", remark='" + remark + '\'' +
                ", isGroup=" + isGroup +
                '}';
    }
}
