package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.FindUserEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 查询用户个信息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FindUserModel {
    private FindUserListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public FindUserModel(Context context) {
        this.context = context;
    }

    /**
     * 查询用户个信息
     *
     * @param userId 管理员id
     */
    public void getFindUser(String userId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_FIND_USER_INFO)
                .addParams("userId", userId)
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
                        Log.e("im", "查询用户个信息: " + response);
                        Gson gson = new Gson();
                        FindUserEntity findUserEntity = gson.fromJson(response, FindUserEntity.class);
                        if (findUserEntity.getCode() == NUM_0) {
                            if (mFindUserListenerCallBack != null) {
                                mFindUserListenerCallBack.onFindUser(findUserEntity.getContent());
                            }
                        } else {
                            ImToastUtil.showShortToast(context, findUserEntity.getMsg());
                        }
                    }
                });
    }

    public interface FindUserListenerCallBack {
        /**
         * 查询用户个信息
         *
         * @param contentBeanList
         */
        void onFindUser(List<FindUserEntity.ContentBean> contentBeanList);
    }

    public void setFindUserListenerCallBack(FindUserListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
