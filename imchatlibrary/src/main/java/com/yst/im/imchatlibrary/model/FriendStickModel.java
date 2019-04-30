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

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 好友置顶
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FriendStickModel {
    private FriendStickListenerCallBack mFriendStickListenerCallBack;
    private Context context;

    public FriendStickModel(Context context) {
        this.context = context;
    }

    /**
     * 好友置顶
     *
     * @param state
     * @param friendId
     */
    public void getFriendStick(String state, String friendId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_FRIEND_STICK)
                .addParams("state", state)
                .addParams("friendId", friendId)
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
                        Log.e("xxx", "好友置顶: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mFriendStickListenerCallBack != null) {
                            mFriendStickListenerCallBack.onFriendStick(baseEntity);
                        }
                    }
                });
    }

    public interface FriendStickListenerCallBack {
        /**
         * 好友置顶
         */
        void onFriendStick(BaseEntity baseEntity);
    }

    public void setFriendStickListenerCallBack(FriendStickListenerCallBack mFriendStickListenerCallBack) {
        this.mFriendStickListenerCallBack = mFriendStickListenerCallBack;
    }

}
