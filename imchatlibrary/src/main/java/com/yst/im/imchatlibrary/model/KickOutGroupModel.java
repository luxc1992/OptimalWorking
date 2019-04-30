package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 *   踢出群聊
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class KickOutGroupModel {
    private KickOutGroupListenerCallBack mKickOutGroupListenerCallBack;
    private Context context;

    public KickOutGroupModel(Context context) {
        this.context = context;
    }

    /**
     * 踢出群聊
     *
     * @param groupId 群id
     * @param userId  管理员id
     */
    public void getKickOutGroup(String groupId, String userId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_KICK_OUT_GROUP)
                .addParams("groupId", groupId)
                .addParams("manageId", userId)
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
                        Log.e("xxx", "onResponse移出: "+response);
                        mKickOutGroupListenerCallBack.onKickOutGroup();
                    }
                });
    }

    public interface KickOutGroupListenerCallBack {
        /**
         *   踢出群聊
         */
        void onKickOutGroup();
    }

    public void setKickOutGroupListenerCallBack(KickOutGroupListenerCallBack mKickOutGroupListenerCallBack) {
        this.mKickOutGroupListenerCallBack = mKickOutGroupListenerCallBack;
    }

}
