package com.yst.im.imchatlibrary.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;

/**
 * 提交dialog
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/03/02.
 */

public class MyCommitDialog {

    private String discreption = "数据加载中...";
    private Context context;
    private android.app.AlertDialog dialog;

    public MyCommitDialog(@NonNull Context context, String discreption) {
        this.discreption = discreption;
        this.context = context;
        show();
    }

    public void show() {
        dialog = new android.app.AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_commit);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = MyApp.screenWidth * 3 / 5;
        lp.height = MyApp.screenHeight * 1 / 5;
        window.setAttributes(lp);
        TextView tvContent;
        tvContent = (TextView) window.findViewById(R.id.tv_mesage);
        tvContent.setText(discreption);
    }

    public void dismiss() {
        dialog.dismiss();
    }


}
