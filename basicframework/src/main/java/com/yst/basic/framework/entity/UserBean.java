package com.yst.basic.framework.entity;

/**
 * 用户信息
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/27
 */
public class UserBean {

    /**
     * code : 1
     * msg : 登录成功
     * id : 36
     * nickname : 猴子
     * password : c7f0bd8c6dd61d24702161bf4cf7a781
     * phone : 18101249503
     * uuid : 0b73685620ce4bc8a3f6a7bb9b0b976a
     * level : 1
     * groupId : 2
     */

    private int code;
    private String msg;
    private int id;
    private String nickname;
    private String password;
    private String phone;
    private String uuid;
    private int level;
    private String groupId;
    private String qqOppendId;
    private String qqNickName;
    private String wxOppendId;
    private String wxNickName;
    private String card;
    private int isCardRecord;
    private long imId;
    private String imPassword;
    private String imSign;
    private String role;
    private String chatGroup;
    private String name;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getQqOppendId() {
        return qqOppendId;
    }

    public void setQqOppendId(String qqOppendId) {
        this.qqOppendId = qqOppendId;
    }

    public String getQqNickName() {
        return qqNickName;
    }

    public void setQqNickName(String qqNickName) {
        this.qqNickName = qqNickName;
    }

    public String getWxOppendId() {
        return wxOppendId;
    }

    public void setWxOppendId(String wxOppendId) {
        this.wxOppendId = wxOppendId;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getIsCardRecord() {
        return isCardRecord;
    }

    public void setIsCardRecord(int isCardRecord) {
        this.isCardRecord = isCardRecord;
    }

    public long getImId() {
        return imId;
    }

    public void setImId(long imId) {
        this.imId = imId;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public String getImSign() {
        return imSign;
    }

    public void setImSign(String imSign) {
        this.imSign = imSign;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getChatGroup() {
        return chatGroup;
    }

    public void setChatGroup(String chatGroup) {
        this.chatGroup = chatGroup;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", uuid='" + uuid + '\'' +
                ", level=" + level +
                ", groupId=" + groupId +
                ", qqOppendId='" + qqOppendId + '\'' +
                ", qqNickName='" + qqNickName + '\'' +
                ", wxOppendId='" + wxOppendId + '\'' +
                ", wxNickName='" + wxNickName + '\'' +
                ", card='" + card + '\'' +
                ", isCardRecord=" + isCardRecord +
                ", imId=" + imId +
                ", imPassword='" + imPassword + '\'' +
                ", imSign='" + imSign + '\'' +
                ", role='" + role + '\'' +
                ", chatGroup='" + chatGroup + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
