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
 * 忘记密码接口
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class ForgotPasswordModel {
    private ForgotPasswordListenerCallBack mForgotPasswordListenerCallBack;
    private Context context;

    public ForgotPasswordModel(Context context) {
        this.context = context;
    }

    /**
     * 忘记密码接口
     *
     * @param phone            群id
     * @param verificationCode 管理员id
     * @param loginPassword    管理员id
     */
    public void getForgotPassword(String phone, String verificationCode, String loginPassword) {
        String ePhone = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(phone);
        String ePwd = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(loginPassword);
        String eCode = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(verificationCode);
        OkHttpUtils
                .post()
                .url(Constants.URL_FORGOT_PASSWORD)
                .addParams("phone", ePhone)
                .addParams("verificationCode", eCode)
                .addParams("loginPassword", ePwd)
                .addParams("requestSourceSystem", REQUEST_SS + "_android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mForgotPasswordListenerCallBack != null) {
                            mForgotPasswordListenerCallBack.onForgotPassword(baseEntity);
                        }
                    }
                });
    }

    public interface ForgotPasswordListenerCallBack {
        /**
         * 忘记密码接口
         */
        void onForgotPassword(BaseEntity baseEntity);
    }

    public void setForgotPasswordListenerCallBack(ForgotPasswordListenerCallBack mForgotPasswordListenerCallBack) {
        this.mForgotPasswordListenerCallBack = mForgotPasswordListenerCallBack;
    }

}
