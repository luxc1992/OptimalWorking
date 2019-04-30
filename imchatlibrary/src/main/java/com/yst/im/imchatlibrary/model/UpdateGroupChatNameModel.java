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
 * 修改群聊室名称
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class UpdateGroupChatNameModel {
    private UpdateGroupChatNameListenerCallBack mUpdateGroupChatNameListenerCallBack;
    private Context context;

    public UpdateGroupChatNameModel(Context context) {
        this.context = context;
    }

    /**
     * 修改群聊室名称
     *
     * @param groupId   群 id
     * @param groupName 群名称
     */
    public void getUpdateGroupChat(String groupId, String groupName){
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_GROUP_NAME)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("groupName", groupName)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  修改群公告== " + response);
                        mUpdateGroupChatNameListenerCallBack.onUpdateGroupChatName();
                    }
                });
    }

    public interface UpdateGroupChatNameListenerCallBack {
        /**
         * 修改群聊室名称
         */
        void onUpdateGroupChatName();
    }

    public void setUpdateGroupChatNameListenerCallBack(UpdateGroupChatNameListenerCallBack mUpdateGroupChatNameListenerCallBack) {
        this.mUpdateGroupChatNameListenerCallBack = mUpdateGroupChatNameListenerCallBack;
    }

}
