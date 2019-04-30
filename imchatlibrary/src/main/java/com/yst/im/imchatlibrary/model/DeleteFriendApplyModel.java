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
 *  删除好友申请
 * 
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class DeleteFriendApplyModel {
    private DeleteFriendApplyListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public DeleteFriendApplyModel(Context context) {
        this.context = context;
    }

    /**
     *       删除好友申请
     *
     * @param friendNameId 管理员id
     */
    public void getDeleteFriendApply(String friendNameId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DELETE_FRIEND_APPLY)
                .addParams("userNameId", MyApp.manager.getId())
                .addParams("friendNameId", friendNameId)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "        删除好友申请: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mFindUserListenerCallBack != null) {
                            mFindUserListenerCallBack.onDeleteUser(baseEntity);
                        }
                    }
                });
    }

    public interface DeleteFriendApplyListenerCallBack {
        /**
         *       删除好友申请
         */
        void onDeleteUser(BaseEntity baseEntity);
    }

    public void setFindUserListenerCallBack(DeleteFriendApplyListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
