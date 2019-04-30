package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.content.IntentFilter;

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
 * 修改绑定的手机号
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class UpdateBindPhoneModel {
    private UpdateBindPhoneListenerCallBack mUpdateBindPhoneListenerCallBack;
    private Context context;

    public UpdateBindPhoneModel(Context context) {
        this.context = context;
    }

    /**
     *  修改绑定的手机号
     *
     * @param phone
     * @param newPhone
     * @param verificationCode
     * @throws IOException
     */
    public void getUpdateBindPhone(String phone,String newPhone,String verificationCode) {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_BIND_PHONE)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("phone", phone)
                .addParams("newPhone", newPhone)
                .addParams("verificationCode", verificationCode)
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  群详情== " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mUpdateBindPhoneListenerCallBack != null) {
                            mUpdateBindPhoneListenerCallBack.onUpdateBindPhone(baseEntity);
                        }
                    }
                });
    }

    public interface UpdateBindPhoneListenerCallBack {
        /**
         * 修改绑定的手机号
         */
        void onUpdateBindPhone(BaseEntity baseEntity);
    }

    public void setUpdateBindPhoneListenerCallBack(UpdateBindPhoneListenerCallBack mUpdateBindPhoneListenerCallBack) {
        this.mUpdateBindPhoneListenerCallBack = mUpdateBindPhoneListenerCallBack;
    }

}
