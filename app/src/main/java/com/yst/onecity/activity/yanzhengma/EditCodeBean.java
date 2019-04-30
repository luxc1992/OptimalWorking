package com.yst.onecity.activity.yanzhengma;

/**
 * 输入验证码，返回orderId
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/31
 */
public class EditCodeBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"type":12345,"orderId":2345}
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
         * type : 12345
         * orderId : 2345
         */

        private int type;
        private long orderId;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }
    }
}
