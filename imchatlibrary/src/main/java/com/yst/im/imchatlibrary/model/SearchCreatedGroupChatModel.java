package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.MessageConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 搜索我创建的群聊model
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/12.
 */
public class SearchCreatedGroupChatModel {
    private SearchCreatedGroupChatCallBack searchCreatedGroupChatCallBack;
    private Context context;

    public SearchCreatedGroupChatModel(Context context) {
        this.context = context;
    }

    /**
     * 我创建的群聊
     */
    public void searchCreateGroupChat(String searchData) {
        OkHttpUtils.post()
                .url(Constants.URL_SEARCHCREATEDGROUPCHAT)
                .addParams("searchData", searchData)
                .addParams("requestSourceSystem", MessageConstant.REQUEST_SS)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xcc", "sdk登录: " + response);
                        Gson gson = new Gson();
                        MyCreatGroupEntity baseEntity = gson.fromJson(response, MyCreatGroupEntity.class);
                        if (searchCreatedGroupChatCallBack != null) {
                            searchCreatedGroupChatCallBack.searchCreatedGroupChat(baseEntity);
                        }
                    }
                });
    }


    public interface SearchCreatedGroupChatCallBack {
        /**
         * 查询  我创建的群聊
         *
         * @param baseEntity
         */
        void searchCreatedGroupChat(MyCreatGroupEntity baseEntity);

    }

    public void setSearchCreatedGroupChatCallBack(SearchCreatedGroupChatCallBack searchCreatedGroupChatCallBack) {
        this.searchCreatedGroupChatCallBack = searchCreatedGroupChatCallBack;
    }
}
