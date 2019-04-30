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
 *   取消屏蔽群消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class CancelShieldGroupChatModel {
    private CancelShieldGroupChatListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public CancelShieldGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     *   取消屏蔽群消息
     *
     * @param groupId 管理员id
     */
    public void getCancelShieldGroupChat(String groupId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_CANCEL_SHIELD_GROUP_CHAT)
                .addParams("groupId", groupId)
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
                        Log.e("xxx", "    取消屏蔽群消息: " + response);
                        mFindUserListenerCallBack.onFindUser();
                    }
                });
    }

    public interface CancelShieldGroupChatListenerCallBack {
        /**
         *   取消屏蔽群消息
         */
        void onFindUser();
    }

    public void setFindUserListenerCallBack(CancelShieldGroupChatListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
