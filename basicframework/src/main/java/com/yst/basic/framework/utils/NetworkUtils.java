package com.yst.basic.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

/**
 * 网络判断类
 *
 * @author lixiangchao
 * @date 2017/09/21
 * @version 1.0.1
 */
public class NetworkUtils {
    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;

    /**
     * 判断网络是那个
     *
     * @param context 上下文对象
     * @return 返回的网络标识
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Wifi
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORN_WIFI;
        }
        // 3G
        if (isCanUseSim(context)) {
            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORN_MOBILE;
            }
        }
        return NETWORN_NONE;
    }

    /**
     * 手机是否联网
     *
     * @param context 上下文
     * @return 是否连接网络
     */
    public static boolean isConnectNet(Context context) {
        int result = getNetworkState(context);
        return result != NETWORN_NONE;
    }

    /**
     * SIM卡是否可用
     *
     * @param context 上下文
     * @return 是否可用
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
