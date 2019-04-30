package com.yst.onecity.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;
import com.yst.onecity.view.ContainsEmojiEditText;

/**
 * 拒绝理由弹出框
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/26
 */
public abstract class AbstractRefuseDialog {
    protected Dialog dialog;
    private ContainsEmojiEditText refuseReason;

    protected AbstractRefuseDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private void getDialog(Activity activity) {
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.dialog_refuse, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (WindowUtils.dip2px(activity, 265), WindowUtils.dip2px(activity, 185)));
        refuseReason = view.findViewById(R.id.et_refuse_reason);
        refuseReason.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        Button sure = view.findViewById(R.id.sure);
        Button cancle = view.findViewById(R.id.cancle);

        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setAttributes(lp);
        }
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String reason = refuseReason.getText().toString().trim();
                if (TextUtils.isEmpty(reason)) {
                    ToastUtils.show("请输入拒绝理由");
                } else {
                    sureClick(reason);
                    dialog.dismiss();
                }
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
     * 确认按钮
     *
     * @param reason 拒绝理由
     */
    public abstract void sureClick(String reason);

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
