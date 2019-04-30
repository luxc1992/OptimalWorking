package com.yst.onecity.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.LogisticsCompanyBean;

import java.util.List;


/**
 * 自定义下拉框
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/1
 */

public class SpinnerPopWindow extends PopupWindow {
    private LayoutInflater inflater;
    private ListView mListView;
    private List<LogisticsCompanyBean.ContentBean> list;
    private MyAdapter mAdapter;

    public SpinnerPopWindow(Context context, List<LogisticsCompanyBean.ContentBean> list, AdapterView.OnItemClickListener clickListener) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.list = list;
        init(clickListener);
    }

    private void init(AdapterView.OnItemClickListener clickListener) {
        View view = inflater.inflate(R.layout.custom_listview, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = view.findViewById(R.id.lv);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(clickListener);
    }

    private class MyAdapter extends BaseAdapter {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder=new ViewHolder();
                convertView = inflater.inflate(R.layout.item_layout, null);
                holder.tvName = convertView.findViewById(R.id.txt_item_company);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(list.get(position).getCompanyName());
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvName;
    }
}
