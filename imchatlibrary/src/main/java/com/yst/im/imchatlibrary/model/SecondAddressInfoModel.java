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
 * 获取二级地区列表
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SecondAddressInfoModel {
    private SecondAddressInfoListenerCallBack mSecondAddressInfoListenerCallBack;
    private Context context;

    public SecondAddressInfoModel(Context context) {
        this.context = context;
    }

    /**
     * 获取二级地区列表
     *
     * @param parentId 群id
     */
    public void getSecondAddressInfo(String parentId) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SECOND_ADDRESS_INFO)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("id", MyApp.manager.getId())
                .addParams("parentId", parentId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "onResponse移出: " + response);
                        Gson gson = new Gson();
                        CityEntity cityEntity = gson.fromJson(response, CityEntity.class);
                        if (cityEntity.getCode() == NUM_0) {
                            mSecondAddressInfoListenerCallBack.onSecondAddressInfo(cityEntity.getContent());
                        }else {
                            ImToastUtil.showShortToast(context,cityEntity.getMsg());
                        }
                    }
                });
    }

    public interface SecondAddressInfoListenerCallBack {
        /**
         * 获取二级地区列表
         *
         * @param contentBeans
         */
        void onSecondAddressInfo(List<CityEntity.ContentBean> contentBeans);
    }

    public void setSecondAddressInfoListenerCallBack(SecondAddressInfoListenerCallBack mSecondAddressInfoListenerCallBack) {
        this.mSecondAddressInfoListenerCallBack = mSecondAddressInfoListenerCallBack;
    }

}
