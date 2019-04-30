package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.CityEntity;
import com.yst.im.imchatlibrary.bean.RecentContactEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 最近联系人
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/16
 */

public class RecentContactsModel {
    private RecentContactsCallBack mRecentContactsCallBack;
    private Context context;

    public RecentContactsModel(Context context) {
        this.context = context;
    }

    /**
     * 获取最近联系人
     */
    public void getRecentContacts() {
        OkHttpUtils
                .post()
                .url(Constants.URL_RECENT_CONTACTS)
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
                        Log.e("xxx", "response: " + response);
                        Gson gson = new Gson();
                        RecentContactEntity recentContactEntity = gson.fromJson(response, RecentContactEntity.class);
                        if (mRecentContactsCallBack != null) {
                            mRecentContactsCallBack.onRecentContacts(recentContactEntity);
                        }
                    }
                });
    }

    public interface RecentContactsCallBack {
        /**
         * 最近联系人
         *
         * @param recentContactEntity
         */
        void onRecentContacts(RecentContactEntity recentContactEntity);
    }

    public void setRecentContactsCallBack(RecentContactsCallBack mRecentContactsCallBack) {
        this.mRecentContactsCallBack = mRecentContactsCallBack;
    }

}
