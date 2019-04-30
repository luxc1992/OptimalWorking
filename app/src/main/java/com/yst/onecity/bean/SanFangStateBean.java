package com.yst.onecity.bean;

/**
 * 三方绑定状态实体类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/7
 */

public class SanFangStateBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : {"bindStatus":"0"}
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
         * bindStatus : 0
         */
        private String wx_nick_name;
        private String qq_nick_name;
        private String bindStatus;
        private String phone;

        public String getWx_nick_name() {
            return wx_nick_name;
        }

        public void setWx_nick_name(String wx_nick_name) {
            this.wx_nick_name = wx_nick_name;
        }

        public String getQq_nick_name() {
            return qq_nick_name;
        }

        public void setQq_nick_name(String qq_nick_name) {
            this.qq_nick_name = qq_nick_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }
    }
}
