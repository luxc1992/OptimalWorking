package com.yst.basic.framework.entity;

import android.text.TextUtils;

import com.yst.basic.framework.utils.security.PreferenceUtil;

/**
 * 用户信息
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/28
 */

public class UserManager {
    public static UserManager manager;
    private String flag = "app";
    private String mIsLogin = flag + "isLogin";
    private String mUuid = flag + "uuid";
    private String mId = flag + "id";
    private String mPhoneNum = flag + "phone";
    private String mNickname = flag + "nickname";
    private String mLevel = flag + "level";
    private String mGroupId = flag + "groupId";
    private String mCard = flag + "card";
    private String mIsCardRecord = flag + "isCardRecord";
    private String mImId = flag + "imId";
    private String mImPassword = flag + "imPassword";
    private String mImSign = flag + "imSign";
    private String mRole = flag + "role";
    private String mQqNickName = flag + "qqNickName";
    private String mWxNickName = flag + "wxNickName";
    private String mQqOppendId = flag + "qqOppendId";
    private String mWxOppendId = flag + "wxOppendId";
    private String mChatGroup = flag + "chatGroup";
    private String mName = flag + "name";
    private String mToken = flag + "token";
    private String mLoginPwd = flag + "pwd";
    private String mLatitude = "latitude";
    private String mLongitude = "longitude";

    private String firstOpen = "first_open";

    public static UserManager getInstance() {
        if (manager == null) {
            manager = new UserManager();
        }
        return manager;
    }

