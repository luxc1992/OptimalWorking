package com.yst.onecity.view.viewpagerindicator;

/**
 * Created by lenovo on 2017/7/13.
 */

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;

import com.yst.onecity.view.viewpagerindicator.indicatorview.IPagerIndicator;
import com.yst.onecity.view.viewpagerindicator.titleview.IPagerTitleView;


/**
 * CommonNavigator适配器，通过它可轻松切换不同的指示器样式
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public abstract class AbstractCommonNavigatorAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    /**
     * getCount
     * @return count
     */
    public abstract int getCount();

    /**
     * 头布局
     * @param context 上下文
     * @param index 索引
     * @return title
     */
    public abstract IPagerTitleView getTitleView(Context context, int index);

    /**
     * 获取滑动
     * @param context 上下文
     * @return  indicator
     */
    public abstract IPagerIndicator getIndicator(Context context);

    public float getTitleWeight(Context context, int index) {
        return 1;
    }

    public final void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public final void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public final void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public final void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }
}
