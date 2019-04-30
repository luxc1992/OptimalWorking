package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 获取手机验证码
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */
public class VerificationCodeModel {
    private VerificationCodeListenerCallBack mVerificationCodeListenerCallBack;
    private Context context;

    public VerificationCodeModel(Context context) {
        this.context = context;
    }

    /**
     * 获取手机验证码
     *
     * @param phone 群id
     */
    public void getVerificationCode(String phone) {
        String phoneSign = BaseUtils.setEncryption(phone);
        OkHttpUtils
                .post()
                .url(Constants.URL_VERIFICATION_INFO)
                .addParams("phone", phoneSign)
                .addParams("requestSourceSystem", REQUEST_SS + "_android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("im", "  获取手机验证码: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mVerificationCodeListenerCallBack != null) {
                            mVerificationCodeListenerCallBack.onVerificationCode(baseEntity);
                        }
                    }
                });
    }

    public interface VerificationCodeListenerCallBack {
        /**
         * 获取手机验证码
         *
         * @param baseEntity
         */
        void onVerificationCode(BaseEntity baseEntity);
    }

    public void setVerificationCodeListenerCallBack(VerificationCodeListenerCallBack mVerificationCodeListenerCallBack) {
        this.mVerificationCodeListenerCallBack = mVerificationCodeListenerCallBack;
    }

}
