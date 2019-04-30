package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.GroupOwnerEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 获取群主信息及群公告
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class GroupOwnerModel {
    private GroupOwnerCallBack mGroupOwnerCallBack;
    private Context context;

    public GroupOwnerModel(Context context) {
        this.context = context;
    }

    /**
     * 获取群主信息及群公告
     *
     * @param groupId 群 id
     */
    public void getGroupOwner(String groupId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_GROUP_CHAT_OWNER)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
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
                        ImLog.e("imlog", "获取群主信息及群公告== " + response);
                        Gson gson = new Gson();
                        GroupOwnerEntity groupOwnerEntity = gson.fromJson(response, GroupOwnerEntity.class);
                        if (groupOwnerEntity.getCode() == NUM_0) {
                            mGroupOwnerCallBack.onGroupOwner(groupOwnerEntity.getContent());
                        }else {
                            ImToastUtil.showShortToast(context, groupOwnerEntity.getMsg());
                        }
                    }
                });
    }


    public interface GroupOwnerCallBack {
        /**
         * 获取群主信息及群公告
         *
         * @param contentBean
         */
        void onGroupOwner(GroupOwnerEntity.ContentBean contentBean);
    }

    public void setGroupOwnerCallBack(GroupOwnerCallBack mGroupOwnerCallBack) {
        this.mGroupOwnerCallBack = mGroupOwnerCallBack;
    }

}
