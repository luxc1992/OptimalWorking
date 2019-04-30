package com.yst.onecity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/5/16
 */
public class ImageCycleView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;
    private RelativeLayout adRl;
    /**
     * 滚动图片视图适配
     */
    private ImageCycleAdapter mAdvAdapter;
    /**
     * 图片轮播指示器控件(下方圆点脚标)
     */
    private LinearLayout mGroup;
    private int pointSpace = 6;

    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;

    /**
     * 滚动图片指示视图列表
     */
    private ImageView[] mImageViews = null;

    /**
     * 广告图片点击监听
     */
    private ImageCycleViewListener mImageCycleViewListener;

    /**
     * 手机密度
     */
    private float mScale;
    private boolean isStop;
    private ArrayList<String> mImageDescList;

    /**
     * @param context 上下文
     */
    public ImageCycleView(Context context) {
        super(context);
        init(context);
    }

    /**
     * @param context 上下文
     * @param attrs
     */
    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        LayoutInflater.from(context).inflate(R.layout.ad_cycle_view, this);
        mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
        adRl = (RelativeLayout) findViewById(R.id.ad_rl);
        //将容器的触摸事件反馈给ViewPager
        adRl.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return mAdvPager.dispatchTouchEvent(event);
            }

        });

        mAdvPager.addOnPageChangeListener(new GuidePageChangeListener());
        // 滚动图片右下指示器视
        mGroup = findViewById(R.id.circles);
        mGroup.setGravity(Gravity.CENTER);
    }

    /**
     * 装填图片数据
     *
     * @param imageUrlList 图片集合
     * @param isShow       实现画廊效果
     */
    public void setImageResources(List<String> imageUrlList, boolean isShow) {
        if (isShow) {
            int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 4.0f);
            ViewGroup.LayoutParams lp = mAdvPager.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                lp.width = pagerWidth;
            }
            //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
            mAdvPager.setClipChildren(false);
            //父容器一定要设置这个，否则看不出效果
            adRl.setClipChildren(false);
            mAdvPager.setLayoutParams(lp);
            //设置ViewPager切换效果，即实现画廊效果
            mAdvPager.setPageTransformer(true, new ZoomOutPageTransformer());
            //设置预加载数量
            mAdvPager.setOffscreenPageLimit(20);
        }
        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = imageUrlList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = createImagePoint();
            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setImageResource(R.drawable.shape_origin_point_gray);
            } else {
                mImageViews[i].setImageResource(R.drawable.shape_origin_point_black);
            }
            mGroup.addView(mImageViews[i]);
        }
        mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList);
        mAdvPager.setAdapter(mAdvAdapter);
        startImageTimerTask();
    }

    /**
     * 构造一个图片圆点
     */
    private ImageView createImagePoint() {
        ImageView point = new ImageView(mContext);
        LayoutParams params = new LayoutParams((int) (pointSpace * mScale), (int) (pointSpace * mScale));
        params.leftMargin = (int) (pointSpace * mScale);
        point.setScaleType(ImageView.ScaleType.CENTER_CROP);
        point.setLayoutParams(params);
        return point;
    }

    /**
     * 设置监听
     *
     * @param imageCycleViewListener 监听
     */
    public void setImageCycleViewListener(ImageCycleViewListener imageCycleViewListener) {
        mImageCycleViewListener = imageCycleViewListener;
    }

    /**
     * 设置圆点脚标位置
     *
     * @param gravity 位置
     */
    public void setmGroupGravity(int gravity) {
        mGroup.setGravity(gravity);
    }

    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控件）
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片滚动
        mHandler.postDelayed(mImageTimerTask, 3000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (mImageViews != null) {
                mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
                //if  isStop=true 当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                if (!isStop) {
                    mHandler.postDelayed(mImageTimerTask, 5000);
                }
            }
        }
    };

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startImageTimerTask();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            index = index % mImageViews.length;
            // 设置图片滚动指示器背
            mImageViews[index].setImageResource(R.drawable.shape_origin_point_gray);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setImageResource(R.drawable.shape_origin_point_black);
                }
            }
        }
    }

    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private List<ImageView> mImageViewCacheList = new ArrayList<>();

        /**
         * 图片资源列表
         */
        private List<String> mAdList;

        private Context mContext;

        private ImageCycleAdapter(Context context, List<String> adList) {
            this.mContext = context;
            this.mAdList = adList;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            String imageUrl = mAdList.get(position % mAdList.size());
            ImageView imageView;
            if (mImageViewCacheList.size() == 0) {
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(position % mAdList.size(), v);
                }
            });
            container.addView(imageView);
            Glide.with(mContext).load(imageUrl).into(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            mAdvPager.removeView(view);
            mImageViewCacheList.add(view);
        }
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        void onImageClick(int position, View imageView);
    }

    /**
     * 实现的原理是，在当前显示页面放大至原来的MAX_SCALE
     * 其他页面才是正常的的大小MIN_SCALE
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;

        @Override
        public void transformPage(View page, float position) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < -1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position < 0) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position >= 0 && position < 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position >= 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }
        }
    }

    /**
     * 手机屏幕宽度
     *
     * @param ctx
     * @return
     */
    public static int getWindowWidth(Context ctx) {
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.widthPixels;
    }

    /**
     * 手机屏幕高度
     *
     * @param ctx
     * @return
     */
    public static int getWindowHeight(Context ctx) {

        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels;
    }
}
