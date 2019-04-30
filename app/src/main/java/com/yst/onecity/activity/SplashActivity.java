package com.yst.onecity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.yst.onecity.R;


/**
 * 启动页
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/02/28
 */
public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private boolean isPostDelayedGoHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
    }

    /**
     * 延迟启动主界面
     */
    private void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPostDelayedGoHome) {
                    goHome();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    /**
     * 启动主界面
     */
    private void goHome() {
//      Intent it = new Intent(this, MainHtmlActivity.class);
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        isPostDelayedGoHome = false;
        SplashActivity.this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        startActivity();
    }
}
