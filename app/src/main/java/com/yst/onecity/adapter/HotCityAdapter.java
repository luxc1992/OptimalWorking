package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yst.onecity.R;

import java.util.List;

/**
 * 首页
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/17
 */
public class HotCityAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private LayoutInflater inflater;

    public HotCityAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_hot_city, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvHotCity.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView tvHotCity;

        public ViewHolder(View view) {
            tvHotCity = (TextView) view.findViewById(R.id.tv_hot_city);
        }
    }
}
