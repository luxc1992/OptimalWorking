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
 * 修改好友备注
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class UpdateFriendRemarkModel {
    private UpdateFriendRemarkListenerCallBack mUpdateFriendRemarkListenerCallBack;
    private Context context;

    public UpdateFriendRemarkModel(Context context) {
        this.context = context;
    }

    /**
     * 修改好友备注
     *
     * @param friendId
     * @param nickName
     */
    public void getUpdateFriendRemark(String friendId, String nickName) {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_FRIEND_REMARK)
                .addParams("friendId", friendId)
                .addParams("nickName", nickName)
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
                        Log.e("xxx", "  修改好友备注: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mUpdateFriendRemarkListenerCallBack != null) {
                            mUpdateFriendRemarkListenerCallBack.onUpdateFriendRemark(baseEntity);
                        }
                    }
                });
    }

    public interface UpdateFriendRemarkListenerCallBack {
        /**
         * 修改好友备注
         */
        void onUpdateFriendRemark(BaseEntity baseEntity);
    }

    public void setUpdateFriendRemarkListenerCallBack(UpdateFriendRemarkListenerCallBack mUpdateFriendRemarkListenerCallBack) {
        this.mUpdateFriendRemarkListenerCallBack = mUpdateFriendRemarkListenerCallBack;
    }

}
