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
 * 密码验证
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class VerifyPasswordModel {
    private VerifyPassListenerCallBack mVerifyPassListenerCallBack;
    private Context context;

    public VerifyPasswordModel(Context context) {
        this.context = context;
    }

    /**
     * 密码验证
     *
     * @param phone    手机
     * @param password 密码
     */
    public void getVerifyPass(String phone, String password) {
        OkHttpUtils
                .post()
                .url(Constants.URL_VERIFY_PASSWORD)
                .addParams("phone", phone)
                .addParams("password", password)
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
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mVerifyPassListenerCallBack != null) {
                            mVerifyPassListenerCallBack.onVerifyPass(baseEntity);
                        }
                    }
                });
    }

    public interface VerifyPassListenerCallBack {
        /**
         * 密码验证
         */
        void onVerifyPass(BaseEntity baseEntity);
    }

    public void setVerifyPassListenerCallBack(VerifyPassListenerCallBack mVerifyPassListenerCallBack) {
        this.mVerifyPassListenerCallBack = mVerifyPassListenerCallBack;
    }

}
