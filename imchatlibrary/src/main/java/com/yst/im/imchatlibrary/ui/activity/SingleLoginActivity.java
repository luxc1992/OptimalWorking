package com.yst.im.imchatlibrary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.MsClient;
import com.yst.im.imsdk.dao.GreenDaoManager;

/**
 * 单点登录弹框
 *
 * @author Lierpeng
 * @date 2018/3/28.
 * @version 1.0.0
 */
public class SingleLoginActivity extends Activity implements View.OnClickListener {
    private LinearLayout mLlRoot;
    private LinearLayout mLlDialog;
    private Button mBtConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_login);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }

    private void initView() {
        GreenDaoManager.getInstance().getmDaoSession().clear();
        mLlRoot = (LinearLayout) findViewById(R.id.ll_root);
        mLlDialog = (LinearLayout) findViewById(R.id.ll_dialog);
        mBtConfirm = (Button) findViewById(R.id.bt_dismiss);
        mLlRoot.setOnClickListener(this);
        mLlDialog.setOnClickListener(this);
        mBtConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent("com.yst.im.login");
        // 发送广播
        sendBroadcast(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
