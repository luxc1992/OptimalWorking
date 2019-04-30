package com.yst.onecity.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.activity.login.LoginActivity;

import java.util.HashMap;

import static com.yst.onecity.Constant.HTTP;
import static com.yst.onecity.Constant.HTTPS;
import static com.yst.onecity.Constant.SPACE;

/**
 * 功能描述
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/9.
 */

public class CommonUtils {

    /**
     * 限定 输入框输入长度
     *
     * @param editText      editText
     * @param length        长度
     * @param isFilterSpace 是否过滤空格
     */
    public static void setEditTextInputLength(EditText editText, int length, boolean isFilterSpace) {
        if (editText == null) {
            return;
        }
        if (isFilterSpace) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length), spaceFilter});
        } else {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        }
    }

    /**
     * 获取输入框内容的长度
     *
     * @param editText editText
     * @return 内容的长度
     */
    public static int getEditTextInputLength(EditText editText) {
        if (editText == null) {
            return 0;
        }
        return editText.getText().toString().trim().length();
    }

    /**
     * 获取输入框内容
     *
     * @param editText editText
     * @return 内容
     */
    public static String getEditTextInputContent(EditText editText) {
        if (editText == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }


    /**
     * 过滤空格filter
     */
    private static InputFilter spaceFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
            if (SPACE.equals(source)) {
                return "";
            } else {
                return null;
            }
        }
    };

    public static void setWebSettings(Activity context, WebView webview, String loadUrl) {
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况
        context.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        com.tencent.smtt.sdk.WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(false);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        //开启js本地存储
        webview.getSettings().setDomStorageEnabled(true);
        // 修改webview的UserAgent
        webview.getSettings().getUserAgentString();
        webview.getSettings().setUserAgentString("pujiyicheng");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                if (url == null) {
                    return false;
                }
                try {
                    if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
                        view.loadUrl(url);
                        return true;
                    } else {

                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }
        });
        //阻止出现长按复制
        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        //禁止(直接或反射)调用，避免视频画面无法显示：
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webview.setDrawingCacheEnabled(true);
        webview.loadUrl(loadUrl);
    }

    /**
     * 为js设置用户信息
     *
     * @return 用户信息
     */
    public static String jsToClientSetUserInfo() {
        App.isLogin = App.manager.getLoginState();
        HashMap<String, Object> userInfo = new HashMap<>(16);
        String userInfoJson;
        if (App.isLogin) {
            userInfo.put("id", String.valueOf(App.manager.getId()));
            userInfo.put("uuid", App.manager.getUuid());
            userInfo.put("phone", App.manager.getPhoneNum());
            userInfo.put("token", App.manager.getToken());
            userInfoJson = JsonUtil.stringToJson(userInfo);

        } else {
            userInfoJson = "null";
        }
        MyLog.e("sssss", "userinfo--" + userInfoJson);
        return userInfoJson;
    }

    /**
     * @param activity 当前actiivty
     * @author chenjiadi
     * version 1.0.1
     */
    public static void goLogin(Activity activity) {
        JumpIntent.jump(activity, LoginActivity.class);
    }


    /**
     * 复制文本框中的内容到粘贴板
     *
     * @param textView textview
     */
    public static void onClickCopy(Context context, TextView textView) {

        //添加到剪切板
        ClipboardManager clipboardManager = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        /**之前的应用过期的方法，clipboardManager.setText(copy);*/
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, textView.getText()));
        if (clipboardManager.hasPrimaryClip()) {
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }

        ToastUtils.show("复制成功");
    }
}
