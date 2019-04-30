package com.yst.onecity.activity.mine.accountsafe;

import android.os.Bundle;
import android.view.View;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;

import butterknife.OnClick;

/**
 * 密码设置
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/8.
 */

public class SetPwdActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.pwd_set));
    }

    @OnClick({R.id.view_update_trade_pwd, R.id.view_forget_trade_pwd})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.view_update_trade_pwd:
                bundle.putInt("type", 3);
                JumpIntent.jump(this, SetLoginPwdActivity.class, bundle);
                break;
            case R.id.view_forget_trade_pwd:
                bundle.putInt("type", 4);
                JumpIntent.jump(this, SetLoginPwdActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
