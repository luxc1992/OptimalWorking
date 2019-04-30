package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

/**
 * 获取最近联系人
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */
public class RecentContactModel {
    private RecentContactListenerCallBack mRecentContactListenerCallBack;
    private Context context;

    public RecentContactModel(Context context) {
        this.context = context;
    }

    /**
     * 获取最近联系人
     */
    public void getRecentContact() throws IOException {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_GROUP_NAME)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "  群详情== " + response);
                        mRecentContactListenerCallBack.onRecentContact();
                    }
                });
    }

    public interface RecentContactListenerCallBack {
        /**
         * 获取最近联系人
         */
        void onRecentContact();
    }

    public void setRecentContactListenerCallBack(RecentContactListenerCallBack mRecentContactListenerCallBack) {
        this.mRecentContactListenerCallBack = mRecentContactListenerCallBack;
    }

}
