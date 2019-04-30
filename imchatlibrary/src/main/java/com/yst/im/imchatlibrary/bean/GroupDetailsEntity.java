package com.yst.im.imchatlibrary.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * 群聊详情实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class GroupDetailsEntity implements Serializable {


    /**
     * applyUser : []
     * createTime : 1525312157000
     * groupName : 我是神人、月神
     * groupNumberByCurrent : 2
     * groups : [{"address":"辽宁 葫芦岛","createTime":1525312157000,"id":21,"nickName":"月神","phone":"13439223424","requestSourceSystem":"im","updateTime":1525254449000,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180502/20180502174719288807263.jpeg","userId":"C8EA702870AA473C8285EA70CFD9FB27","userType":"0"},{"address":"河北 廊坊","createTime":1525312157000,"id":25,"nickName":"我是神人","phone":"15910422500","requestSourceSystem":"im","updateTime":1525311816000,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180503/20180503094321006728725.jpeg","userId":"80717F143DC849A3BCF5D22A8DC70380","userType":"0"}]
     * id : 38
     * imageUrl : ["https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180503/20180503094321006728725.jpeg","https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180502/20180502174719288807263.jpeg"]
     * requestSourceSystem : im
     * updateTime : 1525312157000
     * userId : 25
     */

    private long createTime;
    private String groupName;
    private int groupNumberByCurrent;
    private int id;
    private String imageUrl;
    private String requestSourceSystem;
    private long updateTime;
    private int userId;
    private List<?> applyUser;
    private List<GroupsBean> groups;
    private String descrbe;

    public String getDescrbe() {
        return descrbe;
    }

    public void setDescrbe(String descrbe) {
        this.descrbe = descrbe;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupNumberByCurrent() {
        return groupNumberByCurrent;
    }

    public void setGroupNumberByCurrent(int groupNumberByCurrent) {
        this.groupNumberByCurrent = groupNumberByCurrent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRequestSourceSystem() {
        return requestSourceSystem;
    }

    public void setRequestSourceSystem(String requestSourceSystem) {
        this.requestSourceSystem = requestSourceSystem;
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

    public List<?> getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(List<?> applyUser) {
        this.applyUser = applyUser;
    }

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public static class GroupsBean implements Serializable, Comparable<GroupsBean> {

        /**
         * address : 辽宁 葫芦岛
         * createTime : 1525312157000
         * id : 21
         * nickName : 月神
         * phone : 13439223424
         * requestSourceSystem : im
         * updateTime : 1525254449000
         * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180502/20180502174719288807263.jpeg
         * userId : C8EA702870AA473C8285EA70CFD9FB27
         * userType : 0
         */

        private String address;
        private long createTime;
        private int id;
        private String nickName;
        private String phone;
        private String requestSourceSystem;
        private long updateTime;
        private String userIcon;
        private String userId;
        private String remark;
        private String userType;
        private boolean isChecked;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRequestSourceSystem() {
            return requestSourceSystem;
        }

        public void setRequestSourceSystem(String requestSourceSystem) {
            this.requestSourceSystem = requestSourceSystem;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
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

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        @Override
        public int compareTo(@NonNull GroupsBean o) {
            return (int) (this.getCreateTime() - o.getCreateTime());
        }
    }

}
