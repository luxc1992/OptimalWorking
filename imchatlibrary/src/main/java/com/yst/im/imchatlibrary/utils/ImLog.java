package com.yst.im.imchatlibrary.utils;

import android.util.Log;

import static com.yst.im.imchatlibrary.utils.Constants.DEBUG;

/**
 * LOG
 *
 * @author lierpeng
 * @date 2018/03/28
 * @version 1.0.0
 */
public class ImLog {

    public static int v(String tag, String msg) {
        if (DEBUG) {
            return Log.v(tag, msg);
        } else {
            return -1;
        }
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            return Log.v(tag, msg, tr);
        } else {
            return -1;
        }
    }

    /**
     * log  d
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int d(String tag, String msg) {
        if (DEBUG) {
            return Log.d(tag, msg);
        } else {
            return -1;
        }
    }

    /**
     * log  d
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            return Log.d(tag, msg, tr);
        } else {
            return -1;
        }
    }

    /**
     * log  i
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int i(String tag, String msg) {
        if (DEBUG) {
            return Log.i(tag, msg);
        } else {
            return -1;
        }
    }

    /**
     * log  i
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            return Log.i(tag, msg, tr);
        } else {
            return -1;
        }
    }

    /**
     * log  w
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int w(String tag, String msg) {
        if (DEBUG) {
            return Log.w(tag, msg);
        } else {
            return -1;
        }
    }

    /**
     * log  w
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            return Log.w(tag, msg, tr);
        } else {
            return -1;
        }
    }

    /**
     * log  w
     *
     * @param tag 内容1
     * @param tr 内容2
     * @return
     */
    public static int w(String tag, Throwable tr) {
        if (DEBUG) {
            return Log.w(tag, tr);
        } else {
            return -1;
        }
    }

    /**
     * log e
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int e(String tag, String msg) {
        if (DEBUG) {
            return Log.e(tag, msg);
        } else {
            return -1;
        }
    }

    /**
     * log e
     *
     * @param tag 内容1
     * @param msg 内容2
     * @return
     */
    public static int e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            return Log.e(tag, msg, tr);
        } else {
            return -1;
        }
    }
}