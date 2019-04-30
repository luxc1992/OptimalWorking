package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 踢出群聊
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class DropGroupChatModel {
    private DropGroupChatCallBack mDropGroupChatCallBack;
    private Context context;

    public DropGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     * 踢出群聊
     *
     * @param groupId
     * @param userId
     */
    public void getDropGroupChat(String groupId, String userId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DROP_GROUP_CHAT)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("userId", userId)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  踢出群聊== " + response);
                        mDropGroupChatCallBack.onDropGroupChat();
                    }
                });
    }


    public interface DropGroupChatCallBack {
        /**
         * 踢出群聊
         */
        void onDropGroupChat();
    }

    public void setDropGroupChatCallBack(DropGroupChatCallBack mDropGroupChatCallBack) {
        this.mDropGroupChatCallBack = mDropGroupChatCallBack;
    }

}
