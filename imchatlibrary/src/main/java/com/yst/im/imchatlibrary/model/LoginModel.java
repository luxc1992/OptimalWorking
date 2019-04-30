package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.LoginEntity;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imchatlibrary.utils.Constants.REQUEST_SS;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;

/**
 * 登录model
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/3/12.
 */
public class LoginModel {
    private LoginListenerCallBack mLoginListenerCallBack;
    private Context context;

    public LoginModel(Context context) {
        this.context = context;
    }

    /**
     * SDK登录接口
     */
    public void sdkLogin(final String loginName, final String loginPassword) throws IOException {
        String userName = BaseUtils.setEncryption(loginName);
        String passWord = BaseUtils.setEncryption(loginPassword);
        OkHttpUtils.post()
                .url(Constants.SDK_LOGIN)
                .addParams("phone", userName)
                .addParams("loginPassword", passWord)
                .addParams("requestSourceSystem", REQUEST_SS + "_android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xcc", "sdk登录: " + response);
//                        Gson gson = new Gson();
//                        LoginEntity loginEntity = gson.fromJson(response, LoginEntity.class);
//                        if (loginEntity.getCode() == NUM_0) {
//                            login(loginName, loginPassword);
//                        }
                    }
                });
    }

    /**
     * 登录接口
     *
     * @param userId
     * @param userPassword
     * @throws IOException
     */
    public void login(String userId, String userPassword) {
        OkHttpUtils.post()
                .url(Constants.URL_API_LOGIN)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("userPhone", userId)
                .addParams("userPassword", userPassword)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ImToastUtil.showShortToast(context, "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("im", "Api登录: " + response);
                        Gson gson = new Gson();
                        LoginEntity loginEntity = gson.fromJson(response, LoginEntity.class);
                        if (loginEntity.getCode() == NUM_0) {
                            MyApp.manager.setId(loginEntity.getContent().getUserId());
                            MyApp.manager.setToken(loginEntity.getContent().getToken());
                            MyApp.manager.setNickName(loginEntity.getContent().getNickName());
                            MyApp.manager.setPhone(loginEntity.getContent().getPhone());
                            MyApp.manager.setUserIcon(loginEntity.getContent().getUserIcon());
                            MyApp.manager.setAddress(loginEntity.getContent().getAddress());
                            MyApp.manager.setSex(loginEntity.getContent().getSex());
                            MyApp.manager.setLoginState(true);
                        }
                        mLoginListenerCallBack.onUserLogin(loginEntity);
                    }
                });
    }

    public interface LoginListenerCallBack {
        /**
         * 用户登录
         *
         * @param contentBean 登录
         */
        void onUserLogin(LoginEntity contentBean);

    }

    public void setLoginListenerCallBack(LoginListenerCallBack mLoginListenerCallBack) {
        this.mLoginListenerCallBack = mLoginListenerCallBack;
    }
}
