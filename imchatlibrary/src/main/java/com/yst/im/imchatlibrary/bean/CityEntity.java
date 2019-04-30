package com.yst.im.imchatlibrary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 地区查询
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/29.
 */

public class CityEntity {

    /**
     * code : 0
     * msg : 地区查询成功
     * content : [{"id":1,"createTime":1383536241000,"updateTime":1383536241000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":20,"createTime":1383536241000,"updateTime":1383536241000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":39,"createTime":1383536242000,"updateTime":1383536242000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":223,"createTime":1383536244000,"updateTime":1383536244000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":354,"createTime":1383536246000,"updateTime":1383536246000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":468,"createTime":1383536248000,"updateTime":1383536248000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":583,"createTime":1383536251000,"updateTime":1383536251000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":653,"createTime":1383536252000,"updateTime":1383536252000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":795,"createTime":1383536255000,"updateTime":1383536255000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":815,"createTime":1383536256000,"updateTime":1383536256000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":935,"createTime":1383536258000,"updateTime":1383536258000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1037,"createTime":1383536260000,"updateTime":1383536260000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1160,"createTime":1383536262000,"updateTime":1383536262000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1255,"createTime":1383536264000,"updateTime":1383536264000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1366,"createTime":1383536267000,"updateTime":1383536267000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1524,"createTime":1383536270000,"updateTime":1383536270000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1701,"createTime":1383536274000,"updateTime":1383536274000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1817,"createTime":1383536275000,"updateTime":1383536275000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":1954,"createTime":1383536277000,"updateTime":1383536277000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2099,"createTime":1383536280000,"updateTime":1383536280000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2223,"createTime":1383536282000,"updateTime":1383536282000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2249,"createTime":1383536283000,"updateTime":1383536283000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2290,"createTime":1383536284000,"updateTime":1383536284000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2493,"createTime":1383536287000,"updateTime":1383536287000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2591,"createTime":1383536289000,"updateTime":1383536289000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2737,"createTime":1383536292000,"updateTime":1383536292000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2818,"createTime":1383536293000,"updateTime":1383536293000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":2936,"createTime":1383536296000,"updateTime":1383536296000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":3038,"createTime":1383536297000,"updateTime":1383536297000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":3090,"createTime":1383536297000,"updateTime":1383536297000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":3117,"createTime":1383536298000,"updateTime":1383536298000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":3230,"createTime":1383536300000,"updateTime":1383536300000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":3254,"createTime":1383536301000,"updateTime":1383536301000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null},{"id":3273,"createTime":1383536301000,"updateTime":1383536301000,"createUser":"","createdIp":"","areaId":0,"areaName":null,"parentId":0,"lockVersion":0,"letter":null}]
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
         * id : 1
         * createTime : 1383536241000
         * updateTime : 1383536241000
         * createUser :
         * createdIp :
         * areaId : 0
         * areaName : null
         * parentId : 0
         * lockVersion : 0
         * letter : null
         */

        private int id;
        private long createTime;
        private long updateTime;
        private String createUser;
        private String createdIp;
        private int areaId;
        private String areaName;
        private int parentId;
        private int lockVersion;
        private String letter;
        private boolean isSecond = false;

        public boolean isSecond() {
            return isSecond;
        }

        public void setSecond(boolean second) {
            isSecond = second;
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

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreatedIp() {
            return createdIp;
        }

        public void setCreatedIp(String createdIp) {
            this.createdIp = createdIp;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getLockVersion() {
            return lockVersion;
        }

        public void setLockVersion(int lockVersion) {
            this.lockVersion = lockVersion;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }
    }
}
