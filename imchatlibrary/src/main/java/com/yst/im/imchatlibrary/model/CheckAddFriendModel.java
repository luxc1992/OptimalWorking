package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 审核添加好友接口
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class CheckAddFriendModel {
    private CheckAddFriendListenerCallBack mCheckAddFriendListenerCallBack;
    private Context context;

    public CheckAddFriendModel(Context context) {
        this.context = context;
    }

    /**
     * 审核添加好友接口
     *
     * @param userId
     * @param state
     */
    public void getCheckAddFriend(String userId, String state) {
        OkHttpUtils
                .post()
                .url(Constants.URL_CHECK_ADD_FRIEND)
                .addParams("userId", userId)
                .addParams("state", state)
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
                        Log.e("xxx", "审核添加好友接口: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mCheckAddFriendListenerCallBack != null) {
                            mCheckAddFriendListenerCallBack.onCheckAddFriend(baseEntity);
                        }
                    }
                });
    }

    public interface CheckAddFriendListenerCallBack {
        /**
         * 审核添加好友接口
         *
         * @param baseEntity
         */
        void onCheckAddFriend(BaseEntity baseEntity);
    }

    public void setCheckAddFriendListenerCallBack(CheckAddFriendListenerCallBack mCheckAddFriendListenerCallBack) {
        this.mCheckAddFriendListenerCallBack = mCheckAddFriendListenerCallBack;
    }

}
