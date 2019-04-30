package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.RecentContactEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 系统通知列表
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SystematicNotificationModel {
    private systematicNotificationListenerCallBack msystematicNotificationListenerCallBack;
    private Context context;

    public SystematicNotificationModel(Context context) {
        this.context = context;
    }

    /**
     * 系统通知列表
     */
    public void systematicNotification() {
        OkHttpUtils
                .post()
                .url(Constants.URL_SYSTEMATIC_NOTIFICATION)
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
                        Log.e("im", "  系统通知列表: " + response);
                        Gson gson = new Gson();
                        RecentContactEntity recentContactEntity = gson.fromJson(response, RecentContactEntity.class);
                        if (msystematicNotificationListenerCallBack != null) {
                            msystematicNotificationListenerCallBack.onsystematicNotification(recentContactEntity);
                        }
                    }
                });
    }

    public interface systematicNotificationListenerCallBack {
        /**
         * 系统通知列表
         *
         * @param recentContactEntity
         */
        void onsystematicNotification(RecentContactEntity recentContactEntity);
    }

    public void setsystematicNotificationListenerCallBack(systematicNotificationListenerCallBack msystematicNotificationListenerCallBack) {
        this.msystematicNotificationListenerCallBack = msystematicNotificationListenerCallBack;
    }

}
