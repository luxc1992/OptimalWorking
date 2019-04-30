package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.utils.BaseUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 完善用户信息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FinishUserInfoModel {
    private FinishUserInfoListenerCallBack mFinishUserInfoListenerCallBack;
    private Context context;

    public FinishUserInfoModel(Context context) {
        this.context = context;
    }

    /**
     * 完善用户信息
     *
     * @param memberName
     * @param logo
     * @param sex
     * @param address
     */
    public void getFinishUserInfo(String memberName, String logo, String sex, String address) {
        String memberNames = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(memberName);
        String logos = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(logo);
        String sexs = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(sex);
        String addresses = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(address);
        String userId = com.yst.im.imchatlibrary.utils.BaseUtils.setEncryption(MyApp.manager.getId());
        OkHttpUtils
                .post()
                .url(Constants.URL_FINISH_USER_INFO)
                .addParams("memberName", memberNames)
                .addParams("logo", logos)
                .addParams("sex", sexs)
                .addParams("address", addresses)
                .addParams("userId", userId)
                .addParams("requestSourceSystem", REQUEST_SS + "_android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "onResponse移出: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        mFinishUserInfoListenerCallBack.onFinishUserInfo(baseEntity);
                    }
                });
    }

    public interface FinishUserInfoListenerCallBack {
        /**
         * 完善用户信息
         *
         * @param baseEntity
         */
        void onFinishUserInfo(BaseEntity baseEntity);
    }

    public void setFinishUserInfoListenerCallBack(FinishUserInfoListenerCallBack mFinishUserInfoListenerCallBack) {
        this.mFinishUserInfoListenerCallBack = mFinishUserInfoListenerCallBack;
    }

}
