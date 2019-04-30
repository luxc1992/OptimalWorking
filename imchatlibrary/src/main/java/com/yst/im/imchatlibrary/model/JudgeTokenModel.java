package com.yst.im.imchatlibrary.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;

/**
 * 判断token是否过期  0 不过期
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/9
 */

public class JudgeTokenModel {
    private JudgeTokenCallBack mJudgeTokenCallBack;
    private Context context;

    public JudgeTokenModel(Context context) {
        this.context = context;
    }

    /**
     * 判断token是否过期  0 不过期
     */
    public void getJudgeToken() {
        OkHttpUtils
                .post()
                .url(Constants.URL_JUDGE_TOKEN)
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
                        ImLog.e("imlog", "  判断token是否过期== " + response);
                        Gson gson = new Gson();
                        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                        if (mJudgeTokenCallBack != null) {
                            mJudgeTokenCallBack.onJudgeToken(baseEntity);
                        }
                    }
                });
    }

    public interface JudgeTokenCallBack {
        /**
         * 判断token是否过期  0 不过期
         *
         * @param baseEntity
         */
        void onJudgeToken(BaseEntity baseEntity);
    }

    public void setJudgeTokenCallBack(JudgeTokenCallBack mJudgeTokenCallBack) {
        this.mJudgeTokenCallBack = mJudgeTokenCallBack;
    }

}
