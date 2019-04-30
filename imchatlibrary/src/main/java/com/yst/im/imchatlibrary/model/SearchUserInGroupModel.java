package com.yst.im.imchatlibrary.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.SearchGroupMemberEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 在群中搜索成员（可根据备注和昵称）
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class SearchUserInGroupModel {
    private SearchUserInGroupListenerCallBack mSearchUserInGroupListenerCallBack;
    private Context context;

    public SearchUserInGroupModel(Context context) {
        this.context = context;
    }

    /**
     * 在群中搜索成员（可根据备注和昵称）
     *
     * @param searchData
     */
    public void getSearchUserInGroup(String groupId, String searchData) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SEARCH_USER_IN_GROUP)
                .addParams("groupId", groupId)
                .addParams("searchData", searchData)
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
                        Log.e("im", "  在群中搜索成员: " + response);
                        Gson gson = new Gson();
                        SearchGroupMemberEntity mSearchGroupMemberEntity = gson.fromJson(response, SearchGroupMemberEntity.class);
                        if (mSearchUserInGroupListenerCallBack != null) {
                            mSearchUserInGroupListenerCallBack.onSearchUserInGroup(mSearchGroupMemberEntity);
                        }
                    }
                });
    }

    public interface SearchUserInGroupListenerCallBack {
        /**
         * 在群中搜索成员（可根据备注和昵称）
         *
         * @param mSearchGroupMemberEntity 昵称
         */
        void onSearchUserInGroup(SearchGroupMemberEntity mSearchGroupMemberEntity);
    }

    public void setSearchUserInGroupListenerCallBack(SearchUserInGroupListenerCallBack mSearchUserInGroupListenerCallBack) {
        this.mSearchUserInGroupListenerCallBack = mSearchUserInGroupListenerCallBack;
    }

}
