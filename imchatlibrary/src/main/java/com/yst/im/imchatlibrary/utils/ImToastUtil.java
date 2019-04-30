package com.yst.im.imchatlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Toast
 *
 * @author lierpeng
 * @date 2018/03/28
 * @version 1.0.0
 */
public class ImToastUtil {
    private static Toast toast;
    private static View view;

    private ImToastUtil() {
    }

    @SuppressLint("ShowToast")
    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }


    /**
     * Toast 展示
     *
     * @param context 上下文
     * @param msg 信息
     */
    public static void showShortToast(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }


    /**
     * Toast 展示
     *
     * @param context  上下文
     * @param resId  资源文件索引
     *
     */
    public static void showShortToast(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }


    /**
     * Toast 展示
     *
     * @param context
     * @param msg
     */
    public static void showLongToast(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
    }


    /**
     * Toast 展示
     *
     * @param context
     * @param resId
     */
    public static void showLongToast(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
    }


    /**
     * Toast 展示
     *
     * @param context
     * @param msg
     * @param duration
     */
    private static void showToast(Context context, CharSequence msg,
                                  int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            Log.d(String.valueOf(context), e.getMessage());
        }
    }

    /**
     * Toast 展示
     *
     * @param context
     * @param resId
     * @param duration
     */
    private static void showToast(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            Log.d(String.valueOf(context), e.getMessage());
        }
    }

}