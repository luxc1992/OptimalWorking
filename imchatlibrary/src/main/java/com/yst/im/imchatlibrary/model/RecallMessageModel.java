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
 * 用户撤回消息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */
public class RecallMessageModel {
    private RecallMessageListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public RecallMessageModel(Context context) {
        this.context = context;
    }

    /**
     * 用户撤回消息
     *
     * @param version 管理员id
     */
    public void getRecallMessage(String version) {
        OkHttpUtils
                .post()
                .url(Constants.URL_RECALL_MESSAGE)
                .addParams("version", version)
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
                        Log.e("xxx", "        用户撤回消息: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        mFindUserListenerCallBack.onRecllBack(baseEntity);
                    }
                });
    }

    public interface RecallMessageListenerCallBack {
        /**
         * 用户撤回消息
         *
         * @param baseEntity
         */
        void onRecllBack(BaseEntity baseEntity);
    }

    public void setFindUserListenerCallBack(RecallMessageListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
