package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.FindUserByPhoneEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 找人
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FindUserByPhoneModel {
    private FindUserByPhoneListenerCallBack mFindUserByPhoneListenerCallBack;
    private Context context;

    public FindUserByPhoneModel(Context context) {
        this.context = context;
    }

    /**
     * 找人
     *
     * @param phone 管理员id
     */
    public void getFindUserByPhone(String phone) {
        OkHttpUtils
                .post()
                .url(Constants.URL_FIND_USER_BY_PHONE)
                .addParams("phone", phone)
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
                        Log.e("im", "找人: " + response);
                        Gson gson = new Gson();
                        FindUserByPhoneEntity findUserByPhoneEntity = gson.fromJson(response, FindUserByPhoneEntity.class);
                        mFindUserByPhoneListenerCallBack.onFindUserByPhone(findUserByPhoneEntity);
                    }
                });
    }

    public interface FindUserByPhoneListenerCallBack {
        /**
         * 找人
         *
         * @param findUserByPhoneEntity
         */
        void onFindUserByPhone(FindUserByPhoneEntity findUserByPhoneEntity);
    }

    public void setFindUserByPhoneListenerCallBack(FindUserByPhoneListenerCallBack mFindUserByPhoneListenerCallBack) {
        this.mFindUserByPhoneListenerCallBack = mFindUserByPhoneListenerCallBack;
    }

}
