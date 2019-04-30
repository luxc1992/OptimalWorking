package com.yst.onecity.bean;

/**
 * 添加银行卡
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/13
 */

public class BankBean {

    /**
     * code : 1
     * msg : 绑定成功
     * content : {"isSetPassword":"0"}
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
         * isSetPassword : 0
         */

        private String isSetPassword;

        public String getIsSetPassword() {
            return isSetPassword;
        }

        public void setIsSetPassword(String isSetPassword) {
            this.isSetPassword = isSetPassword;
        }
    }
}
