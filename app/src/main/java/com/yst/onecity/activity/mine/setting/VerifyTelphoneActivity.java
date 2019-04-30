package com.yst.onecity.activity.mine.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.FormatUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * (我的)验证手机号页面
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/24
 */

public class VerifyTelphoneActivity extends BaseActivity {


    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.txt_bank_next)
    TextView txtBankNext;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.txt_verify_num)
    TextView txtVerifyNum;
    private String etVerifyCode1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verifyphone;
    }

    @Override
    public void initData() {
        initTitleBar("验证手机号");
        txtVerifyNum.setText(FormatUtils.formaPhone(App.manager.getPhoneNum()));



    }

    @OnClick({R.id.tv_get_code, R.id.txt_bank_next})
    public void onClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_get_code:
                Utils.startCountDown(tvGetCode, R.drawable.shape_get_verification_code, R.color.color_ED5452, R.drawable.shape_gray_frame, R.color.color_999999, 60000, "获取验证码");
             //验证手机号
                RequestApi.setOuBindBank(App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode()==1){
                            ToastUtils.show(msgBean.getMsg());
                        }else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
                break;
            case R.id.txt_bank_next:
                if (TextUtils.isEmpty(etVerifyCode.getText().toString().trim())) {
                    ToastUtils.show("请获取您的验证码");
                    return;
                }
                //解绑银行卡
                etVerifyCode1 = this.etVerifyCode.getText().toString().trim();
                RequestApi.setUnBindBank(App.manager.getUuid(), App.manager.getPhoneNum(), etVerifyCode1, new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        ToastUtils.show(msgBean.getMsg());
                        if (msgBean.getCode() == NO1){
                            JumpIntent.jump(VerifyTelphoneActivity.this, NoBankNumActivity.class);
                            finish();
                        }
                    }
                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
                break;
            default:
                break;
        }
    }


}
