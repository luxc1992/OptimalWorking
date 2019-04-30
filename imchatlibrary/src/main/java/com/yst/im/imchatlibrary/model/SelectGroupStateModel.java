package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.SelectGroupStateEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.GROUP_DELETE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 查询群聊室状态
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SelectGroupStateModel {
    private SelectGroupStateListenerCallBack mFindUserListenerCallBack;
    private Context context;

    public SelectGroupStateModel(Context context) {
        this.context = context;
    }

    /**
     * 查询群聊室状态
     *
     * @param groupId 管理员id
     */
    public void getSelectGroupState(final String groupId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_GROUP_SHIELD_STATE)
                .addParams("groupId", groupId)
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
                        Log.e("xxx", "      查询群聊室状态: " + response);
                        Gson gson = new Gson();
                        SelectGroupStateEntity selectGroupStateEntity = gson.fromJson(response, SelectGroupStateEntity.class);
                        if (selectGroupStateEntity.getCode() == NUM_0) {
                            mFindUserListenerCallBack.onSelectGroupState(selectGroupStateEntity.getContent());
                        } else {
                            ImToastUtil.showShortToast(context, selectGroupStateEntity.getMsg());
                        }
                    }
                });
    }

    public interface SelectGroupStateListenerCallBack {
        /**
         * 查询群聊室状态
         *
         * @param selectGroupStateEntity
         */
        void onSelectGroupState(SelectGroupStateEntity.ContentBean selectGroupStateEntity);
    }

    public void setSelectGroupStateListenerCallBack(SelectGroupStateListenerCallBack mFindUserListenerCallBack) {
        this.mFindUserListenerCallBack = mFindUserListenerCallBack;
    }

}
