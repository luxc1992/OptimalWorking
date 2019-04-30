package com.yst.onecity.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 *  权限工具类
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/11.
 */
public class PermissionUtil{
    private static PermissionUtil mPermissionUtil;
    private PermissionUtil () {
    }
    public static PermissionUtil getInstance() {
        if(mPermissionUtil == null){
            synchronized (PermissionUtil.class){
                if(mPermissionUtil == null){
                    mPermissionUtil = new PermissionUtil();
                }
            }
        }
        return  mPermissionUtil;
    }

    public interface OnPermissionGrantListener {
        /**
         * 获取权限
         * @param isGrant grant
         * @param permission 权限
         * @param requestCode 请求码
         */
        void grantPermission(boolean isGrant, String permission, int requestCode);
    }
    private OnPermissionGrantListener mPermissionGrantListener;
    public void setmPermissionGrantListener(OnPermissionGrantListener permissionGrantListener){
        mPermissionGrantListener = permissionGrantListener;
    }



    public static boolean isGranted(String permission,Activity activity) {
        return !isMarshmallow() || isGranteds(permission,activity);
    }

    public static boolean isGranteds(String permission,Activity activity) {
        int checkSelfPermission = ContextCompat.checkSelfPermission(activity, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     *  shouldShowRequestPermissionRationale主要用于给用户一个申请权限的解释，该方法只有在用户在上一次已经拒绝过你的这个权限申请。也就是说，用户已经拒绝一次了，你又弹个授权框，你需要给用户一个解释，为什么要授权，则使用该方法。
     * @param activity 类
     * @param permission 权限
     * @param requestCode 请求码
     */
    public static void requestPermissionForActivity(Activity activity ,final String permission, final int requestCode) {
        if (!isGranted(permission,activity)) {
            //小于23 或 未开启权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        } else {
            Log.i("onRequestPermissions","requestPermissionForActivity==直接执行相应操作了");
            //直接执行相应操作了
            if(mPermissionUtil.mPermissionGrantListener != null){
                mPermissionUtil.mPermissionGrantListener.grantPermission(true,permission,requestCode);
            }
        }
    }

    public static void requestPermissionForFragment(Activity activity ,final String permission, final int requestCode) {
        if (!isGranted(permission,activity)) {
            if(mPermissionUtil.mPermissionGrantListener != null){
                mPermissionUtil.mPermissionGrantListener.grantPermission(false,permission,requestCode);
            }
        } else {
            //直接执行相应操作了
            if(mPermissionUtil.mPermissionGrantListener != null){
                mPermissionUtil.mPermissionGrantListener.grantPermission(true,permission,requestCode);
            }
        }
    }
}
