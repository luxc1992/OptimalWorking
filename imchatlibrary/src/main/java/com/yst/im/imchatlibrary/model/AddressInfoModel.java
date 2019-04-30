package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.CityEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 获取一级地区
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class AddressInfoModel {
    private AddressInfoListenerCallBack mAddressInfoListenerCallBack;
    private Context context;

    public AddressInfoModel(Context context) {
        this.context = context;
    }

    /**
     * 获取一级地区
     */
    public void getAddressInfo() {
        OkHttpUtils
                .post()
                .url(Constants.URL_ADDRESS_INFO)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "获取一级地区: " + response);
                        Gson gson = new Gson();
                        CityEntity cityEntity = gson.fromJson(response, CityEntity.class);
                        if (cityEntity.getCode() == NUM_0) {
                            mAddressInfoListenerCallBack.onAddressInfo(cityEntity.getContent());
                        }
                    }
                });
    }

    public interface AddressInfoListenerCallBack {
        /**
         * 获取一级地区
         *
         * @param content
         */
        void onAddressInfo(List<CityEntity.ContentBean> content);
    }

    public void setAddressInfoListenerCallBack(AddressInfoListenerCallBack mAddressInfoListenerCallBack) {
        this.mAddressInfoListenerCallBack = mAddressInfoListenerCallBack;
    }

}
