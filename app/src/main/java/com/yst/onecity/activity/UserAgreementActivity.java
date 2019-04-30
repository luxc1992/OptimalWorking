package com.yst.onecity.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;

import butterknife.BindView;

/**
 * 用户协议页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/23
 */

public class UserAgreementActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.web_home)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_agreement;
    }

    @Override
    public void initData() {
        initTitleBar("用户协议");
        webview.loadUrl("file:///android_asset/useraggerment.html");
    }
}
