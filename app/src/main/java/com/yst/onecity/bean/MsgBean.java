package com.yst.onecity.bean;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/27.
 */

public class MsgBean {

    /**
     * code : 1
     * msg : 验证码发送成功
     */

    private int code;
    private String msg;

    @Override
    public String toString() {
        return "MsgBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

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

}
