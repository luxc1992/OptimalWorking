package com.yst.onecity.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.UserBean;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.LoginEntity;
import com.yst.im.imchatlibrary.model.LoginModel;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.ForgetPasWordActivity;
import com.yst.onecity.activity.TrilateralLoginActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.VerifyUtil;
import com.yst.onecity.view.ContainsEmojiEditText;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO6;

/**
 * 登录页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/23
 */

public class LoginActivity extends BaseActivity implements View.OnTouchListener, LoginModel.LoginListenerCallBack {
    @BindView(R.id.change_login)
    TextView changeLogin;
    @BindView(R.id.forget_psw)
    TextView forgetPsw;
    @BindView(R.id.et_user_card)
    ContainsEmojiEditText etUserCard;
    @BindView(R.id.et_user_psw)
    ContainsEmojiEditText etUserPsw;
    @BindView(R.id.et_user_message_code)
    ContainsEmojiEditText etUserMessageCode;
    @BindView(R.id.code_relative)
    RelativeLayout codeRelative;
    boolean flag = false;
    @BindView(R.id.gain_code)
    TextView gainCode;
    @BindView(R.id.eyes_linear)
    LinearLayout eyesLinear;
    @BindView(R.id.psw_state)
    ImageView pswState;
    @BindView(R.id.psw_relative)
    RelativeLayout pswRelative;
    private boolean pState = false;
    private int loginState = 0;
    private String phone;
    private String pwd;
    private int loginType;
    private int imLoginType;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;
    /**
     * 三方登录接口回调
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA shareMedia, int i, Map<String, String> map) {
            Bundle bundle = new Bundle();
            bundle.putString("openid", map.get("openid"));
            bundle.putString("name", map.get("screen_name"));
            bundle.putString("gender", map.get("gender"));
            bundle.putString("imgUrl", map.get("profile_image_url"));
            bundle.putInt("type", loginType);
            JumpIntent.jump(LoginActivity.this, TrilateralLoginActivity.class, bundle);
        }

        @Override
        public void onError(SHARE_MEDIA shareMedia, int i, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA shareMedia, int i) {
        }
    };

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
        ImmersionBar.with(this).reset().init();
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            String loginType="loginType";
            if (getIntent().hasExtra(loginType)) {
                imLoginType = getIntent().getIntExtra(loginType, 0);
            }
        }

        eyesLinear.setOnTouchListener(this);
        CommonUtils.setEditTextInputLength(etUserCard, 11, true);
        CommonUtils.setEditTextInputLength(etUserPsw, 20, true);
        CommonUtils.setEditTextInputLength(etUserMessageCode, 6, true);


    }

    @OnClick({R.id.change_login, R.id.forget_psw, R.id.weixin_login, R.id.qq_login, R.id.jump_register, R.id.gain_code, R.id.close, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_login:
                flag = !flag;
                if (flag) {
                    loginState = 1;
                    changeLogin.setText(R.string.login_number_login);
                    forgetPsw.setVisibility(View.GONE);
                    codeRelative.setVisibility(View.VISIBLE);
                    pswRelative.setVisibility(View.GONE);
                } else {
                    loginState = 0;
                    pswRelative.setVisibility(View.VISIBLE);
                    changeLogin.setText(R.string.login_exempt);
                    forgetPsw.setVisibility(View.VISIBLE);
                    codeRelative.setVisibility(View.GONE);
                }
                break;
            case R.id.forget_psw:
                JumpIntent.jump(this, ForgetPasWordActivity.class);
                break;
            case R.id.weixin_login:
                String wx = "com.tencent.mm";
                if (!Utils.isAvilible(this, wx)) {
                    ToastUtils.show("未安装微信");
                    return;
                }
                loginType = 2;
                UMShareAPI.get(App.getInstance()).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.qq_login:
                String qq = "com.tencent.mobileqq";
                if (!Utils.isAvilible(this, qq)) {
                    ToastUtils.show("未安装QQ");
                    return;
                }
                loginType = 1;
                UMShareAPI.get(App.getInstance()).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.jump_register:
                JumpIntent.jump(this, RegisterActivity.class);
                finish();
                break;
            case R.id.gain_code:
                if (!Utils.isClickable()) {
                    return;
                }
                if (TextUtils.isEmpty(etUserCard.getText().toString())) {
                    ToastUtils.show("请输入手机号码");
                    return;
                }
                if (!VerifyUtil.isMobileNO(etUserCard.getText().toString().trim())) {
                    ToastUtils.show("请输入正确的手机号码");
                    return;
                }
                RequestApi.getVerifyCode(etUserCard.getText().toString().trim(), "1", new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == 1) {
                            Utils.startCountDown(gainCode, R.color.color_ED5452, R.color.color_999999, 60000, "获取验证码");
                        }
                        ToastUtils.show(msgBean.getMsg());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                    }
                });


                break;
            case R.id.close:
                finish();
                break;
            case R.id.tv_login:
                if (loginState == NO0) {
                    passwordLogin();
                } else {
                    verifyLogin();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 验证码登录
     */
    private void verifyLogin() {
        if (TextUtils.isEmpty(etUserCard.getText().toString())) {
            ToastUtils.show("请输入手机号码");
            return;
        }
        if (!VerifyUtil.isMobileNO(etUserCard.getText().toString().trim())) {
            ToastUtils.show("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(etUserMessageCode.getText().toString())) {
            ToastUtils.show("请输入验证码");
            return;
        }
        if (etUserMessageCode.getText().toString().length() < NO6) {
            ToastUtils.show("请输入6位数的验证码");
            return;
        }
        RequestApi.verifyLogin(etUserCard.getText().toString().trim(), "2", etUserMessageCode.getText().toString()
                , new AbstractNetWorkCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        MyLog.e("userBean", "验证码登录：" + userBean);
                        if (userBean.getCode() == 1) {
                            App.manager.saveUserInfo(userBean);
                            App.manager.setLoginState(true);
                            App.isLogin = App.manager.getLoginState();

                            LoginModel loginModel = new LoginModel(LoginActivity.this);
                            loginModel.setLoginListenerCallBack(LoginActivity.this);
                            String phone = "root";
                            String pwd = "123456";
                            try {
                                loginModel.sdkLogin(phone, pwd);
                                loginModel.login(App.manager.getPhoneNum(), pwd);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (JPushInterface.isPushStopped(LoginActivity.this)) {
                                //恢复极光推送
                                JPushInterface.resumePush(LoginActivity.this);
                            }
                            MyLog.i("JPush", "isPushStopped:" + JPushInterface.isPushStopped(LoginActivity.this));
                            // 调用 JPush 接口来设置别名。
                            Set<String> tags = new HashSet<String>();
                            JPushInterface.setAlias(getApplicationContext(), 2, userBean.getPhone() + "_pjyc");
                            JPushInterface.getAlias(getApplicationContext(), 2);
                            JPushInterface.setTags(getApplicationContext(), 2, setUserTags());

                        } else {
                            ToastUtils.show(userBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                    }
                });
    }

    /**
     * 密码登录
     */
    private void passwordLogin() {

        if (!VerifyUtil.isMobileNO(etUserCard.getText().toString().trim())) {
            ToastUtils.show("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(etUserPsw.getText().toString())) {
            ToastUtils.show("请输入密码");
            return;
        }
        RequestApi.passWordLogin(
                etUserCard.getText().toString(),
                etUserPsw.getText().toString(),
                "1",
                new AbstractNetWorkCallback<UserBean>() {

                    @Override
                    public void onSuccess(UserBean userBean) {
                        if (userBean.getCode() == 1) {
                            App.isLogin = true;
                            App.manager.setLoginState(true);
                            App.manager.saveUserInfo(userBean);

                            LoginModel loginModel = new LoginModel(LoginActivity.this);
                            loginModel.setLoginListenerCallBack(LoginActivity.this);
                            String phone = "root";
                            String pwd = "123456";
                            try {
                                loginModel.sdkLogin(phone, pwd);
                                loginModel.login(App.manager.getPhoneNum(), pwd);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (JPushInterface.isPushStopped(LoginActivity.this)) {
                                //恢复极光推送
                                JPushInterface.resumePush(LoginActivity.this);
                            }
                            MyLog.i("JPush", "isPushStopped:" + JPushInterface.isPushStopped(LoginActivity.this));
                            // 调用 JPush 接口来设置别名。
                            Set<String> tags = new HashSet<String>();
                            JPushInterface.setAlias(getApplicationContext(), 2, userBean.getPhone() + "_pjyc");
                            JPushInterface.getAlias(getApplicationContext(), 2);
                            JPushInterface.setTags(getApplicationContext(), 2, setUserTags());

                        } else {
                            ToastUtils.show(userBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        pState = !pState;
        if (pState) {
            pswState.setImageResource(R.mipmap.ic_eye_look);
            etUserPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            pswState.setImageResource(R.mipmap.ic_eye_psw);
            etUserPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return false;
    }

    @Override
    public void onUserLogin(final LoginEntity loginEntity) {
        MyLog.e("sss", "loginEntity===" + loginEntity.toString());
        if (loginEntity.getCode() == 0) {
            EventBus.getDefault().post(new EventBean("refresh"));
            ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
                @Override
                public void run() {
                    MyApp.getMsClient().init(Constants.HOST, Constants.PORT, String.valueOf(MyApp.manager.getId()), REQUEST_SS, MyApp.manager.getToken());
                }
            }).start();
            if (imLoginType == 1){
                App.getInstance().exitActivity();
            }else {
                this.finish();
            }
        } else {
            ToastUtils.show(loginEntity.getMsg());
            App.manager.setLoginState(false);
            App.manager.quitLogin();
        }
    }
}
