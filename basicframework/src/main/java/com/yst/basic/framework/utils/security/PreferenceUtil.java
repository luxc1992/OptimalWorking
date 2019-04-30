package com.yst.basic.framework.utils.security;

import android.content.Context;

import com.yst.basic.framework.App;
import com.yst.basic.framework.Const;


/**
 * 用户偏好设置(工具类)
 *
 * @author lixiangchao
 * @date 201711/30
 * @version 1.0.1
 */
public class PreferenceUtil {
    /**
     * 偏好设置常量区
     * App版本号
     * 设备id
     * 推送开关
     * 极光推送是否已经设置了别名
     */
    public static final String APP_VERSION = "app_version";
    public static final String DEVICE_ID = "device_id";
    public static final String PUSH_CONFIG = "push_config";
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

        if (Const.STRING.equals(type)) {
            editor.putString(key, (String) object);
        } else if (Const.INTEGER.equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if (Const.BOOLEAN.equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if (Const.FLOAT.equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if (Const.LONG.equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

}
