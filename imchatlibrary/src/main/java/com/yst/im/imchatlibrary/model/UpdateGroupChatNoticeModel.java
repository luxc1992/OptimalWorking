package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * 修改群公告
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class UpdateGroupChatNoticeModel {
    private UpdateGroupChatNoticeListenerCallBack mUpdateGroupChatNoticeListenerCallBack;
    private Context context;

    public UpdateGroupChatNoticeModel(Context context) {
        this.context = context;
    }

    /**
     * 修改群公告
     *
     * @param groupId 群 id
     */
    public void getUpdateGroupChatNotice(String groupId, String describ) {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_GROUP_NOTICE)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("describ", describ)
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
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        mUpdateGroupChatNoticeListenerCallBack.onUpdateGroupChatNotice(baseEntity);
                    }
                });
    }

    public interface UpdateGroupChatNoticeListenerCallBack {
        /**
         * 修改群公告
         *
         * @param baseEntity
         */
        void onUpdateGroupChatNotice(BaseEntity baseEntity);
    }

    public void setUpdateGroupChatNoticeListenerCallBack(UpdateGroupChatNoticeListenerCallBack mUpdateGroupChatNoticeListenerCallBack) {
        this.mUpdateGroupChatNoticeListenerCallBack = mUpdateGroupChatNoticeListenerCallBack;
    }

}
