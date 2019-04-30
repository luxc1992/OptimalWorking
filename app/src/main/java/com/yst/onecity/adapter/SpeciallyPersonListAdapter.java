package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.SpeciallyPersonPhoneBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 专员列表适配器
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/27
 */

public class SpeciallyPersonListAdapter extends BaseAdapter {
    private ArrayList<SpeciallyPersonPhoneBean.ContentBean> list;
    private Context context;

    public SpeciallyPersonListAdapter(ArrayList<SpeciallyPersonPhoneBean.ContentBean> list, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_speciallyperson_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (list.get(i).ischeck()) {
            viewHolder.tvState.setImageResource(R.mipmap.am_check);
        } else {
            viewHolder.tvState.setImageResource(R.mipmap.am_uncheck);
        }
        viewHolder.tvName.setText(list.get(i).getNickname() == null ? "暂无昵称" : list.get(i).getNickname());
        Glide.with(context).load(list.get(i).getAddress() == null ? "" : list.get(i).getAddress()).error(R.mipmap.ic_head).into(viewHolder.ivHead);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_head)
        RoundedImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_state)
        ImageView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
