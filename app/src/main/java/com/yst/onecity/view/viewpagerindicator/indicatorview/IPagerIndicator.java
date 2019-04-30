package com.yst.onecity.view.viewpagerindicator.indicatorview;

import com.yst.onecity.view.viewpagerindicator.PositionData;

import java.util.List;

/**
 * 抽象的viewpager指示器，适用于CommonNavigator
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public interface IPagerIndicator {
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
     * positionDataProvide
     *
     * @param dataList 数据
     */
    void onPositionDataProvide(List<PositionData> dataList);
}
