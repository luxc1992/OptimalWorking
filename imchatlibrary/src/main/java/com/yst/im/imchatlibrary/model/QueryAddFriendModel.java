package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.ApplyEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 查询好友申请
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class QueryAddFriendModel {
    private QueryAddFriendListenerCallBack mQueryAddFriendListenerCallBack;
    private Context context;

    public QueryAddFriendModel(Context context) {
        this.context = context;
    }

    /**
     * 查询好友申请
     */
    public void getQueryAddFriend() {
        OkHttpUtils
                .post()
                .url(Constants.URL_QUERY_ADD_FRIEND)
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
                        Log.e("xxx", "查询好友申请: " + response);
                        Gson gson = new Gson();
                        ApplyEntity applyEntity = gson.fromJson(response, ApplyEntity.class);
                        if (mQueryAddFriendListenerCallBack != null) {
                            mQueryAddFriendListenerCallBack.onQueryAddFriend(applyEntity);
                        }
                    }
                });
    }

    public interface QueryAddFriendListenerCallBack {
        /**
         * 查询好友申请
         */
        void onQueryAddFriend(ApplyEntity applyEntity);
    }

    public void setQueryAddFriendListenerCallBack(QueryAddFriendListenerCallBack mQueryAddFriendListenerCallBack) {
        this.mQueryAddFriendListenerCallBack = mQueryAddFriendListenerCallBack;
    }

}
