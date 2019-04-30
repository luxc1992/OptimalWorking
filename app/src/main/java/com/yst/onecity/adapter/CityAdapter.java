package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.issue.CityBean;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * 城市列表适配器
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CityAdapter extends IndexableAdapter<CityBean> {
    private LayoutInflater mInflater;

    public CityAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_letter, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        LetterViewHolder viewHolder = (LetterViewHolder) holder;
        viewHolder.tvLetter.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, CityBean entity) {
        CityViewHolder viewHolder = (CityViewHolder) holder;
        viewHolder.tvCity.setText(entity.getName());
    }

    private class LetterViewHolder extends RecyclerView.ViewHolder {
        TextView tvLetter;

        private LetterViewHolder(View itemView) {
            super(itemView);
            tvLetter = itemView.findViewById(R.id.tv_letter);
        }
    }

    private class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;

        private CityViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
        }
    }
}
