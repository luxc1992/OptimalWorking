package com.yst.im.imchatlibrary.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.model.QuitLoginModel;
import com.yst.im.imchatlibrary.model.VerifyPasswordModel;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.FileSizeUtil;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.widget.AbstractImChargeDialog;
import com.yst.im.imchatlibrary.widget.CustomAlterDialogUtil;
import com.yst.im.imsdk.MsClient;
import com.yst.im.imsdk.dao.GreenDaoManager;

import java.io.File;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 设置手机号，登录密码
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/28.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener,
        QuitLoginModel.QuitLoginListenerCallBack, VerifyPasswordModel.VerifyPassListenerCallBack {
    private TextView txtMyPhone;
    private TextView txtCache;
    private QuitLoginModel quitLoginModel;
    private AbstractImChargeDialog abstractImChargeDialog;
    private VerifyPasswordModel verifyPasswordModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        AbstractTitleView titleViewSetting = (AbstractTitleView) findViewById(R.id.titleview_setting);
        LinearLayout layoutSetMyPhone = (LinearLayout) findViewById(R.id.layout_set_my_phone);
        LinearLayout layoutSetMyPwd = (LinearLayout) findViewById(R.id.layout_set_my_pwd);
        LinearLayout layoutClearCache = (LinearLayout) findViewById(R.id.layout_clear_cache);
        TextView txtExitLogin = (TextView) findViewById(R.id.txt_exit_login);
        txtMyPhone = (TextView) findViewById(R.id.txt_my_phone);
        txtCache = (TextView) findViewById(R.id.txt_cache);
        titleViewSetting.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layoutSetMyPhone.setOnClickListener(this);
        layoutSetMyPwd.setOnClickListener(this);
        layoutClearCache.setOnClickListener(this);
        txtExitLogin.setOnClickListener(this);
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        quitLoginModel = new QuitLoginModel(this);
        quitLoginModel.setQuitLoginListenerCallBack(this);
        verifyPasswordModel = new VerifyPasswordModel(this);
        verifyPasswordModel.setVerifyPassListenerCallBack(this);
        abstractImChargeDialog = new AbstractImChargeDialog(this) {
            @Override
            public void sureClick() {
                super.sureClick();
                String trim = abstractImChargeDialog.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ImToastUtil.showShortToast(SettingActivity.this, "请输入登录密码");
                    return;
                }
                verifyPasswordModel.getVerifyPass(MyApp.manager.getPhone(), abstractImChargeDialog.getEditText().getText().toString().trim());
            }

            @Override
            public void closeClick() {
                super.closeClick();
                abstractImChargeDialog.getEditText().setText("");
            }
        };
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.layout_set_my_phone) {
            abstractImChargeDialog.setIsVisiable(true, true, true);
            abstractImChargeDialog.setText("更改手机号码", "");
            abstractImChargeDialog.showDialog();
        } else if (id == R.id.layout_set_my_pwd) {
            SettingLoginPwdActivity.getJumpSettingLoginPwdActivity(this, 0);
        } else if (id == R.id.layout_clear_cache) {
            File dbFile = new File("/data/data/com.yst.im.demo/databases/im_db");
            if (dbFile.exists()) {
                dbFile.delete();
            }
            CustomAlterDialogUtil.clearCache(this);
            @SuppressLint("SdCardPath") String filePath = "/data/data/com.yst.im.demo/databases/im_db";
            String autoFileOrFilesSize = FileSizeUtil.getAutoFileOrFilesSize(filePath);
            txtCache.setText(autoFileOrFilesSize);
            Log.e("im", "initData: " + autoFileOrFilesSize);
            GreenDaoManager.init(MyApp.getInstance());
        } else if (id == R.id.txt_exit_login) {
            MyApp.getMsClient().isUserOut = true;
            exitLoginDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtMyPhone.setText(MyApp.manager.getPhone());
        @SuppressLint("SdCardPath") String filePath = "/data/data/com.yst.im.demo/databases/im_db";
        String autoFileOrFilesSize = FileSizeUtil.getAutoFileOrFilesSize(filePath);
        txtCache.setText(autoFileOrFilesSize);
        Log.e("im", "initData: " + autoFileOrFilesSize);
    }

    /**
     * 退出登录
     */
    public void exitLoginDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_exitlogin, null);
        TextView tvPhotoAlbum = (TextView) dialogView.findViewById(R.id.tv_exit_login);
        TextView tvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);

        tvPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitLoginModel.getQuitLogin();
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(dialogView);
        dialog.show();
    }

    @Override
    public void onQuitLogin(BaseEntity baseEntity) {
        MyApp.manager.setUserId("");
        MyApp.manager.setId("");
        MyApp.manager.setToken("");
        MyApp.manager.setNickName("");
        MyApp.manager.setUserIcon("");
        MyApp.manager.setPhone("");
        MyApp.manager.setPassword("");
        MyApp.manager.setSex("");
        MyApp.getMsClient().isUserOut=true;
        MyApp.getMsClient().close();
        MyApp.resetMsClient();
        Intent intent = new Intent("com.yst.im.login");
        sendBroadcast(intent);
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
    }

    @Override
    public void onVerifyPass(BaseEntity baseEntity) {
        abstractImChargeDialog.getEditText().setText("");
        if (baseEntity.getCode() == NUM_0) {
            abstractImChargeDialog.dismissDialog();
            JumpIntent.jump(SettingActivity.this, UpdatePhoneActivity.class);
        } else {
            ImToastUtil.showShortToast(this, baseEntity.getMsg());
        }
    }
}
