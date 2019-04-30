package com.yst.im.imsdk.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yst.im.imsdk.MessageConstant.CONTENT;
import static com.yst.im.imsdk.MessageConstant.FILE_NAME;
import static com.yst.im.imsdk.MessageConstant.NUM_10;
import static com.yst.im.imsdk.MessageConstant.NUM_100;
import static com.yst.im.imsdk.MessageConstant.NUM_1000;
import static com.yst.im.imsdk.MessageConstant.NUM_1024;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_24;
import static com.yst.im.imsdk.MessageConstant.NUM_5;
import static com.yst.im.imsdk.MessageConstant.NUM_60;
import static com.yst.im.imsdk.MessageConstant.NUM_800;
import static com.yst.im.imsdk.MessageConstant.NUM_99;

/**
 * 基础工具类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class BaseUtils {
    private static boolean isQuit = false;
    private static long lastClickTime = 0;

    /**
     * 时间格式化
     *
     * @param i 时间
     * @return 时间串
     */
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < NUM_10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * 二次back退出
     *
     * @param context 上下文
     */
    public static void onBackPressed(Context context) {

        if (!isQuit) {
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            isQuit = true;
            //在两秒钟之后isQuit会变成false
            ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        isQuit = false;
                    }
                }
            }).start();
        } else {
            System.exit(0);
        }
    }

    /**
     * 按钮防爆点击
     *
     * @return b
     */
    public static boolean isClickable() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < NUM_800) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过Uri获取文件
     *
     * @param ac  上下文
     * @param uri uri
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        if (uri.getScheme().toString().compareTo(CONTENT) == 0) {
            ContentResolver cr = ac.getContentResolver();
            // 根据Uri从数据库中找
            Cursor cursor = cr.query(uri, new String[]{"_data"}, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                // 获取图片路径
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        } else if (uri.getScheme().toString().compareTo(FILE_NAME) == 0) {
            return new File(uri.toString().replace("file://", ""));
        }
        return null;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context  上下文
     * @param dipValue
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * dp转dip
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5F);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s, String format) throws Exception {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 获取当天0点的时间   num表示天  当天为0    前一天为 -1   后一天为 1
     */
    public static String dateToDateStartTime(int num) throws Exception {
        //取时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        calendar.add(Calendar.DATE, num);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateToStamp(formatter.format(date), "yyyy-MM-dd");
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToYear(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToYearAndDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToTime(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String timeDate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("m:ss");
        @SuppressWarnings("unused")
        int i = Integer.parseInt(time);
        if (i < NUM_1000) {
            return "0:01";
        } else {
            String times = sdr.format(new Date(i));
            return times;
        }

    }

    /**
     * 获取系统时间戳
     *
     * @return 时间
     */
    public static long getSystemTime() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取系统0点时间戳
     *
     * @return 时间
     */
    public static long getSystemZeroTime() {
        long zero = getSystemTime() / (NUM_1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        return zero;
    }

    /**
     * 是否今天
     *
     * @return 是否超过
     */
    public static String getTime(long time) {
//        long time = lastClickTime * NUM_1000;
        if (time > getSystemZeroTime()) {
            return stampToTime(time);
        } else {
            return stampToYmd(time);
        }
    }

    public static String stampToYmd(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 是否需要显示时间
     * 1、当天  5分钟内显示
     * 2、非当天   不显示
     *
     * @param firstTime  上一条时间
     * @param secondTime 本条时间
     * @return 是否超过
     */
    public static boolean isFiveMinuteNoShowTime(long firstTime, long secondTime) {
        //转化为秒
        Long msgTime = Math.abs(firstTime - secondTime) / NUM_1000;
        if (msgTime > 0 && msgTime < (NUM_5 * NUM_60)) {
            return true;
        } else if (msgTime >= (NUM_24 * NUM_60 * NUM_60)) {

        }
        return false;
    }

    public static long getDate() throws Exception {
        long parseLongToday = Long.parseLong(dateToDateStartTime(0));
        long parseLongYesToday = Long.parseLong(dateToDateStartTime(-1));
        return parseLongToday - parseLongYesToday;
    }

    /**
     * 聊天记录时间显示格式化
     *
     * @param time 时间格式化
     * @return 字符串
     */
    public static String getFormatNewsTimeTxt(long time) {
        String res = null;
        int diffTime = (int) (getSystemTime() - time) / NUM_1000;
        if (diffTime < NUM_60) {
            res = "刚刚";
        } else if (diffTime >= NUM_60 && diffTime < (NUM_60 * NUM_60)) {
            res = (diffTime - (diffTime % NUM_60)) / (NUM_60) + "分钟前";
        } else if (diffTime >= (NUM_60 * NUM_60) && diffTime < (NUM_24 * NUM_60 * NUM_60)) {
            res = (diffTime - (diffTime % (NUM_60 * NUM_60))) / (NUM_60 * NUM_60) + "小时前";
        } else if (diffTime >= (NUM_24 * NUM_60 * NUM_60) && diffTime < (NUM_2 * NUM_24 * NUM_60 * NUM_60)) {
            res = "一天前";
        } else {
            res = BaseUtils.stampToYear(time);
        }
        return res;
    }

    /**
     * 聊天记录时间显示格式化
     * 1、 今天  时 分
     * 2、 今天之前  年月日
     *
     * @param time 时间格式化
     * @return 字符串
     */
    public static String getFormatNearestTimeText(long time) {
        String res = null;
        int diffTime = (int) (getSystemTime() - time) / NUM_1000;
        if (diffTime < (NUM_24 * NUM_60 * NUM_60)) {
            res = stampToTime(time);
        } else {
            res = stampToYear(time);
        }
        return res;
    }

    /**
     * 判断在不在数组里
     *
     * @param arr         shuzu
     * @param targetValue 值
     * @return 0:1
     */
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 数组转集合
     *
     * @param array 数组
     * @return 集合
     */
    public static List<String> arrayToList(String[] array) {
        List<String> list = new ArrayList<String>(array.length);
        Collections.addAll(list, array);
        return list;
    }

    /**
     * 图片按比例大小压缩图片（根据路径获取图片并压缩)
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImageFromFile(String srcPath, Activity activity) {

        Display mDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        mDisplay.getSize(point);
        int screenWidth = point.x;
        int screenHeight = point.y;

        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            // 此时返回bm为空
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

            int w = newOpts.outWidth;
            int h = newOpts.outHeight;

            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            // be=1表示不缩放
            int be = 1;
            // 如果宽度大的话根据宽度固定大小缩放
            if (w > h && w > screenWidth) {
                be = (int) Math.ceil(newOpts.outWidth / screenWidth);
                // 如果高度高的话根据宽度固定大小缩放
            } else if (w < h && h > screenHeight) {
                be = (int) Math.ceil(newOpts.outHeight / screenHeight);
            }
            if (be <= 0) {
                be = 1;
            }
            // 设置缩放比例
            newOpts.inSampleSize = be;

            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            newOpts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            // 压缩好比例大小后再进行质量压缩
            return compressImage(bitmap);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 质量压缩图片(根据图片压缩)
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            int options = 100;
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (baos.toByteArray().length / NUM_1024 > NUM_100) {
                // 重置baos即清空baos
                baos.reset();
                // 每次都减少5
                options -= 5;
                // 这里压缩options%，把压缩后的数据存放到baos中
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);

            }
            // 把压缩后的数据baos存放到ByteArrayInputStream中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            // 把ByteArrayInputStream数据生成图片
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        //单位MB
        return (freeBlocks * blockSize) / NUM_1024 / NUM_1024;
    }

}
