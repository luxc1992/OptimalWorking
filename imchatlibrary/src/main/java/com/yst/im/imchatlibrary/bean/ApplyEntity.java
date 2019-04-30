package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 查询申请列表
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/04/13.
 */

public class ApplyEntity {


    /**
     * code : 0
     * msg : 查询申请列表成功！
     * content : [{"id":null,"createTime":null,"updateTime":null,"userNameId":30,"friendNameId":7,"state":null,"describ":"123","userChat":{"id":7,"createTime":null,"updateTime":null,"userId":"BD3779D0EA14472EAFED54493C4D4BC8","nickName":"不好鹏","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","userType":"0","requestSourceSystem":null,"address":null,"phone":"im001","sex":null,"userPassword":"123456","remark":null}},{"id":null,"createTime":null,"updateTime":null,"userNameId":30,"friendNameId":6,"state":null,"describ":"123","userChat":{"id":6,"createTime":null,"updateTime":null,"userId":"F7ACC4E54FDA4D2A9B6836E02F83ABEF","nickName":"1111","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","userType":"0","requestSourceSystem":null,"address":null,"phone":"1111111111","sex":null,"userPassword":"123456","remark":null}}]
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

    public static class ContentBean implements Serializable {

        private String describ;
        private String friendId;
        private String friendName;
        private String state;
        private String userIcon;
        private String userId;
        private String sex;
        private String address;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }
}
