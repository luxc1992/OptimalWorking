package com.yst.basic.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yst.basic.framework.App;
import com.yst.basic.framework.R;
import com.yst.basic.framework.utils.AnimationUtil;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.basic.framework.view.RequestProcessDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * MVC项目框架使用的基类
 *
 * @author lixiangchao
 * @version 1.0.1
 * @date 2017/07/06
 */
public abstract class BaseActivity extends FragmentActivity {
    public Context context;
    protected boolean isFirst = true;
    protected TextView tvMainTitle;
    protected TextView titleView;
    protected TextView tvRight;
    protected ImageView ivRight;
    /**
     * 显示界面等待条
     */
    private RequestProcessDialog mInfoProgressDialog;
    private List<View> views;
    private Bundle savedInstanceState;
    private LinearLayout llBack;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        //所有都为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //所有都为竖屏模式
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarAlpha(0.2f).init();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        }
        setContentView(getLayoutId());
        App.getInstance().addActivityList(this);
        ButterKnife.bind(this);
        context = this;
        views = new ArrayList<>();
        views.add(new EditText(context));
        initData();
        initCtrl();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 设置布局文件
     *
     * @return 布局资源
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 适配器
     */
    public void initCtrl() {

    }

    /**
     * 点击监听
     */
    public void setListener() {

    }

    /**
     * 退出Activity
     */
    public void quitActivity(Activity activity) {
        AnimationUtil.exitActivityAnimation(activity);
    }

    /**
     * 进入Activity
     */
    public void inActivity(Activity activity) {
        AnimationUtil.goInActivityAnimation(activity);
    }

    /**
     * 展示页面等待框
     */
    public void showInfoProgressDialog() {
        if (mInfoProgressDialog == null) {
            mInfoProgressDialog = new RequestProcessDialog(this);
        }
        mInfoProgressDialog.setMessage("加载中");
        mInfoProgressDialog.setCancelable(false);
        if (!this.isFinishing()) {
            try {
                mInfoProgressDialog.show();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 关闭进度条
     */
    public void dismissInfoProgressDialog() {
        if (mInfoProgressDialog != null) {
            mInfoProgressDialog.dismiss();
        }
    }

    /**
     * 设置标题
     *
     * @param text 标题
     */
    protected void setTitle(String text) {
        if (null != titleView) {
            titleView.setText(null == text ? "" : text);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题（不带返回键监听）
     */
    protected void setTitleBarTitle(String title) {
        if (tvMainTitle != null) {
            tvMainTitle.setText(title);
        } else {
            tvMainTitle = findViewById(R.id.tv_main_title);
            tvMainTitle.setText(title);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 设置标题
     *
     * @param title 标题（返回键关闭当前页）
     */
    protected void initTitleBar(String title) {
        llBack = findViewById(R.id.ll_back);
        tvMainTitle = findViewById(R.id.tv_main_title);
        tvRight = findViewById(R.id.tv_right);
        llBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleBarTitle(title);
    }

    /**
     * 设置标题字体加粗
     */
    protected void setTitleBold() {
        TextPaint paint = tvMainTitle.getPaint();
        paint.setFakeBoldText(true);
    }

    /**
     * 设置标题栏的返回键显隐
     *
     * @param visible 显示/隐藏
     */
    protected void setBackVisibility(int visible) {
        if (llBack != null) {
            llBack.setVisibility(visible);
        } else {
            llBack = findViewById(R.id.ll_back);
            llBack.setVisibility(visible);
        }
    }

    protected void setBackResouce(int resouce) {
        if (ivBack != null) {
            ivBack.setImageResource(resouce);
        } else {
            ivBack = findViewById(R.id.iv_back);
            ivBack.setImageResource(resouce);
        }
    }

    /**
     * 设置标题栏右侧标题
     *
     * @param title 右侧文字
     */
    protected void setRightText(String title) {
        setRightTextVisibility(View.VISIBLE);
        if (tvRight != null) {
            tvRight.setText(title);
        } else {
            tvRight = findViewById(R.id.tv_right);
            tvRight.setText(title);
        }
    }

    /**
     * 设置标题栏右侧文字颜色
     *
     * @param color 字体颜色
     */
    protected void setRightTextColor(int color) {
        if (tvRight != null) {
            tvRight.setTextColor(color);
        } else {
            tvRight = findViewById(R.id.tv_right);
            tvRight.setTextColor(color);
        }
    }

    /**
     * 设置标题栏右侧文字可见性
     *
     * @param visible 可见/不可见
     */
    protected void setRightTextVisibility(int visible) {
        if (tvRight != null) {
            tvRight.setVisibility(visible);
        } else {
            tvRight = findViewById(R.id.tv_right);
            tvRight.setVisibility(visible);
        }
    }

    /**
     * 设置标题栏右侧文字是否可点击
     *
     * @param clickable 可点击/不可点击
     */
    protected void setRightTextViewClickable(boolean clickable) {
        if (clickable) {
            tvRight.setClickable(true);
        } else {
            tvRight.setClickable(false);
        }
    }

    /**
     * 设置标题栏右侧文字点击监听
     *
     * @param onclickListener
     */
    protected void setRightTextViewOnClickListener(View.OnClickListener onclickListener) {
        tvRight.setOnClickListener(onclickListener);
    }

    /**
     * 设置标题栏右侧图片和可见性
     *
     * @param imageResouse 图片
     * @param visible      可见/不可见
     */
    protected void setRightImageVisibility(int imageResouse, int visible) {
        if (ivRight != null) {
            ivRight.setVisibility(visible);
            ivRight.setImageResource(imageResouse);
        } else {
            ivRight = findViewById(R.id.iv_right);
            ivRight.setVisibility(visible);
            ivRight.setImageResource(imageResouse);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        WindowUtils.hideInputWhenTouchOtherView(this, ev, views);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 这个函数在Activity创建完成之后会调用。购物车悬浮窗需要依附在Activity上，如果Activity还没有完全建好就去
     * 调用showCartFloatView()，则会抛出异常
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 记录当前的position
        outState.putBoolean("isLogin", App.isLogin);
        MyLog.i("User", "isLogin onSaveInstanceState= " + App.isLogin);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        // 针对用户点击了清除内存的加速球的解决方法----需要saveInstance方法
        App.isLogin = savedInstanceState.getBoolean("isLogin");
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
