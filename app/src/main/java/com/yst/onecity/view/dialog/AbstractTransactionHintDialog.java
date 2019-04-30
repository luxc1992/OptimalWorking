package com.yst.onecity.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;

/**
 * 提现交易提示框
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/3/7
 */

public abstract class AbstractTransactionHintDialog {
    private Dialog dialog;
    private Activity activity;
    private TextView title;
    private Button sure;
    private Button cancel;

    public AbstractTransactionHintDialog(Activity activity) {
        this.activity = activity;
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private void getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_transactionhint, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setCancelable(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (WindowUtils.dip2px(activity, 265), WindowUtils.dip2px(activity, 135)));
        title = view.findViewById(R.id.title);
        sure = view.findViewById(R.id.sure);
        cancel = view.findViewById(R.id.cancle);

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setAttributes(lp);
        }
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sureClick();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelClick();
            }
        });

    }

    /**
     * 确认按钮
     */
    public abstract void sureClick();

    /**
     * 取消按钮
     */
    public abstract void cancelClick();

    /**
     * 设置弹出框中的文字
     *
     * @param s1 标题
     */
    public void setText(String s1) {
        title.setText(s1);

    }

    /**
     * 设置确认按钮的文字
     *
     * @param s1 确认按钮
     */
    public void setSureText(String s1) {
        sure.setText(s1);
    }

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

    public boolean isShowing() {
        return dialog.isShowing();
    }
}
