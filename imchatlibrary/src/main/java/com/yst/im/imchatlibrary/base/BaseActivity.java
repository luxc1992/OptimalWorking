package com.yst.im.imchatlibrary.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imsdk.utils.LocalLog;

import java.util.List;

import gorden.rxbus2.RxBus;

/**
 * BaseActivity
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/27.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    protected Bundle savedInstanceState;
    protected TextView titleView;
    private PowerManager.WakeLock wakeLock;
    protected boolean isActive = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //跳转动画——右进左出
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        initVideoFeature();
        super.onCreate(savedInstanceState);
        //展示页面
        setContentView(getLayout());

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();

        if(!getStatus()){
            BaseUtils.setStatusBar(this, false, getStatus());
        }
        init();
        //初始化当前Activity控件
        initView();
        //初始化数据
        initData();
        RxBus.get().register(this);
        this.savedInstanceState = savedInstanceState;
    }

    protected void initVideoFeature(){}

    private void init() {
        mContext = this;
    }

    /**
     * 设置标题f
     */
    protected void setTitle(String text) {
        titleView.setText(text);

    }

    /**
     * 点击外部区域关闭输入键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Activity跳转类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Presenter解除绑定
        RxBus.get().unRegister(this);
        if (null != wakeLock) {
            wakeLock.release();
        }
    }

    /**
     * 获得视图id
     *
     * @return
     */
    protected abstract int
    getLayout();

    /**
     * 状态栏颜色
     *
     * @return
     */
    protected abstract int
    getStatusColor();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 是否设置状态栏
     * @return
     */
    protected abstract boolean getStatus();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
//            //全局变量,记录当前已经进入后台
//            isActive = false;
//            MyApp.getMsClient().isUserOut = false;
//            MyApp.getMsClient().closeChannel();
            LocalLog.e("im_test", "isAppOnForeground ---onStop---" + isAppOnForeground());
            LocalLog.e("im_test", "isActive ---onStop---" + isActive);
            ImLog.e("==", "isActive = " + isActive);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        if (!isActive) {
//            isActive = true;
//        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
