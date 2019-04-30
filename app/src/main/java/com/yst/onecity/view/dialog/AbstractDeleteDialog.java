package com.yst.onecity.view.dialog;

import android.annotation.SuppressLint;
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
 * 删除弹出框
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/24
 */
public abstract class AbstractDeleteDialog {
    protected Dialog dialog;
    private TextView title;
    private Button sure;
    private Button cancle;
    private TextView tvTishi;

    protected AbstractDeleteDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private void getDialog(Activity activity) {
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.delete_dialog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (WindowUtils.dip2px(activity, 265), WindowUtils.dip2px(activity, 135)));
        tvTishi = view.findViewById(R.id.tv_tishi);
        title = view.findViewById(R.id.title);
        sure = view.findViewById(R.id.sure);
        cancle = view.findViewById(R.id.cancle);

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

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * title是否可见
     */
    public void setTitleisVisible(boolean visible) {
        if (visible) {
            tvTishi.setVisibility(View.VISIBLE);
        } else {
            tvTishi.setVisibility(View.GONE);
        }
    }
    /**
     * 是否只是显示一个button
     */
    public void setOneVisible(boolean visible) {
        if (visible) {
            cancle.setVisibility(View.GONE);
        } else {
            cancle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 确认按钮
     */
    public abstract void sureClick();

    /**
     * 设置弹出框中的文字
     *
     * @param s1 标题
     * @param s2 确认
     * @param s3 取消
     */
    public void setText(String s1, String s2, String s3) {
        title.setText(s1);
        sure.setText(s2);
        cancle.setText(s3);
    }

    /**
     * 设置弹出框中的button颜色
     *
     * @param sureColor   确认
     * @param cancleColor 取消
     */
    public void setButtonColor(int sureColor, int cancleColor) {
        sure.setTextColor(sureColor);
        cancle.setTextColor(cancleColor);
    }
    /**
     * 设置弹出框中提示的文字颜色
     *
     * @param tiShiColor   提示
     */
    public void setTColor(int tiShiColor) {
        tvTishi.setTextColor(tiShiColor);
    }
    /**
     * 设置弹出框中提示的文字颜色
     *
     * @param titleColor   title的颜色
     */
    public void setTitleColor(int titleColor) {
        title.setTextColor(titleColor);
    }

    /**
     * 设置弹出框中提示的内容
     *
     * @param tiShi   提示
     */
    public void setTContent(String tiShi) {
        tvTishi.setText(tiShi);
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
}
