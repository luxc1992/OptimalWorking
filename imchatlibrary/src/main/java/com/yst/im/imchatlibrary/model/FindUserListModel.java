package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 查询好友列表
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FindUserListModel {
    private FindUserListListenerCallBack mFindUserListListenerCallBack;
    private Context context;

    public FindUserListModel(Context context) {
        this.context = context;
    }

    /**
     * 查询好友列表
     */
    public void getFindUserList() {
        OkHttpUtils
                .post()
                .url(Constants.URL_FIND_USER_LIST)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("im", "  查询好友列表: " + response);
                        Gson gson = new Gson();
                        FriendListEntity friendListEntity = gson.fromJson(response, FriendListEntity.class);
                        mFindUserListListenerCallBack.onFindUserList(friendListEntity);
                    }
                });
    }

    public interface FindUserListListenerCallBack {
        /**
         * 查询好友列表
         *
         * @param friendListEntity
         */
        void onFindUserList(FriendListEntity friendListEntity);
    }

    public void setFindUserListListenerCallBack(FindUserListListenerCallBack mFindUserListListenerCallBack) {
        this.mFindUserListListenerCallBack = mFindUserListListenerCallBack;
    }

}
