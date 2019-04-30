package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 *   忘记密码修改密model
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/12.
 */
public class ForgetChangePasswordModel {
    private ForgetChangePasswordModelCallBack forgetChangePasswordModelCallBack;
    private Context context;

    public ForgetChangePasswordModel(Context context) {
        this.context = context;
    }

    /**
     *   忘记密码修改密
     */
    public void changePassword(String phone, String loginPassword) {
        OkHttpUtils.post()
                .url(Constants.URL_AMENDPASSWORD)
                .addParams("phone", phone)
                .addParams("password", loginPassword)
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
                        if (forgetChangePasswordModelCallBack != null) {
                            forgetChangePasswordModelCallBack.changePassword(baseEntity);
                        }
                    }
                });
    }


    public interface ForgetChangePasswordModelCallBack {
        /**
         * 查询  我创建的群聊
         *
         * @param baseEntity
         */
        void changePassword(BaseEntity baseEntity);

    }

    public void setForgetChangePasswordModelCallBack(ForgetChangePasswordModelCallBack forgetChangePasswordModelCallBack) {
        this.forgetChangePasswordModelCallBack = forgetChangePasswordModelCallBack;
    }
}
