package com.yst.im.imchatlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imsdk.utils.BaseUtils;

import java.util.ArrayList;

/**
 * 自定义表情底部指示器
 *
 * @author Lierpeng
 * @date 2018/3/29.
 * @version 1.0.0
 */
public class IndicatorView extends LinearLayout {

    private Context mContext;
    /**
     * 所有指示器集合
     */
    private ArrayList<View> mImageViews;
    private int size = 6;
    private int marginSize = 15;
    /**
     * 指示器的大小
     */
    private int pointSize;
    /**
     * 间距
     */
    private int marginLeft;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        pointSize = BaseUtils.dp2px(context, size);
        marginLeft = BaseUtils.dp2px(context, marginSize);
    }

    /**
     * 初始化指示器
     *
     * @param count 指示器的数量
     */
    public void initIndicator(int count) {
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        LayoutParams lp;
        for (int i = 0; i < count; i++) {
            View v = new View(mContext);
            lp = new LayoutParams(pointSize, pointSize);
            if (i != 0) {
                lp.leftMargin = marginLeft;
            }
            v.setLayoutParams(lp);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.bg_circle_white);
            } else {
                v.setBackgroundResource(R.drawable.bg_circle_gary);
            }
            mImageViews.add(v);
            this.addView(v);
        }
    }

    /**
     * 页面移动时切换指示器
     */
    public void playByStartPointToNext(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            startPosition = nextPosition = 0;
        }
        final View viewStrat = mImageViews.get(startPosition);
        final View viewNext = mImageViews.get(nextPosition);
        viewNext.setBackgroundResource(R.drawable.bg_circle_white);
        viewStrat.setBackgroundResource(R.drawable.bg_circle_gary);
    }

}
