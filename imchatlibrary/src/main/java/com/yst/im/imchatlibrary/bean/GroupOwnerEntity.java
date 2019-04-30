package com.yst.im.imchatlibrary.bean;

/**
 *
 * 群公告
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/23
 */

public class GroupOwnerEntity {

    /**
     * code : 0
     * msg : 查询群主信息及群公告成功
     * content : {"describ":null,"nickName":"萧姑娘","updateTime":null,"userIcon":"https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180413/20180413102937033124470.jpeg","userId":"5E69DF51A79F442CAED48BFF411FDABA"}
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
        /**
         * describ : null
         * nickName : 萧姑娘
         * updateTime : null
         * userIcon : https://yst-im-server.oss-cn-beijing.aliyuncs.com/datas/image/20180413/20180413102937033124470.jpeg
         * userId : 5E69DF51A79F442CAED48BFF411FDABA
         */

        private String describ;
        private String nickName;
        private long updateTime;
        private String userIcon;
        private String userId;

        public String getDescrib() {
            return describ;
        }

        public void setDescrib(String describ) {
            this.describ = describ;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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
    }
}
