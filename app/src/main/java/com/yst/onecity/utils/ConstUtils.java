package com.yst.onecity.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import me.nereo.multi_image_selector.MultiImageSelector;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO1024;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO500;
import static com.yst.onecity.Constant.NULL;

/**
 * 常用工具类
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/6
 */
public class ConstUtils {
    /**
     * 对字符串判空
     *
     * @param string 字符串
     * @return s
     */
    public static String getStringNoEmpty(String string) {
        if (null == string || string.length() == 0 || NULL.equals(string)) {
            string = "";
        }
        return string;
    }

    /**
     * 空string改0
     *
     * @param string 字符串
     * @return s
     */
    public static String changeEmptyStringToZero(String string) {
        if (null == string || string.length() == 0 || NULL.equals(string)) {
            string = "0";
        }
        return string;
    }

    /**
     * 设置文本
     */
    public static void setTextString(String string, TextView textView) {
        if (!TextUtils.isEmpty(getStringNoEmpty(string))) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(string);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
    /**
     * 设置文本invisible
     */
    public static void setTextInVisibleString(String string, TextView textView) {
        if (!TextUtils.isEmpty(getStringNoEmpty(string))) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(string);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 选择图片
     *
     * @param activity  activity
     * @param maxCount  图片最大数量
     * @param imgCount  已选择图片数量
     * @param imageCode code
     */
    public static void selectImg(Activity activity, int maxCount,int imgCount, int imageCode) {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MultiImageSelector.create().count(maxCount - imgCount).showCamera(true).start(activity, imageCode);
        } else {
            EasyPermissions.requestPermissions(activity, "请打开拍照和读取照片权限", 300,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 选择图片
     *
     * @param activity  activity
     * @param imageCode code
     */
    public static void selectOneImg(Activity activity, int imageCode) {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MultiImageSelector.create().count(1).single().showCamera(true).start(activity, imageCode);
        } else {
            EasyPermissions.requestPermissions(activity, "请打开拍照和读取照片权限", 300,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 判断交易密码，不能是连续相同的数字
     *
     * @param pwd 密码
     * @return b
     */
    public static boolean checkTradePwd(String pwd) {
        char[] chars = pwd.toCharArray();

        return !(chars[NO0] == chars[NO1] || chars[NO1] == chars[NO2]
                || chars[NO2] == chars[NO3] || chars[NO3] == chars[NO4]
                || chars[NO4] == chars[NO5]);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 复制编号
     *
     * @param no 编号
     */
    public static void copyNo(String no, Activity activity) {
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, no));
            if (clipboardManager.hasPrimaryClip()) {
                clipboardManager.getPrimaryClip().getItemAt(0).getText();
            }
            ToastUtils.show("复制成功");
        }
    }

    /**
     * 创建线程池
     */
    public static final ThreadFactory S_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "download#" + mCount.getAndIncrement());
        }
    };

    /**
     * 将长整型转换成时间格式
     *
     * @param time
     * @return
     */
    public static String longToHms(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(time);
    }

    /**
     * 将长整型转换成带两位小数点的string格式
     *
     * @param size
     * @return
     */
    public static String longToPoint(long size) {
        float i = Float.parseFloat(String.valueOf(size));
        DecimalFormat fnum = new DecimalFormat("##0.00");
        if (i / NO1024 / NO1024 > NO500) {
            return fnum.format(i / 1024 / 1024 / 1024) + " G";
        } else {
            return fnum.format(i / 1024 / 1024) + " M";
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳string
     */
    public static String getCurrentDateFormat() {
        //设置日期显示格式
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-HH:mm:ss");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        // 将时间装换为设置好的格式
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 根据订单状态码返回对应状态文字
     *
     * @param status 状态码
     * @return 订单状态
     */
    public static String getReturnStatus(int status) {
        //申请状态 0申请退货退款中 1 申请退款中 2已确认退货退款 3已确认退款 4已拒绝退货退款 5已拒绝退款 6 同意退货
        String str = "";
        switch (status) {
            case 0:
                str = "申请退货退款中";
                break;
            case 1:
                str = "申请退款中";
                break;
            case 2:
                str = "已确认退货退款";
                break;
            case 3:
                str = "已确认退款";
                break;
            case 4:
                str = "已拒绝退货退款";
                break;
            case 5:
                str = "已拒绝退款";
                break;
            case 6:
                str = "已同意退货";
                break;
            default:
                break;
        }
        return str;
    }

    /**
     * 转换时间日期格式
     * @param time 年月日 时分秒
     * @return 日期
     */
    public static String getDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = null;
        String str2 = null;
        try {
            if (TextUtils.isEmpty(time)) {
                return "2018/02/10";
            } else {
                Date date = format.parse(time);
                // 获取日期实例
                Calendar calendar = Calendar.getInstance();
                // 将日历设置为指定的时间
                calendar.setTime(date);
                // 获取年
                int year = calendar.get(Calendar.YEAR);
                // 月份是从0开始。
                int month = calendar.get(Calendar.MONTH) + 1;
                // 获取天
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                Calendar calendar2 = Calendar.getInstance();
                int year2 = calendar2.get(Calendar.YEAR);
                int month2 = calendar2.get(Calendar.MONTH) + 1;
                int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                if (year == year2 && month == month2 && day == day2) {
                    int hour=calendar.get(Calendar.HOUR_OF_DAY);
                    int minute=calendar.get(Calendar.MINUTE);
                    if (String.valueOf(hour).length() == NO1) {
                        str2 = "0" + hour+ ":" + minute;
                        if (String.valueOf(minute).length() == NO1) {
                            str2 = "0" + hour+ ":" + "0" + minute;
                        }
                    } else {
                        if (String.valueOf(minute).length() == NO1) {
                            str2 = hour+ ":" + "0" + minute;
                        } else {
                            str2 = hour+ ":" + minute;
                        }
                    }
                    return str2;
                } else {
                    // 如果月份为单数
                    if (String.valueOf(month).length() == NO1) {
                        str = year + "/0" + month + "/" + day;
                        if (String.valueOf(day).length() == NO1) {
                            str = year + "/0" + month + "/0" + day;
                        }
                    } else {
                        if (String.valueOf(day).length() == NO1) {
                            str = year + "/" + month + "/0" + day;
                        } else {
                            str = year + "/" + month + "/" + day;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 实现文本复制功能
     *
     * @param copy 内容
     * @param context 当前文
     */
    public static void copy(String copy, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        assert cmb != null;
        cmb.setPrimaryClip(ClipData.newPlainText(null,copy));
        if (cmb.hasPrimaryClip()){
            cmb.getPrimaryClip().getItemAt(0).getText();
        }
    }

    /**
     * 调用拨号界面
     *
     * @param phoneNumber 电话号
     * @param activity 当前类
     */
    public static void call(String phoneNumber, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 每次加载图片的时候判断一下context是否为空
     *
     * @param context context
     * @return boolean
     */
    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 解决scrollview嵌套listview问题
     *
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 控制按钮可点击延时
     *
     * @param view 控件
     * @param ms 时间
     */
    public static void setFilter(View view, long ms){
        try {
            Field field = View.class.getDeclaredField("mListenerInfo");
            field.setAccessible(true);
            Class listInfoType = field.getType();
            Object listInfo = field.get(view);
            Field onclickField = listInfoType.getField("mOnClickListener");
            View.OnClickListener origin = (View.OnClickListener) onclickField.get(listInfo);
            onclickField.set(listInfo, new ClickProxy(origin, ms));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 控制按钮点击代理
     */
    private static class ClickProxy implements View.OnClickListener {

        private View.OnClickListener origin;
        private long lastClick = 0;
        private long time;

        public ClickProxy(View.OnClickListener origin, long time) {
            this.origin = origin;
            this.time = time;
        }

        @Override
        public void onClick(View v) {
            if (System.currentTimeMillis() - lastClick >= time){
                origin.onClick(v);
                lastClick = System.currentTimeMillis();
            }
        }
    }

}
