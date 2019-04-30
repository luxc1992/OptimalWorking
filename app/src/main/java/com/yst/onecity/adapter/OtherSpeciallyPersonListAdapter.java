package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yst.onecity.R;
import com.yst.onecity.bean.SpeciallyPersonPhoneBean;

import java.util.ArrayList;

/**
 * 其他专员列表适配器
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/27
 */

public class OtherSpeciallyPersonListAdapter extends BaseAdapter {
    private ArrayList<SpeciallyPersonPhoneBean.ContentBean> list;
    private Context context;

    public OtherSpeciallyPersonListAdapter(ArrayList<SpeciallyPersonPhoneBean.ContentBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_speciallyperson_list, null);
            viewHolder.tvState = view.findViewById(R.id.tv_state);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        if (list.get(i).ischeck()) {
            viewHolder.tvState.setImageResource(R.mipmap.am_check);
        }else {
            viewHolder.tvState.setImageResource(R.mipmap.am_uncheck);
        }
        return view;
    }

    class ViewHolder {
        ImageView tvState;

    }
}
