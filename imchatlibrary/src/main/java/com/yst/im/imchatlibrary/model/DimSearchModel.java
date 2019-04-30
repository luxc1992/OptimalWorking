package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.SearchEntity;
import com.yst.im.imchatlibrary.bean.SearchResultEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 搜索群聊室和用户
 *
 * @author Lierpeng
 * @date 2018/4/9
 * @version 1.0.0
 */

public class DimSearchModel {
    private DimSearchListenerCallBack mDimSearchListenerCallBack;
    private Context context;

    public DimSearchModel(Context context) {
        this.context = context;
    }

    /**
     * 搜索群聊室和用户
     *
     * @param searchData
     * @param figure 群id 1 群 2 用户
     */
    public void getDimSearch(String searchData,String figure) {
        OkHttpUtils
                .post()
                .url(Constants.URL_DIM_SEARCH)
                .addParams("searchData", searchData)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .addParams("figure", figure)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "  搜索群聊室和用户: " + response);
                        Gson gson = new Gson();
                        SearchEntity searchEntity = gson.fromJson(response, SearchEntity.class);
                        if (mDimSearchListenerCallBack != null) {
                            mDimSearchListenerCallBack.onDimSearch(searchEntity);
                        }
                    }
                });
    }

    public interface DimSearchListenerCallBack {
        /**
         * 搜索群聊室和用户
         *
         * @param searchEntity
         */
        void onDimSearch(SearchEntity searchEntity);
    }

    public void setDimSearchListenerCallBack(DimSearchListenerCallBack mDimSearchListenerCallBack) {
        this.mDimSearchListenerCallBack = mDimSearchListenerCallBack;
    }

}
