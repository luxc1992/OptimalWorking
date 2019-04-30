package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.SelectFriendEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 *   查询好友详情
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/12.
 */
public class SelectFriendInfoModel {
    private SelectFriendInfoCallBack mSelectFriendInfoCallBack;
    private Context context;

    public SelectFriendInfoModel(Context context) {
        this.context = context;
    }

    /**
     *   查询好友详情
     */
    public void selectFriendInfo(String userId) {
        OkHttpUtils.post()
                .url(Constants.URL_SELECTFRIENDINFO)
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
                        Log.d("xcc", "查询好友详情: " + response);
                        Gson gson = new Gson();
                        SelectFriendEntity selectFriendEntity = gson.fromJson(response, SelectFriendEntity.class);
                        if (mSelectFriendInfoCallBack != null) {
                            mSelectFriendInfoCallBack.onSelectFriendInfo(selectFriendEntity);
                        }
                    }
                });
    }


    public interface SelectFriendInfoCallBack {
        /**
         *   查询好友详情
         *
         * @param selectFriendEntity
         */
        void onSelectFriendInfo(SelectFriendEntity selectFriendEntity);

    }

    public void setSelectFriendInfoCallBack(SelectFriendInfoCallBack mSelectFriendInfoCallBack) {
        this.mSelectFriendInfoCallBack = mSelectFriendInfoCallBack;
    }
}
