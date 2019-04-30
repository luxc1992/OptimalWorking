package com.yst.onecity.view.viewpagerindicator.titleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * 通用的指示器标题，子元素内容由外部提供，事件回传给外部
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public class CommonPagerTitleView extends FrameLayout implements IMeasurablePagerTitleView {
    private OnPagerTitleChangeListener mOnPagerTitleChangeListener;
    private ContentPositionDataProvider mContentPositionDataProvider;

    public CommonPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        if (mOnPagerTitleChangeListener != null) {
            mOnPagerTitleChangeListener.onSelected(index, totalCount);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        if (mOnPagerTitleChangeListener != null) {
            mOnPagerTitleChangeListener.onDeselected(index, totalCount);
        }
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (mOnPagerTitleChangeListener != null) {
            mOnPagerTitleChangeListener.onLeave(index, totalCount, leavePercent, leftToRight);
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (mOnPagerTitleChangeListener != null) {
            mOnPagerTitleChangeListener.onEnter(index, totalCount, enterPercent, leftToRight);
        }
    }

    @Override
    public int getContentLeft() {
        if (mContentPositionDataProvider != null) {
            return mContentPositionDataProvider.getContentLeft();
        }
        return getLeft();
    }

    @Override
    public int getContentTop() {
        if (mContentPositionDataProvider != null) {
            return mContentPositionDataProvider.getContentTop();
        }
        return getTop();
    }

    @Override
    public int getContentRight() {
        if (mContentPositionDataProvider != null) {
            return mContentPositionDataProvider.getContentRight();
        }
        return getRight();
    }

    @Override
    public int getContentBottom() {
        if (mContentPositionDataProvider != null) {
            return mContentPositionDataProvider.getContentBottom();
        }
        return getBottom();
    }

    /**
     * 外部直接将布局设置进来
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        setContentView(contentView, null);
    }

    public void setContentView(View contentView, LayoutParams lp) {
        removeAllViews();
        if (contentView != null) {
            if (lp == null) {
                lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            addView(contentView, lp);
        }
    }

    public void setContentView(int layoutId) {
        View child = LayoutInflater.from(getContext()).inflate(layoutId, null);
        setContentView(child, null);
    }

    public OnPagerTitleChangeListener getOnPagerTitleChangeListener() {
        return mOnPagerTitleChangeListener;
    }

    public void setOnPagerTitleChangeListener(OnPagerTitleChangeListener onPagerTitleChangeListener) {
        mOnPagerTitleChangeListener = onPagerTitleChangeListener;
    }

    public ContentPositionDataProvider getContentPositionDataProvider() {
        return mContentPositionDataProvider;
    }

    public void setContentPositionDataProvider(ContentPositionDataProvider contentPositionDataProvider) {
        mContentPositionDataProvider = contentPositionDataProvider;
    }

    public interface OnPagerTitleChangeListener {
        /**
         * 选择
         *
         * @param index      索引
         * @param totalCount 总数
         */
        void onSelected(int index, int totalCount);

        /**
         * 未选择
         *
         * @param index      索引
         * @param totalCount 总数
         */
        void onDeselected(int index, int totalCount);

        /**
         * leave
         *
         * @param index        索引
         * @param totalCount   总数
         * @param leavePercent leavePercent
         * @param leftToRight  leftToRight
         */
        void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

        /**
         * leave
         *
         * @param index        索引
         * @param totalCount   总数
         * @param enterPercent enterPercent
         * @param leftToRight  leftToRight
         */
        void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);
    }

    public interface ContentPositionDataProvider {
        /**
         * 左侧内容
         *
         * @return left
         */
        int getContentLeft();

        /**
         * 顶部内容
         *
         * @return top
         */
        int getContentTop();

        /**
         * 右侧内容
         *
         * @return right
         */
        int getContentRight();

        /**
         * 底部内容
         *
         * @return bottom
         */
        int getContentBottom();
    }
}
