package com.yst.onecity.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.activity.TrilateralLoginActivity;
import com.yst.onecity.activity.UserAgreementActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.VerifyUtil;
import com.yst.onecity.view.ContainsEmojiEditText;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO6;

/**
 * 注册页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/23
 */

public class RegisterActivity extends BaseActivity implements View.OnTouchListener {
    @BindView(R.id.et_user_code)
    ContainsEmojiEditText etUserCode;
    @BindView(R.id.et_user_num)
    ContainsEmojiEditText etUserNum;
    @BindView(R.id.et_user_psw)
    ContainsEmojiEditText etUserPsw;
    @BindView(R.id.gain_code)
    TextView gainCode;
    @BindView(R.id.code_relative)
    RelativeLayout codeRelative;
    @BindView(R.id.iv_check_agree)
    CheckBox ivCheckAgree;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.jump_login)
    TextView jumpLogin;
    @BindView(R.id.san_fang)
    TextView sanFang;
    @BindView(R.id.eyes_linear)
    LinearLayout eyesLinear;
    @BindView(R.id.psw_state)
    ImageView pswState;
    @BindView(R.id.register_agree)
    LinearLayout registerAgree;
    private boolean pState = false;
    private int loginType;
    /**
     * 三方登录接口回调
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA shareMedia, int i, Map<String, String> map) {
            //1微信，2QQ
            Bundle bundle = new Bundle();
            bundle.putString("openid", map.get("openid"));
            bundle.putString("name", map.get("screen_name"));
            bundle.putString("gender", map.get("gender"));
            bundle.putString("imgUrl", map.get("profile_image_url"));
            bundle.putInt("type", loginType);
            JumpIntent.jump(RegisterActivity.this, TrilateralLoginActivity.class ,bundle);
        }

        @Override
        public void onError(SHARE_MEDIA shareMedia, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA shareMedia, int i) {

        }
    };

    @Override
    public int getLayoutId() {
        ImmersionBar.with(this).reset().init();
        return R.layout.activity_register;
    }

    @Override
    public void initData() {

        eyesLinear.setOnTouchListener(this);
        CommonUtils.setEditTextInputLength(etUserCode, 6, true);
        CommonUtils.setEditTextInputLength(etUserPsw, 20, true);
        CommonUtils.setEditTextInputLength(etUserNum, 11, true);
        registerAgree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ivCheckAgree.isChecked()) {
                    ivCheckAgree.setChecked(false);
                } else {
                    ivCheckAgree.setChecked(true);
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tv_xieyi, R.id.jump_login, R.id.gain_code, R.id.bt_register, R.id.close, R.id.weixin_login, R.id.qq_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xieyi:
                JumpIntent.jump(this, UserAgreementActivity.class);
                break;
            case R.id.jump_login:
                JumpIntent.jump(this, LoginActivity.class);
                finish();
                break;
            case R.id.gain_code:
                if (Utils.isClickable()) {
                    getVerify();
                }
                break;
            case R.id.bt_register:
                goRegister();
                break;
            case R.id.weixin_login:
                String wx="com.tencent.mm";
                if (!Utils.isAvilible(this, wx)){
                    ToastUtils.show("未安装微信");
                    return;
                }
                loginType = 2;
                UMShareAPI.get(App.getInstance()).getPlatformInfo(RegisterActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.qq_login:
                String qq="com.tencent.mobileqq";
                if (!Utils.isAvilible(this, qq)){
                    ToastUtils.show("未安装QQ");
                    return;
                }
                loginType = 1;
                UMShareAPI.get(App.getInstance()).getPlatformInfo(RegisterActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.close:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void goRegister() {

        if (!VerifyUtil.isMobileNO(etUserNum.getText().toString().trim())) {
            ToastUtils.show("请输入正确的手机号码");
            return;
        }
        if (etUserPsw.getText().length() < NO6) {
            ToastUtils.show("密码长度需为6-20位");
            return;
        }
        if (TextUtils.isEmpty(etUserCode.getText().toString())) {
            ToastUtils.show("请输入验证码");
            return;
        }
        if (etUserCode.getText().toString().length()<NO6) {
            ToastUtils.show("请输入6位数的验证码");
            return;
        }
        if (!ivCheckAgree.isChecked()){
            ToastUtils.show("请先同意用户服务协议");
            return;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (etUserPsw.getText().toString().matches(regex)){
            RequestApi.register(etUserNum.getText().toString(), etUserCode.getText().toString(), etUserPsw.getText().toString(), "0", new AbstractNetWorkCallback<MsgBean>() {

                @Override
                public void onSuccess(MsgBean msgBean) {
                    if (msgBean.getCode() == 1) {
                        finish();
                        JumpIntent.jump(RegisterActivity.this, LoginActivity.class);
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
        }else {
            ToastUtils.show("密码必须为字母跟数字的组合");
        }
    }

    /**
     * 注册发送验证码
     */
    private void getVerify() {
        if (TextUtils.isEmpty(etUserNum.getText().toString())) {
            ToastUtils.show("请输入手机号码");
            return;
        }

        if (!VerifyUtil.isMobileNO(etUserNum.getText().toString().trim())) {
            ToastUtils.show("请输入正确的手机号码");
            return;
        }
        RequestApi.getVerifyCode(etUserNum.getText().toString().trim(), "3", new AbstractNetWorkCallback<MsgBean>() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
