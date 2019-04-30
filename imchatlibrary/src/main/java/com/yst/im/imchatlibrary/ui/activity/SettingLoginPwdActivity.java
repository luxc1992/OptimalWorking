package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.model.ForgetChangePasswordModel;
import com.yst.im.imchatlibrary.model.ForgotPasswordModel;
import com.yst.im.imchatlibrary.model.UpdatePasswordModel;
import com.yst.im.imchatlibrary.model.VerificationCodeModel;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.widget.AbstractImChargeDialog;

import java.io.EOFException;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;

/**
 * 设置登录密码
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/29.
 */
public class SettingLoginPwdActivity extends BaseActivity implements UpdatePasswordModel.UpdatePasswordListenerCallBack, ForgetChangePasswordModel.ForgetChangePasswordModelCallBack {

    private AbstractTitleView titleViewSetLoginPwd;
    private TextView txtForgetPwd;
    private TextView txtForgetPwdmsg;
    private TextView txtForgetPwdmsg1;
    private TextView txtSetLoginPhone;
    private EditText edtInputOldPwd;
    private EditText edtInputNewPwd;
    private EditText edtInputAginpwd;
    private AbstractImChargeDialog abstractImChargeDialog;
    private LinearLayout llInputOldPwd;
    /**
     * 来源类型， 0 正常设置登陆密码， 1忘记登录密码,设置新密码
     */
    private int pwdType = 0;
    private boolean isCheDan = false;
    private UpdatePasswordModel updatePasswordModel;
    private ForgetChangePasswordModel forgetChangePasswordModel;

    /**
     * 页面跳转
     *
     * @param context 上下文
     * @param pwdType 跳转来源类型
     */
    public static void getJumpSettingLoginPwdActivity(Context context, int pwdType) {
        Intent intent = new Intent(context, SettingLoginPwdActivity.class);
        intent.putExtra("pwdType", pwdType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_set_login_pwd;
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        titleViewSetLoginPwd = (AbstractTitleView) findViewById(R.id.titleview_set_login_pwd);
        txtForgetPwd = (TextView) findViewById(R.id.txt_forget_pwd);
        txtForgetPwdmsg = (TextView) findViewById(R.id.txt_forget_pwd_msg);
        txtForgetPwdmsg1 = (TextView) findViewById(R.id.txt_forget_pwd_msg1);
        edtInputOldPwd = (EditText) findViewById(R.id.edt_input_old_pwd);
        edtInputNewPwd = (EditText) findViewById(R.id.edt_input_new_pwd);
        edtInputAginpwd = (EditText) findViewById(R.id.edt_input_agin_pwd);
        llInputOldPwd = (LinearLayout) findViewById(R.id.ll_input_old_pwd);
        txtSetLoginPhone = (TextView) findViewById(R.id.txt_set_login_phone);

        titleViewSetLoginPwd.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleViewSetLoginPwd.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPwd = edtInputOldPwd.getText().toString().trim();
                String newPwd = edtInputNewPwd.getText().toString().trim();
                String aginNewPwd = edtInputAginpwd.getText().toString().trim();
                if (pwdType == NUM_1) {
                    oldPwd = "111mmm";
                }
                if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(aginNewPwd)) {
                    abstractImChargeDialog.setIsVisiable(false, true);
                    abstractImChargeDialog.setText("手机号错误", "请输入正确信息");
                    abstractImChargeDialog.showDialog();
                }else if (BaseUtils.isNumericZidai(newPwd) || BaseUtils.isNumericZidai(aginNewPwd)) {
                    abstractImChargeDialog.setIsVisiable(false, true);
                    abstractImChargeDialog.setText("手机号错误", "密码必须是8—16位英文字母，数字（不能是纯数字）");
                    abstractImChargeDialog.showDialog();
                }else if (!aginNewPwd.equals(newPwd)) {
                    abstractImChargeDialog.setIsVisiable(false, true);
                    abstractImChargeDialog.setText("手机号错误", "两次填写的密码不一致");
                    abstractImChargeDialog.showDialog();
                }else {
                    try {
                        if (pwdType == 1) {
                            forgetChangePasswordModel.changePassword(MyApp.manager.getPhone(), aginNewPwd);
                        }else {
                            updatePasswordModel.getUpdatePassword(oldPwd, aginNewPwd, MyApp.manager.getPhone());
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        txtForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abstractImChargeDialog.setIsVisiable(false, true);
                abstractImChargeDialog.setText("手机号错误", "你当前已经绑定手机号，可通过 短信验证码重置密码，即将发送 验证码到" + MyApp.manager.getPhone());
                txtForgetPwd.setVisibility(View.GONE);
                txtForgetPwdmsg.setVisibility(View.GONE);
                txtForgetPwdmsg1.setVisibility(View.VISIBLE);
                abstractImChargeDialog.showDialog();
                isCheDan = true;
            }
        });

        abstractImChargeDialog = new AbstractImChargeDialog(this) {
            @Override
            public void sureClick() {
                super.sureClick();
                /** 如果点击了忘记旧密码 */
                if (isCheDan) {
                    finish();
                    WriteCodeActivity.getJumpWriteCodeActivity(SettingLoginPwdActivity.this, 1, "");
                }
            }

            @Override
            public void closeClick() {
                super.closeClick();
                if (isCheDan) {
                    txtForgetPwd.setVisibility(View.VISIBLE);
                    txtForgetPwdmsg.setVisibility(View.VISIBLE);
                    txtForgetPwdmsg1.setVisibility(View.GONE);
                    isCheDan = false;
                }
            }
        };

    }

    @Override
    protected void initData() {
        pwdType = getIntent().getIntExtra("pwdType", 0);
        txtSetLoginPhone.setText("手机号：" + MyApp.manager.getPhone());
        if (pwdType == NUM_1) {
            llInputOldPwd.setVisibility(View.GONE);
            txtForgetPwd.setVisibility(View.GONE);
            forgetChangePasswordModel = new ForgetChangePasswordModel(this);
            forgetChangePasswordModel.setForgetChangePasswordModelCallBack(this);
            txtForgetPwdmsg.setText("如果你已经忘记登录密码，可以在本页面重置登录密码。后续可通过手机号加微信密码登录。");
        } else {
            updatePasswordModel = new UpdatePasswordModel(this);
            updatePasswordModel.setUpdatePasswordListenerCallBack(this);
        }
    }

    @Override
    public void onUpdatePassword(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            ImToastUtil.showShortToast(this, baseEntity.getMsg());
            MyApp.manager.setPassword(edtInputAginpwd.getText().toString().trim());
            finish();
        } else {
            abstractImChargeDialog.setIsVisiable(false, true);
            abstractImChargeDialog.setText("手机号错误", "账号或密码错误，请重新填写");
            abstractImChargeDialog.showDialog();
        }
    }

    @Override
    public void changePassword(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            MyApp.manager.setPassword(edtInputAginpwd.getText().toString().trim());
            finish();
        }
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
    }
}
