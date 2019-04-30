package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 转发接口
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class ForwardingModel {
    private ForwardingListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public ForwardingModel(Context context) {
        this.context = context;
    }

    /**
     * 转发接口
     *
     * @param messageInfo 管理员id
     */
    private void getForwarding(String messageInfo) {
        OkHttpUtils
                .post()
                .url(Constants.URL_FORWARDING_MESSAGE)
                .addParams("messageInfo", messageInfo)
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
                        Log.e("xxx", "  转发接口: " + response);
                        mFindUserListenerCallBack.onFindUser();
                    }
                });
    }

    public interface ForwardingListenerCallBack {
        /**
         * 转发接口
         */
        void onFindUser();
    }

    public void setFindUserListenerCallBack(ForwardingListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
