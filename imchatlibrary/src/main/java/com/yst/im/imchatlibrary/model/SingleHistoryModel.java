package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 用户获取单聊历史消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SingleHistoryModel {
    private SingleHistoryListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public SingleHistoryModel(Context context) {
        this.context = context;
    }

    /**
     * 用户获取单聊历史消息
     *
     * @param accepteId
     * @param pageSize
     * @param pageNumber
     */
    private void getSingleHistory(String accepteId, String pageSize, String pageNumber) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SINGLE_CHAT_HISTORY)
                .addParams("accepteId", accepteId)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("pageSize", pageSize)
                .addParams("pageNumber", pageNumber)
                .addParams("token", MyApp.manager.getToken())
                .addParams("senderId", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "          用户获取单聊历史消息: " + response);
                        mFindUserListenerCallBack.onFindUser();
                    }
                });
    }

    public interface SingleHistoryListenerCallBack {
        /**
         * 用户获取单聊历史消息
         */
        void onFindUser();
    }

    public void setFindUserListenerCallBack(SingleHistoryListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
