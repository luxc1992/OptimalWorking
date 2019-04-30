package com.yst.onecity.activity.mine.accountsafe;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/7.
 */

public class UpdatePhoneNumActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_update_phone_num;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.update_phone_num_title));
    }
}
