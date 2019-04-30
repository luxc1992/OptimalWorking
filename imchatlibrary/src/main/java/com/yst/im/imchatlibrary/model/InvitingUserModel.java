package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 邀请用户加入聊天室
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/8
 */

public class InvitingUserModel {
    private InvitingListenerCallBack mInvitingListenerCallBack;
    private Context context;

    public InvitingUserModel(Context context) {
        this.context = context;
    }

    /**
     * 邀请用户加入聊天室
     *
     * @param groupId
     * @param manageId
     */
    public void getInvitingUser(String groupId,String manageId){
        OkHttpUtils.post()
                .url(Constants.URL_INVITE_USER)
                .addParams("id", MyApp.manager.getId())
                .addParams("groupId", groupId)
                .addParams("token", MyApp.manager.getToken())
                .addParams("manageId", manageId)
                .addParams("requestSourceSystem", MessageConstant.REQUEST_SS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("imlog", "邀请用户加入== " + response);
                        Gson gson=new Gson();
                        FriendListEntity friendListEntity = gson.fromJson(response, FriendListEntity.class);
                        if (friendListEntity.getCode() ==NUM_0) {
                            mInvitingListenerCallBack.onInvitingUser();
                        }
                    }
                });
    }

    public interface InvitingListenerCallBack {
        /**
         * 邀请用户加入回调
         */
        void onInvitingUser();
    }

    public void setInvitingListenerCallBack(InvitingListenerCallBack mInvitingListenerCallBack) {
        this.mInvitingListenerCallBack = mInvitingListenerCallBack;
    }

}
