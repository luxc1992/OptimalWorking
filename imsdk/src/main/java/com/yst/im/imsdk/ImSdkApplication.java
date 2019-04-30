package com.yst.im.imsdk;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * @author wangj
 * @date 2018/5/15
 */

public class ImSdkApplication extends Application {

    public static String channel="";

    @Override
    public void onCreate() {
        super.onCreate();
        sdkConfig();
        channel= getRequestSource();
        Log.e("sdk", "onCreate: "+channel );
    }

    protected void sdkConfig() {

    }

    public String getRequestSource() {
        try {
            channel = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString("REQUEST_SS");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
