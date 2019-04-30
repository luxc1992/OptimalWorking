package com.yst.basic.framework.entity;

import android.view.View;

import butterknife.ButterKnife;

/**
 * 通用适配器Holder
 *
 * @author lixiangchao
 * @date 2017/3/14.
 * @version 1.0.1
 */
public class BaseHoler {
    private View convertView;

    /**
     *  设置item的样式
     * @param convertView
     */
    public void setConvertView(View convertView) {
        this.convertView = convertView;
        ButterKnife.bind(this, convertView);
    }

    /**
     * 获取视图
     * @return
     */
    public View getConvertView() {
        return convertView;
    }
}
