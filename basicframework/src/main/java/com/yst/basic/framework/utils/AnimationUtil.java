package com.yst.basic.framework.utils;

import android.app.Activity;
import android.content.Context;

import com.yst.basic.framework.R;

/**
 * 加载动画的辅助类
 *
 * @author lixiangchao
 * @date 2017/11/29
 * @version 1.0.1
 */
public class AnimationUtil {

    /**
     * 跳转进入 In 新的页面动画效果    左出右入 +渐变透明度
     *
     * @param context 传入的 Context
     */
    public static void goInActivityAnimation(Context context) {
        if (context != null) {
            ((Activity) context).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
        }
    }

    /**
     * Activity 跳转进入 In 新的页面动画效果     左出右入 +渐变透明度
     *
     * @param act 传入的 Activity
     */
    public static void goInActivityAnimation(Activity act) {
        if (act != null) {
            act.overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
        }
    }


    /**
     * 退出 Exit 当前页面动画效果    左入右出+渐变透明度
     *
     * @param context 传入的Context
     */
    public static void exitActivityAnimation(Context context) {
        if (context != null) {
            ((Activity) context).overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
        }
    }


    /**
     * Activity 退出 Exit 当前页面动画效果     左入右出+渐变透明度
     *
     * @ act  传入的Activity
     */
    public static void exitActivityAnimation(Activity act) {
        if (act != null) {
            act.overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
            act.finish();
        }
    }


    /**
     * 进入 In 当前页面动画效果     缩放扩大进入+渐变透明度
     *
     * @ context  传入的Context
     */
    public static void scaleInActivityAnimation(Context context) {
        if (context != null) {
            ((Activity) context).overridePendingTransition(R.anim.zoom_in, 0);
        }
    }

    /**
     * Activity In  缩放扩大进入+渐变透明度
     *
     * @ act  传入的Activity
     */
    public static void scaleInActivityAnimation(Activity act) {
        if (act != null) {
            act.overridePendingTransition(R.anim.zoom_in, 0);
        }
    }

    /**
     * 退出  Exit 当前页面动画效果     缩小退出+渐变透明度
     *
     * @ context  传入的Context
     */
    public static void scaleOutActivityAnimation(Context context) {
        if (context != null) {
            ((Activity) context).overridePendingTransition(0, R.anim.zoom_out);
        }
    }

    /**
     * Activity 退出 Exit   缩小退出+渐变透明度
     *
     * @ act  传入的Activity
     */
    public static void scaleOutActivityAnimation(Activity act) {
        if (act != null) {
            act.overridePendingTransition(0, R.anim.zoom_out);
        }
    }

}
