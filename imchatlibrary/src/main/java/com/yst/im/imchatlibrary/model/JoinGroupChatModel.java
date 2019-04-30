package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 申请加入回调
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class JoinGroupChatModel {
    private JoinGroupListenerCallBack mJoinGroupListenerCallBack;
    private Context context;

    public JoinGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     * 申请加入
     *
     * @param describ 用户id
     * @param groupId 群 id
     */
    public void getJoinGroupChat(String describ, String groupId) throws IOException {
        OkHttpUtils
                .post()
                .url(Constants.URL_JOIN_GROUP_CHAT)
                .addParams("requestSourceSystem", REQUEST_SS)
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
                        ImLog.e("imlog", "申请加入== " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mJoinGroupListenerCallBack != null) {
                            mJoinGroupListenerCallBack.onJoinGroupUser(baseEntity);
                        }
                    }
                });
    }


    public interface JoinGroupListenerCallBack {
        /**
         * 申请加入回调
         *
         * @param baseEntity
         */
        void onJoinGroupUser(BaseEntity baseEntity);
    }

    public void setJoinGroupListenerCallBack(JoinGroupListenerCallBack mJoinGroupListenerCallBack) {
        this.mJoinGroupListenerCallBack = mJoinGroupListenerCallBack;
    }

}
