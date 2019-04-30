package com.yst.onecity.view.viewpagerindicator.titleview;


/**
 * 可测量内容区域的指示器标题
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public interface IMeasurablePagerTitleView extends IPagerTitleView {
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
