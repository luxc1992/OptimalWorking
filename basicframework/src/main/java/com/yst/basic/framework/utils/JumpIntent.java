package com.yst.basic.framework.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Intent跳转工具类
 *
 * @author lixiangchao
 * @date 2017/9/14
 * @version 1.0.1
 */
public class JumpIntent {
    public final static String REQUEST_CODE = "REQUEST_CODE";

    /**
     * 意图跳转到指定目标
     * @param activity 当前活动
     * @param cls 目标活动
     */
    public static void jump(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 意图跳转携带参数
     * @param activity 当前活动
     * @param cls 目标活动
     * @param bundle 携带的参数
     */
    public static void jump(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 意图跳转没有参数
     * @param activity 当前活动
     * @param cls 目标活动
     * @param isFinish 当前类是否销毁标识
     */
    public static void jump(Activity activity, Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    /**
     * 意图跳转携带参数
     * @param activity 当前活动
     * @param cls 目标活动
     * @param isFinish 当前类是否销毁标识
     * @param bundle 携带的参数
     */
    public static void jump(Activity activity, Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    /**
     * 意图跳转携带参数并有回调
     * @param activity 当前活动
     * @param cls 目标活动
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 意图跳转携带参数并有回调
     * @param activity 当前活动
     * @param cls 目标活动
     * @param bundle 携带的参数
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            bundle.putInt(REQUEST_CODE, requestCode);
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 意图跳转携带参数
     * @param activity 当前活动
     * @param cls 目标活动
     * @param isFinish 当前类是否销毁标识
     * @param bundle 携带的参数
     */
    public static void jump(Activity activity, Class<?> cls, boolean isFinish, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    /**
     * 意图跳转并有回调
     * @param activity 当前活动
     * @param cls 目标活动
     * @param isFinish 当前类是否销毁标识
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, Class<?> cls, boolean isFinish, int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
        if (isFinish) {
            activity.finish();
        }
    }

    /**
     * 意图跳转携带参数并有回调
     * @param activity 当前活动
     * @param cls 目标活动
     * @param isFinish 当前类是否销毁标识
     * @param bundle 携带的参数
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, Class<?> cls, boolean isFinish, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            bundle.putInt(REQUEST_CODE, requestCode);
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
        if (isFinish) {
            activity.finish();
        }
    }

    /**
     * 意图跳转
     * @param activity 当前活动
     * @param action 意图
     */
    public static void jump(Activity activity, String action) {
        Intent intent = new Intent(action);
        activity.startActivity(intent);
    }

    /**
     * 意图跳转携带参数
     * @param activity 当前活动
     * @param action 意图
     * @param bundle 携带的参数
     */
    public static void jump(Activity activity, String action, Bundle bundle) {
        Intent intent = new Intent(action);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 意图跳转 带有回调
     * @param activity 当前活动
     * @param action 意图
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, String action, int requestCode) {
        Intent intent = new Intent(action);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 意图跳转 携带参数并带有回调
     * @param activity 当前活动
     * @param action 意图
     * @param bundle 携带的参数
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, String action, Bundle bundle, int requestCode) {
        Intent intent = new Intent(action);
        bundle.putInt(REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 路由打开并且带有回调
     * @param activity 当前的活动
     * @param action 意图
     * @param uri
     */
    public static void jump(Activity activity, String action, Uri uri) {
        Intent intent = new Intent(action, uri);
        activity.startActivity(intent);
    }

    /**
     * 路由打开并且带有回调
     * @param activity 当前的活动
     * @param action 意图
     * @param uri
     * @param requestCode 请求码
     */
    public static void jump(Activity activity, String action, Uri uri, int requestCode) {
        Intent intent = new Intent(action, uri);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

}
