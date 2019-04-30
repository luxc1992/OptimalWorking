package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 优工连不经过同意直接添加为好友
 *
 * @author qinchaohshuai
 * @version 1.0.0
 * @date 2018/4/9
 */
public class FriendRegistrationModel {
    private FriendRegistrationListenerCallBack mFriendRegistrationListenerCallBack;
    private Context context;

    public FriendRegistrationModel(Context context) {
        this.context = context;
    }

    /**
     * 优工连不经过同意直接添加为好友
     *
     * @param userId 好友唯一标识
     */
    public void getFriendRegistration(String userId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_FRIENDREGISTRATION)
                .addParams("userId", userId)
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
                        Log.e("im", "  优工连不经过同意直接添加为好友: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mFriendRegistrationListenerCallBack != null) {
                            mFriendRegistrationListenerCallBack.onFriendRegistration(baseEntity);
                        }
                    }
                });
    }

    public interface FriendRegistrationListenerCallBack {
        /**
         * 优工连不经过同意直接添加为好友
         *
         * @param baseEntity
         */
        void onFriendRegistration(BaseEntity baseEntity);
    }

    public void setFriendRegistrationListenerCallBack(FriendRegistrationListenerCallBack mFriendRegistrationListenerCallBack) {
        this.mFriendRegistrationListenerCallBack = mFriendRegistrationListenerCallBack;
    }

}
