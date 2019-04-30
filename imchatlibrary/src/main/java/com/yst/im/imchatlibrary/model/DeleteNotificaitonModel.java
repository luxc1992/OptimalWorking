package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 删除系统通知
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class DeleteNotificaitonModel {
    private DeleteNotificaitonCallBack mDeleteNotificaitonCallBack;
    private Context context;

    public DeleteNotificaitonModel(Context context) {
        this.context = context;
    }

    /**
     * 删除系统通知
     */
    public void getDeleteNotificaiton(final int position) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DELETE_NOTIFICATION)
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
                        Log.e("xxx", "删除系统通知: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mDeleteNotificaitonCallBack != null) {
                            mDeleteNotificaitonCallBack.onDeleteNotificaiton(baseEntity, position);
                        }
                    }
                });
    }

    public interface DeleteNotificaitonCallBack {
        /**
         * 删除系统通知
         *
         * @param baseEntity
         * @param position
         */
        void onDeleteNotificaiton(BaseEntity baseEntity, int position);
    }

    public void setDeleteNotificaitonCallBack(DeleteNotificaitonCallBack mDeleteNotificaitonCallBack) {
        this.mDeleteNotificaitonCallBack = mDeleteNotificaitonCallBack;
    }

}
