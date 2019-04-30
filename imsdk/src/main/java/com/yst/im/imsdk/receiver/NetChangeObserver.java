package com.yst.im.imsdk.receiver;

/**
 * 是检测网络改变的观察者
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/11.
 */
public interface NetChangeObserver {
    /**
     * 网络连接连接时调用
     */
    void onNetConnect();

    /**
     * 当前没有网络连接
     */
    void onNetDisconnect();
}