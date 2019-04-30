package com.yst.im.imsdk.dao;

import android.content.Context;

/**
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/26.
 */

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static Context context;

    private GreenDaoManager() {
        init();
    }

    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleInstanceHolder {
        private static GreenDaoManager INSTANCE = null;
    }

    /**
     * 对外唯一实例的接口 * * @return
     */
    public static void init(Context context1) {
        context = context1;
        SingleInstanceHolder.INSTANCE = new GreenDaoManager();
    }

    /**
     * 对外唯一实例的接口 * * @return
     */
    public static GreenDaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

//    public static DaoManager getInstance(Context context, String DB_NAME) {
//        if (null == mDbManager) {
//            synchronized (DaoManager.class) {
//                if (null == mDbManager) {
//                    mDbManager = new DaoManager(context,DB_NAME);
//                }
//            }
//        }
//        return mDbManager;
//    }


    /**
     * 初始化数据
     */
    private void init() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "im_db");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}



