package com.yst.im.imchatlibrary.bean;

import java.util.List;

/**
 * 创建群聊
 *
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/4/24.
 */

public class CreateGroupChatEntity {


    /**
     * code : 0
     * msg : 发起群聊成功
     * id : 75
     * createTime : null
     * updateTime : null
     * userId : 7
     * groupNumberByMax : null
     * groupNumberByCurrent : 3
     * groupType : null
     * groupName : 发给、决心12331、Sas
     * descrbe : null
     * updateDescriptionTime : null
     * topic : null
     * imageUrl : ["https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180423/20180423151217338479266.png","https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180417/20180417105155211045029.png"]
     * groupUserType : null
     * prohibitUserSpeak : null
     * requestSourceSystem : im
     * groups : []
     * applyUser : []
     */

    private int code;
    private String msg;
    private int id;
    private Object createTime;
    private Object updateTime;
    private int userId;
    private Object groupNumberByMax;
    private int groupNumberByCurrent;
    private Object groupType;
    private String groupName;
    private Object descrbe;
    private Object updateDescriptionTime;
    private Object topic;
    private String imageUrl;
    private Object groupUserType;
    private Object prohibitUserSpeak;
    private String requestSourceSystem;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getGroupNumberByMax() {
        return groupNumberByMax;
    }

    public void setGroupNumberByMax(Object groupNumberByMax) {
        this.groupNumberByMax = groupNumberByMax;
    }

    public int getGroupNumberByCurrent() {
        return groupNumberByCurrent;
    }

    public void setGroupNumberByCurrent(int groupNumberByCurrent) {
        this.groupNumberByCurrent = groupNumberByCurrent;
    }

    public Object getGroupType() {
        return groupType;
    }

    public void setGroupType(Object groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Object getDescrbe() {
        return descrbe;
    }

    public void setDescrbe(Object descrbe) {
        this.descrbe = descrbe;
    }

    public Object getUpdateDescriptionTime() {
        return updateDescriptionTime;
    }

    public void setUpdateDescriptionTime(Object updateDescriptionTime) {
        this.updateDescriptionTime = updateDescriptionTime;
    }

    public Object getTopic() {
        return topic;
    }

    public void setTopic(Object topic) {
        this.topic = topic;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getGroupUserType() {
        return groupUserType;
    }

    public void setGroupUserType(Object groupUserType) {
        this.groupUserType = groupUserType;
    }

    public Object getProhibitUserSpeak() {
        return prohibitUserSpeak;
    }

    public void setProhibitUserSpeak(Object prohibitUserSpeak) {
        this.prohibitUserSpeak = prohibitUserSpeak;
    }

    public String getRequestSourceSystem() {
        return requestSourceSystem;
    }

    public void setRequestSourceSystem(String requestSourceSystem) {
        this.requestSourceSystem = requestSourceSystem;
    }

}
