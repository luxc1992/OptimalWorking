package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 邀请入群列表
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class GroupInviteUserListModel {
    private GroupInviteUserListCallBack mGroupInviteUserListCallBack;
    private Context context;

    public GroupInviteUserListModel(Context context) {
        this.context = context;
    }

    /**
     * 群置顶
     *
     * @param keyWord 用户id
     * @param groupId 群 id
     */
    public void getGroupInviteUserList(String keyWord, String groupId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_INVITE_LIST)
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .addParams("groupId", groupId)
                .addParams("keyWord", keyWord)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "邀请入群列表== " + response);
                        Gson gson = new Gson();
                        FriendListEntity friendListEntity = gson.fromJson(response, FriendListEntity.class);
                        if (mGroupInviteUserListCallBack != null) {
                            mGroupInviteUserListCallBack.onGroupInviteUserList(friendListEntity);
                        }
                    }
                });
    }


    public interface GroupInviteUserListCallBack {
        /**
         * 邀请入群列表
         *
         * @param friendListEntity
         */
        void onGroupInviteUserList(FriendListEntity friendListEntity);
    }

    public void setGroupInviteUserListCallBack(GroupInviteUserListCallBack mGroupInviteUserListCallBack) {
        this.mGroupInviteUserListCallBack = mGroupInviteUserListCallBack;
    }

}
