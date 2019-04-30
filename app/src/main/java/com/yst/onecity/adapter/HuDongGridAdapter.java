package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.WindowUtils;
import com.yst.onecity.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/5/17.
 */

public class HuDongGridAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public HuDongGridAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_hudong_img, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams layoutParams = mViewHolder.imgGridHudong.getLayoutParams();
        int width = (App.screenWidth - WindowUtils.dip2px(mContext, 45)) / 3;
        layoutParams.width = width;
        layoutParams.height = width;
        mViewHolder.imgGridHudong.setLayoutParams(layoutParams);
        Glide.with(App.getInstance()).load(mList.get(position)).into(mViewHolder.imgGridHudong);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_grid_hudong)
        ImageView imgGridHudong;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
