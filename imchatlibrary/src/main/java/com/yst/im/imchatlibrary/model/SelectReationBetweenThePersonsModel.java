package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 查询两个用户是否是好友
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/12.
 */
public class SelectReationBetweenThePersonsModel {
    private selectReationBetweenThePersonsCallBack verificationVerifyCallBack;
    private Context context;

    public SelectReationBetweenThePersonsModel(Context context) {
        this.context = context;
    }

    /**
     * 查询两个用户是否是好友
     */
    public void selectReationBetweenThePersons(final String userId) {
        OkHttpUtils.post()
                .url(Constants.URL_REATION_BETWEEN_THE_PERSONS)
                .addParams("userId", userId)
                .addParams("requestSourceSystem", MessageConstant.REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xcc", "查询两个用户是否是好友: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (verificationVerifyCallBack != null) {
                            verificationVerifyCallBack.selectReationBetweenThePersons(baseEntity);
                        }
                    }
                });
    }


    public interface selectReationBetweenThePersonsCallBack {
        /**
         * 查询两个用户是否是好友
         *
         * @param baseEntity
         */
        void selectReationBetweenThePersons(BaseEntity baseEntity);

    }

    public void setselectReationBetweenThePersonsCallBack(selectReationBetweenThePersonsCallBack verificationVerifyCallBack) {
        this.verificationVerifyCallBack = verificationVerifyCallBack;
    }
}
