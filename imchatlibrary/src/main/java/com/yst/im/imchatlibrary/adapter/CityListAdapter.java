package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.bean.CityEntity;

import java.util.List;

/**
 * 城市列表adapter
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/27.
 */
public class CityListAdapter extends BaseAdapter {
    private Context context;
    private List<CityEntity.ContentBean> mCityList;

    public CityListAdapter(Context context, List<CityEntity.ContentBean> mCityList) {
        this.context = context;
        this.mCityList = mCityList;
    }

    @Override
    public int getCount() {
        return null == mCityList ? 0 : mCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_city_list, null);
            holder.mTxtCityName = (TextView) convertView.findViewById(R.id.txt_city_name);

            holder.mImgCityRightArrow = (ImageView) convertView.findViewById(R.id.img_city_right_arrow);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mCityList.get(position).isSecond()) {
            holder.mImgCityRightArrow.setVisibility(View.GONE);
        }else {
            holder.mImgCityRightArrow.setVisibility(View.VISIBLE);
        }
        holder.mTxtCityName.setText(null == mCityList.get(position).getAreaName() ? "北京" : mCityList.get(position).getAreaName());
        return convertView;
    }

    private class ViewHolder {
        private TextView mTxtCityName;
        private ImageView mImgCityRightArrow;
    }
}
