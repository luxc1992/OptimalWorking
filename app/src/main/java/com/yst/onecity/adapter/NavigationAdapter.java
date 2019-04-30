package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yst.onecity.R;

import java.util.List;

/**
 * 首页分类适配器
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Integer> mData;

    public NavigationAdapter(Context context, List<Integer> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel;
        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_homepage_type, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvLabel = (TextView) view.findViewById(R.id.tv_label);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.tvLabel.setText(mData.get(i));
    }
}
