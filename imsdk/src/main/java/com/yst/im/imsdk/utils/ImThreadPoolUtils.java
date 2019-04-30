package com.yst.im.imsdk.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class ImThreadPoolUtils {
    public static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(5);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, String.valueOf(mCount.getAndIncrement()));
        }
    };

}
