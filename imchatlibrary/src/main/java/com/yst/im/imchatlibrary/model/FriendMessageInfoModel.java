package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.UsersEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

/**
 * 查询用户详情
 *
 * @author Lierpeng
 * @date 2018/3/16
 * @version 1.0.0
 */
public class FriendMessageInfoModel {

    private Context context;

    private FriendMessageInfoCallBack mFriendMessageInfoCallBack;

    public FriendMessageInfoModel(Context context) {
        this.context = context;
    }

    /**
     * 查询用户请求
     */
    public void findUser(String userId) throws IOException {
        OkHttpUtils.post()
                .url(Constants.URL_FIND_USER_INFO)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .addParams("userId", userId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                ImLog.e("xxxim", "查询用户" + response);
                Gson gson = new Gson();
                UsersEntity usersEntity = gson.fromJson(response, UsersEntity.class);
                if (usersEntity.getCode() == 0) {
                    UsersEntity.ContentBean contentBean = usersEntity.getContent().get(0);

                    mFriendMessageInfoCallBack.onFriendMessageInfo(contentBean);
                }
            }
        });

    }

    public interface FriendMessageInfoCallBack {

        /**
         * 好友信息详情
         *
         * @param contentBean 好友实体bean
         */
        void onFriendMessageInfo(UsersEntity.ContentBean contentBean);

    }

    public void setFriendMessageInfoCallBack(FriendMessageInfoCallBack mFriendMessageInfoCallBack) {
        this.mFriendMessageInfoCallBack = mFriendMessageInfoCallBack;
    }
}
