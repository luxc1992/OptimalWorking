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
 * 审核用户加入群回调
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class CheckJoinGroupModel {
    private CheckJoinGroupListenerCallBack mCheckJoinGroupListenerCallBack;
    private Context context;

    public CheckJoinGroupModel(Context context) {
        this.context = context;
    }

    /**
     * 申请加入
     *
     * @param manageId 用户id
     * @param state    用户id
     * @param groupId  群 id
     */
    public void getCheckJoinGroup(String manageId, String state, String groupId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_CHECK_JOIN_GROUP)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("manageId", manageId)
                .addParams("state", state)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  审核用户加入群== " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mCheckJoinGroupListenerCallBack != null) {
                            mCheckJoinGroupListenerCallBack.onCheckJoinGroup(baseEntity);
                        }
                    }
                });
    }


    public interface CheckJoinGroupListenerCallBack {
        /**
         * 审核用户加入群回调
         *
         * @param baseEntity
         */
        void onCheckJoinGroup(BaseEntity baseEntity);
    }

    public void setCheckJoinGroupListenerCallBack(CheckJoinGroupListenerCallBack mCheckJoinGroupListenerCallBack) {
        this.mCheckJoinGroupListenerCallBack = mCheckJoinGroupListenerCallBack;
    }

}
