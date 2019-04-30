package com.yst.basic.framework.view.jazzyviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 自定义viewpager滑动速度控制
 *
 * @author lixiangchao
 * @date 2017/12/12
 * @version 1.0.1
 */
@SuppressLint("NewApi")
public class FixedSpeedScroller extends Scroller {

    /**
     * 默认时长
     */
    private int mDuration = 3000;

    /**
     * 构造方法
     * @param context
     */
    public FixedSpeedScroller(Context context) {
        super(context);
    }

    /**
     * 构造方法
     * @param context
     * @param interpolator
     */
    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /**
     * 构造方法
     * @param context
     * @param interpolator
     * @param flywheel
     */
    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // TODO Auto-generated method stub
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // TODO Auto-generated method stub
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    /**
     * 设置滑动时间
     * @param mDuration 间隔
     */
    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    /**
     * 获取滑动时间
     * @return
     */
    public int getmDuration() {
        return mDuration;
    }
}