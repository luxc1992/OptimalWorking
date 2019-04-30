package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.GroupDetialsStringEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 获取聊天室详情
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class FindGroupDetailModel {
    private FindGroupDetailListenerCallBack mFindGroupDetailListenerCallBack;
    private Context context;

    public FindGroupDetailModel(Context context) {
        this.context = context;
    }

    /**
     * 获取聊天室详情
     *
     * @param groupId 群 id
     */
    public void getFindGroupDetail(final String groupId){
        OkHttpUtils
                .post()
                .url(Constants.URL_GROUP_DETAIL)
                .addParams("requestSourceSystem", MessageConstant.REQUEST_SS)
                .addParams("groupId", groupId)
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
                        Gson gson = new Gson();
                        GroupDetialsStringEntity groupDetialsStringEntity = gson.fromJson(response, GroupDetialsStringEntity.class);
                        if (groupDetialsStringEntity.getCode() == NUM_0) {
                            Gson gson1 = new Gson();
                            GroupDetailsEntity groupDetailsEntity = gson1.fromJson(groupDetialsStringEntity.getContent(), GroupDetailsEntity.class);
                            mFindGroupDetailListenerCallBack.onFindGroupDetail(groupDetailsEntity);
                        }else {
                            ImToastUtil.showShortToast(context, groupDetialsStringEntity.getMsg());
                        }
                    }
                });
    }

    public interface FindGroupDetailListenerCallBack {
        /**
         * 获取聊天室详情
         *
         * @param groupDetailsEntity
         */
        void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity);
    }

    public void setFindGroupDetailListenerCallBack(FindGroupDetailListenerCallBack mFindGroupDetailListenerCallBack) {
        this.mFindGroupDetailListenerCallBack = mFindGroupDetailListenerCallBack;
    }

}
