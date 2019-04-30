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
 * (我的)解绑银行卡提示框
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/24
 */

public abstract class AbstractUnBindDialog {

    private Dialog dialog;
    private TextView title;

    public AbstractUnBindDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private void getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_nobank, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view);
        TextView sure = view.findViewById(R.id.txt_ok);
        title = view.findViewById(R.id.txt_msg);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (lp != null) {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.y = 400;
            //设置dialog 在布局中的位置
            lp.gravity = Gravity.CENTER | Gravity.TOP;
            dialogWindow.setAttributes(lp);
        }
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sureClick();
            }
        });
    }

    /**
     * 设置文本
     *
     * @param s msg文本
     */
    public void setText(String s) {
        title.setVisibility(View.VISIBLE);
        title.setText(s);
    }

    /**
     * 确定按钮点击事件
     */
    protected abstract void sureClick();
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
