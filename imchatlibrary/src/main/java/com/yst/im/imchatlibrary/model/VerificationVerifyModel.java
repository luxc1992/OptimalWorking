package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.LoginEntity;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imchatlibrary.utils.Constants.REQUEST_SS;
import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 验证验证码model
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/12.
 */
public class VerificationVerifyModel {
    private VerificationVerifyCallBack verificationVerifyCallBack;
    private Context context;

    public VerificationVerifyModel(Context context) {
        this.context = context;
    }

    /**
     * 验证验证码
     */
    public void verificationVerify(final String phone, final String verificationCode) {
        OkHttpUtils.post()
                .url(Constants.URL_VERIFICATIONVERIFY)
                .addParams("phone", phone)
                .addParams("verificationCode", verificationCode)
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
                        Log.d("xcc", "sdk登录: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (verificationVerifyCallBack != null) {
                            verificationVerifyCallBack.verificationVerify(baseEntity);
                        }
                    }
                });
    }


    public interface VerificationVerifyCallBack {
        /**
         * 验证验证码
         *
         * @param baseEntity
         */
        void verificationVerify(BaseEntity baseEntity);

    }

    public void setVerificationVerifyCallBack(VerificationVerifyCallBack verificationVerifyCallBack) {
        this.verificationVerifyCallBack = verificationVerifyCallBack;
    }
}
