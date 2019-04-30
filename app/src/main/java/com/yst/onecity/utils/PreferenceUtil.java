package com.yst.onecity.utils;

import android.content.Context;

import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.security.SecuritySharedPreference;

/**
 * sp工具类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/6
 */
public class PreferenceUtil {

    private static String sString = "String";
    private static String sInteger = "Integer";
    private static String sBoolean = "Boolean";
    private static String sFloat = "Float";
    private static String sLong = "Long";
    /**
     * App版本号
     */
    public static final String APP_VERSION = "app_version";
    /**
     * 设备id
     */
    public static final String DEVICE_ID = "device_id";
    /**
     * 推送开关
     */
    public static final String PUSH_CONFIG = "push_config";
    /**
     * 极光推送是否已经设置了别名
     */
    public static final String SETTED_ALIAS = "setted_alias";
    private static SecuritySharedPreference securitySharedPreference;

    public static SecuritySharedPreference getSp() {
        if (securitySharedPreference == null) {
            securitySharedPreference = new SecuritySharedPreference(App.getInstance(), "security_prefs", Context.MODE_PRIVATE);
        }
        return securitySharedPreference;
    }

    public static SecuritySharedPreference.Editor getEditor() {
        return getSp().edit();
    }

    /**
     * 或去指定key对应的值，若没有该值则返回指定”默认值“
     */
    public static int getInt(String key, int defaultVal) {
        return getSp().getInt(key, defaultVal);
    }

    public static String getString(String key, String defaultVal) {
        return getSp().getString(key, defaultVal);
    }

    public static boolean getBoolean(String key, boolean defaultVal) {
        return getSp().getBoolean(key, defaultVal);
    }

    public static float getFloat(String key, float defaultVal) {
        return getSp().getFloat(key, defaultVal);
    }

    public static long getLong(String key, long defaultVal) {
        return getSp().getLong(key, defaultVal);
    }

    /**
     * 为给定key设置指定的值
     */
    public static void put(String key, Object object) {
        SecuritySharedPreference.Editor editor = getEditor();

        if (null == object) {
            return;
        }

        String type = object.getClass().getSimpleName();

        if (sString.equals(type)) {
            editor.putString(key, (String) object);
        } else if (sInteger.equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if (sBoolean.equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if (sFloat.equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if (sLong.equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

}
