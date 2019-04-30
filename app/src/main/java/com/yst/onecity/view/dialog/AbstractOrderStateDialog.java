package com.yst.onecity.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 申请退款 订单状态选择弹出框
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/24
 */
public abstract class AbstractOrderStateDialog {
    protected Dialog dialog;
    private ImageView ivNoFinish;
    private ImageView ivFinish;

    protected AbstractOrderStateDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    @SuppressWarnings("deprecation")
    private void getDialog(Activity activity) {
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.dialog_order_state, null);
        //默认样式
        dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        RelativeLayout rlOrderNoFinish = view.findViewById(R.id.rl_order_no_finish);
        RelativeLayout rlOrderFinish = view.findViewById(R.id.rl_order_finish);
        ivNoFinish = view.findViewById(R.id.iv_no_finish);
        ivFinish = view.findViewById(R.id.iv_finish);
        TextView tvClose = view.findViewById(R.id.tv_close);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            wl.y = dm.heightPixels;
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            // 设置显示位置
            dialog.onWindowAttributesChanged(wl);
        }
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);

        tvClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rlOrderNoFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ivNoFinish.setImageResource(R.mipmap.checked);
                ivFinish.setImageResource(R.mipmap.circle);
                noFinishClick();
                dialog.dismiss();
            }
        });

        rlOrderFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ivNoFinish.setImageResource(R.mipmap.circle);
                ivFinish.setImageResource(R.mipmap.checked);
                finishClick();
                dialog.dismiss();
            }
        });

    }

    /**
     * 未完成订单
     */
    public abstract void noFinishClick();

    /**
     * 已完成订单
     */
    public abstract void finishClick();

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
