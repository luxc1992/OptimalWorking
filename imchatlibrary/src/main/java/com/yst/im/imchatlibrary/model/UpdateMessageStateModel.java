package com.yst.im.imchatlibrary.model;

import android.app.Activity;
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
 * 修改消息备份表中消息的状态
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/3
 */

public class UpdateMessageStateModel {
    private UpdateMessageStateListenerCallBack mUpdateMessageStateListenerCallBack;
    private Activity context;

    public UpdateMessageStateModel(Activity context) {
        this.context = context;
    }

    /**
     * 修改消息备份表中消息的状态
     *
     * @param accepteId   接收者的id
     * @param isGroupChat 是否是群聊
     * @param time        时间戳
     */
    public void updateMessageState(String accepteId, String isGroupChat, String time) {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATEMESSAGESTATE)
                .addParams("accepteId", accepteId)
                .addParams("isGroupChat", isGroupChat)
                .addParams("time", time + "000")
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        context.finish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("im", "  修改消息备份表中消息的状态: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mUpdateMessageStateListenerCallBack != null) {
                            mUpdateMessageStateListenerCallBack.onUpdateMessageState(baseEntity);
                        }
                    }
                });
    }

    public interface UpdateMessageStateListenerCallBack {
        /**
         * 修改消息备份表中消息的状态
         *
         * @param baseEntity
         */
        void onUpdateMessageState(BaseEntity baseEntity);
    }

    public void setUpdateMessageStateListenerCallBack(UpdateMessageStateListenerCallBack mUpdateMessageStateListenerCallBack) {
        this.mUpdateMessageStateListenerCallBack = mUpdateMessageStateListenerCallBack;
    }

}
