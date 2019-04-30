package com.yst.onecity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.listener.OnPasswordInputFinish;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Md5Util;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.activity.mine.accountsafe.SetLoginPwdActivity;
import com.yst.onecity.bean.BankinfroBean;
import com.yst.onecity.bean.CashDetailsBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.bean.mine.GroupInfoBean;
import com.yst.onecity.bean.mine.MyInComeListBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.AbstractTransactionHintDialog;
import com.yst.onecity.view.dialog.AbstractTransactionPswDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.DOT;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 零钱提现页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/24
 */

public class FetchCashActivity extends BaseActivity {
    @BindView(R.id.et_balance)
    EditText etBalance;
    @BindView(R.id.tv_fetch)
    TextView tvFetch;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.second)
    LinearLayout second;
    @BindView(R.id.all_fetch)
    TextView allFetch;
    @BindView(R.id.income_money)
    TextView incomeMoney;
    @BindView(R.id.et_bank_card_num)
    TextView bankCardNum;
    @BindView(R.id.into_time)
    TextView intoTime;
    @BindView(R.id.into_money)
    TextView intoMoney;
    @BindView(R.id.service_charge)
    TextView serviceCharge;
    @BindView(R.id.into_bank)
    TextView intoBank;
    @BindView(R.id.into_state)
    TextView intoState;
    @BindView(R.id.second_line)
    TextView secondLine;
    @BindView(R.id.three_state)
    ImageView threeState;
    private String inComeMoney = "0.00";
    private String bankNum = "";
    private AbstractTransactionHintDialog hintDialog;
    private Double singleFee;
    /**
     * 是否实名认证
     */
    private boolean hasIdentity = false;

    /**
     * 是否设置交易密码
     */
    private boolean hasTradePwd = false;

    private String type = "";

    private String id = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_fetchcash;
    }

    @Override
    public void initData() {
        initTitleBar("零钱提现");
        type = getIntent().getStringExtra("type");
        if (Integer.parseInt(type) == NO1) {
            first.setVisibility(View.VISIBLE);
            second.setVisibility(View.GONE);
            getBankInfo();
            getInComeList();
            getThirdLoginInfo();
        } else {
            id = getIntent().getStringExtra("id");
            first.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
            getCashDetails();
        }

        etBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (s.toString().contains(DOT)) {
                    if (s.length() - 1 - s.toString().indexOf(DOT) > NO2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + NO3);
                        etBalance.setText(s);
                        etBalance.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(DOT)) {
                    s = "0" + s;
                    etBalance.setText(s);
                    etBalance.setSelection(2);
                }
                if (s.toString().startsWith(String.valueOf(NO0)) && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(NO1, NO2).equals(DOT)) {
                        etBalance.setText(s.subSequence(0, 1));
                        etBalance.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    tvFetch.setEnabled(true);
                    tvFetch.setBackground(ContextCompat.getDrawable(App.getInstance(), R.drawable.shape_fetchcash_bt_bg_true));
                } else {
                    tvFetch.setBackground(ContextCompat.getDrawable(App.getInstance(), R.drawable.shape_fetchcash_bt_bg_false));
                    tvFetch.setEnabled(false);
                }
            }
        });
        hintDialog = new AbstractTransactionHintDialog(this) {
            @Override
            public void sureClick() {
                if (!hasIdentity) {
                    JumpIntent.jump(FetchCashActivity.this, RealNameAuthenticationActivity.class);
                } else if (!hasTradePwd) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    JumpIntent.jump(FetchCashActivity.this, SetLoginPwdActivity.class, bundle);
                }
            }

            @Override
            public void cancelClick() {
                FetchCashActivity.this.finish();
            }

        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Integer.parseInt(type) == NO1) {
            getThirdLoginInfo();
        }
    }

    @OnClick({R.id.tv_fetch, R.id.all_fetch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fetch:
                if (Double.valueOf(etBalance.getText().toString()) == 0) {
                    ToastUtils.show("提现金额必须大于0!");
                    return;
                }
                if (Double.valueOf(etBalance.getText().toString().trim()) > (Double.valueOf(inComeMoney))) {
                    ToastUtils.show("超出本次可提现金额");
                    return;
                }
                String fetchMoney;
                if (etBalance.getText().toString().trim().substring(etBalance.length()).equals(DOT)) {
                    fetchMoney = Utils.doubleToString(Double.valueOf(etBalance.getText().toString().substring(0, etBalance.length() - 1)));
                } else {
                    fetchMoney = Utils.doubleToString(Double.valueOf(etBalance.getText().toString()));
                }
                final AbstractTransactionPswDialog dialog = new AbstractTransactionPswDialog(this, fetchMoney, String.valueOf(singleFee)) {
                    @Override
                    public void closeClick() {

                    }
                };
                dialog.showDialog();
                dialog.setOnFinishInput(new OnPasswordInputFinish() {
                    @Override
                    public void inputFinish(String password) {
                        checkPsw(password, dialog);
                    }
                });
                break;
            case R.id.all_fetch:
                if (Double.parseDouble(inComeMoney) == NO0) {
                    etBalance.setText("0");
                }
                etBalance.setText(inComeMoney);
                etBalance.setSelection(etBalance.length());
                break;
            default:
                break;
        }
    }

    /**
     * 密码校验
     *
     * @param password 密码
     * @param dialog   弹出输入密码框
     */
    private void checkPsw(final String password, final AbstractTransactionPswDialog dialog) {

        RequestApi.updateTradePwdCheckedOldTradePwd(App.manager.getUuid(), App.manager.getPhoneNum(), password, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    dialog.dismissDialog();
                    fetchCash(password);
                } else {
                    dialog.dismissDialog();
                    dialog.cleanPasswrod();
                    dialog.showDialog();
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                dialog.dismissDialog();
                dialog.cleanPasswrod();
            }
        });
    }

    /**
     * 提现
     *
     * @param password 密码
     */
    private void fetchCash(String password) {
        RequestApi.cash(App.manager.getUuid(),
                App.manager.getPhoneNum(),
                Utils.getIPAddress(this),
                etBalance.getText().toString().trim(),
                bankNum,
                Md5Util.getMD5(password),
                new AbstractNetWorkCallback<GroupInfoBean>() {
                    @Override
                    public void onSuccess(GroupInfoBean msgBean) {
                        if (msgBean.getCode() == 1) {
                            id = msgBean.getContent();
                            getCashDetails();
                            first.setVisibility(View.GONE);
                            second.setVisibility(View.VISIBLE);
                        }
                        ToastUtils.show(msgBean.getMsg());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 获取提现详情
     */
    private void getCashDetails() {
        RequestApi.getCashDetails(App.manager.getPhoneNum(),
                App.manager.getUuid(),
                id,
                new AbstractNetWorkCallback<CashDetailsBean>() {
                    @Override
                    public void onSuccess(CashDetailsBean cashDetailsBean) {
                        if (cashDetailsBean.getCode() == 1) {
                            if (cashDetailsBean.getContent() != null) {
                                if (cashDetailsBean.getContent().getStatus() == NO1) {
                                    intoState.setText("到账成功");
                                    secondLine.setBackgroundColor(Color.parseColor("#FF444A"));
                                    threeState.setImageResource(R.mipmap.ic_round_true);
                                } else if (cashDetailsBean.getContent().getStatus() == NO2) {
                                    secondLine.setBackgroundColor(Color.parseColor("#FF444A"));
                                    threeState.setImageResource(R.mipmap.ic_round_true);
                                    intoState.setText("到账失败");
                                } else if (cashDetailsBean.getContent().getStatus() == NO0) {
                                    intoState.setText("到账成功");
                                }
                                String bankNum = cashDetailsBean.getContent().getBank_num();
                                String time = Utils.getCustomStrTime(String.valueOf(cashDetailsBean.getContent().getCreatedTime()), "MM-dd HH:mm");
                                intoTime.setText(String.format("预计%1$s前到账", time));
                                intoBank.setText(cashDetailsBean.getContent().getBank() + " 尾号" + bankNum.substring(bankNum.length() - 4, bankNum.length()));
                                intoMoney.setText("¥" + cashDetailsBean.getContent().getMoney());
                                serviceCharge.setText("¥" + cashDetailsBean.getContent().getFee());
                            } else {
                                ToastUtils.show("数据异常");
                            }
                        } else {
                            ToastUtils.show(cashDetailsBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        MyLog.e("okError", errorMsg);
                    }
                });
    }

    /**
     * 获取银行卡信息
     *
     * @version 1.0.1
     */
    public void getBankInfo() {
        RequestApi.getBindInfo(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<BankinfroBean>() {
            @Override
            public void onSuccess(BankinfroBean msgBean) {
                if (msgBean.getCode() == 1) {
                    String bankNo = msgBean.getContent().getBankNo();
                    String bank = msgBean.getContent().getBank();
                    bankNum = bankNo;
                    if (!TextUtils.isEmpty(bank) || !TextUtils.isEmpty(bankNo)) {
                        bankCardNum.setText(bank + "  (" + bankNo.substring(bankNo.length() - 4, bankNo.length()) + ")");
                    }
                    singleFee = Double.parseDouble(msgBean.getContent().getSingleFee());
                }
            }

            @Override
            public void onError(String errorMsg) {
                MyLog.e("onError", errorMsg);
            }
        });
    }

    /**
     * 获取总收入
     */
    private void getInComeList() {

        RequestApi.getMyInComeListData(String.valueOf(App.manager.getId()),
                "0",
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                "1",
                new AbstractNetWorkCallback<MyInComeListBean>() {
                    @Override
                    public void onSuccess(MyInComeListBean myInComeListBean) {
                        if (myInComeListBean.getCode() == 1) {
                            if (!TextUtils.isEmpty(String.valueOf(myInComeListBean.getContent().getMoney()))) {
                                inComeMoney = String.valueOf(myInComeListBean.getContent().getMoney());
                                incomeMoney.setText(String.format("收入余额¥%1$s", inComeMoney));
                            } else {
                                incomeMoney.setText(String.format("收入余额¥%1$s", inComeMoney));
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        incomeMoney.setText(String.format("收入余额¥%1$s", inComeMoney));
                        MyLog.e("onError", errorMsg);
                    }
                });
    }

    /**
     * 获取信息
     * version 1.0.1
     */
    private void getThirdLoginInfo() {
        RequestApi.getAccountSafe(App.manager.getId() + "", App.manager.getPhoneNum(),
                App.manager.getUuid(),
                new AbstractNetWorkCallback<AccountSafeBean>() {
                    @Override
                    public void onSuccess(AccountSafeBean accountSafeBean) {
                        MyLog.e("tag", accountSafeBean.getContent().getPhone());
                        if (accountSafeBean.getCode() == NO1) {
                            int transactionPasswordStatus = accountSafeBean.getContent().getTransactionPasswordStatus();
                            if (transactionPasswordStatus == NO1) {
                                hasTradePwd = true;
                            } else {
                                hasTradePwd = false;
                            }
                            if (accountSafeBean.getContent().getRealNameStatus() == NO1) {
                                hasIdentity = true;
                            } else {
                                hasIdentity = false;
                            }
                            if (!hintDialog.isShowing()) {
                                if (!hasIdentity) {
                                    hintDialog.setText("您还未实名认证，请实名认证");
                                    hintDialog.showDialog();
                                } else if (!hasTradePwd) {
                                    hintDialog.setText("您还未设置交易密码，请设置交易密码");
                                    hintDialog.showDialog();
                                }
                            }
                        } else {
                            ToastUtils.show(accountSafeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }
}
