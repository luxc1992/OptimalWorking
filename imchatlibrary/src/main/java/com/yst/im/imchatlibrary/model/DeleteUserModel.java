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

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 刪除好友
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/9
 */

public class DeleteUserModel {
    private DeleteUserModelCallBack mDeleteUserModelCallBack;
    private Context context;

    public DeleteUserModel(Context context) {
        this.context = context;
    }

    /**
     * 刪除好友
     *
     * @param delUserId 群id
     */
    public void deleteUser(String delUserId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DELETE_USER)
                .addParams("delUserId", delUserId)
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
                        Log.e("im", "  刪除好友車公共: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (baseEntity.getCode() == NUM_0) {
                            if (mDeleteUserModelCallBack != null) {
                                mDeleteUserModelCallBack.onDeleteUser(baseEntity);
                            }
                        }
                    }
                });
    }

    public interface DeleteUserModelCallBack {
        /**
         * 刪除好友
         *
         * @param baseEntity
         */
        void onDeleteUser(BaseEntity baseEntity);
    }

    public void setDeleteUserModelCallBack(DeleteUserModelCallBack mDeleteUserModelCallBack) {
        this.mDeleteUserModelCallBack = mDeleteUserModelCallBack;
    }

}
