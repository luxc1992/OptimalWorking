package com.yst.im.imchatlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;


import com.yst.im.imchatlibrary.bean.InviteEntity;
import com.yst.im.imchatlibrary.utils.CrashHandler;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.OkHttpLog;
import com.yst.im.imchatlibrary.utils.UsersMag;
import com.yst.im.imsdk.ImSdkApplication;
import com.yst.im.imsdk.MsClient;
import com.yst.im.imsdk.dao.GreenDaoManager;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 全局单利模式
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/29
 */
public class MyApp extends ImSdkApplication {
    public static UsersMag manager;
    public static MyApp mInstance;
    public static Context mContext;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    /**
     * 单利socketChannel
     */
    private static volatile MsClient msClient = null;
    private static List<InviteEntity.ContentBean> mInviteList;
    /**
     * 存放Activity，用来统一关闭
     */
    public static List<Activity> activityList;
    public static String conId = "";

    public static MsClient getMsClient() {
        return msClient;
    }

    public static void resetMsClient() {
        msClient = null;
        msClient = MsClient.getClient();
    }

    public static boolean isConnectionSocket = false;
//    public static boolean isLogin = false;

    /**
     * 单利被邀请者列表选中
     */
    public static List<InviteEntity.ContentBean> getInviteList() {
        if (mInviteList == null) {
            synchronized (MsClient.class) {
                if (mInviteList == null) {
                    mInviteList = new ArrayList<>();
                }
            }
        }
        return mInviteList;
    }

    /**
     * 私有构造器
     */
    public MyApp() {

    }

    @Override
    protected void sdkConfig() {
        super.sdkConfig();
        /**
         * Multidex突破64K方法数限制
         */
        MultiDex.install(this);
        mContext = getApplicationContext();
        msClient = MsClient.getClient();
        mInstance = this;
        CrashHandler.init();
        initScreenSize();
        manager = UsersMag.getInstance();
        activityList = new ArrayList<>();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpLog("IM"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        config();
        GreenDaoManager.init(this);

    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    /**
     * 配置信息
     */
    protected void config() {

    }

    /**
     * 添加群详情和聊天页面
     */
    public static void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activityList.add(activity);
    }

    /**
     * 关闭群详情和聊天页面
     */
    public static void quiteActivity() {
        ImLog.e("ImLog", "activityList = " + activityList.size());
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i) != null) {
                activityList.get(i).finish();
            }
        }
    }
}
