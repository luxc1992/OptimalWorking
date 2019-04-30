package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 修改群备注
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class UpdateGroupRemarkModel {
    private UpdateGroupRemarkListenerCallBack mUpdateGroupRemarkListenerCallBack;
    private Context context;

    public UpdateGroupRemarkModel(Context context) {
        this.context = context;
    }

    /**
     * 修改群备注
     *
     * @param groupId 群id
     * @param remark  管理员id
     */
    public void getUpdateGroupRemark(String groupId, String remark) {
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_GROUP_REMARK)
                .addParams("groupId", groupId)
                .addParams("remark", remark)
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
                        Log.e("xxx", "  修改群备注: " + response);
                        mUpdateGroupRemarkListenerCallBack.onUpdateGroupRemark();
                    }
                });
    }

    public interface UpdateGroupRemarkListenerCallBack {
        /**
         * 修改群备注
         */
        void onUpdateGroupRemark();
    }

    public void setUpdateGroupRemarkListenerCallBack(UpdateGroupRemarkListenerCallBack mUpdateGroupRemarkListenerCallBack) {
        this.mUpdateGroupRemarkListenerCallBack = mUpdateGroupRemarkListenerCallBack;
    }

}
