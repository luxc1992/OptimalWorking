package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

/**
 * 群置顶回调
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class GroupStickModel {
    private GroupStickListenerCallBack mGroupStickListenerCallBack;
    private Context context;

    public GroupStickModel(Context context) {
        this.context = context;
    }

    /**
     * 群置顶
     *
     * @param state 用户id
     * @param groupId 群 id
     */
    public void getGroupStick(String state, String groupId) throws IOException {
        OkHttpUtils
                .post()
                .url(Constants.URL_GROUP_STICK)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("state", state)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "群置顶== " + response);
                        mGroupStickListenerCallBack.onJGroupStick();
                    }
                });
    }


    public interface GroupStickListenerCallBack {
        /**
         * 群置顶回调
         */
        void onJGroupStick();
    }

    public void setGroupStickListenerCallBack(GroupStickListenerCallBack mGroupStickListenerCallBack) {
        this.mGroupStickListenerCallBack = mGroupStickListenerCallBack;
    }

}
