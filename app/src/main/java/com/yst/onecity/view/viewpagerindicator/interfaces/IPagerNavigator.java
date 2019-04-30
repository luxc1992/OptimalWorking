package com.yst.onecity.view.viewpagerindicator.interfaces;

/**
 * 抽象的ViewPager导航器
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public interface IPagerNavigator {

    /**
     * 滑动
     *
     * @param position             位置
     * @param positionOffset       偏移
     * @param positionOffsetPixels 偏移量
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * 滑动
     *
     * @param position 位置
     */
    void onPageSelected(int position);

    /**
     * 变化
     *
     * @param state 状态
     */
    void onPageScrollStateChanged(int state);

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    void onAttachToMagicIndicator();

    /**
     * 当IPagerNavigator从MagicIndicator上移除时调用
     */
    void onDetachFromMagicIndicator();

    /**
     * ViewPager内容改变时需要先调用此方法，自定义的IPagerNavigator应当遵守此约定
     */
    void notifyDataSetChanged();
}
