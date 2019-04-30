package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.bean.RegisterEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.MessageConstant;
import com.yst.im.imsdk.utils.BaseUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/3/26.
 */
public class RegisterModel {
    private RegisterListenerCallBack mRegisterListenerCallBack;
    private Context context;

    public RegisterModel(Context context) {
        this.context = context;
    }

    /**
     * 注册请求
     *
     * @param verificationCode
     * @param phone
     * @param loginPassword
     * @throws IOException
     */
    public void getRegisterUser(String phone, String loginPassword, String verificationCode) throws IOException {
        String ePhone = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(phone);
        String ePwd = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(loginPassword);
        String eCode = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(verificationCode);
        OkHttpUtils.post()
                .url(Constants.URL_REGISTER)
                .addParams("phone", ePhone)
                .addParams("loginPassword", ePwd)
                .addParams("requestSourceSystem", MessageConstant.REQUEST_SS+"_android")
                .addParams("verificationCode", eCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "注册== " + response);
                        Gson gson = new Gson();
                        RegisterEntity registerEntity = gson.fromJson(response, RegisterEntity.class);
                        if (registerEntity.getCode() == NUM_0) {
                            RegisterEntity.ContentBean content = registerEntity.getContent().get(0);
                            mRegisterListenerCallBack.onRegisterUser(content);
                        } else {
                            ImToastUtil.showShortToast(context, registerEntity.getMsg());
                        }
                    }
                });
    }

    public interface RegisterListenerCallBack {
        /**
         * 用户注册回调
         *
         * @param contentBean
         */
        void onRegisterUser(RegisterEntity.ContentBean contentBean);
    }

    public void setRegisterListenerCallBack(RegisterListenerCallBack mRegisterListenerCallBack) {
        this.mRegisterListenerCallBack = mRegisterListenerCallBack;
    }
}
