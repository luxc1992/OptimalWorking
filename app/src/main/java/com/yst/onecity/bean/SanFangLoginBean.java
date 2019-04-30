package com.yst.onecity.bean;

/**
 * 三方登录和手机绑定实体类
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/7
 */

public class SanFangLoginBean {

    /**
     * code : 1
     * msg : 登录成功
     * id : 59
     * password : bbe5d9a7ef2e7fdbfe5530e299fb6a38
     * phone : 18701574341
     * uuid : 49ff4a40e70f4a7ca9ad7b28586df3d5
     * wxNickName : 将心比心便是佛心
     * wxOppendId : oprSkwYuMZmUOvA9QimaGxgHe0iU
     * token : df05b2c94e8f4a05bc71fc7c91c6e9a0
     */

    private int code;
    private String msg;
    private int id;
    private String password;
    private String phone;
    private String uuid;
    private String wxNickName;
    private String wxOppendId;
    private String qqOppendId;
    private String qqOppendName;
    private String token;

    public String getQqOppendId() {
        return qqOppendId;
    }

    public void setQqOppendId(String qqOppendId) {
        this.qqOppendId = qqOppendId;
    }

    public String getQqOppendName() {
        return qqOppendName;
    }

    public void setQqOppendName(String qqOppendName) {
        this.qqOppendName = qqOppendName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getWxOppendId() {
        return wxOppendId;
    }

    public void setWxOppendId(String wxOppendId) {
        this.wxOppendId = wxOppendId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
