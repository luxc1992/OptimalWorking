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
 * 删除单聊历史消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class DeleteSingleChatMessageModel {
    private DeleteSingleChatMessageCallBack mDeleteSingleChatMessageCallBack;
    private Context context;

    public DeleteSingleChatMessageModel(Context context) {
        this.context = context;
    }

    /**
     * 删除单聊历史消息
     *
     * @param accepteId 群id
     */
    public void getDeleteSingleChatMessage(String accepteId, final int position) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DELETE_SINGLE_CHAT_MESSAGE)
                .addParams("accepteId", accepteId)
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
                        Log.e("xxx", "        删除单聊历史消息: " + response);
                        BaseEntity baseEntity = new Gson().fromJson(response, BaseEntity.class);
                        if (mDeleteSingleChatMessageCallBack != null) {
                            mDeleteSingleChatMessageCallBack.onDeleteSingleChat(baseEntity, position);
                        }
                    }
                });
    }

    public interface DeleteSingleChatMessageCallBack {
        /**
         * 删除单聊历史消息
         *
         * @param baseEntity
         * @param position
         */
        void onDeleteSingleChat(BaseEntity baseEntity, int position);
    }

    public void setDeleteSingleChatMessageCallBack(DeleteSingleChatMessageCallBack mDeleteSingleChatMessageCallBack) {
        this.mDeleteSingleChatMessageCallBack = mDeleteSingleChatMessageCallBack;
    }

}
