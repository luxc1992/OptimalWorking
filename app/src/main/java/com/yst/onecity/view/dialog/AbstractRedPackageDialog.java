package com.yst.onecity.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;

/**
 * 红包弹窗
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/2/24
 */
public abstract class AbstractRedPackageDialog {
    protected Dialog dialog;


    protected AbstractRedPackageDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_red_package, null);
        dialog = new Dialog(activity, R.style.redDialog_style);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setContentView(view);
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        TextView tvOpen = view.findViewById(R.id.tv_get_red_package);
        TextView tvClose = view.findViewById(R.id.tv_close_red_package);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvClose.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.rightMargin =  WindowUtils.dip2px(activity, 40);
        tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClick();
            }
        });
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeClick();
            }
        });

        return dialog;
    }


    /**
     * 打开
     */
    public abstract void openClick();

    /**
     * 关闭
     */
    public abstract void closeClick();

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
