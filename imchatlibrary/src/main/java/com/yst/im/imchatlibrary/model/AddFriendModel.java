package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 添加好友
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class AddFriendModel {
    private AddFriendListenerCallBack mAddFriendListenerCallBack;
    private Context context;

    public AddFriendModel(Context context) {
        this.context = context;
    }

    /**
     * 添加好友
     *
     * @param userId  要添加的好友
     * @param describ 申请信息
     */
    public void getAddFriend(String userId, String describ) {
        OkHttpUtils
                .post()
                .url(Constants.URL_ADD_USER)
                .addParams("userId", userId)
                .addParams("describ", describ)
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
                        Log.e("xxx", "添加好友申请: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mAddFriendListenerCallBack != null) {
                            mAddFriendListenerCallBack.onAddFriend(baseEntity);
                        }
                    }
                });
    }

    public interface AddFriendListenerCallBack {
        /**
         * 查询用户个信息
         *
         * @param baseEntity
         */
        void onAddFriend(BaseEntity baseEntity);
    }

    public void setAddFriendListenerCallBack(AddFriendListenerCallBack mAddFriendListenerCallBack) {
        this.mAddFriendListenerCallBack = mAddFriendListenerCallBack;
    }

}