    /**
     * 登录后保存用户信息
     *
     * @param userBean
     */
    public void saveUserInfo(UserBean userBean) {
        if (userBean == null) {
            setLoginState(false);
            setId(0);
            setPhoneNum("");
            setUuid("");
            setLevel(-10);
            setNickname("");
            setGroupId("");
            setCard("");
            setIsCardRecord(-1);
            setImId(-1);
            setImPassword("");
            setImSign("");
            setRole("");
            setQqNickName("");
            setWxNickName("");
            setWxOppendId("");
            setQqOppendId("");
            setWxOppendId(String.valueOf(-1));
            setQqOppendId(String.valueOf(-1));
            setChatGroup("");
            setName("");
            setToken("");
            return;
        }
        if (!TextUtils.isEmpty(userBean.getUuid())) {
            setUuid(userBean.getUuid());
        }
        if (!TextUtils.isEmpty(userBean.getPhone())) {
            setPhoneNum(userBean.getPhone());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getId()))) {
            setId(userBean.getId());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getGroupId()))) {
            setGroupId(userBean.getGroupId());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getLevel()))) {
            setLevel(userBean.getLevel());
        }
        if (!TextUtils.isEmpty(userBean.getNickname())) {
            setNickname(userBean.getNickname());
        }
        if (!TextUtils.isEmpty(userBean.getCard())) {
            setCard(userBean.getCard());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getIsCardRecord()))) {
            setIsCardRecord(userBean.getIsCardRecord());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getImId()))) {
            setImId(userBean.getImId());
        }
        if (!TextUtils.isEmpty(userBean.getImPassword())) {
            setImPassword(userBean.getImPassword());
        }
        if (!TextUtils.isEmpty(userBean.getImSign())) {
            setImSign(userBean.getImSign());
        }
        if (!TextUtils.isEmpty(userBean.getRole())) {
            setRole(userBean.getRole());
        }
        if (!TextUtils.isEmpty(userBean.getQqNickName())) {
            setQqNickName(userBean.getQqNickName());
        }
        if (!TextUtils.isEmpty(userBean.getWxNickName())) {
            setQqNickName(userBean.getWxNickName());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getQqOppendId()))) {
            setQqOppendId(userBean.getQqOppendId());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getWxOppendId()))) {
            setWxOppendId(userBean.getWxOppendId());
        }
        if (!TextUtils.isEmpty(String.valueOf(userBean.getChatGroup()))) {
            setChatGroup(userBean.getChatGroup());
        }
        if (!TextUtils.isEmpty(userBean.getName())) {
            setName(userBean.getName());
        }
        if (!TextUtils.isEmpty(userBean.getToken())) {
            setToken(userBean.getToken());
        }
    }

    public String getmLoginPwd() {
        return PreferenceUtil.getString(mLoginPwd, "");
    }

    public void setmLoginPwd(String loginPwd) {
        PreferenceUtil.put(mLoginPwd, loginPwd);
    }

    public boolean getLoginState() {
        return PreferenceUtil.getBoolean(mIsLogin, false);
    }

    public void setLoginState(boolean isLogin) {
        PreferenceUtil.put(mIsLogin, isLogin);
    }

    public String getUuid() {
        return PreferenceUtil.getString(mUuid, "");
    }

    public void setUuid(String uuid) {
        PreferenceUtil.put(mUuid, uuid);
    }

    public int getId() {
        return PreferenceUtil.getInt(mId, 0);
    }

    public void setId(int id) {
        PreferenceUtil.put(mId, id);
    }

    public String getPhoneNum() {
        return PreferenceUtil.getString(mPhoneNum, "");
    }

    public void setPhoneNum(String phoneNum) {
        PreferenceUtil.put(mPhoneNum, phoneNum);
    }

    public String getNickname() {
        return PreferenceUtil.getString(mNickname, "");
    }

    public void setNickname(String nickname) {
        PreferenceUtil.put(mNickname, mNickname);
    }

    public int getLevel() {
        return PreferenceUtil.getInt(mLevel, -10);
    }

    public void setLevel(int level) {
        PreferenceUtil.put(mLevel, level);
    }

    public String getGroupId() {
        return PreferenceUtil.getString(mGroupId, "");
    }

    public void setGroupId(String groupId) {
        PreferenceUtil.put(mGroupId, groupId);
    }

    public String getCard() {
        return PreferenceUtil.getString(mCard, "");
    }

    public void setCard(String card) {
        PreferenceUtil.put(mCard, card);
    }

    public int getIsCardRecord() {
        return PreferenceUtil.getInt(mCard, -1);
    }

    public void setIsCardRecord(int isCardRecord) {
        PreferenceUtil.put(mIsCardRecord, isCardRecord);
    }

    public long getImId() {
        return PreferenceUtil.getLong(mImId, -1);
    }

    public void setImId(long imId) {
        PreferenceUtil.put(mImId, imId);
    }

    public String getImPassword() {
        return PreferenceUtil.getString(mImPassword, "");
    }

    public void setImPassword(String imPassword) {
        PreferenceUtil.put(mImPassword, imPassword);
    }

    public String getImSign() {
        return PreferenceUtil.getString(mImSign, "");
    }

    public void setImSign(String imSign) {
        PreferenceUtil.put(mImSign, imSign);
    }

    public String getRole() {
        return PreferenceUtil.getString(mRole, "");
    }

    public void setRole(String role) {
        PreferenceUtil.put(mRole, role);
    }

    public String getQqNickName() {
        return PreferenceUtil.getString(mQqNickName, "");
    }

    public void setQqNickName(String qqNickName) {
        PreferenceUtil.put(mQqNickName, qqNickName);
    }

    public String getWxNickName() {
        return PreferenceUtil.getString(mWxNickName, "");
    }

    public void setWxNickName(String wxNickName) {
        PreferenceUtil.put(mWxNickName, wxNickName);
    }

    public String getQqOppendId() {
        return PreferenceUtil.getString(mQqOppendId, "");
    }

    public void setQqOppendId(String qqOppendId) {
        PreferenceUtil.put(mQqOppendId, qqOppendId);
    }

    public String getWxOppendId() {
        return PreferenceUtil.getString(mWxOppendId, "");
    }

    public void setWxOppendId(String wxOppendId) {
        PreferenceUtil.put(mWxOppendId, wxOppendId);
    }

    public String getChatGroup() {
        return PreferenceUtil.getString(mChatGroup, "");
    }

    public void setChatGroup(String chatGroup) {
        PreferenceUtil.put(mChatGroup, chatGroup);
    }

    public void setName(String name) {
        PreferenceUtil.put(mName, name);
    }

    public String getName() {
        return PreferenceUtil.getString(mName, "");
    }

    public String getToken() {
        return PreferenceUtil.getString(mToken, "");
    }

    public void setToken(String token) {
        PreferenceUtil.put(mToken,token);
    }

    public void setLatitude(double latitude){
        PreferenceUtil.put(mLatitude, latitude);
    }
    public void setLongitude(double longitude){
        PreferenceUtil.put(mLongitude, longitude);
    }
    public double getLatitude() {
        return PreferenceUtil.getFloat(mLatitude, 0);
    }

    public double getLongitude() {
        return PreferenceUtil.getFloat(mLongitude, 0);
    }

    public boolean getOpenState() {
        return PreferenceUtil.getBoolean(firstOpen, false);
    }

    public void setOpenState() {
        PreferenceUtil.put(firstOpen, true);
    }

    public void quitLogin() {
        setLoginState(false);
        setId(0);
        setPhoneNum("");
        setUuid("");
        setLevel(-10);
        setNickname("");
        setGroupId("");
        setCard("");
        setIsCardRecord(-1);
        setImId(-1);
        setImPassword("");
        setImSign("");
        setRole("");
        setQqNickName("");
        setWxNickName("");
        setWxOppendId("");
        setQqOppendId("");
        setWxOppendId(String.valueOf(-1));
        setQqOppendId(String.valueOf(-1));
        setChatGroup("");
        setName("");
        setToken("");
        setmLoginPwd("");
    }
}
