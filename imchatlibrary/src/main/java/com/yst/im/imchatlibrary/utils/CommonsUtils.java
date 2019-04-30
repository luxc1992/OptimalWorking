package com.yst.im.imchatlibrary.utils;

import android.content.Context;

import static com.yst.im.imsdk.MessageConstant.NUM_10;
import static com.yst.im.imsdk.MessageConstant.NUM_2;

/**
 * 语音时长
 *
 * @author lierpeng
 * @date 2018/03/28
 * @version 1.0.0
 */
public class CommonsUtils {
    /**
     * 根据时间长短计算语音条宽度:220dp
     *
     * @param context
     * @param seconds
     * @return
     */
    public synchronized static int getVoiceLineWight(Context context, int seconds) {
        //1-2s是最短的。2-10s每秒增加一个单位。10-60s每10s增加一个单位。
        if (seconds <= NUM_2) {
            return dip2px(context, 90);
        } else if (seconds <= NUM_10) {
            //90~170
            return dip2px(context, 90 + 8 * seconds);
        } else {
            //170~220
            return dip2px(context, 170 + 10 * (seconds / 10));

        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
