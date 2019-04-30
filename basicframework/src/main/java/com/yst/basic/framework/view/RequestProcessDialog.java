package com.yst.basic.framework.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yst.basic.framework.R;
import com.yst.basic.framework.utils.WindowUtils;

/**
 * 自定义加载框. <br>
 * 类详细说明.
 *
 * @author lixiangchao
 * @date 2017/11/30
 * @version 1.0.1
 */
public class RequestProcessDialog extends Dialog {

    private String content = null;

    private ImageView animImg;
    private TextView tvProcess;
    private TextView loadTv;
    private TextView tvTitle;
    private ProgressBar proBar;
    private Button btnCancel;
    private TranslateAnimation tranAnim;

    /**
     * @param context
     */
    public RequestProcessDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        setCancelable(false);
        initUI();
    }

    public RequestProcessDialog(Context context, String content) {
        super(context, R.style.CustomProgressDialog);
        setCancelable(false);
        initUI();
    }

    public RequestProcessDialog(Context context, int id) {
        super(context, R.style.CustomProgressDialog);
        setCancelable(false);
        initUI(id);
    }

    @SuppressWarnings("deprecation")
    private void initUI(int id) {
        setContentView(R.layout.down_process);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowUtils.getScreenWidth((Activity) getContext());

        dialogWindow.setAttributes(p);
        tvProcess = findViewById(R.id.tv_process);
        tvTitle = findViewById(R.id.tv_title);
        proBar = findViewById(R.id.process);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    public void setProgressMax(int max) {
        if (proBar != null) {
            proBar.setMax(max);
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        btnCancel.setOnClickListener(cancelListener);
    }

    /**
     * 初始化
     */
    private void initUI() {
        setContentView(R.layout.process_dlg);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        animImg = findViewById(R.id.tv_process);
        loadTv = findViewById(R.id.lodding);
        animImg.setBackgroundResource(R.drawable.anim_progress);
        AnimationDrawable anim = (AnimationDrawable) animImg.getBackground();
        anim.start();
        if (content != null && content.trim().length() > 0) {
            loadTv.setText(content);
        }
    }

    /**
     * 设置新的内容
     *
     * @param info 内容
     */
    public void setMessage(String info) {
        if (info != null) {
            this.content = info;
            if (loadTv != null) {
                loadTv.setText(content);
            }
        }
    }

    public void setCurProgress(int process) {
        tvProcess.setText(String.valueOf(process) + "%/100%");
        proBar.setProgress(process);
    }

    private static float perProc = 0;

    /**
     * 设置当前进度
     *
     * @param proc
     */
    public void setProgress(int proc) {
        proBar.setProgress(proc);
        float p = (float) proc / 100;
        tranAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                perProc, Animation.RELATIVE_TO_PARENT, p,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        tranAnim.setFillAfter(true);
        tranAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                animImg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animImg.setVisibility(View.VISIBLE);
            }
        });
        animImg.setAnimation(tranAnim);
        tranAnim.start();
        perProc = p;
    }

    public void resertProc() {
        perProc = 0;
    }
}
