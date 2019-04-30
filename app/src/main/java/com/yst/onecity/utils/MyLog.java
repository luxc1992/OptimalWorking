package com.yst.onecity.utils;

/**
 * 日志辅助类，打印需要的日志
 * Boolean 是否打印日志；正式环境需要把日志关掉
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2017-01-23
 */

public final class MyLog {
    /**
     * 是否需要打印日志
     * debug 模式需要开启
     * release 模式需要关闭
     */
    private static boolean mbLoggable = true;

    public static void setLoggable(boolean bLoggable) {
        mbLoggable = bLoggable;
    }

    public static boolean isDebuggable() {
        return mbLoggable;
    }

    /**
     * 日志打印级别 i
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int i(String tag, String msg) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg) {
            return -1;
        }
        return android.util.Log.i(tag, msg);
    }

    /**
     * 日志打印级别 i
     *
     * @param tag
     * @param msg
     * @param tr
     * @return
     */
    public static int i(String tag, String msg, Throwable tr) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg || null == tr) {
            return -1;
        }
        return android.util.Log.i(tag, msg, tr);
    }

    /**
     * 日志打印级别 d
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int d(String tag, String msg) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg) {
            return -1;
        }
        return android.util.Log.d(tag, msg);
    }

    /**
     * 日志打印级别 d
     *
     * @param tag
     * @param msg
     * @param tr
     * @return
     */
    public static int d(String tag, String msg, Throwable tr) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg || null == tr) {
            return -1;
        }
        return android.util.Log.d(tag, msg, tr);
    }

    /**
     * 日志打印级别 e
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int e(String tag, String msg) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg) {
            return -1;
        }
        return android.util.Log.e(tag, msg);
    }

    /**
     * 日志打印级别 e
     *
     * @param tag
     * @param msg
     * @param tr
     * @return
     */
    public static int e(String tag, String msg, Throwable tr) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg || null == tr) {
            return -1;
        }
        return android.util.Log.e(tag, msg, tr);
    }

    /**
     * 日志打印级别 w
     *
     * @param tag
     * @param msg
     * @param tr
     * @return
     */
    public static int w(String tag, String msg, Throwable tr) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg || null == tr) {
            return -1;
        }
        return android.util.Log.w(tag, msg, tr);
    }

    /**
     * 日志打印级别 w
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int w(String tag, String msg) {
        if (!mbLoggable) {
            return -1;
        }
        if (null == tag || null == msg) {
            return -1;
        }
        return android.util.Log.w(tag, msg);
    }
}
