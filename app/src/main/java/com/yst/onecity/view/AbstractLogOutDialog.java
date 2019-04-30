package com.yst.onecity.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 退出登录弹出框
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/22
 */

public abstract class AbstractLogOutDialog {
    protected Dialog dialog;
    private TextView cancle;
    private TextView sure;

    public AbstractLogOutDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_outlog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view);
        sure = view.findViewById(R.id.sure);
        cancle =view.findViewById(R.id.cancel);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (lp != null) {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //设置dialog 在布局中的位置
            lp.gravity = Gravity.BOTTOM;

            dialogWindow.setAttributes(lp);
        }
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                sureClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 确认退出
     */
    public abstract void sureClick();

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

