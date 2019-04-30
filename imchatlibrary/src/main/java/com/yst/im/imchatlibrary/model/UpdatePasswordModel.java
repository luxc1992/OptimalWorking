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
 * 修改登录密码
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class UpdatePasswordModel {
    private UpdatePasswordListenerCallBack mUpdatePasswordListenerCallBack;
    private Context context;

    public UpdatePasswordModel(Context context) {
        this.context = context;
    }

    /**
     * 修改登录密码
     *
     * @param loginPassword 旧密码
     * @param newPassword 新密码
     * @param phone 手机号
     */
    public void getUpdatePassword(String loginPassword, String newPassword, String phone) throws IOException {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_PASSWORD)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("loginPassword", loginPassword)
                .addParams("newPassword", newPassword)
                .addParams("phone", phone)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  修改密码== " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mUpdatePasswordListenerCallBack != null) {
                            mUpdatePasswordListenerCallBack.onUpdatePassword(baseEntity);
                        }
                    }
                });
    }


    public interface UpdatePasswordListenerCallBack {
        /**
         * 修改登录密码
         */
        void onUpdatePassword(BaseEntity baseEntity);
    }

    public void setUpdatePasswordListenerCallBack(UpdatePasswordListenerCallBack mUpdatePasswordListenerCallBack) {
        this.mUpdatePasswordListenerCallBack = mUpdatePasswordListenerCallBack;
    }

}
