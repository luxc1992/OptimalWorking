package com.yst.onecity.wheelview.listener;

import com.yst.onecity.view.AddressSelector;
import com.yst.onecity.view.viewpagerindicator.interfaces.CityInterface;

/**
 * 地址选择器条目监听
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/6
 */
public interface OnItemClickListener {

    /**
     * 条目监听回调
     *
     * @param addressSelector 选择器对象
     * @param city            返回地址列表对应点击的对
     * @param tabPosition     对应tab的位置
     */
    void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition);
}
