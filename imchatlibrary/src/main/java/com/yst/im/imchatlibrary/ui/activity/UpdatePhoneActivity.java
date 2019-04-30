package com.yst.im.imchatlibrary.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.widget.AbstractImChargeDialog;

/**
 * 更换手机号
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/11.
 */

public class UpdatePhoneActivity extends BaseActivity{

    private AbstractTitleView titleViewSetUpdatePhone;
    private EditText etUpdatePhone;
    private TextView txtUpdatePhonemsg;
    private AbstractImChargeDialog abstractImChargeDialog;
    private boolean isClick = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        titleViewSetUpdatePhone = (AbstractTitleView) findViewById(R.id.titleview_set_update_phone);
        etUpdatePhone = (EditText) findViewById(R.id.et_update_phone);
        txtUpdatePhonemsg = (TextView) findViewById(R.id.txt_update_phone_msg);

        titleViewSetUpdatePhone.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleViewSetUpdatePhone.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BaseUtils.isMobileNO(etUpdatePhone.getText().toString())) {
                    abstractImChargeDialog.setIsVisiable(true, false);
                    abstractImChargeDialog.setText("手机号错误", "你输入的是一个无效的手机号码");
                    isClick = false;
                }else if (MyApp.manager.getPhone().equals(etUpdatePhone.getText().toString())){
                    abstractImChargeDialog.setIsVisiable(false, false);
                    abstractImChargeDialog.setText("", "该手机号与当前绑定的手机号相同");
                    isClick = false;
                }else {
                    abstractImChargeDialog.setIsVisiable(true, true);
                    abstractImChargeDialog.setText("确认手机号码", "我们将发送验证码短信到这个号码：" + etUpdatePhone.getText().toString().trim());
                    isClick = true;
                }
                abstractImChargeDialog.showDialog();
            }
        });
        abstractImChargeDialog = new AbstractImChargeDialog(this){
            @Override
            public void sureClick() {
                super.sureClick();
                if (isClick) {
                    WriteCodeActivity.getJumpWriteCodeActivity(UpdatePhoneActivity.this, 2, etUpdatePhone.getText().toString().trim());
                    finish();
                }
            }
        };
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        txtUpdatePhonemsg.setText("更换手机后，下次登录可使用新手机号登录，当前手机号：" + MyApp.manager.getPhone());
    }
}
