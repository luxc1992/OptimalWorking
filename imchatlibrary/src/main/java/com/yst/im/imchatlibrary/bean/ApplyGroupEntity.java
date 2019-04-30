package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 查询群组成功
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/04/13.
 */
public class ApplyGroupEntity {


    /**
     * code : 0
     * msg : 查询成功
     * content : [{"userGroupApplyId":1,"describ":null,"groupChatId":13,"groupName":"456789","nickName":"huangfan","imageUrl":null,"userChatId":3,"state":null},{"userGroupApplyId":2,"describ":null,"groupChatId":13,"groupName":"456789","nickName":"不好鹏","imageUrl":null,"userChatId":7,"state":null}]
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
        /**
         * userGroupApplyId : 1
         * describ : null
         * groupChatId : 13
         * groupName : 456789
         * nickName : huangfan
         * imageUrl : null
         * userChatId : 3
         * state : 0 已同意 1 已拒绝 2 待审批
         */

        private String userGroupApplyId;
        private String describ;
        private String groupChatId;
        private String groupName;
        private String nickName;
        private String imageUrl;
        private String userChatId;
        private String state;
        private String sex;
        private String address;
        private String userId;
        private String userIcon;
        private String currentMemberNumber;

        public String getCurrentMemberNumber() {
            return currentMemberNumber;
        }

        public void setCurrentMemberNumber(String currentMemberNumber) {
            this.currentMemberNumber = currentMemberNumber;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

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

        public String getUserGroupApplyId() {
            return userGroupApplyId;
        }

        public void setUserGroupApplyId(String userGroupApplyId) {
            this.userGroupApplyId = userGroupApplyId;
        }

        public String getDescrib() {
            return describ;
        }

        public void setDescrib(String describ) {
            this.describ = describ;
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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
