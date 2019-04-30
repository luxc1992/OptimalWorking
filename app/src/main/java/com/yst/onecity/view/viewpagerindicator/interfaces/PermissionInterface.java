package com.yst.onecity.view.viewpagerindicator.interfaces;

/**
 * 权限请求接口
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/9
 */
public interface PermissionInterface {
    /**
     * 可设置请求权限请求码
     *
     * @return 请求码
     */
    int getPermissionsRequestCode();

    /**
     * 设置需要请求的权限
     *
     * @return 权限数组
     */
    String[] getPermissions();

    /**
     * 请求权限成功回调
     */
    void requestPermissionsSuccess();

    /**
     * 请求权限失败的回调
     */
    void requestPermissionsFail();
}
