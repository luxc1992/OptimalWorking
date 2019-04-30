package com.yst.onecity.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.VerifyUtil;
import com.yst.onecity.view.ContainsEmojiEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO6;

/**
 * 忘记密码页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/23
 */

public class ForgetPasWordActivity extends BaseActivity {
    @BindView(R.id.btn_get_verification_code)
    TextView btnGetVerificationCode;
    @BindView(R.id.et_real_phone)
    ContainsEmojiEditText etRealPhone;
    @BindView(R.id.et_real_code)
    ContainsEmojiEditText etRealCode;
    @BindView(R.id.et_real_psw)
    ContainsEmojiEditText etRealPsw;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgetpaswrod;
    }

    @Override
    public void initData() {
        initTitleBar("忘记密码");
        CommonUtils.setEditTextInputLength(etRealCode, 6, true);
        CommonUtils.setEditTextInputLength(etRealPhone, 11, true);
        CommonUtils.setEditTextInputLength(etRealPsw, 20, true);
    }

    @OnClick({R.id.btn_get_verification_code, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verification_code:
                if (!Utils.isClickable()) {
                    return;
                }
                RequestApi.getVerifyCode(etRealPhone.getText().toString().trim(), "2", new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode()==NO1){
                            Utils.startCountDown(btnGetVerificationCode, R.drawable.shape_get_verification_code, R.color.color_ED5452, R.drawable.shape_gray_frame, R.color.color_999999, 60000, "获取验证码");
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
            case R.id.btn_commit:
                if (TextUtils.isEmpty(etRealPhone.getText().toString())) {
                    ToastUtils.show("请输入手机号码");
                    return;
                }
                if (!VerifyUtil.isMobileNO(etRealPhone.getText().toString().trim())) {
                    ToastUtils.show("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(etRealCode.getText().toString())) {
                    ToastUtils.show("请输入验证码");
                    return;
                }
                if (etRealPsw.getText().length()<NO6){
                    ToastUtils.show("请输入6~20位的密码");
                    return;
                }
                String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
                if (!etRealPsw.getText().toString().matches(regex)){
                    ToastUtils.show("密码必须为字母跟数字的组合");
                    return;
                }
                RequestApi.forgetPassWord(etRealPhone.getText().toString().trim(),
                        etRealCode.getText().toString().trim(),
                        etRealPsw.getText().toString(),
                        new AbstractNetWorkCallback<MsgBean>() {
                            @Override
                            public void onSuccess(MsgBean msgBean) {
                                ToastUtils.show(msgBean.getMsg());
                                if (msgBean.getCode() == 1) {
                                    finish();
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

                break;
            default:
                break;
        }
    }

}
