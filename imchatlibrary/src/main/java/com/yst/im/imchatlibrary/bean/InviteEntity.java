package com.yst.im.imchatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 被邀请者实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class InviteEntity {
    /**
     * code : 0
     * msg : 获取所有用户成功
     * content : [{"id":5,"createTime":1513216552000,"updateTime":1513216552000,"userId":"xcc001","nickName":"xcc","userIcon":"xcc","userType":"-1","requestSourceSystem":"xcc","address":null,"phone":null,"companyName":null,"job":null,"industry":null},{"id":6,"createTime":1513235596000,"updateTime":1513235599000,"userId":"gsq","nickName":"gsq","userIcon":"gsq","userType":"-1","requestSourceSystem":"gsq","address":null,"phone":null,"companyName":null,"job":null,"industry":null},{"id":7,"createTime":1513240380000,"updateTime":1513240380000,"userId":"xcc000","nickName":"xcc_user_icon","userIcon":"心","userType":"-1","requestSourceSystem":"王宇","address":null,"phone":null,"companyName":null,"job":null,"industry":null},{"id":8,"createTime":1513586731000,"updateTime":1513586731000,"userId":"xcc009","nickName":"xcc_user_icon","userIcon":"android.app.SharedPreferencesImpl@ebb3ee","userType":"0","requestSourceSystem":"王宇","address":null,"phone":null,"companyName":null,"job":null,"industry":null},{"id":9,"createTime":1513665104000,"updateTime":1513665104000,"userId":"zhubiao","nickName":"zhubiao","userIcon":"123456","userType":"0","requestSourceSystem":"iOS","address":null,"phone":null,"companyName":null,"job":null,"industry":null},{"id":10,"createTime":1513765218000,"updateTime":1513765218000,"userId":"xhcw","nickName":"1","userIcon":"1","userType":"0","requestSourceSystem":"1","address":"1","phone":"1","companyName":"1","job":"1","industry":"1"}]
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

    public static class ContentBean implements Serializable,Parcelable {
        /**
         * id : 5
         * createTime : 1513216552000
         * updateTime : 1513216552000
         * userId : xcc001
         * nickName : xcc
         * userIcon : xcc
         * userType : -1
         * requestSourceSystem : xcc
         * address : 34,35
         * phone : null
         * companyName : null
         * job : null
         * industry : null
         */
        private int id;
        private long createTime;
        private long updateTime;
        private String userId;
        private String nickName;
        private String userIcon;
        private String userType;
        private String requestSourceSystem;
        private String address;
        private String phone;
        private String companyName;
        private String job;
        private String industry;
        private boolean isCheck;

        protected ContentBean(Parcel in) {
            id = in.readInt();
            createTime = in.readLong();
            updateTime = in.readLong();
            userId = in.readString();
            nickName = in.readString();
            userIcon = in.readString();
            userType = in.readString();
            requestSourceSystem = in.readString();
            address = in.readString();
            phone = in.readString();
            companyName = in.readString();
            job = in.readString();
            industry = in.readString();
            isCheck = in.readByte() != 0;
        }

        public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel in) {
                return new ContentBean(in);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

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

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id=" + id +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", userId='" + userId + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", userIcon='" + userIcon + '\'' +
                    ", userType='" + userType + '\'' +
                    ", requestSourceSystem='" + requestSourceSystem + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", job='" + job + '\'' +
                    ", industry='" + industry + '\'' +
                    ", isCheck=" + isCheck +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeLong(createTime);
            parcel.writeLong(updateTime);
            parcel.writeString(userId);
            parcel.writeString(nickName);
            parcel.writeString(userIcon);
            parcel.writeString(userType);
            parcel.writeString(requestSourceSystem);
            parcel.writeString(address);
            parcel.writeString(phone);
            parcel.writeString(companyName);
            parcel.writeString(job);
            parcel.writeString(industry);
            parcel.writeByte((byte) (isCheck ? 1 : 0));
        }
    }
}
