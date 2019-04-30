package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 群组聊天实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class GroupChatEntity {

    /**
     * code : 0
     * msg : 查询成功
     * content : [[{"id":7,"createTime":1513671764000,"updateTime":1513671768000,"userId":6,"groupNumberByMax":null,"groupNumberByCurrent":3,"groupType":0,"groupName":"a","descrbe":"a","topic":"a","imageUrl":"a","groupUserType":1,"groups":[{"id":7,"createTime":1513240380000,"updateTime":1513240380000,"userId":"xcc000","nickName":"xcc_user_icon","profession":null,"userIcon":"心","userType":"-1","requestSourceSystem":"王宇"},{"id":6,"createTime":1513235596000,"updateTime":1513235599000,"userId":"gsq","nickName":"gsq","profession":null,"userIcon":"gsq","userType":"-1","requestSourceSystem":"gsq"},{"id":5,"createTime":1513216552000,"updateTime":1513216552000,"userId":"xcc001","nickName":"xcc","profession":null,"userIcon":"xcc","userType":"-1","requestSourceSystem":"xcc"}],"applyUser":[]}],[{"id":7,"createTime":1513671764000,"updateTime":1513671768000,"userId":6,"groupNumberByMax":null,"groupNumberByCurrent":3,"groupType":0,"groupName":"a","descrbe":"a","topic":"a","imageUrl":"a","groupUserType":1,"groups":[{"id":7,"createTime":1513240380000,"updateTime":1513240380000,"userId":"xcc000","nickName":"xcc_user_icon","profession":null,"userIcon":"心","userType":"-1","requestSourceSystem":"王宇"},{"id":6,"createTime":1513235596000,"updateTime":1513235599000,"userId":"gsq","nickName":"gsq","profession":null,"userIcon":"gsq","userType":"-1","requestSourceSystem":"gsq"},{"id":5,"createTime":1513216552000,"updateTime":1513216552000,"userId":"xcc001","nickName":"xcc","profession":null,"userIcon":"xcc","userType":"-1","requestSourceSystem":"xcc"}],"applyUser":[]},{"id":7,"createTime":1513671764000,"updateTime":1513671768000,"userId":6,"groupNumberByMax":null,"groupNumberByCurrent":3,"groupType":0,"groupName":"a","descrbe":"a","topic":"a","imageUrl":"a","groupUserType":1,"groups":[{"id":7,"createTime":1513240380000,"updateTime":1513240380000,"userId":"xcc000","nickName":"xcc_user_icon","profession":null,"userIcon":"心","userType":"-1","requestSourceSystem":"王宇"},{"id":6,"createTime":1513235596000,"updateTime":1513235599000,"userId":"gsq","nickName":"gsq","profession":null,"userIcon":"gsq","userType":"-1","requestSourceSystem":"gsq"},{"id":5,"createTime":1513216552000,"updateTime":1513216552000,"userId":"xcc001","nickName":"xcc","profession":null,"userIcon":"xcc","userType":"-1","requestSourceSystem":"xcc"}],"applyUser":[]},{"id":16,"createTime":1513679541000,"updateTime":1513679541000,"userId":7,"groupNumberByMax":1,"groupNumberByCurrent":null,"groupType":0,"groupName":"1","descrbe":"1","topic":"1","imageUrl":"1","groupUserType":1,"groups":[{"id":6,"createTime":1513235596000,"updateTime":1513235599000,"userId":"gsq","nickName":"gsq","profession":null,"userIcon":"gsq","userType":"-1","requestSourceSystem":"gsq"}],"applyUser":[]},{"id":17,"createTime":1513731614000,"updateTime":1513731614000,"userId":7,"groupNumberByMax":1,"groupNumberByCurrent":null,"groupType":0,"groupName":"1","descrbe":"1","topic":"1","imageUrl":"1","groupUserType":1,"groups":[{"id":9,"createTime":1513665104000,"updateTime":1513665104000,"userId":"zhubiao","nickName":"zhubiao","profession":null,"userIcon":"123456","userType":"0","requestSourceSystem":"iOS"}],"applyUser":[]}]]
     */
    private int code;
    private String msg;
    private List<List<ContentBean>> content;

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

    public List<List<ContentBean>> getContent() {
        return content;
    }

    public void setContent(List<List<ContentBean>> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        /**
         * id : 7
         * createTime : 1513671764000
         * updateTime : 1513671768000
         * userId : 6
         * groupNumberByMax : null
         * groupNumberByCurrent : 3
         * groupType : 0
         * groupName : a
         * descrbe : a
         * topic : a
         * imageUrl : a
         * groupUserType : 1
         * prohibitUserSpeak":"xcc003,xcc002",
         * groups : [{"id":7,"createTime":1513240380000,"updateTime":1513240380000,"userId":"xcc000","nickName":"xcc_user_icon","profession":null,"userIcon":"心","userType":"-1","requestSourceSystem":"王宇"},{"id":6,"createTime":1513235596000,"updateTime":1513235599000,"userId":"gsq","nickName":"gsq","profession":null,"userIcon":"gsq","userType":"-1","requestSourceSystem":"gsq"},{"id":5,"createTime":1513216552000,"updateTime":1513216552000,"userId":"xcc001","nickName":"xcc","profession":null,"userIcon":"xcc","userType":"-1","requestSourceSystem":"xcc"}]
         * applyUser : []
         */
        private String id;
        private long createTime;
        private long updateTime;
        private int userId;
        private Object groupNumberByMax;
        private int groupNumberByCurrent;
        private int groupType;
        private String groupName;
        private String descrbe;
        private String topic;
        private Object imageUrl;
        private int groupUserType;
        private String prohibitUserSpeak;
        private List<GroupsBean> groups;
        private List<?> applyUser;
        private boolean isShowRedPoint;

        public boolean isShowRedPoint() {
            return isShowRedPoint;
        }

        public void setShowRedPoint(boolean showRedPoint) {
            isShowRedPoint = showRedPoint;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
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

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
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

        public Object getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(Object imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getGroupUserType() {
            return groupUserType;
        }

        public void setGroupUserType(int groupUserType) {
            this.groupUserType = groupUserType;
        }

        public List<GroupsBean> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupsBean> groups) {
            this.groups = groups;
        }

        public List<?> getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(List<?> applyUser) {
            this.applyUser = applyUser;
        }

        public String getProhibitUserSpeak() {
            return prohibitUserSpeak;
        }

        public void setProhibitUserSpeak(String prohibitUserSpeak) {
            this.prohibitUserSpeak = prohibitUserSpeak;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", userId=" + userId +
                    ", groupNumberByMax=" + groupNumberByMax +
                    ", groupNumberByCurrent=" + groupNumberByCurrent +
                    ", groupType=" + groupType +
                    ", groupName='" + groupName + '\'' +
                    ", descrbe='" + descrbe + '\'' +
                    ", topic='" + topic + '\'' +
                    ", imageUrl=" + imageUrl +
                    ", groupUserType=" + groupUserType +
                    ", prohibitUserSpeak='" + prohibitUserSpeak + '\'' +
                    ", groups=" + groups +
                    ", applyUser=" + applyUser +
                    '}';
        }

        public static class GroupsBean {
            /**
             * id : 7
             * createTime : 1513240380000
             * updateTime : 1513240380000
             * userId : xcc000
             * nickName : xcc_user_icon
             * profession : null
             * userIcon : 心
             * userType : -1
             * requestSourceSystem : 王宇
             */
            private int id;
            private long createTime;
            private long updateTime;
            private String userId;
            private String nickName;
            private Object profession;
            private String userIcon;
            private String userType;
            private String requestSourceSystem;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
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

            public Object getProfession() {
                return profession;
            }

            public void setProfession(Object profession) {
                this.profession = profession;
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

            @Override
            public String toString() {
                return "GroupsBean{" +
                        "id=" + id +
                        ", createTime=" + createTime +
                        ", updateTime=" + updateTime +
                        ", userId='" + userId + '\'' +
                        ", nickName='" + nickName + '\'' +
                        ", profession=" + profession +
                        ", userIcon='" + userIcon + '\'' +
                        ", userType='" + userType + '\'' +
                        ", requestSourceSystem='" + requestSourceSystem + '\'' +
                        '}';
            }
        }
    }
}
