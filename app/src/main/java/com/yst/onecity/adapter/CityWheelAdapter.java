
package com.yst.onecity.adapter;
import android.content.Context;

import com.yst.onecity.bean.CityAdressBean;

import java.util.List;

/**
 *(个人信息)服务弹框市适配
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/6
 */
public class CityWheelAdapter extends BaseWheelAdapter<CityAdressBean.ContentBean> {
    public CityWheelAdapter(Context context, List<CityAdressBean.ContentBean> list) {
        super(context, list);
    }

    @Override
    protected CharSequence getItemText(int index) {
        CityAdressBean.ContentBean data = getItemData(index);
        if (data != null) {
            return data.getName();
        }
        return null;
    }
}
