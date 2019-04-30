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
 * 设置头像
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SetLogoModel {
    private SetLogoListenerCallBack mSetLogoListenerCallBack;
    private Context context;

    public SetLogoModel(Context context) {
        this.context = context;
    }

    /**
     * 设置头像
     *
     * @param logo 群id
     */
    public void getSetLogo(final String logo) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SET_LOGO)
                .addParams("logo", logo)
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
                        Log.e("xxx", "onResponse移出: " + response);
                        MyApp.manager.setUserIcon(logo);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mSetLogoListenerCallBack != null) {
                            mSetLogoListenerCallBack.onSetLogo(baseEntity);
                        }
                    }
                });
    }

    public interface SetLogoListenerCallBack {
        /**
         * 设置头像
         */
        void onSetLogo(BaseEntity baseEntity);
    }

    public void setSetLogoListenerCallBack(SetLogoListenerCallBack mSetLogoListenerCallBack) {
        this.mSetLogoListenerCallBack = mSetLogoListenerCallBack;
    }

}
