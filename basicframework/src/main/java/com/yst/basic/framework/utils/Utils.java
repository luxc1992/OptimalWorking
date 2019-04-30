package com.yst.basic.framework.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.Const;
import com.yst.basic.framework.view.ContainsEmojiEditText;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的一些辅助方法
 *
 * @author lixiangchao
 * @date 2017/12/1.
 * @version 1.0.1
 */
public class Utils {

    /**
     * 上次点击的时间
     */
    private static long lastClickTime;
    public static final int SPACE_TIME = 800;
    /**
     * 十六进制数组
     */
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
     */
    private static Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

    /**
     * 手机号的匹配规则
     */
    private static Pattern mMobilePattern = Pattern.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,3,6,7,8]))\\d{8}$");

    /**
     * 对点击事件进行防爆处理
     *
     * @return 是否可点
     */
    public static boolean isClickable() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < SPACE_TIME) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 设置过滤表情框的长度
     *
     * @param editText 目标控件
     * @param length   最大长度
     */
    public static void setEmojiEdittextLength(ContainsEmojiEditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * 获取指定字符串长度，最后以dot补充
     *
     * @param content 要截取的字符串内容
     * @param length  新的内容长度
     * @return 指定的内容
     */
    public static String getAssignDesc(String content, int length) {
        if (!TextUtils.isEmpty(content) && !Const.NULL.equals(content)) {
            if (content.length() > length) {
                return content.substring(0, length) + "...";
            } else {
                return content;
            }
        }
        return "";
    }

    /**
     * 获取当前时分
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
        return format.format(date);
    }
    /**
     * VerName
     *
     * @param context
     * @return
     */
    public static String getVerCode(Context context) {
        String verName = null;
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verName;
    }
    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 字节转十六进制
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 字节数组转十六进制
     *
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    /**
     * 加密md5
     *
     * @param origin
     * @param charsetname
     * @return
     */
    public static String mMD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {
            resultString = null;
            exception.printStackTrace();
        }
        return resultString;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 得到当前手机的ip地址
     *
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            //当前使用2G/3G/4G网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //得到IPV4地址
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }


    /**
     * 判断身份证号码
     **/
    public static boolean isCard(String cardStr) {
        // 通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(cardStr);
        // 判断用户输入是否为身份证号
        return idNumMatcher.matches();

    }

    /**
     * 将字符串转为时间戳
     *
     * @param mTime
     * @return
     */
    public static long getTime(String mTime) {
        String mTempTimeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        Date d;
        try {
            d = sdf.parse(mTime);
            long l = d.getTime();
            String str = String.valueOf(l);
            mTempTimeString = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.parseLong(mTempTimeString);
    }

    public static String getStrTime1111(String mTimeContent) {
        String mTimeString = null;
        if (!Const.NULL.equals(mTimeContent)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            long mLccTime = Long.valueOf(mTimeContent);
            mTimeString = sdf.format(new Date(mLccTime * 1000L));
        }
        return mTimeString;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param mTimeContent
     * @return
     */
    public static String getStrTime(String mTimeContent) {
        String mTempTimeString = null;
        if (!Const.NULL.equals(mTimeContent)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long mLccTime = Long.valueOf(mTimeContent);
            mTempTimeString = sdf.format(new Date(mLccTime * 1000L));
        }
        return mTempTimeString;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param mTimeContent
     * @return
     */
    public static String getStrTimeLength10(String mTimeContent) {
        String mTempTimeString = null;
        if (!Const.NULL.equals(mTimeContent)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long mLccTime = Long.valueOf(mTimeContent);
            mTempTimeString = sdf.format(new Date(mLccTime));
        }
        return mTempTimeString;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param mDateContent
     * @return
     */
    public static String getYearMonthDay(String mDateContent) {
        String mDateString = null;
        if (!Const.NULL.equals(mDateContent)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            long mLccTime = Long.valueOf(mDateContent);
            mDateString = sdf.format(new Date(mLccTime));
        }
        return mDateString;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param mTempTime
     * @return
     */
    public static String getStrTime2(String mTempTime) {
        String mTempStrTime = null;
        if (!Const.NULL.equals(mTempTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long lccTime = Long.valueOf(mTempTime);
            mTempStrTime = sdf.format(new Date(lccTime * 1000L));
        }
        return mTempStrTime;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param mTempTime
     * @return
     */
    public static String getStrTime3(String mTempTime) {
        String reStrTime = null;
        if (!Const.NULL.equals(mTempTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long lccTime = Long.valueOf(mTempTime);
            reStrTime = sdf.format(new Date(lccTime * 1000L));
        }
        return reStrTime;
    }

    /**
     * 毫秒转换
     *
     * @param ms 毫秒
     * @return 时间
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
//        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        //天
//        String strDay = day < 10 ? "0" + day : "" + day;
        //小时
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        //分钟
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        //秒
//        String strSecond = second < 10 ? "0" + second : "" + second;
        //毫秒
//        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
//        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strHour + "小时" + strMinute + " 分钟 ";
    }

    /**
     * 正则匹配是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Matcher m = mMobilePattern.matcher(mobiles);
        return m.matches();
    }

    /**
     * 嵌套listview，完全显示
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 半角转为全角
     */
    public static String mInputContentToDBC(String input) {
        if (input != null && !"".equals(input)) {
            char[] c = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375) {
                    c[i] = (char) (c[i] - 65248);
                }
            }
            return new String(c);
        }
        return "";
    }

    /**
     * 开始一个按钮的倒计时
     *
     * @param button                    控件(TextView类型)
     * @param normalBackGroundResources 正常的控件背景资源id
     * @param normalTextColorResources  正常字体颜色资源id
     * @param unableBackGroundResources 不能点击时背景资源id
     * @param unableTextColorResources  不能点击是字体颜色资源id
     * @param totalTime  倒计时总时长
     * @param content  倒计时结束后展示文本
     */
    public static void startCountDown(final TextView button, final int normalBackGroundResources,
                                      final int normalTextColorResources,
                                      final int unableBackGroundResources,
                                      final int unableTextColorResources,
                                      final int totalTime, final String content) {
        final int normalTextColor = ContextCompat.getColor(button.getContext(), normalTextColorResources);
        final int unableTextColor = ContextCompat.getColor(button.getContext(), unableTextColorResources);
        button.setEnabled(false);
        button.setBackgroundResource(unableBackGroundResources);
        button.setTextColor(unableTextColor);
        new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText((millisUntilFinished / 1000) + "秒后可重发");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setBackgroundResource(normalBackGroundResources);
                button.setTextColor(normalTextColor);
                button.setText(content);
            }
        }.start();
    }

    /**
     * 开始一个按钮的倒计时
     *
     * @param button                    控件(TextView类型)
     * @param normalTextColorResources  正常字体颜色资源id
     * @param unableTextColorResources  不能点击是字体颜色资源id
     * @param totalTime  倒计时总时长
     * @param content  倒计时结束后展示文本
     */
    public static void startCountDown(final TextView button,
                                      final int normalTextColorResources,
                                      final int unableTextColorResources,
                                      final int totalTime, final String content) {
        final int normalTextColor = ContextCompat.getColor(button.getContext(), normalTextColorResources);
        final int unableTextColor = ContextCompat.getColor(button.getContext(), unableTextColorResources);
        button.setEnabled(false);
        button.setTextColor(unableTextColor);
        new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText((millisUntilFinished / 1000) + "秒后可重发");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setTextColor(unableTextColorResources);
                button.setText(content);
            }
        }.start();
    }

    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    /**
     * 将时间戳转为字符串
     *
     * @param mTimeContent 时间戳
     * @param format 格式化
     * @return 转义后的字段
     */
    public static String getCustomStrTime(String mTimeContent,String format) {
        String mTempTimeString = null;
        if (!Const.NULL.equals(mTimeContent)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            long mLccTime = Long.valueOf(mTimeContent);
            mTempTimeString = sdf.format(new Date(mLccTime));
        }
        return mTempTimeString;
    }
}
