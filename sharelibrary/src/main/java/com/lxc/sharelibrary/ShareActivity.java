package com.lxc.sharelibrary;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * 分享页面
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/02/06
 */
public class ShareActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private int screenHeight;
    private int screenWidth;
    private boolean isCollection;
    private View shareDialog;
    private Dialog showDialogToClearCache;
    private Context context;
    private String title;
    private String content;
    private String url;
    private String imagePath;
    private Handler handler;
    private int resId;
    private boolean isNetImage = false;
    private static ShareActivity shareActivity = null;
    private static String WEIXIN_FAVORITE = "WEIXIN_FAVORITE";
    private static String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    private static String WEIXIN_PACKAGE_NAME = "com.tencent.mm";
    private static String SINA_PACKAGE_NAME = "com.sina.weibo";
    private static String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static String writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static TextView collectionStatus;
    private boolean type = false;

    public static ShareActivity getInstance() {
        if (shareActivity == null) {
            return new ShareActivity();
        } else {
            return shareActivity;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().init();
    }

    /**
     * 分享带有网络图片
     *
     * @param context   上下文
     * @param title     标题
     * @param content   内容
     * @param url       分享地址链接
     * @param imagePath 图片地址
     */
    public void popShare(Context context, String title, String content, String url, String imagePath) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.url = url;
        this.imagePath = imagePath;
        isNetImage = true;
        initView();
    }

    /**
     * 分享带有网络图片  且带有 收藏 举报按钮的分享弹窗
     *
     * @param context      上下文
     * @param handler      handler 用来和UI交互
     * @param title        标题
     * @param isCollection 是否已收藏
     * @param content      内容
     * @param url          分享地址链接
     * @param imagePath    本地资源图片id
     */
    public void popShare(Context context, Handler handler, boolean isCollection, String title, String content, String url, String imagePath) {
        this.context = context;
        this.handler = handler;
        this.isCollection = isCollection;
        this.title = title;
        this.content = content;
        this.url = url;
        this.imagePath = imagePath;
        isNetImage = true;
        initView();
    }

    /**
     * 分享本地资源图片
     *
     * @param context 上下文
     * @param title   标题
     * @param content 内容
     * @param url     分享地址链接
     * @param resId   本地资源图片id
     */
    public void popShare(Context context, String title, String content, String url, int resId) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.url = url;
        this.resId = resId;
        initView();
    }

    /**
     * 分享本地资源图片 且带有 收藏 举报按钮的分享弹窗
     *
     * @param context      上下文
     * @param handler      handler 用来和UI交互
     * @param isCollection 是否已收藏
     * @param title        标题
     * @param content      内容
     * @param url          分享地址链接
     * @param resId        本地资源图片id
     */
    public void popShare(Context context, Handler handler, boolean isCollection, String title, String content, String url, int resId) {
        this.context = context;
        this.title = title;
        this.handler = handler;
        this.isCollection = isCollection;
        this.content = content;
        this.url = url;
        this.resId = resId;
        initView();
    }

    private void initView() {
        screenHeight = getScreenHeight(context);
        screenWidth = getScreenWidth(context);
        if (shareDialog == null) {
            shareDialog = LayoutInflater.from(context).inflate(R.layout.activity_my_share, null);
        }
        if (showDialogToClearCache == null) {
            showDialogToClearCache = new Dialog(context, R.style.BottomDialog_Animation);
            showDialogToClearCache.setContentView(shareDialog);
            android.view.WindowManager.LayoutParams p = showDialogToClearCache.getWindow().getAttributes();
            // 高度设置为屏幕的0.3
            p.height = screenHeight;
            // 宽度设置为屏幕的0.5
            p.width = screenWidth;
            // 设置生效
            showDialogToClearCache.getWindow().setAttributes(p);
            showDialogToClearCache.setCanceledOnTouchOutside(false);
            RelativeLayout disPop = shareDialog.findViewById(R.id.dis_pop);
            RelativeLayout shareWeChat = shareDialog.findViewById(R.id.share_wechat);
            RelativeLayout shareWeChatCircle1 = shareDialog.findViewById(R.id.share_wechat_circle1);
            RelativeLayout shareQQ = shareDialog.findViewById(R.id.share_qq);
            RelativeLayout shareQZone = shareDialog.findViewById(R.id.share_qzone);
            RelativeLayout shareSina = shareDialog.findViewById(R.id.share_sina);
            LinearLayout llCopyView = shareDialog.findViewById(R.id.ll_copy_view);
            RelativeLayout copyUrl = shareDialog.findViewById(R.id.copy_url);
            RelativeLayout collection = shareDialog.findViewById(R.id.collection);
            collectionStatus = shareDialog.findViewById(R.id.tv_collection_status);
            RelativeLayout report = shareDialog.findViewById(R.id.report);
            if (isCollection) {
                collectionStatus.setText("取消收藏");
            } else {
                collectionStatus.setText("收藏");
            }
            if (handler != null) {
                llCopyView.setVisibility(View.VISIBLE);
            } else {
                llCopyView.setVisibility(View.GONE);
            }

            disPop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogToClearCache.dismiss();
                }
            });
            shareWeChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAvilible(context, WEIXIN_PACKAGE_NAME)) {
                        share(SHARE_MEDIA.WEIXIN, isNetImage);
                    } else {
                        Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            shareWeChatCircle1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAvilible(context, WEIXIN_PACKAGE_NAME)) {
                        share(SHARE_MEDIA.WEIXIN_CIRCLE, isNetImage);
                    } else {
                        Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            shareQQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAvilible(context, QQ_PACKAGE_NAME)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (EasyPermissions.hasPermissions(context, readExternalStoragePermission, writeExternalStoragePermission)) {
                                share(SHARE_MEDIA.QQ, isNetImage);
                            } else {
                                EasyPermissions.requestPermissions((Activity) context, "请打开存储权限", 1000, readExternalStoragePermission, writeExternalStoragePermission);
                            }
                        } else {
                            share(SHARE_MEDIA.QQ, isNetImage);
                        }
                    } else {
                        Toast.makeText(context, "未安装QQ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            shareQZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAvilible(context, QQ_PACKAGE_NAME)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (EasyPermissions.hasPermissions(context, readExternalStoragePermission, writeExternalStoragePermission)) {
                                share(SHARE_MEDIA.QZONE, isNetImage);
                            } else {
                                EasyPermissions.requestPermissions((Activity) context, "请打开存储权限", 1001, readExternalStoragePermission, writeExternalStoragePermission);
                            }
                        } else {
                            share(SHARE_MEDIA.QZONE, isNetImage);
                        }
                    } else {
                        Toast.makeText(context, "未安装QQ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            shareSina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAvilible(context, SINA_PACKAGE_NAME)) {
                        share(SHARE_MEDIA.SINA, isNetImage);
                    } else {
                        Toast.makeText(context, "未安装微博", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // 复制链接
            copyUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendMessage(handler.obtainMessage(6, url));
                    showDialogToClearCache.dismiss();
                }
            });

            // 收藏
            collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendMessage(handler.obtainMessage(7, ""));
                    showDialogToClearCache.dismiss();
                }
            });

            // 举报
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendMessage(handler.obtainMessage(8, ""));
                    showDialogToClearCache.dismiss();

                }
            });
        }
        if(showDialogToClearCache != null) {
            showDialogToClearCache.show();
        }
    }

    /**
     * 隐藏分享朋友圈
     */
    public void setNoCircleOfFriends(boolean b) {
        type = b;
    }

    /**
     * 网络图片
     *
     * @return UMImage 对象
     */
    private UMImage getImageUrl() {
        UMImage image = new UMImage(context, imagePath);
        return image;
    }

    /**
     * 本地资源图片
     *
     * @return image
     */
    private UMImage getImageResource() {
        UMImage image = new UMImage(context, resId);
        return image;
    }

    /**
     * 单个平台分享
     *
     * @param shareMedia 分享的平台  eg: SHARE_MEDIA.QQ
     * @param context    上下文
     * @param title      标题
     * @param content    内容
     * @param url        链接
     * @param imgPath    图片地址  如果是本地图片  传  null
     * @param resId      资源id
     */
    public void sharePlatform(SHARE_MEDIA shareMedia, Context context, String title, String content, String url, String imgPath, int resId) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.url = url;
        this.imagePath = imgPath;
        this.resId = resId;
        isNetImage = !TextUtils.isEmpty(imagePath);
        share(shareMedia, isNetImage);
    }

    /**
     * 开启自定义分享页面
     *
     * @param shareMedia 分享的平台
     * @param isNetImage 是否是网络图片
     */
    private void share(SHARE_MEDIA shareMedia, Boolean isNetImage) {
        ShareAction shareAction = new ShareAction((Activity) context);
        shareAction.setPlatform(shareMedia)
                .setCallback(umShareListener)
                .withTitle(title)
                .withText(TextUtils.isEmpty(content) ? title : content)
                .withTargetUrl(url);
        if (isNetImage) {
            shareAction.withMedia(getImageUrl());
        } else {
            shareAction.withMedia(getImageResource());
        }
        shareAction.share();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {

            Log.e("plat", "platform" + platform);
            if (WEIXIN_FAVORITE.equals(platform.name())) {
            } else {
                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                Log.e("hahahaha", "platform.name()------" + platform.name());
                EventBus.getDefault().post(new EventBean("sharerefresh"));
                if (showDialogToClearCache != null) {
                    showDialogToClearCache.dismiss();
                }
            }

            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
            finish();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return 高度px
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 高度px
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 分享本地资源图片 且带有 收藏 举报按钮的分享弹窗
     *
     * @param context 上下文
     * @param handler handler 用来和UI交互
     * @param title   标题
     * @param content 内容
     * @param url     分享地址链接
     * @param resId   本地资源图片id
     */
    public void popShare(Context context, Handler handler, String title, String content, String url, int resId) {
        this.context = context;
        this.title = title;
        this.handler = handler;
        this.content = content;
        this.url = url;
        this.resId = resId;
        initView();
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
                share(SHARE_MEDIA.QQ, isNetImage);
                break;
            case 1001:
                share(SHARE_MEDIA.QZONE, isNetImage);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
