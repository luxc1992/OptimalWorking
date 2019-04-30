package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yst.onecity.R;
import com.yst.onecity.view.MyGridView;

import java.util.List;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;

/**
 * 城市选择头适配器
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CityHeaderAdapter extends IndexableHeaderAdapter {
    private static final int TYPE = 1;
    private Context context;
    private List<String> hotCities;
    private OnHotCityListener hotCityListener;

    public CityHeaderAdapter(String index, String indexTitle, List data, List<String> hotCities, Context context, OnHotCityListener hotCityListener) {
        super(index, indexTitle, data);
        this.context = context;
        this.hotCities = hotCities;
        this.hotCityListener = hotCityListener;
    }

    @Override
    public int getItemViewType() {
        return TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city_header, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindContentViewHolder(final RecyclerView.ViewHolder holder, Object entity) {
        // 数据源为null时, 该方法不用实现
        final MyViewHolder vh = (MyViewHolder) holder;
        HotCityAdapter hotCityAdapter = new HotCityAdapter(context, hotCities);
        vh.gridView.setAdapter(hotCityAdapter);
        vh.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotCityListener.onCityListener(hotCities.get(position));
            }
        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        GridView gridView;

        private MyViewHolder(View itemView) {
            super(itemView);
            gridView = (MyGridView) itemView.findViewById(R.id.griView);
        }
    }


    public interface OnHotCityListener {
        /**
         * 城市回调
         *
         * @param hotCity 热门城市
         */
        void onCityListener(String hotCity);
    }
}
