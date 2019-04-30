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
 * 设置姓名
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SetNameModel {
    private SetNameListenerCallBack mSetNameListenerCallBack;
    private Context context;

    public SetNameModel(Context context) {
        this.context = context;
    }

    /**
     * 设置姓名
     *
     * @param name 群id
     */
    public void getSetName(String name) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SET_NAME)
                .addParams("name", name)
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
                        Log.e("xxx", "    设置姓名: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (baseEntity.getCode() == NUM_0) {
                            if (mSetNameListenerCallBack != null) {
                                mSetNameListenerCallBack.onSetName(baseEntity);
                            }
                        }
                    }
                });
    }

    public interface SetNameListenerCallBack {
        /**
         * 设置姓名
         *
         * @param baseEntity
         */
        void onSetName(BaseEntity baseEntity);
    }

    public void setSetNameListenerCallBack(SetNameListenerCallBack mSetNameListenerCallBack) {
        this.mSetNameListenerCallBack = mSetNameListenerCallBack;
    }

}
