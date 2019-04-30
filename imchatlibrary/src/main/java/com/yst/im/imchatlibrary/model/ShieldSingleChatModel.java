package com.yst.im.imchatlibrary.model;

import android.content.Context;

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
 * 屏蔽好友
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/20
 */

public class ShieldSingleChatModel {
    private ShieldSingleChatListenerCallBack mShieldSingleChatListenerCallBack;
    private Context context;

    public ShieldSingleChatModel(Context context) {
        this.context = context;
    }

    /**
     * 屏蔽好友
     *
     * @param userId 好友唯一标识
     * @param state  状态，0 屏蔽 ， 1 ，取消屏蔽
     */
    public void setShieldSingleChat(String userId, String state) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SHIELDSINGLECHAT)
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
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mShieldSingleChatListenerCallBack != null) {
                            mShieldSingleChatListenerCallBack.onShieldSingleChat(baseEntity);
                        }
                    }
                });
    }

    public interface ShieldSingleChatListenerCallBack {
        /**
         * 屏蔽好友
         */
        void onShieldSingleChat(BaseEntity baseEntity);
    }

    public void setShieldSingleChatListenerCallBack(ShieldSingleChatListenerCallBack mShieldSingleChatListenerCallBack) {
        this.mShieldSingleChatListenerCallBack = mShieldSingleChatListenerCallBack;
    }

}
