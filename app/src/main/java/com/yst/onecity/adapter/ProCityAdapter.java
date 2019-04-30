package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yst.basic.framework.utils.MyLog;
import com.yst.onecity.R;
import com.yst.onecity.bean.PositionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 城市列表的适配器
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/26
 */

public class ProCityAdapter extends BaseAdapter {
    private Context context;
    private List<PositionBean.ContentBean> addressList = new ArrayList<>();

    public ProCityAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param mList 地址集合
     */
    public void addData(List<PositionBean.ContentBean> mList) {
        if (null != mList) {
            this.addressList.clear();
            this.addressList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        MyLog.e("sss","==="+addressList.size());
        return addressList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == view) {
            view = View.inflate(context, R.layout.item_address_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvPosition.setText(addressList.get(i).getName());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_position)
        TextView tvPosition;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
