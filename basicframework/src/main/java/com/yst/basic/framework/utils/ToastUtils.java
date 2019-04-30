package com.yst.basic.framework.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yst.basic.framework.App;

/**
 * Toast工具类
 *
 * @author lixiangchao
 * @date 2017/11/29
 * @version 1.0.1
 */
public class ToastUtils {

    /**
     * 是否打开Toast显示开关
     */
    private static boolean isShow = true;

    private static Toast sToast;

    /**
     * 展示页面黑色弹框
     *
     * @param content
     */
    public static void toast(String content) {
        Toast.makeText(App.getInstance(), null == content ? "" : content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 最常用的提示文本
     */
    public static void show(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }


    /**
     * 直接显示文本
     *
     * @param messageId 需要显示的文字
     */
    public static void showShort(int messageId) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), messageId, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param message 需要显示的文字
     */
    public static void showShort(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param messageId 需要显示的文字
     */
    public static void showLong(int messageId) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), messageId, Toast.LENGTH_LONG);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param message 需要显示的文字
     */
    public static void showLong(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param messageId 需要显示的文字资源
     * @param duration  自定义显示时间
     */
    public static void show(int messageId, int duration) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), messageId, duration);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param message  需要显示的文字
     * @param duration 自定义显示时间
     */
    public static void show(String message, int duration) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(App.getInstance(), message, duration);
            sToast.show();
        }
    }

    /**
     * 带图片消息提示
     *
     * @param mImageResourceId 图片资源
     * @param messageId       文字资源
     */
    public static void showImageAndText(int mImageResourceId, int messageId) {
        Context context = App.getInstance();
        showImageAndText(mImageResourceId, context.getResources().getString(messageId), Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    /**
     * 带图片消息提示
     *
     * @param mImageResourceId 图片资源
     * @param message         文字
     */
    public static void showImageAndText(int mImageResourceId, CharSequence message) {
        showImageAndText(mImageResourceId, message, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    /**
     * 带图片消息提示
     *
     * @param mImageResourceId 图片资源
     * @param message         文字
     * @param duration        显示时间
     * @param gravity         显示位置
     */
    public static void showImageAndText(int mImageResourceId, CharSequence message, int duration, int gravity) {
        Toast toast = Toast.makeText(App.getInstance(),
                message, duration);
        toast.setGravity(gravity, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(App.getInstance());
        imageCodeProject.setImageResource(mImageResourceId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}
