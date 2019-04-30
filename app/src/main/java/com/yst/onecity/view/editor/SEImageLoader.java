package com.yst.onecity.view.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

/**
 * 图片工具类
 *
 * @author songbinbin
 * @version 3.0.1
 * @date 2017/12/14
 */
public class SEImageLoader {
    /**
     * 图片缓存的核心类
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    /**
     * 线程池的线程数量，默认为1
     */
    private int mThreadCount = 1;
    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTasks;
    /**
     * 轮询的线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHander;
    /**
     * 运行在UI线程的handler，用于给ImageView设置图片
     */
    private Handler mHandler;

    /**
     * 引入一个值为1的信号量，防止mPoolThreadHander未初始化完成
     */
    private volatile Semaphore mSemaphore = new Semaphore(0);

    /**
     * 引入一个值为1的信号量，由于线程池内部也有一个阻塞线程，防止加入任务的速度过快，使LIFO效果不明显
     */
    private volatile Semaphore mPoolSemaphore;

    private static SEImageLoader mInstance;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    /**
     * 队列的调度方式
     */
    public enum Type {
        //先进先出
        FIFO
        //后进先出
        , LIFO
    }

    /**
     * 单例获得该实例对象
     *
     * @return
     */
    public static SEImageLoader getInstance() {

        if (mInstance == null) {
            synchronized (SEImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SEImageLoader(1, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    private SEImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {

        class LoggerThreadFactory implements ThreadFactory {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {

                    }
                });
                return t;
            }
        }
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new LoggerThreadFactory());
        //Looper Three
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHander = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        scheduledThreadPoolExecutor.execute(getTask());
                        try {
                            mPoolSemaphore.acquire();
                        } catch (InterruptedException e) {
                        }
                    }
                };
                // 释放一个信号量
                mSemaphore.release();
                Looper.loop();
            }
        });

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                return null;
            }
        });
        mPoolSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<Runnable>();
        mType = type == null ? Type.LIFO : type;

    }

    /**
     * 加载图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView) {
        // set tag
        imageView.setTag(path);
        createMainHandler();

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            sendImageLoadCompleteMessage(path, imageView, bm);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {

                    ImageSize imageSize = getImageViewWidth(imageView);
                    int reqWidth = imageSize.width;
                    int reqHeight = imageSize.height;
                    Bitmap bm = decodeSampledBitmapFromResource(path, reqWidth, reqHeight);
                    addBitmapToLruCache(path, bm);
                    sendImageLoadCompleteMessage(path, imageView, getBitmapFromLruCache(path));
                    mPoolSemaphore.release();
                }
            });
        }
    }

    /**
     * 加载Net图片
     *
     * @param path
     * @param imageView
     */
    public void loadNetImage(final String path, final ImageView imageView, Context context) {

        Glide.with(context)
                .load(path)
                //不执行淡入淡出动画
                .dontAnimate()
                //加载中占位图
                .placeholder(R.mipmap.loading)
                //加载错误默认图
                .error(R.mipmap.loading)
                .into(imageView);
    }

    /**
     * 加载Net图片
     *
     * @param path
     * @param imageView
     */
    public void loadNetImage(String imagePath, final String path, final ImageView imageView, Context context) {

        Glide.with(context)
                .load(imagePath)
                //不执行淡入淡出动画
                .dontAnimate()
                //加载中占位图
                .placeholder(R.mipmap.camera)
                //加载错误默认图
                .error(R.mipmap.camera)
                .into(imageView);
    }

    /**
     * 根据指定的尺寸去加载图片
     *
     * @param path
     * @param imageView
     * @param maxSize
     */
    public void loadImage(final String path, final ImageView imageView, final PointF maxSize) {
        // set tag
        imageView.setTag(path);
        // UI线程
        createMainHandler();

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            sendImageLoadCompleteMessage(path, imageView, bm);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    Bitmap bm = compressBitmap(path, maxSize);
                    addBitmapToLruCache(path, bm);
                    sendImageLoadCompleteMessage(path, imageView, getBitmapFromLruCache(path));
                    mPoolSemaphore.release();
                }
            });
        }
    }

    /**
     * 发送图片已经加载完成的消息到主线程
     *
     * @param path
     * @param imageView
     * @param bm
     */
    private void sendImageLoadCompleteMessage(String path, ImageView imageView, Bitmap bm) {
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.bitmap = bm;
        holder.imageView = imageView;
        holder.path = path;
        Message message = Message.obtain();
        message.obj = holder;
        mHandler.sendMessage(message);
    }

    private void createMainHandler() {
        // UI线程
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bm = holder.bitmap;
                    String path = holder.path;
                    if (imageView.getTag().toString().equals(path)) {
                        if (null == bm) {
                            imageView.setImageResource(R.mipmap.camera);
                        } else {
                            imageView.setImageBitmap(bm);
                        }
                    }
                }
            };
        }
    }

    public Bitmap compressBitmap(String path, PointF maxSize) {
        Bitmap compressBitmap = null;
        // 限定-尺寸
        float wMax = maxSize.x;
        float hMax = maxSize.y;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opt);
        if (opt.outWidth != -1 && opt.outHeight != -1) {
            float wIn = opt.outWidth < opt.outHeight ? opt.outWidth : opt.outHeight;
            float hIn = opt.outHeight > opt.outWidth ? opt.outHeight : opt.outWidth;

            float scaleIn = wIn / hIn;
            float scaleMax = wMax / hMax;

            // 若较宽 则限制宽
            opt.inJustDecodeBounds = false;
            // "1/opt.inSampleSize"表缩放比率
            if (scaleIn > scaleMax) {
                opt.inSampleSize = (int) Math.ceil(wIn / wMax);
            } else {
                opt.inSampleSize = (int) Math.ceil(hIn / hMax);
            }

            compressBitmap = BitmapFactory.decodeFile(path, opt);
        }
        return compressBitmap;
    }

    /**
     * 添加一个任务
     *
     * @param runnable
     */
    private synchronized void addTask(Runnable runnable) {
        try {
            // 请求信号量，防止mPoolThreadHander为null
            if (mPoolThreadHander == null) {
                mSemaphore.acquire();
            }
        } catch (InterruptedException e) {
        }
        mTasks.add(runnable);

        mPoolThreadHander.sendEmptyMessage(0x110);
    }

    /**
     * 取出一个任务
     *
     * @return
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

    /**
     * 单例获得该实例对象
     *
     * @param threadCount 线程数
     * @param type        队列调度方式
     * @return
     */
    public static SEImageLoader getInstance(int threadCount, Type type) {

        if (mInstance == null) {
            synchronized (SEImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SEImageLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }

    /**
     * 根据ImageView获得适当的压缩的宽和高
     *
     * @param imageView
     * @return
     */
    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        final LayoutParams params = imageView.getLayoutParams();
        // Get
        int width = params.width == LayoutParams.WRAP_CONTENT ? 0 : imageView.getWidth();
        // actual
        // image
        // width
        if (width <= 0) {
            // Get layout width parameter
            width = params.width;
        }
        if (width <= 0) {
            // Check
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        // maxWidth
        // parameter
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }
        // Get
        int height = params.height == LayoutParams.WRAP_CONTENT ? 0 : imageView.getHeight();
        // actual
        // image
        // height
        if (height <= 0) {
            // Get layout height parameter
            height = params.height;
        }
        if (height <= 0) {
            // Check
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        }
        // maxHeight
        // parameter
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;

    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     */
    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 往LruCache中添加一张图片
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (getBitmapFromLruCache(key) == null) {
            if (bitmap != null) {
                mLruCache.put(key, bitmap);
            }
        }
    }

    /**
     * 计算inSampleSize，用于压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight) {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 根据计算的inSampleSize，得到压缩后图片
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

        return bitmap;
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }

    /**
     * 反射获得ImageView设置的最大宽度和高度
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        Field field;
        try {
            field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;

                Log.e("TAG", value + "");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

}
