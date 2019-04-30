package com.yst.im.imchatlibrary.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yst.im.imchatlibrary.R;

/**
 * 弹窗类
 *
 * @author qinchaoshuai
 * @version 1.0.2
 * @date 2018/3/28.
 */

public class CustomAlterDialogUtil {

    public interface DialogClickListenner {
        void confirm();

        void cancle();
    }

    /**
     * @param context
     */
    public static void clearCache(Context context) {
        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        View view = LayoutInflater.from(context).inflate(R.layout.clear_cache, null);
        window.setContentView(view);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }
}
