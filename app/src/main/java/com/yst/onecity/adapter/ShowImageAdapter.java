package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示图片适配器
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/24
 */
public class ShowImageAdapter extends BaseAdapter {
    private List<ImageBean> datas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public ShowImageAdapter(List<ImageBean> list, Context context) {
        datas = list;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_image, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(datas.get(position).getAddress()).into(holder.ivAddImage);
        holder.rlDelete.setVisibility(View.GONE);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_add_image)
        ImageView ivAddImage;
        @BindView(R.id.rl_delete)
        RelativeLayout rlDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
