package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.GroupChatEntity;
import com.yst.im.imchatlibrary.bean.UserIntentChatEntity;
import com.yst.im.imchatlibrary.model.UpdateBindPhoneModel;
import com.yst.im.imchatlibrary.model.VerificationCodeModel;
import com.yst.im.imchatlibrary.model.VerificationVerifyModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.ChatType;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_6;

/**
 * 填写验证码
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/11.
 */

public class WriteCodeActivity extends BaseActivity implements View.OnClickListener,
        VerificationCodeModel.VerificationCodeListenerCallBack,
        UpdateBindPhoneModel.UpdateBindPhoneListenerCallBack,
        VerificationVerifyModel.VerificationVerifyCallBack {

    private AbstractTitleView titleviewSetCode;
    private EditText etUpdatePhone;
    private TextView txtCommit;
    private TextView txtNoCode;
    private TextView txtSecond;
    private TextView txtSendSecond;
    private TextView txtMyPhone;
    private RelativeLayout relForgSendCode;
    /**
     * 来源类型， 1忘记登录密码， 2 更换手机号
     */
    private int pwdType = 0;
    private String newPhone;
    private VerificationCodeModel verificationCodeModel;
    private UpdateBindPhoneModel updateBindPhoneModel;
    private VerificationVerifyModel verificationVerifyModel;
    private CountDownTimer timer = new CountDownTimer(120000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtSecond.setText(millisUntilFinished / 1000 + "s");
            txtSecond.setVisibility(View.VISIBLE);
            txtSendSecond.setVisibility(View.GONE);
        }

        @Override
        public void onFinish() {
            txtSecond.setVisibility(View.GONE);
            txtSendSecond.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 页面跳转
     *
     * @param context  上下文
     * @param pwdType  跳转来源类型
     * @param newPhone 手机号更改
     */
    public static void getJumpWriteCodeActivity(Context context, int pwdType, String newPhone) {
        Intent intent = new Intent(context, WriteCodeActivity.class);
        intent.putExtra("pwdType", pwdType);
        intent.putExtra("newPhone", newPhone);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verification_code;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        titleviewSetCode = (AbstractTitleView) findViewById(R.id.titleview_set_code);
        etUpdatePhone = (EditText) findViewById(R.id.et_update_phone);
        txtNoCode = (TextView) findViewById(R.id.txt_no_code);
        txtCommit = (TextView) findViewById(R.id.txt_commit);
        txtSecond = (TextView) findViewById(R.id.txt_second);
        txtSendSecond = (TextView) findViewById(R.id.txt_send_second);
        relForgSendCode = (RelativeLayout) findViewById(R.id.rel_forg_send_code);
        txtMyPhone = (TextView) findViewById(R.id.txt_my_phone);

        titleviewSetCode.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtCommit.setOnClickListener(this);
        txtNoCode.setOnClickListener(this);
        txtSendSecond.setOnClickListener(this);
        etUpdatePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < NUM_6) {
                    txtCommit.setBackgroundResource(R.drawable.btn_shape_width_gray);
                } else {
                    txtCommit.setBackgroundResource(R.drawable.btn_shape_width_blue);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        timer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtSecond.setText(millisUntilFinished / 1000 + "s");
                txtSecond.setVisibility(View.VISIBLE);
                txtSendSecond.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                txtSecond.setVisibility(View.GONE);
                txtSendSecond.setVisibility(View.VISIBLE);
            }
        };
        pwdType = getIntent().getIntExtra("pwdType", 0);
        verificationCodeModel = new VerificationCodeModel(this);
        verificationCodeModel.setVerificationCodeListenerCallBack(this);
        updateBindPhoneModel = new UpdateBindPhoneModel(this);
        updateBindPhoneModel.setUpdateBindPhoneListenerCallBack(this);
        verificationVerifyModel = new VerificationVerifyModel(this);
        verificationVerifyModel.setVerificationVerifyCallBack(this);
        txtNoCode.setVisibility(View.GONE);
        relForgSendCode.setVisibility(View.VISIBLE);
        if (pwdType == 1) {
            txtMyPhone.setText("手机号：" + MyApp.manager.getPhone());
        } else {
            newPhone = getIntent().getStringExtra("newPhone");
            txtMyPhone.setText("手机号：" + newPhone);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.txt_no_code) {
            getNewCode();
        } else if (id == R.id.txt_commit) {
            String verificationCode = etUpdatePhone.getText().toString().trim();
            if (TextUtils.isEmpty(verificationCode)) {
                ImToastUtil.showShortToast(this, "请输入验证码");
                return;
            }
            if (pwdType == 1) {
                verificationVerifyModel.verificationVerify(MyApp.manager.getPhone(), verificationCode);
            } else {
                updateBindPhoneModel.getUpdateBindPhone(MyApp.manager.getPhone(), newPhone, verificationCode);
            }
        } else if (id == R.id.txt_send_second) {
            if (pwdType == NUM_2) {
                verificationCodeModel.getVerificationCode(newPhone);
            } else {
                verificationCodeModel.getVerificationCode(MyApp.manager.getPhone());
            }
        }
    }

    /**
     * 重新获取验证码
     */
    public void getNewCode() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_get_code, null);
        TextView tvGetNewCode = (TextView) dialogView.findViewById(R.id.tv_get_new_code);
        TextView tvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);

        tvGetNewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationCodeModel.getVerificationCode(MyApp.manager.getPhone());
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(dialogView);
        dialog.show();
    }


    @Override
    public void onVerificationCode(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            timer.start();
        }
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
    }

    @Override
    public void onUpdateBindPhone(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            MyApp.manager.setPhone(newPhone);
            finish();
        }
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
    }

    @Override
    public void verificationVerify(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            SettingLoginPwdActivity.getJumpSettingLoginPwdActivity(this, pwdType);
            finish();
        }
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
    }
}
