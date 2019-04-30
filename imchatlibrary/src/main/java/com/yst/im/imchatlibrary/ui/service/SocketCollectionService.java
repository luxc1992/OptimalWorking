package com.yst.im.imchatlibrary.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;

import java.util.Random;

/**
 * 服务socket
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/28
 */
public class SocketCollectionService extends Service {

    /**
     * client 可以通过Binder获取Service实例
     */
    public class MyBinder extends Binder {
        public SocketCollectionService getService() {
            return SocketCollectionService.this;
        }
    }

    /**
     * 通过binder实现调用者client与Service之间的通信
     */
    private MyBinder binder = new MyBinder();
    private final Random generator = new Random();

    @Override
    public void onCreate() {
        Log.i("Kathy", "SocketCollectionService - onCreate - Thread = " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        启动线程判断是否连接
        MyApp.getMsClient().getCollection();
        Log.i("Kathy", "SocketCollectionService - onStartCommand - startId = " + startId + ", Thread = " + Thread.currentThread().getName());
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Kathy", "SocketCollectionService - onBind - Thread = " + Thread.currentThread().getName());
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("Kathy", "SocketCollectionService - onUnbind - from = " + intent.getStringExtra("from"));
        return false;
    }

    @Override
    public void onDestroy() {
        Log.i("Kathy", "SocketCollectionService - onDestroy - Thread = " + Thread.currentThread().getName());
        super.onDestroy();
    }

    /**
     * getRandomNumber是Service暴露出去供client调用的公共方法
     */
    public int getRandomNumber() {
        return generator.nextInt();
    }
}


