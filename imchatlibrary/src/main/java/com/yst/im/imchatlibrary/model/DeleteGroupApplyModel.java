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
 * 删除加群申请
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class DeleteGroupApplyModel {
    private DeleteGroupApplyListenerCallBack mDeleteGroupApplyListenerCallBack;
    private Context context;

    public DeleteGroupApplyModel(Context context) {
        this.context = context;
    }

    /**
     * 删除加群申请
     *
     * @param groupNameId 群id
     * @param userNameId  管理员id
     */
    public void getDeleteGroupApply(String groupNameId, String userNameId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DELETE_GROUP_APPLY)
                .addParams("groupNameId", groupNameId)
                .addParams("userNameId", userNameId)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("manageId", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "  删除加群申请: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mDeleteGroupApplyListenerCallBack != null) {
                            mDeleteGroupApplyListenerCallBack.onDeleteGroupApply(baseEntity);
                        }
                    }
                });
    }

    public interface DeleteGroupApplyListenerCallBack {
        /**
         * 删除加群申请
         */
        void onDeleteGroupApply(BaseEntity baseEntity);
    }

    public void setDeleteGroupApplyListenerCallBack(DeleteGroupApplyListenerCallBack mDeleteGroupApplyListenerCallBack) {
        this.mDeleteGroupApplyListenerCallBack = mDeleteGroupApplyListenerCallBack;
    }

}
