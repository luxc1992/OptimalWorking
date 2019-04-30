package com.yst.onecity.activity.mine.setting;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.accountsafe.SetLoginPwdActivity;
import com.yst.onecity.bean.BandBankBean;
import com.yst.onecity.bean.BankBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO6;

/**
 * (我的)添加银行卡页面
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/23
 */
public class BankActivity extends BaseActivity implements TextWatcher{
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.txt_bank_next)
    TextView txtBankNext;
    @BindView(R.id.txt_bank_card_name)
    TextView txtBankCardName;
    @BindView(R.id.et_bank_card_num)
    EditText etBankCardNum;
    @BindView(R.id.txt_card_text)
    TextView txtCardText;
    private String sequenceCode;
    private String bankNano;
    private String isSetPassword;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bank;
    }

    @Override
    public void initData() {
        initTitleBar("添加银行卡");
        etBankCardNum.addTextChangedListener(this);
        txtBankCardName.setText(App.manager.getName());
    }

    private void bindBank() {
        String etBankCard = etBankCardNum.getText().toString().trim();
        String cardType = txtCardText.getText().toString().trim();
        //绑定银行卡
        RequestApi.getBindBank(App.manager.getUuid(), App.manager.getPhoneNum(), ConstUtils.getStringNoEmpty(etBankCard), ConstUtils.getStringNoEmpty(cardType),ConstUtils.getStringNoEmpty(bankNano), new AbstractNetWorkCallback<BankBean>() {

            @Override
            public void onSuccess(BankBean msgBean) {
                //code==2未实名认证   code=0 余额不足无法提现
                if (msgBean.getCode()==NO1){
                    //0没有设置交易密码
                    if (msgBean.getContent()!=null){
                        isSetPassword = msgBean.getContent().getIsSetPassword();
                        if (String.valueOf(NO0).equals(isSetPassword)){
                            Bundle bundle=new Bundle();
                            bundle.putInt("type",2);
                            JumpIntent.jump(BankActivity.this, SetLoginPwdActivity.class,bundle);
                            finish();
                        }else {
                            JumpIntent.jump(BankActivity.this, BankNumActivity.class);
                            ToastUtils.show(msgBean.getMsg());
                        }
                    }else {
                        ToastUtils.show(msgBean.getMsg());
                    }
                }else {
                    ToastUtils.show(msgBean.getMsg());
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

    @OnClick({ R.id.txt_bank_next})
    public void onClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.txt_bank_next:
                if (TextUtils.isEmpty(sequenceCode)) {
                    ToastUtils.show("请输入您的银行卡号");
                    return;
                }
                //绑定银行卡防爆
                if(Utils.isClickable()) {
                    bindBank();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        sequenceCode = charSequence.toString().trim();
        // 在输入数据时监听
        if (sequenceCode.length() >= NO6) {
            // 开户行
            RequestApi.setOpenBank(sequenceCode,new AbstractNetWorkCallback<BandBankBean>() {

                @Override
                public void onSuccess(BandBankBean msgBean) {
                    if (msgBean.getCode()==1){
                        txtCardText.setText(ConstUtils.getStringNoEmpty(msgBean.getContent().getBankName()));
                        bankNano = msgBean.getContent().getBankNano();
                        tvBankType.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        } else {
            txtCardText.setText("");
            sequenceCode = "";
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 0) {
            txtBankNext.setEnabled(true);
            txtBankNext.setBackground(ContextCompat.getDrawable(App.getInstance(), R.drawable.shape_fetchcash_bt_bg_true));
        } else {
            txtBankNext.setBackground(ContextCompat.getDrawable(App.getInstance(), R.drawable.shape_fetchcash_bt_bg_false));
            txtBankNext.setEnabled(false);
        }
    }
}
