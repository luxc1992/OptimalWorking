package com.yst.onecity.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 删除弹出框
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/24
 */
public abstract class AbstractSelectProductDialog {
    protected Dialog dialog;
    private TextView title;
    private RelativeLayout sure;
    private TextView cancle;

    protected AbstractSelectProductDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private void getDialog(Activity activity) {
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.select_product_dialog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        cancle = view.findViewById(R.id.cancel);
        sure   = view.findViewById(R.id.select_product_tv);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
     * 确认按钮
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
