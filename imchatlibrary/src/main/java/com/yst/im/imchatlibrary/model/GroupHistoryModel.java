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
 * 用户获取群历史消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class GroupHistoryModel {
    private GroupHistoryListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public GroupHistoryModel(Context context) {
        this.context = context;
    }

    /**
     * 用户获取群历史消息
     *
     * @param groupId
     * @param pageSize
     * @param pageNumber
     */
    private void getGroupHistory(String groupId, String pageSize, String pageNumber) {
        OkHttpUtils
                .post()
                .url(Constants.URL_GROUP_CHAT_HISTORY)
                .addParams("groupId", groupId)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("pageSize", pageSize)
                .addParams("pageNumber", pageNumber)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "            用户获取群历史消息: " + response);
                        mFindUserListenerCallBack.onFindUser();
                    }
                });
    }

    public interface GroupHistoryListenerCallBack {
        /**
         * 用户获取群历史消息
         */
        void onFindUser();
    }

    public void setFindUserListenerCallBack(GroupHistoryListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
