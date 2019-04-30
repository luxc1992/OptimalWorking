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
 * 用户删除群聊历史记录
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class DeleteGroupChatMessageModel {
    private DeleteGroupChatMessageCallBack mDeleteGroupChatMessageCallBack;
    private Context context;

    public DeleteGroupChatMessageModel(Context context) {
        this.context = context;
    }

    /**
     * 用户删除群聊历史记录
     *
     * @param groupId 群id
     */
    public void getDeleteGroupChatMessage(String groupId, final int position) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DELETE_GROUP_CHAT_MESSAGE)
                .addParams("groupId", groupId)
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
                        Log.e("xxx", "  用户删除群聊历史记录: " + response);
                        BaseEntity baseEntity = new Gson().fromJson(response, BaseEntity.class);
                        if (mDeleteGroupChatMessageCallBack != null) {
                            mDeleteGroupChatMessageCallBack.onDeleteGroupChatMessage(baseEntity, position);
                        }
                    }
                });
    }

    public interface DeleteGroupChatMessageCallBack {
        /**
         * 用户删除群聊历史记录
         *
         * @param baseEntity
         * @param position
         */
        void onDeleteGroupChatMessage(BaseEntity baseEntity, int position);
    }

    public void setDeleteGroupChatMessageCallBack(DeleteGroupChatMessageCallBack mDeleteGroupChatMessageCallBack) {
        this.mDeleteGroupChatMessageCallBack = mDeleteGroupChatMessageCallBack;
    }

}
