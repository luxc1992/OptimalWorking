package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 找群
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class QueryGroupChatModel {
    private QueryGroupChatListenerCallBack mQueryGroupChatListenerCallBack;
    private Context context;

    public QueryGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     * 找群
     */
    public void getQueryGroupChat(String searchData) {
        OkHttpUtils
                .post()
                .url(Constants.URL_FIND_GROUP_BY_DIM)
                .addParams("requestSourceSystem", REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("searchData", searchData)
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("xxx", "    找群: " + response);
                        Gson gson = new Gson();
                        MyCreatGroupEntity baseEntity = gson.fromJson(response, MyCreatGroupEntity.class);
                        if (mQueryGroupChatListenerCallBack != null) {
                            mQueryGroupChatListenerCallBack.onQueryGroupChat(baseEntity);
                        }
                    }
                });
    }

    public interface QueryGroupChatListenerCallBack {
        /**
         * 找群
         */
        void onQueryGroupChat(MyCreatGroupEntity baseEntity);
    }

    public void setQueryGroupChatListenerCallBack(QueryGroupChatListenerCallBack mQueryGroupChatListenerCallBack) {
        this.mQueryGroupChatListenerCallBack = mQueryGroupChatListenerCallBack;
    }

}
