package com.yst.im.imchatlibrary.utils;

import android.text.TextUtils;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.UsersEntity;

/**
 * 用户数据保存
 *
 * @author Lierpeng
 * @date 2018/3/26.
 * @version 1.0.0
 */
public class UsersMag {
    private static UsersMag manager;
    private String mId = "id";
    private String mUserId = "userId";
    private String mUserIcon = "userIcon";
    private String mNickName = "nickName";
    private String mToken = "token";
    private String mGroupId = "groupId";
    private String mIsManager = "isManager";
    private String mNewApplyGroupId = "newApplyGroupId";
    private String mNewApplyIsShow = "newApplyIsShow";
    private String mNewApply = "newApply";
    private String mUserShut = "userShut";
    private String mAcceptNickName = "acceptNickName";
    private String mCreateNum = "createNum";
    private String mMineNum = "mineNum";
    private String mAdminNum = "adminNum";
    private String mPhone = "phone";
    private String mPassword = "password";
    private String mAddress = "address";
    private String mSex = "sex";
    private String mIsLogin = "isLogin";
    private String mIsRegisterUser="isRegisterUser";


    public static UsersMag getInstance() {
        if (manager == null) {
            manager = new UsersMag();
        }
        return manager;
    }

    public void setId(String id) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mId,id);
    }

    public void setUserId(String userId) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mUserId,userId);
    }

    public void setUserIcon(String userIcon) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mUserIcon,userIcon);
    }

    public void setNickName(String nickName) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mNickName,nickName);
    }

    public void setToken(String token) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mToken,token);
    }
    public void setGroupId(String groupId) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mGroupId,groupId);
    }

    public void setAcceptNickName(String acceptNickName) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mAcceptNickName,acceptNickName);
    }
    public void setNewApplyGroupId(String newApplyGroupId) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mNewApplyGroupId,newApplyGroupId);
    }
    public void setUserShut(String userShut) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mUserShut,userShut);
    }
    public void setPhone(String phone) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mPhone,phone);
    }

    public void setPassword(String password) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mPassword,password);
    }
    public void setSex(String sex) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mSex,sex);
    }

    public void setAddress(String address) {
        SpUtils.saveSPDates(MyApp.getInstance().getApplicationContext(),mAddress,address);
    }

    public void setMineNum(int mineNum) {
        SpUtils.saveSPIntDates(MyApp.getInstance().getApplicationContext(),mMineNum,mineNum);
    }
    public void setCreateNum(int createNum) {
        SpUtils.saveSPIntDates(MyApp.getInstance().getApplicationContext(),mCreateNum,createNum);
    }
    public void setAdminNum(int adminNum) {
        SpUtils.saveSPIntDates(MyApp.getInstance().getApplicationContext(),mAdminNum,adminNum);
    }

    public void setRegisterUser(boolean isRegisterUser) {
        SpUtils.saveSPBooleanDates(MyApp.getInstance().getApplicationContext(),mIsRegisterUser,isRegisterUser);
    }

    public void setManager(boolean isManager) {
        SpUtils.saveSPBooleanDates(MyApp.getInstance().getApplicationContext(),mIsManager,isManager);
    }

    public void setLoginState(boolean isLogin) {
        SpUtils.saveSPBooleanDates(MyApp.getInstance().getApplicationContext(),mIsLogin,isLogin);
    }

    public void setNewApplyIsShow(boolean newApplyIsShow) {
        SpUtils.saveSPBooleanDates(MyApp.getInstance().getApplicationContext(),mNewApplyIsShow,newApplyIsShow);
    }

    public void setNewApply(boolean newApply) {
        SpUtils.saveSPBooleanDates(MyApp.getInstance().getApplicationContext(),mNewApply,newApply);
    }

    public boolean isManager() {
        return  SpUtils.getSPBooleanValues(MyApp.getInstance().getApplicationContext(),mIsManager);
    }

    public String getGroupId() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(), mGroupId);
    }
    public String getAcceptNickName() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mAcceptNickName);
    }
    public String getId() {
        return  SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mId);
    }

    public String getNickName() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mNickName);
    }

    public String getToken() {
        return  SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mToken);
    }

    public String getUserIcon() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mUserIcon);
    }
    public String getSex() {
        return  SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mSex);
    }

    public String getAddress() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mAddress);
    }
    public String getUserId() {
        return  SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mUserId);
    }
    public String getNewApplyGroupId() {
       return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mNewApplyGroupId);
    }
    public boolean isNewApplyIsShow() {
        return SpUtils.getSPBooleanValues(MyApp.getInstance().getApplicationContext(),mNewApplyIsShow);
    }

    public boolean isNewApply() {
        return SpUtils.getSPBooleanValues(MyApp.getInstance().getApplicationContext(),mNewApply);
    }

    public boolean getRegisterUser() {
        return SpUtils.getSPBooleanValues(MyApp.getInstance().getApplicationContext(),mIsRegisterUser);
    }

    public boolean getLoginState() {
        return SpUtils.getSPBooleanValues(MyApp.getInstance().getApplicationContext(),mIsLogin);
    }

    public String getUserShut() {
       return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mUserShut);
    }
    public String getPhone() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mPhone);
    }
    public String getPassword() {
        return SpUtils.getSPValues(MyApp.getInstance().getApplicationContext(),mPassword);
    }

    public int getMineNum() {
        return SpUtils.getSPIntValues(MyApp.getInstance().getApplicationContext(),mMineNum);
    }
    public int getCreateNum() {
        return SpUtils.getSPIntValues(MyApp.getInstance().getApplicationContext(),mCreateNum);
    }
    public int getAdminNum() {
        return SpUtils.getSPIntValues(MyApp.getInstance().getApplicationContext(),mAdminNum);
    }
}