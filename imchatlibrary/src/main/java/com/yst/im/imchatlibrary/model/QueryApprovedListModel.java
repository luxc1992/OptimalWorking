package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.ApplyGroupEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 查询待审批人员列表回调
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class QueryApprovedListModel {
    private QueryApprovedListListenerCallBack mQueryApprovedListListenerCallBack;
    private Context context;

    public QueryApprovedListModel(Context context) {
        this.context = context;
    }

    /**
     * 查询待审批人员
     *
     */
    public void getQueryApprovedList(){
        OkHttpUtils
                .post()
                .url(Constants.URL_GROUP_NEW_QUERYUSER)
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
                        ImLog.e("imlog", "查询待审批人员== " + response);
                        Gson gson = new Gson();
                        ApplyGroupEntity applyGroupEntity = gson.fromJson(response, ApplyGroupEntity.class);
                        mQueryApprovedListListenerCallBack.onQueryApprovedList(applyGroupEntity);
                    }
                });
    }


    public interface QueryApprovedListListenerCallBack {
        /**
         * 查询待审批人员列表回调
         */
        void onQueryApprovedList(ApplyGroupEntity applyGroupEntity);
    }

    public void setQueryApprovedListListenerCallBack(QueryApprovedListListenerCallBack mQueryApprovedListListenerCallBack) {
        this.mQueryApprovedListListenerCallBack = mQueryApprovedListListenerCallBack;
    }

}
