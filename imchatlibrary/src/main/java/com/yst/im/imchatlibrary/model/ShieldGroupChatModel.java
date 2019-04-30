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

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 屏蔽消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class ShieldGroupChatModel {
    private ShieldGroupChatListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public ShieldGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     * 屏蔽消息
     *
     * @param groupId 管理员id
     */
    public void getShieldGroup(String state,String groupId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SHIELD_GROUP_CHAT)
                .addParams("groupId", groupId)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .addParams("state", state)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "      屏蔽消息: " + response);
                        Gson gson=new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (baseEntity.getCode()==NUM_0) {
                            mFindUserListenerCallBack.onShieldGroup();
                        }
                    }
                });
    }

    public interface ShieldGroupChatListenerCallBack {
        /**
         * 屏蔽消息
         */
        void onShieldGroup();
    }

    public void setFindUserListenerCallBack(ShieldGroupChatListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
