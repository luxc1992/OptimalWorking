package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.HistoryMsgEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 用户获取离线消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class GetOfflineMessageModel {
    private GetOfflineMessageListenerCallBack mGetOfflineMessageListenerCallBack;
    private Context context;

    public GetOfflineMessageModel(Context context) {
        this.context = context;
    }

    /**
     * 用户获取离线消息
     */
    public void getGetOfflineMessage() {
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_OFF_MSG)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("senderId", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        HistoryMsgEntity historyMsgEntity = gson.fromJson(response, HistoryMsgEntity.class);
                        Log.e("xxx", "用户获取离线消息: " + response);
                        if (mGetOfflineMessageListenerCallBack != null) {
                           mGetOfflineMessageListenerCallBack.onGetOfflineMessage(historyMsgEntity);
                        }
                    }
                });
    }

    public interface GetOfflineMessageListenerCallBack {
        /**
         * 用户获取离线消息
         */
        void onGetOfflineMessage(HistoryMsgEntity historyMsgEntity);
    }

    public void setGetOfflineMessageListenerCallBack(GetOfflineMessageListenerCallBack mGetOfflineMessageListenerCallBack) {
        this.mGetOfflineMessageListenerCallBack = mGetOfflineMessageListenerCallBack;
    }

}
