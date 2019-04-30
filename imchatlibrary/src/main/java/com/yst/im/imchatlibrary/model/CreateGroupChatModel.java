package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.CreateGroupChatEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 去发起群聊
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/9
 */

public class CreateGroupChatModel {
    private CreateGroupChatListenerCallBack mCreateGroupChatListenerCallBack;
    private Context context;

    public CreateGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     * 去发起群聊
     *
     * @param ids ids
     */
    public void getCreateGroupChat(String ids) {
        OkHttpUtils
                .post()
                .url(Constants.URL_TAKE_GROUP_CHAT)
                .addParams("ids", ids)
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
                        Log.e("im", "  去发起群聊: " + response);
                        Gson gson = new Gson();
                        CreateGroupChatEntity baseEntity = gson.fromJson(response, CreateGroupChatEntity.class);
                        if (baseEntity.getCode() == NUM_0) {
                            if (mCreateGroupChatListenerCallBack != null) {
                               mCreateGroupChatListenerCallBack.onCreateGroupChat(baseEntity);
                            }
                        }
                    }
                });
    }

    public interface CreateGroupChatListenerCallBack {
        /**
         * 去发起群聊
         *
         * @param baseEntity
         */
        void onCreateGroupChat(CreateGroupChatEntity baseEntity);
    }

    public void setCreateGroupChatListenerCallBack(CreateGroupChatListenerCallBack mCreateGroupChatListenerCallBack) {
        this.mCreateGroupChatListenerCallBack = mCreateGroupChatListenerCallBack;
    }

}
