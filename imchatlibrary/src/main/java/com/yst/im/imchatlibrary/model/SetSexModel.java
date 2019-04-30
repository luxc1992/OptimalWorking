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
 * 设置性别
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */

public class SetSexModel {
    private SetSexListenerCallBack mSetSexListenerCallBack;
    private Context context;

    public SetSexModel(Context context) {
        this.context = context;
    }

    /**
     * 设置性别
     */
    public void setSex(String sex) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SET_SEX)
                .addParams("sex", sex)
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
                        Log.e("xxx", "      设置性别: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mSetSexListenerCallBack != null) {
                            mSetSexListenerCallBack.onFindUser(baseEntity);
                        }
                    }
                });
    }

    public interface SetSexListenerCallBack {
        /**
         * 设置性别
         */
        void onFindUser(BaseEntity baseEntity);
    }

    public void setSetSexListenerCallBack(SetSexListenerCallBack mSetSexListenerCallBack) {
        this.mSetSexListenerCallBack = mSetSexListenerCallBack;
    }

}
