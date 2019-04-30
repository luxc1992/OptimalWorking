package com.yst.onecity.bean.accountsafe;

/**
 * 实名认证信息回显 实体类
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/27.
 */

public class RealNameAuthenticationInfoBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"name":"卢旭昌","cardNo":"13043519921126341X"}
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
         * name : 卢旭昌
         * cardNo : 13043519921126341X
         */

        private String name;
        private String cardNo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }
    }
}
