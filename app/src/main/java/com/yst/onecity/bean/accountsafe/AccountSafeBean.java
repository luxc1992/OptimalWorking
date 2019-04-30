package com.yst.onecity.bean.accountsafe;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/3/2.
 */

public class AccountSafeBean {

    /**
     * code : 1
     * msg : 获取第三方用户昵称成功
     * content : {"passwordStatus":1,"phone":"18101249503","qqName":null,"wxName":null,"realNameStatus":1,"transactionPasswordStatus":1}
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
         * passwordStatus : 1
         * phone : 18101249503
         * qqName : null
         * wxName : null
         * realNameStatus : 1
         * transactionPasswordStatus : 1
         */

        private int passwordStatus;
        private String phone;
        private String qqName;
        private String wxName;
        private int realNameStatus;
        private int transactionPasswordStatus;

        public int getPasswordStatus() {
            return passwordStatus;
        }

        public void setPasswordStatus(int passwordStatus) {
            this.passwordStatus = passwordStatus;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQqName() {
            return qqName;
        }

        public void setQqName(String qqName) {
            this.qqName = qqName;
        }

        public String getWxName() {
            return wxName;
        }

        public void setWxName(String wxName) {
            this.wxName = wxName;
        }

        public int getRealNameStatus() {
            return realNameStatus;
        }

        public void setRealNameStatus(int realNameStatus) {
            this.realNameStatus = realNameStatus;
        }

        public int getTransactionPasswordStatus() {
            return transactionPasswordStatus;
        }

        public void setTransactionPasswordStatus(int transactionPasswordStatus) {
            this.transactionPasswordStatus = transactionPasswordStatus;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "passwordStatus=" + passwordStatus +
                    ", phone='" + phone + '\'' +
                    ", qqName='" + qqName + '\'' +
                    ", wxName='" + wxName + '\'' +
                    ", realNameStatus=" + realNameStatus +
                    ", transactionPasswordStatus=" + transactionPasswordStatus +
                    '}';
        }
    }
}
