package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

/**
 *   解散群聊回调
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class DissolveGroupChatModel {
    private DissolveGroupChatListenerCallBack mDissolveGroupChatListenerCallBack;
    private Context context;

    public DissolveGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     *   解散群聊
     *
     * @param groupId 群 id
     */
    public void getDissolveGroupChat(String groupId){
        OkHttpUtils
                .post()
                .url(Constants.URL_DISSOLVE_GROUP)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  解散群聊== " + response);
                        mDissolveGroupChatListenerCallBack.onDissolveGroupChat();
                    }
                });
    }


    public interface DissolveGroupChatListenerCallBack {
        /**
         * 解散群聊
         */
        void onDissolveGroupChat();
    }

    public void setDissolveGroupChatListenerCallBack(DissolveGroupChatListenerCallBack mDissolveGroupChatListenerCallBack) {
        this.mDissolveGroupChatListenerCallBack = mDissolveGroupChatListenerCallBack;
    }

}
