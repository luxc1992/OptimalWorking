package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 修改地址
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SetAddressModel {
    private SetAddressListenerCallBack mSetAddressListenerCallBack;
    private Context context;

    public SetAddressModel(Context context) {
        this.context = context;
    }

    /**
     * 修改地址
     *
     * @param address 群id
     */
    public void getSetAddress(String address) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SET_ADDRESS)
                .addParams("address", address)
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
                        Log.e("xxx", "  修改地址: " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (baseEntity.getCode() == NUM_0) {
                            mSetAddressListenerCallBack.onSetAddress(baseEntity);
                            ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
                        }
                    }
                });
    }

    public interface SetAddressListenerCallBack {
        /**
         * 修改地址
         *
         * @param baseEntity
         */
        void onSetAddress(BaseEntity baseEntity);
    }

    public void setSetAddressListenerCallBack(SetAddressListenerCallBack mSetAddressListenerCallBack) {
        this.mSetAddressListenerCallBack = mSetAddressListenerCallBack;
    }

}
