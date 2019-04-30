package com.yst.onecity.activity.mine.accountsafe;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;

import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.SanFangLoginBean;
import com.yst.onecity.bean.SanFangStateBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.VerifyUtil;
import com.yst.onecity.view.dialog.AbstractBindSangHintDialog;


import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO5;

/**
 * 绑定手机号
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/7.
 */

public class BindPhoneNumActivity extends BaseActivity {
    @BindView(R.id.btn_get_verification_code)
    TextView btnGetVerificationCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_verification)
    EditText etVerification;
    @BindView(R.id.bind_phone)
    TextView bindPhone;
    private String type;
    private String bindStatus;
    private String name;
    private String openId;
    private String logoAttachmentAddress;

    /**
     * 标识是否获取验证码
     */
    private boolean flag = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone_num;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.bind_phone_num_str));
        CommonUtils.setEditTextInputLength(etPhone, 11, true);
        CommonUtils.setEditTextInputLength(etVerification, 6, true);
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        openId = getIntent().getStringExtra("openid");
        logoAttachmentAddress = getIntent().getStringExtra("imgUrl");
        bindPhone.setEnabled(false);
    }

    @OnClick({R.id.btn_get_verification_code, R.id.bind_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verification_code:
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    ToastUtils.show("请输入手机号码");
                    return;
                }
                if (!VerifyUtil.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.show("请输入正确的手机号码");
                    return;
                }
                getBindState();
                break;
            case R.id.bind_phone:
                if (etVerification.getText().length() > NO5) {
                    bindPhone();
                } else {
                    ToastUtils.show("请输入6位的验证码");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取三方绑定的状态
     */
    private void getBindState() {
        RequestApi.getSanFangBindState(etPhone.getText().toString().trim(), new AbstractNetWorkCallback<SanFangStateBean>() {
            @Override
            public void onSuccess(final SanFangStateBean sanFangStateBean) {
                bindStatus = sanFangStateBean.getContent().getBindStatus();
                AbstractBindSangHintDialog hintDialog = new AbstractBindSangHintDialog(
                        BindPhoneNumActivity.this,
                        sanFangStateBean.getContent().getQq_nick_name(),
                        sanFangStateBean.getContent().getWx_nick_name(),
                        Integer.parseInt(sanFangStateBean.getContent().getBindStatus())) {
                    @Override
                    public void sureClick() {
                        if (sanFangStateBean.getContent().getBindStatus().equals(String.valueOf(NO3))) {
                            ToastUtils.show("您的账号已绑定QQ和微信");
                        }
                    }
                };
                if (sanFangStateBean.getContent().getBindStatus().equals(String.valueOf(NO3))) {
                    hintDialog.showDialog();
                    return;
                }
                if (type.equals(sanFangStateBean.getContent().getBindStatus())) {
                    hintDialog.showDialog();
                } else {
                    getCode();
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
     * 获取验证码
     */
    private void getCode() {
        RequestApi.getVerifyCode(etPhone.getText().toString().trim(), "4", new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    Utils.startCountDown(btnGetVerificationCode, R.drawable.shape_get_verification_code, R.color.color_ED5452, R.drawable.shape_gray_frame, R.color.color_999999, 60000, "获取验证码");
                    bindPhone.setBackground(ContextCompat.getDrawable(BindPhoneNumActivity.this, R.drawable.shape_fetchcash_bt_bg_true));
                    bindPhone.setEnabled(true);
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


    /**
     * 绑定手机号
     */
    private void bindPhone() {
        RequestApi.qqOrWxLogin(type, etPhone.getText().toString(), logoAttachmentAddress, openId, name, bindStatus, etVerification.getText().toString().trim(), new AbstractNetWorkCallback<SanFangLoginBean>() {
            @Override
            public void onSuccess(SanFangLoginBean sanFangLoginBean) {
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

                    if (Integer.parseInt(type) == NO1) {
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
                    App.getInstance().exitActivity();
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
}
