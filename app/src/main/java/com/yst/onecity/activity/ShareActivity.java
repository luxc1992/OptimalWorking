package com.yst.onecity.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;

import butterknife.BindView;

/**
 * 分享页面
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/2
 */

public class ShareActivity extends BaseActivity {
    @BindView(R.id.web_prentice)
    WebView webPrentice;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initData() {
        WebSettings webSettings = webPrentice.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(false);
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webPrentice.loadUrl(H5Const.H5_FENXIANG);
    }

}
