package com.yst.im.imchatlibrary.bean;

import java.util.List;

/**
 * 搜索
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/13
 */

public class SearchEntity {

    /**
     * code : 0
     * msg : 查询成功
     * content : {"groupChat":[{"id":8,"createTime":1523502957000,"updateTime":null,"userId":25,"groupNumberByMax":500,"groupNumberByCurrent":null,"groupType":null,"groupName":"葫不好","descrbe":"回家睡不好","topic":null,"imageUrl":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg\"]","groupUserType":null,"prohibitUserSpeak":null,"requestSourceSystem":"im","groups":[],"applyUser":[]},{"id":6,"createTime":1523257327000,"updateTime":null,"userId":7,"groupNumberByMax":500,"groupNumberByCurrent":null,"groupType":null,"groupName":"还是不好","descrbe":"把表","topic":null,"imageUrl":"[\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411163442561758648.png\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg\",\"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180412/20180412173559877636712.png\"]","groupUserType":null,"prohibitUserSpeak":null,"requestSourceSystem":"im","groups":[],"applyUser":[]}],"userChat":[{"id":7,"createTime":null,"updateTime":1523179441000,"userId":"BD3779D0EA14472EAFED54493C4D4BC8","nickName":"不好鹏","userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180411/20180411140048050425910.jpeg","userType":"0","requestSourceSystem":"im","address":"北京","phone":"im001","sex":"男","userPassword":"123456","remark":null}]}
     */

    private int code;
    private String msg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private List<SearchResultEntity> groupChat;
        private List<SearchResultEntity> userChat;

        public List<SearchResultEntity> getGroupChat() {
            return groupChat;
        }

        public void setGroupChat(List<SearchResultEntity> groupChat) {
            this.groupChat = groupChat;
        }

        public List<SearchResultEntity> getUserChat() {
            return userChat;
        }

        public void setUserChat(List<SearchResultEntity> userChat) {
            this.userChat = userChat;
        }

        public static class SearchContentBean {
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
            private boolean isGroup;

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
        }
    }
}
