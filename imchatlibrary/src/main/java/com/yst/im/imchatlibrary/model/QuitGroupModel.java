package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 退出群聊
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class QuitGroupModel {
    private QuitGroupCallBack mQuitGroupCallBack;
    private Context context;

    public QuitGroupModel(Context context) {
        this.context = context;
    }

    /**
     * 退出群聊
     *
     * @param groupId 群 id
     */
    public void getQuitGroup(String groupId){
        OkHttpUtils
                .post()
                .url(Constants.URL_QUIT_GROUP)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "退出群聊== " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mQuitGroupCallBack != null) {
                            mQuitGroupCallBack.onQuitGroup(baseEntity);
                        }
                    }
                });
    }


    public interface QuitGroupCallBack {
        /**
         * 退出群聊
         *
         * @param baseEntity
         */
        void onQuitGroup(BaseEntity baseEntity);
    }

    public void setQuitGroupCallBack(QuitGroupCallBack mQuitGroupCallBack) {
        this.mQuitGroupCallBack = mQuitGroupCallBack;
    }

}
