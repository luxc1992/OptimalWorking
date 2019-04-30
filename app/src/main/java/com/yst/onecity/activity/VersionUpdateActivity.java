package com.yst.onecity.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.CheckVersionBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.service.DownloadService;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.PermissionUtil;
import com.yst.onecity.view.AbstractVersionCheckDialog;
import com.yst.onecity.view.TipsDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yst.onecity.Constant.NO2;

/**
 * 版本更新页面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/11.
 */
public class VersionUpdateActivity extends BaseActivity {

    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;
    @BindView(R.id.tv_new_version_code)
    TextView tvNewVersionCode;
    @BindView(R.id.tv_version_time)
    TextView tvVersionTime;
    @BindView(R.id.tv_version_content)
    LinearLayout tvVersionContent;
    @BindView(R.id.tv_check_version)
    TextView tvCheckVersion;

    /**
     * 版本更新显示界面
     */
    private TipsDialog tipsDialog;
    /**
     * 更新时用到的进度条等
     */
    private ProgressBar progressBar;
    /**
     * 显示进度的view
     */
    private TextView progressText;
    /**
     * 下载apk线程
     */
    private Thread thread;
    /**
     * 进度数
     */
    public static int loadingProgress;
    private File file;
    private AbstractVersionCheckDialog dialog;
    private String downloadApkUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_version_update;
    }

    @Override
    public void initData() {
        initTitleBar("版本更新");
        //当前版本
        if (!TextUtils.isEmpty(Utils.getVerCode(VersionUpdateActivity.this))) {
            String verCode = "当前版本: V" + Utils.getVerCode(VersionUpdateActivity.this);
            tvVersionCode.setText(verCode);
        }

        RequestApi.myCheckVerson(ConstUtils.getStringNoEmpty(Utils.getVerCode(VersionUpdateActivity.this)), "1", 0, new AbstractNetWorkCallback<CheckVersionBean>() {
            private TextView textView;
            @Override
            public void onSuccess(CheckVersionBean checkVersionBean) {
                if (checkVersionBean.getContent() != null) {
                    String newVerCode = "最新版本: V" + checkVersionBean.getContent().getNewVersion();
                    tvNewVersionCode.setText(newVerCode);
                    tvVersionTime.setText(checkVersionBean.getContent().getTime());
                    String[] split = checkVersionBean.getContent().getUpdateDetias().split("/n");
                    for (String explain:split) {
                        textView = new TextView(VersionUpdateActivity.this);
                        textView.setText(explain);
                        tvVersionContent.addView(textView);
                    }
                }else {
                    ToastUtils.show(checkVersionBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @OnClick({R.id.tv_check_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //检查版本更新
            case R.id.tv_check_version:
                getCheckVersion();
                break;
            default:
                break;
        }
    }

    /**
     * 版本检测
     */
    private void getCheckVersion() {
        RequestApi.myCheckVerson(ConstUtils.getStringNoEmpty(Utils.getVerCode(VersionUpdateActivity.this)), "1", 0, new AbstractNetWorkCallback<CheckVersionBean>() {
            @Override
            public void onSuccess(CheckVersionBean checkVersionBean) {
                //code 1.当前为最新版本   2.有更新
                if (checkVersionBean.getCode() == 1) {
                    if (checkVersionBean.getContent() != null) {
                        ToastUtils.show(checkVersionBean.getMsg());
                    }
                }else {
                    ToastUtils.show(checkVersionBean.getMsg());
                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Constant.APP_NAME);
                    if (file.exists()) {
                        Log.d("update", "delete exist file");
                        file.delete();
                    }
                    downloadApkUrl = checkVersionBean.getContent().getUrl();
                    //是否强制更新
                    boolean isStrong;
                    isStrong = (checkVersionBean.getContent().getForcedUpdate() == NO2);
                    dialog = new AbstractVersionCheckDialog(VersionUpdateActivity.this, isStrong) {
                        @Override
                        public void sureClick() {
                            if(TextUtils.isEmpty(downloadApkUrl)){
                                ToastUtils.show("下载失败");
                                return;
                            }
                            setDialog(Gravity.CENTER, (WindowUtils.getScreenWidth(VersionUpdateActivity.this) / 6) * 5, R.layout.dialog_tips_mid, R.id.tvId, 0);
                        }
                    };
                    dialog.showDialog();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }

    /**
     * 版本更新进度框
     *
     * @param grivate 样式
     * @param width   宽
     * @param layout  布局
     * @param txtId   id
     * @param anim    动画
     */
    private void setDialog(int grivate, int width, int layout, int txtId, int anim) {
        tipsDialog = TipsDialog.creatTipsDialog(this, width, layout, grivate, anim);
        progressBar = tipsDialog.findViewById(R.id.down_pb);
        progressText = tipsDialog.findViewById(txtId);
        tipsDialog.setCancelable(false);
        Button btnCancle = tipsDialog.findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VersionUpdateActivity.this, DownloadService.class);
                // 由intent启动service，后台运行下载进程，在服务中调用notifycation状态栏显示进度条
                startService(intent);
                tipsDialog.dismiss();
            }
        });
        tipsDialog.show();
        tipsDialog.setCanceledOnTouchOutside(false);
        thread = ConstUtils.S_THREAD_FACTORY.newThread(new Runnable() {
            @Override
            public void run() {
                uploadApkFile();
            }
        });
        PermissionUtil.getInstance().setmPermissionGrantListener(new PermissionUtil.OnPermissionGrantListener() {
            @Override
            public void grantPermission(boolean isGrant, String permission, int requestCode) {
                if (isGrant == false) {
                    return;
                    //23以下直接回调
                }
                thread.start();
            }
        });
        PermissionUtil.requestPermissionForActivity(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
    }

    /**
     * 下载apk
     */
    private void uploadApkFile() {
        OkHttpUtils.get()
                .url(downloadApkUrl)
                .tag(VersionUpdateActivity.this)
                .build()
                .execute(new FileCallBack(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), Constant.APP_NAME) {
                    @Override
                    public void inProgress(float v, long l, int id) {
                        loadingProgress = (int) (v * 100);
                        progressBar.setProgress(loadingProgress);
                        progressText.setText("已为您加载了:" + loadingProgress + "%");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("User", "e =" + e.getMessage());
                        ToastUtils.show("下载失败");
                        tipsDialog.dismiss();
                        VersionUpdateActivity.this.finish();
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        tipsDialog.dismiss();
                        installNewApk();
                    }
                });
    }

    /**
     * 跳转安装
     */
    private void installNewApk() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.yst.im.imchatlibrary.fileProvider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);

    }
}
