package com.yst.onecity.http;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;


import com.yst.onecity.utils.ContactUtils;

import java.util.concurrent.Executor;

/**
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/31
 */

public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get()
    {

        return PLATFORM;
    }

    private static Platform findPlatform()
    {
        try
        {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0)
            {
                return new Platform.Android();
            }
        } catch (ClassNotFoundException ignored)
        {
        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor()
    {
        return ContactUtils.Executorsa.executor;
    }

    public void execute(Runnable runnable)
    {
        defaultCallbackExecutor().execute(runnable);
    }


    static class Android extends Platform
    {
        @Override
        public Executor defaultCallbackExecutor()
        {
            return new Platform.Android.MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor
        {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r)
            {
                handler.post(r);
            }
        }
    }

}
