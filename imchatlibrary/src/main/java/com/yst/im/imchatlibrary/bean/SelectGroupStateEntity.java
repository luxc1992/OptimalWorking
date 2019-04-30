package com.yst.im.imchatlibrary.bean;

/**
 * 群聊状态
 *
 * @author Lierpeng
 * @date 2018/4/18
 * @version 1.0.0
 */

public class SelectGroupStateEntity {

    /**
     * code : 0
     * msg : 查询状态成功
     * content : {"isStick":0,"isShield":1,"remark":"狗神","relation":1}
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
         * isStick : 0
         * isShield : 1
         * remark : 狗神
         * relation : 1
         */

        private int isStick;
        private int isShield;
        private String remark;
        private int relation;

        public int getIsStick() {
            return isStick;
        }

        public void setIsStick(int isStick) {
            this.isStick = isStick;
        }

        public int getIsShield() {
            return isShield;
        }

        public void setIsShield(int isShield) {
            this.isShield = isShield;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }
    }
}
