package com.yst.onecity.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.yst.basic.framework.utils.ToastUtils;
import com.lxc.sharelibrary.ShareActivity;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.mine.ContactFriendsActivity;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.MyLog;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.Constant.COMMA;
import static com.yst.onecity.Constant.shareContent;
import static com.yst.onecity.Constant.shareTitle;

/**
 * 收徒有奖
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/27
 */

public class RecruitActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.web_reciurt)
    WebView webReciurt;

    private static String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    private static String WEIXIN_PACKAGE_NAME = "com.tencent.mm";
    private static String SINA_PACKAGE_NAME = "com.sina.weibo";

    private static String contactPermission = Manifest.permission.READ_CONTACTS;
    private static String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static String writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private String shareUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reciurt;
    }

    @Override
    public void initData() {
        CommonUtils.setWebSettings(RecruitActivity.this, webReciurt, H5Const.H5_SHOUTU);
        webReciurt.addJavascriptInterface(new JavaScriptInterface(), "stub");
    }

    class JavaScriptInterface {
        /**
         * 安卓系统4.2以上的系统需要加上     @JavascriptInterface，才可以让webview读取自己的方法
         *
         * @param
         */
        @JavascriptInterface
        public void jsMethod(final String str) {
            MyLog.e("ssss",shareUrl);
            if (TextUtils.isEmpty(str) || !str.contains(COMMA)) {
                return;
            }
            if (!App.manager.getLoginState()) {
                JumpIntent.jump(RecruitActivity.this, LoginActivity.class);
                return;
            }
            shareUrl = str.split(",")[0] + "?phone=" + App.manager.getPhoneNum() + "&token=" + MyApp.manager.getToken();
            String type = str.split(",")[1];
            switch (type) {
                case "wx":
                    if (!Utils.isAvilible(context, WEIXIN_PACKAGE_NAME)){
                        ToastUtils.show("未安装微信");
                        return;
                    }
                    RecruitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.WEIXIN, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                        }
                    });
                    break;
                case "wxc":
                    if (!Utils.isAvilible(context, WEIXIN_PACKAGE_NAME)){
                        ToastUtils.show("未安装微信");
                        return;
                    }
                    RecruitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.WEIXIN_CIRCLE, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                        }
                    });
                    break;
                case "qq":
                    if (!Utils.isAvilible(context, QQ_PACKAGE_NAME)){
                        ToastUtils.show("未安装QQ");
                        return;
                    }
                    RecruitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (EasyPermissions.hasPermissions(RecruitActivity.this, readExternalStoragePermission,writeExternalStoragePermission)) {
                                    ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QQ, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                                } else {
                                    EasyPermissions.requestPermissions(RecruitActivity.this, "请打开存储权限", 1000, readExternalStoragePermission,writeExternalStoragePermission);
                                }
                            } else {
                                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QQ, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                            }
                        }
                    });
                    break;
                case "qzone":
                    if (!Utils.isAvilible(context, QQ_PACKAGE_NAME)){
                        ToastUtils.show("未安装QQ");
                        return;
                    }
                    RecruitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (EasyPermissions.hasPermissions(RecruitActivity.this, readExternalStoragePermission,writeExternalStoragePermission)) {
                                    ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QZONE, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                                } else {
                                    EasyPermissions.requestPermissions(RecruitActivity.this, "请打开存储权限", 1001, readExternalStoragePermission,writeExternalStoragePermission);
                                }
                            } else {
                                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QZONE, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                            }

                        }
                    });
                    break;
                case "xl":
                    if (!Utils.isAvilible(context, SINA_PACKAGE_NAME)){
                        ToastUtils.show("未安装微博");
                        return;
                    }
                    RecruitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.SINA, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                        }
                    });
                    break;
                case "dx":
                    RecruitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (EasyPermissions.hasPermissions(RecruitActivity.this, contactPermission)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("shareUrl", shareUrl);
                                    JumpIntent.jump(RecruitActivity.this, ContactFriendsActivity.class, bundle);
                                } else {
                                    EasyPermissions.requestPermissions(RecruitActivity.this, "请打开通讯录权限", 1002, contactPermission);
                                }
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("shareUrl", shareUrl);
                                JumpIntent.jump(RecruitActivity.this, ContactFriendsActivity.class, bundle);
                            }
                        }
                    });

                    break;
                default:
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case 1000:
                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QQ, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                break;
            case 1001:
                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QZONE, RecruitActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                break;
            case 1002:
                Bundle bundle = new Bundle();
                bundle.putString("shareUrl", shareUrl);
                JumpIntent.jump(RecruitActivity.this, ContactFriendsActivity.class, bundle);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.show(perms + "权限被禁用，请到设置里打开");
    }


}
