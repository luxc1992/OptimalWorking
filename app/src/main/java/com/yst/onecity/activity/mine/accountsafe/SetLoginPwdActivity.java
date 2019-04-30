package com.yst.onecity.activity.mine.accountsafe;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.FormatUtils;
import com.yst.onecity.utils.IsCard;
import com.yst.onecity.utils.MyEditTextChangeListener;
import com.yst.onecity.utils.VerifyUtil;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.MyPasswordView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO18;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO6;

/**
 * 设置(修改）登录（交易）密码、忘记交易密码
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/7.
 */

public class SetLoginPwdActivity extends BaseActivity {
    @BindView(R.id.tv_set_login_tips)
    TextView tvSetLoginTips;
    @BindView(R.id.ll_set_pwd_view)
    LinearLayout llSetPwdView;
    @BindView(R.id.ll_update_login_pwd_view)
    LinearLayout llUpdateLoginPwdView;
    @BindView(R.id.ll_update_trade_pwd_view)
    LinearLayout llUpdateTradePwdView;
    @BindView(R.id.ll_set_trade_pwd)
    LinearLayout llSetTradePwd;
    @BindView(R.id.ll_forget_trade_verify_identity_card_view)
    LinearLayout llForgetTradeVerifyIdentityCardView;
    @BindView(R.id.et_verify_identity_card)
    ContainsEmojiEditText etVerifyIdentityCard;
    @BindView(R.id.et_verification)
    ContainsEmojiEditText etVerification;
    @BindView(R.id.btn_commit)
    TextView btnCommit;
    @BindView(R.id.btn_next_step)
    TextView btnNextStep;
    @BindView(R.id.passwordView)
    MyPasswordView passwordView;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.tv_trade_pwd_tips)
    TextView tvTradePwdTips;
    @BindView(R.id.et_set_login_pwd)
    ContainsEmojiEditText etSetLoginPwd;
    @BindView(R.id.et_sure_login_pwd)
    ContainsEmojiEditText etSureLoginPwd;
    @BindView(R.id.et_old_login_pwd)
    ContainsEmojiEditText etOldLoginPwd;
    @BindView(R.id.et_new_login_pwd)
    ContainsEmojiEditText etNewLoginPwd;
    @BindView(R.id.et_sure_new_login_pwd)
    ContainsEmojiEditText etSureNewLoginPwd;
    @BindView(R.id.tv_set_trade_pwd_phone)
    TextView tvSetTradePwdPhone;
    @BindView(R.id.et_set_trade_pwd)
    ContainsEmojiEditText etSetTradePwd;
    @BindView(R.id.et_sure_trade_pwd)
    ContainsEmojiEditText etSureTradePwd;
    @BindView(R.id.btn_sure_verification_code)
    TextView btnSureVerificationCode;

    /**
     * type  0 设置登录密码  1 修改登录密码 2 设置交易密码 3修改交易密码 4 忘记交易密码
     */
    private int type;
    /**
     * 修改交易密码时，验证旧密码是否正确
     */
    private boolean isVerifyPwd;
    /**
     * 忘记密码时，身份认证的标识
     */
    private boolean isVerifyIdentity;
    /**
     * 设置的新密码
     */
    private String newPwd = "";
    private String strPhone;
    private String strUuid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_login_pwd;
    }

    @Override
    public void initData() {
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        strPhone = App.manager.getPhoneNum();
        strUuid = App.manager.getUuid();

        if (type == NO0) {
            initTitleBar(getString(R.string.set_login_pwd));
            tvSetLoginTips.setVisibility(View.VISIBLE);
            llSetPwdView.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.VISIBLE);
        } else if (type == NO1) {
            initTitleBar(getString(R.string.update_login_password));
            tvSetLoginTips.setVisibility(View.VISIBLE);
            llUpdateLoginPwdView.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.VISIBLE);
        } else if (type == NO2) {
            initTitleBar(getString(R.string.set_trade_pwd));
            tvSetTradePwdPhone.setText(FormatUtils.formatPhone(App.manager.getPhoneNum()));
            llSetTradePwd.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.VISIBLE);
        } else if (type == NO3) {
            initTitleBar(getString(R.string.update_trade_password));
            llUpdateTradePwdView.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        } else {
            initTitleBar(getString(R.string.forget_trade_password));
            llUpdateTradePwdView.setVisibility(View.VISIBLE);
            passwordView.setVisibility(View.GONE);
            tvTop.setText(String.format("请填写%1$s的身份证号，验证身份", FormatUtils.formatName(App.manager.getName())));
            llForgetTradeVerifyIdentityCardView.setVisibility(View.VISIBLE);
        }

        //限制登录密码输入框的输入长度
        CommonUtils.setEditTextInputLength(etSetLoginPwd, 20, true);
        CommonUtils.setEditTextInputLength(etSureLoginPwd, 20, true);
        //限制修改登录密码输入框的输入长度
        CommonUtils.setEditTextInputLength(etOldLoginPwd, 20, true);
        CommonUtils.setEditTextInputLength(etNewLoginPwd, 20, true);
        CommonUtils.setEditTextInputLength(etSureNewLoginPwd, 20, true);
        //限制交易密码输入框的输入长度
        CommonUtils.setEditTextInputLength(etSetTradePwd, 6, true);
        CommonUtils.setEditTextInputLength(etSureTradePwd, 6, true);
        //限制验证码输入框的输入长度
        CommonUtils.setEditTextInputLength(etVerification, 6, true);
        //限制身份证输入框的输入长度
        CommonUtils.setEditTextInputLength(etVerifyIdentityCard, 18, true);

        setListener();

    }

    /**
     * 添加输入框监听
     */
    @Override
    public void setListener() {

        // 设置登录密码
        etSetLoginPwd.addTextChangedListener(new MyEditTextChangeListener(etSetLoginPwd, etSureLoginPwd, btnCommit));
        etSureLoginPwd.addTextChangedListener(new MyEditTextChangeListener(etSetLoginPwd, etSureLoginPwd, btnCommit));
        //修改登录密码
        etOldLoginPwd.addTextChangedListener(new MyEditTextChangeListener(etOldLoginPwd, etNewLoginPwd, etSureNewLoginPwd, btnCommit));
        etNewLoginPwd.addTextChangedListener(new MyEditTextChangeListener(etOldLoginPwd, etNewLoginPwd, etSureNewLoginPwd, btnCommit));
        etSureNewLoginPwd.addTextChangedListener(new MyEditTextChangeListener(etOldLoginPwd, etNewLoginPwd, etSureNewLoginPwd, btnCommit));
        //设置交易密码
        etSetTradePwd.addTextChangedListener(new MyEditTextChangeListener(etSetTradePwd, etSureTradePwd, etVerification, btnCommit));
        etSureTradePwd.addTextChangedListener(new MyEditTextChangeListener(etSetTradePwd, etSureTradePwd, etVerification, btnCommit));
        etVerification.addTextChangedListener(new MyEditTextChangeListener(etSetTradePwd, etSureTradePwd, etVerification, btnCommit));
        // 身份证输入框监听
        etVerifyIdentityCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == NO18) {
                    btnNextStep.setEnabled(true);
                    btnNextStep.setBackgroundResource(R.drawable.shape_btn_bg);
                } else {
                    btnNextStep.setBackgroundResource(R.drawable.shape_btn_pink_red_bg);
                    btnNextStep.setEnabled(false);
                }
            }
        });

        //  密码框监听时事件
        passwordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == NO6) {
                    btnNextStep.setBackgroundResource(R.drawable.shape_btn_bg);
                    btnNextStep.setEnabled(true);
                    btnCommit.setBackgroundResource(R.drawable.shape_btn_bg);
                    btnCommit.setEnabled(true);
                } else {
                    btnNextStep.setBackgroundResource(R.drawable.shape_btn_pink_red_bg);
                    btnNextStep.setEnabled(false);
                    btnCommit.setBackgroundResource(R.drawable.shape_btn_pink_red_bg);
                    btnCommit.setEnabled(false);
                }
            }
        });
    }

    /**
     * 修改登录密码
     *
     * @param type 1 设置登录密码  2 修改密码
     */
    private void updateLoginPwd(int type) {
        RequestApi.updateLoginPwd(App.manager.getUuid(), App.manager.getPhoneNum(), CommonUtils.getEditTextInputContent(etSetLoginPwd), CommonUtils.getEditTextInputContent(etNewLoginPwd), CommonUtils.getEditTextInputContent(etOldLoginPwd), type, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                ToastUtils.show(bean.getMsg());
                if (bean.getCode() == NO1) {
                    finish();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 验证旧的交易密码
     */
    private void checkOldTradePwd() {
        RequestApi.updateTradePwdCheckedOldTradePwd(strUuid, strPhone, getPasswordText(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                if (bean.getCode() == NO1) {
                    isVerifyPwd = true;
                    if (!VerifyUtil.checkTradePwd(getPasswordText())) {
                        tvTop.setText(String.format("请为%1$s账号，设置6位交易密码", FormatUtils.formatPhone(App.manager.getPhoneNum())));
                        tvTradePwdTips.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtils.show(getString(R.string.trade_pwd_not_match_condition));
                    }
                } else {
                    ToastUtils.show("旧密码错误，请重新输入");
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 修改交易密码
     */
    private void updateTradePwd() {
        RequestApi.updateTradePwd(strUuid, strPhone, getPasswordText(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                if (bean.getCode() == NO1) {
                    ToastUtils.show(bean.getMsg());
                    finish();
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 设置交易密码
     */
    private void setTradePwd() {
        RequestApi.setTradePwd(strUuid, strPhone, CommonUtils.getEditTextInputContent(etSetTradePwd), CommonUtils.getEditTextInputContent(etVerification), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                if (bean.getCode() == 1) {
                    finish();
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 获取密码框内容
     *
     * @return 密码输入框内容
     */
    public String getPasswordText() {
        return passwordView.getText().toString().trim();
    }

    /**
     * 清空密码框内容
     */
    public void clearPassword() {
        passwordView.setText("");
    }

    @OnClick({R.id.btn_sure_verification_code, R.id.btn_commit, R.id.btn_next_step})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_sure_verification_code:
                Utils.startCountDown(btnSureVerificationCode, R.drawable.shape_get_verification_code, R.color.color_ED5452, R.drawable.shape_gray_frame, R.color.color_999999, 60000, "获取验证码");
                //设置交易密码 获取验证码
                RequestApi.getVerifyCodeForSetTradePwd(strPhone, new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean bean) {
                        ToastUtils.show(bean.getMsg());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
                break;
            case R.id.btn_next_step:
                switch (type) {
                    // 点击忘记交易密码跳转过来点击下一步
                    case NO4:
                        String identityCard = etVerifyIdentityCard.getText().toString().trim();
                        if (!(new IsCard().verify(identityCard))) {
                            ToastUtils.show(getString(R.string.please_input_right_identity_card));
                            return;
                        }

                        if (!isVerifyIdentity) {
                            //忘记交易密码 验证身份证号码
                            RequestApi.forgetTradePwdCheckIdCard(strUuid, strPhone, App.manager.getName(), CommonUtils.getEditTextInputContent(etVerifyIdentityCard).toUpperCase(), new AbstractNetWorkCallback<MsgBean>() {
                                @Override
                                public void onSuccess(MsgBean bean) {
                                    if (bean.getCode() == NO1) {
                                        //验证身份
                                        isVerifyIdentity = true;
                                        tvTop.setText(String.format("请为%1$s账号，设置6位交易密码", FormatUtils.formatPhone(App.manager.getPhoneNum())));
                                        setTitleBarTitle(getString(R.string.update_trade_password));
                                        passwordView.setVisibility(View.VISIBLE);
                                        tvTradePwdTips.setVisibility(View.VISIBLE);
                                        etVerifyIdentityCard.setVisibility(View.GONE);
                                    } else {
                                        ToastUtils.show(bean.getMsg());
                                    }
                                }

                                @Override
                                public void onError(String errorMsg) {
                                    ToastUtils.show(errorMsg);
                                }
                            });

                        } else {
                            if (!VerifyUtil.checkTradePwd(getPasswordText())) {
                                newPwd = getPasswordText();
                                tvTop.setText("请再次输入");
                                tvTradePwdTips.setVisibility(View.GONE);
                                btnNextStep.setVisibility(View.GONE);
                                btnCommit.setVisibility(View.VISIBLE);
                            } else {
                                ToastUtils.show(getString(R.string.trade_pwd_not_match_condition));
                            }
                        }

                        break;
                    default:
                        // 修改交易密码 需要先验证密码 如果是验证后 再次点击下一步 显示请再次输入密码 点击完成 修改成功

                        if (isVerifyPwd) {
                            if (!VerifyUtil.checkTradePwd(getPasswordText())) {
                                newPwd = getPasswordText();
                                tvTop.setText("请再次输入");
                                tvTradePwdTips.setVisibility(View.GONE);
                                btnNextStep.setVisibility(View.GONE);
                                btnCommit.setVisibility(View.VISIBLE);
                            } else {
                                ToastUtils.show(getString(R.string.trade_pwd_not_match_condition));
                            }
                        } else {
                            // 修改交易密码 验证旧密码是否正确
                            checkOldTradePwd();
                        }
                }
                clearPassword();
                break;
            case R.id.btn_commit:
                if (type == NO0) {
                    if (VerifyUtil.checkLoginPwd(CommonUtils.getEditTextInputContent(etSetLoginPwd))) {
                        if (CommonUtils.getEditTextInputLength(etSetLoginPwd) < NO6) {
                            ToastUtils.show("请输入6~20为字母数字组合");
                            return;
                        } else {
                            if (etSetLoginPwd.getText().toString().trim().equals(etSureLoginPwd.getText().toString().trim())) {
                                updateLoginPwd(1);
                            } else {
                                ToastUtils.show(getString(R.string.verify_pwd_is_same));
                            }
                        }
                    } else {
                        ToastUtils.show("请输入6~20为字母数字组合");
                        return;
                    }
                } else if (type == NO1) {
                    if (CommonUtils.getEditTextInputLength(etNewLoginPwd) < NO6) {
                        ToastUtils.show("请输入6~20为字母数字组合");
                        return;
                    } else {
                        if (etNewLoginPwd.getText().toString().trim().equals(etSureNewLoginPwd.getText().toString().trim())) {
                            if (!VerifyUtil.checkLoginPwd(etNewLoginPwd.getText().toString().trim())) {
                                ToastUtils.show("登录密码必须为字母数字组合");
                                return;
                            }
                            // 修改登录密码
                            updateLoginPwd(2);
                        } else {
                            ToastUtils.show(getString(R.string.verify_pwd_is_same));
                        }
                    }
                } else if (type == NO2) {
                    if (CommonUtils.getEditTextInputLength(etSetTradePwd) < NO6) {
                        ToastUtils.show("请输入6位交易密码");
                        return;
                    } else {
                        if (etSetTradePwd.getText().toString().trim().equals(etSureTradePwd.getText().toString().trim())) {
                            if (!VerifyUtil.isContinuityCharacter(etSetTradePwd.getText().toString().trim()) && !VerifyUtil.checkTradePwd(etSetTradePwd.getText().toString().trim())) {
                                setTradePwd();
                            } else {
                                ToastUtils.show(getString(R.string.trade_pwd_not_match_condition));
                            }
                        } else {
                            ToastUtils.show(getString(R.string.verify_pwd_is_same));
                        }
                    }
                } else if (type == NO3) {
                    if (!VerifyUtil.isContinuityCharacter(getPasswordText()) && !VerifyUtil.checkTradePwd(getPasswordText())) {
                        if (!newPwd.equals(getPasswordText())) {
                            ToastUtils.show(getString(R.string.verify_pwd_is_same));
                            clearPassword();
                            return;
                        } else {
                            //修改交易密码
                            updateTradePwd();
                        }
                    } else {
                        ToastUtils.show(getString(R.string.trade_pwd_not_match_condition));
                        clearPassword();
                    }
                } else {
                    if (!VerifyUtil.isContinuityCharacter(getPasswordText()) && !VerifyUtil.checkTradePwd(getPasswordText())) {
                        if (!newPwd.equals(getPasswordText())) {
                            ToastUtils.show(getString(R.string.verify_pwd_is_same));
                            clearPassword();
                            return;
                        } else {
                            //修改交易密码
                            updateTradePwd();
                        }
                    } else {
                        ToastUtils.show(getString(R.string.trade_pwd_not_match_condition));
                        clearPassword();
                    }
                }
                break;
            default:
                break;
        }
    }
}