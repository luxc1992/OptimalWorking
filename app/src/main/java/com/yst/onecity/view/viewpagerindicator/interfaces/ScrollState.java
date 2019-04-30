package com.yst.onecity.view.viewpagerindicator.interfaces;

/**
 * 自定义滚动状态，消除对ViewPager的依赖
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */

public interface ScrollState {
    int SCROLL_STATE_IDLE = 0;
    int SCROLL_STATE_DRAGGING = 1;
    int SCROLL_STATE_SETTLING = 2;
}
