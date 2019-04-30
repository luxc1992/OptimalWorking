package com.yst.im.imchatlibrary.bean;

/**
 * 登录实体类
 *
 * @author Lierpeng
 * @date 2018/03/27.
 * @version 1.0.0
 */
public class BaseEntity {
    /**
     * code : 1
     * msg : 查询失败！
     */
    private int code;
    private String msg;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "LoginEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
