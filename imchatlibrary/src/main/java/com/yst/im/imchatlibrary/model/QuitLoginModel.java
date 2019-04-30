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
 * 退出登录
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class QuitLoginModel {
    private QuitLoginListenerCallBack mQuitLoginListenerCallBack;
    private Context context;

    public QuitLoginModel(Context context) {
        this.context = context;
    }

    /**
     * 退出登录
     */
    public void getQuitLogin() {
        OkHttpUtils
                .post()
                .url(Constants.URL_QUIT_LOGIN)
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
                        ImLog.e("imlog", "  退出登录 == " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        MyApp.manager.setLoginState(false);
                        if (mQuitLoginListenerCallBack != null) {
                            mQuitLoginListenerCallBack.onQuitLogin(baseEntity);
                        }
                    }
                });
    }

    public interface QuitLoginListenerCallBack {
        /**
         * 退出登录
         */
        void onQuitLogin(BaseEntity baseEntity);
    }

    public void setQuitLoginListenerCallBack(QuitLoginListenerCallBack mQuitLoginListenerCallBack) {
        this.mQuitLoginListenerCallBack = mQuitLoginListenerCallBack;
    }

}
