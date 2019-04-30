package com.yst.basic.framework;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.SearchHistory;
import com.yst.basic.framework.entity.SearchHistoryBean;
import com.yst.basic.framework.entity.UserManager;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.OkHttpLog;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.im.imchatlibrary.MyApp;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

import static com.yst.im.imsdk.MessageConstant.NUM_9;


/**
 * 项目的初始化
 *
 * @author lixiangchao
 * @version 1.0.1
 * @date 2017/11/29
 */
public class App extends MyApp {

    /**
     * 搜索历史
     */
    public static final String SEARCH_HISTORY = "search_history";
    /**
     * 应用的登录状态
     */
    public static boolean isLogin = false;
    public static UserManager manager;
    /**
     * 全局的上下文
     */
    private static App instance = null;
    /**
     * bugly的key
     * 需要对应的项目的key
     */
    private static String QQ_BUGLY = "c60e90ac25";

    static {//static 代码段可以防止内存泄露
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.light_grey, R.color.colorBlack);
                //指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    /**
     * 所有activity的集合列表
     */
    private LinkedList<Activity> activitys = new LinkedList<>();

    /**
     * 获取应用对象
     *
     * @return 应用是实例
     */
    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /**
         * Multidex突破64K方法数限制
         */
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    @Override
    protected void config() {
        instance = this;
        manager = UserManager.getInstance();
        //网络请求添加日志打印
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpLog("OneCity", true))
                .connectTimeout(20000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        /**
         * 需要在项目中必须打开
         */
        CrashReport.initCrashReport(this, QQ_BUGLY, false);
        initShareApi();
    }

    /**
     * 初始化友盟分享部件
     */
    private void initShareApi() {
        UMShareAPI.get(this);
        UMConfigure.setLogEnabled(true);
        {
            PlatformConfig.setWeixin(Const.WECHAT_KEY, Const.WECHAT_SECRET);
            PlatformConfig.setQQZone(Const.QQ_ID, Const.QQ_KET);
            PlatformConfig.setSinaWeibo(Const.SINA_KET, Const.SINA_SECRET);
            Config.REDIRECT_URL = Const.REDIRECT_URL;
        }

        //防止拍照崩溃
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    /**
     * 保存搜索历史
     *
     * @param string 要保存的数据
     */
    public void saveSearchHistory(String string) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        List<SearchHistory> historyBeanList = getSearchHistory();
        if (historyBeanList.size() == NUM_9) {
            historyBeanList.remove(historyBeanList.size() - 1);
        }
        for (int i = 0; i < historyBeanList.size(); i++) {
            if (historyBeanList.get(i).getKeyword().trim().equals(string.trim())) {
                historyBeanList.remove(i);
                break;
            }
        }
        JSONObject historyJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        SearchHistory historyBean = new SearchHistory(string);
        historyBeanList.add(0, historyBean);
        try {
            for (int i = 0; i < historyBeanList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("keyword", historyBeanList.get(i).getKeyword());
                jsonArray.put(jsonObject);
            }
            historyJson.put("history", jsonArray);
        } catch (JSONException jsone) {
            MyLog.e("app", "jsone = " + jsone.getMessage());
        }
        PreferenceUtil.put(SEARCH_HISTORY, historyJson.toString());
    }

    /**
     * 清除搜索历史
     */
    public void clearSearchHistory() {
        PreferenceUtil.put(SEARCH_HISTORY, "");
    }

    /**
     * 用户未登录情况下获取本地存储的搜索历史
     *
     * @return
     */
    public List<SearchHistory> getSearchHistory() {
        String historyJson = PreferenceUtil.getString(SEARCH_HISTORY, "");
        if (!TextUtils.isEmpty(historyJson)) {
            SearchHistoryBean searchHistoryBean = new Gson().fromJson(historyJson, SearchHistoryBean.class);
            if (searchHistoryBean.getHistory() != null) {
                return searchHistoryBean.getHistory();
            }
        }
        return new ArrayList<>();
    }

    /**
     * 添加activity到LinkedList集合
     *
     * @param activity
     */
    public void addActivityList(Activity activity) {
        activitys.add(activity);
    }

    /**
     * 移除当前的页面
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activitys.remove(activity);
    }

    /**
     * 退出集合所有的activity
     */
    public void exitActivity() {
        try {
            String mainHtmlActivity = "MainActivity";
            for (int i = 0; i < activitys.size(); i++) {
                int i1 = activitys.get(i).getClass().getName().lastIndexOf(".");
                String substring = activitys.get(i).getClass().getName().substring(i1 + 1, activitys.get(i).getClass().getName().length());
                if (!substring.equals(mainHtmlActivity)) {
                    activitys.get(i).finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * 退出应用程序
     */
    public void exit() {
        System.exit(0);
    }
}
