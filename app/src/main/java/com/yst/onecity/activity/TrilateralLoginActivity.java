package com.yst.onecity.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.LoginEntity;
import com.yst.im.imchatlibrary.model.LoginModel;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.accountsafe.BindPhoneNumActivity;
import com.yst.onecity.bean.SanFangLoginBean;
import com.yst.onecity.bean.SanFangStateBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 第三方登录页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/26
 */

public class TrilateralLoginActivity extends BaseActivity implements LoginModel.LoginListenerCallBack {

    private String openid;
    private String name;
    private String gender;
    private String imgUrl;
    private int type;

    /**
     * 标签用户
     */
    private static Set<String> setUserTags() {
        Set<String> tags = new HashSet<>();
        tags.add("0x123");
        tags.add("0x124");
        tags.add("0x125");
        return tags;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trilateral_login;
    }

    @Override
    public void initData() {
        initTitleBar("第三方登录");
        openid = getIntent().getStringExtra("openid");
        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        imgUrl = getIntent().getStringExtra("imgUrl");
        type = getIntent().getIntExtra("type", 0);
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        RequestApi.getIsBindPhone(openid, type, new AbstractNetWorkCallback<SanFangStateBean>() {
            @Override
            public void onSuccess(SanFangStateBean sanFangStateBean) {
                //（绑定状态 ：0 未绑定 1 QQ 2 微信 3 QQ和微信）
                if (sanFangStateBean.getCode() == 1) {
                    if (sanFangStateBean.getContent().getBindStatus().equals(String.valueOf(NO0))) {
                        //未绑定
                        ToastUtils.show("您还未绑定手机号，请先绑定手机");
                        Bundle bundle = new Bundle();
                        if (type == NO1) {
                            bundle.putString("type", String.valueOf(NO1));
                        } else {
                            bundle.putString("type", String.valueOf(NO2));
                        }
                        bundle.putString("name", name);
                        bundle.putString("openid", openid);
                        bundle.putString("imgUrl", imgUrl);
                        JumpIntent.jump(TrilateralLoginActivity.this, BindPhoneNumActivity.class, bundle);
                    } else {
                        //已绑定
                        RequestApi.qqOrWxLogin(String.valueOf(type),
                                sanFangStateBean.getContent().getPhone(),
                                imgUrl, openid, name,
                                sanFangStateBean.getContent().getBindStatus(),
                                "", new AbstractNetWorkCallback<SanFangLoginBean>() {

                                    @Override
                                    public void onSuccess(SanFangLoginBean sanFangLoginBean) {
                                        MyLog.e("sss", "三方登录返回数据===" + sanFangLoginBean);
                                        if (sanFangLoginBean.getCode() == NO1) {
                                            if (!TextUtils.isEmpty(String.valueOf(sanFangLoginBean.getId()))) {
                                                App.manager.setId(sanFangLoginBean.getId());
                                            }
                                            if (!TextUtils.isEmpty(sanFangLoginBean.getPhone())) {
                                                App.manager.setPhoneNum(sanFangLoginBean.getPhone());
                                            }
                                            if (!TextUtils.isEmpty(sanFangLoginBean.getUuid())) {
                                                App.manager.setUuid(sanFangLoginBean.getUuid());
                                            }
                                            if (!TextUtils.isEmpty(sanFangLoginBean.getToken())) {
                                                App.manager.setToken(sanFangLoginBean.getToken());
                                            }

                                            if (type == NO1) {
                                                if (!TextUtils.isEmpty(sanFangLoginBean.getQqOppendName())) {
                                                    App.manager.setNickname(sanFangLoginBean.getQqOppendName());
                                                }
                                                if (!TextUtils.isEmpty(sanFangLoginBean.getQqOppendId())) {
                                                    App.manager.setQqOppendId(sanFangLoginBean.getQqOppendId());
                                                }

                                            } else {
                                                if (!TextUtils.isEmpty(sanFangLoginBean.getWxNickName())) {
                                                    App.manager.setNickname(sanFangLoginBean.getWxNickName());
                                                }
                                                if (!TextUtils.isEmpty(sanFangLoginBean.getWxOppendId())) {
                                                    App.manager.setWxOppendId(sanFangLoginBean.getWxOppendId());
                                                }
                                            }
                                            ToastUtils.show(sanFangLoginBean.getMsg());
                                            App.manager.setLoginState(true);
                                            App.isLogin = App.manager.getLoginState();
                                            LoginModel loginModel = new LoginModel(TrilateralLoginActivity.this);
                                            loginModel.setLoginListenerCallBack(TrilateralLoginActivity.this);
                                            String phone = "root";
                                            String pwd = "123456";
                                            try {
                                                loginModel.sdkLogin(phone, pwd);
                                                loginModel.login(App.manager.getPhoneNum(), pwd);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            if (JPushInterface.isPushStopped(TrilateralLoginActivity.this)) {
                                                //恢复极光推送
                                                JPushInterface.resumePush(TrilateralLoginActivity.this);
                                            }
                                            MyLog.i("JPush", "isPushStopped:" + JPushInterface.isPushStopped(TrilateralLoginActivity.this));
                                            // 调用 JPush 接口来设置别名。
                                            Set<String> tags = new HashSet<String>();
                                            JPushInterface.setAlias(getApplicationContext(), 2, sanFangLoginBean.getPhone() + "_pjyc");
                                            JPushInterface.getAlias(getApplicationContext(), 2);
                                            JPushInterface.setTags(getApplicationContext(), 2, setUserTags());
                                            finish();

                                        } else {
                                            ToastUtils.show(sanFangLoginBean.getMsg());
                                        }
                                    }

                                    @Override
                                    public void onError(String errorMsg) {
                                        ToastUtils.show(errorMsg);
                                    }
                                });
                    }

                } else {
                    ToastUtils.show(sanFangStateBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }

    @Override
    public void onUserLogin(LoginEntity loginEntity) {
        MyLog.e("sss", "三方登录===" + loginEntity);
        if (loginEntity.getCode() == 0) {
            App.getInstance().exitActivity();
            ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
                @Override
                public void run() {
                    MyApp.getMsClient().init(Constants.HOST, Constants.PORT, String.valueOf(MyApp.manager.getId()), REQUEST_SS, MyApp.manager.getToken());
                }
            }).start();
        } else {
            App.manager.setLoginState(false);
            App.manager.quitLogin();
        }
    }
}
