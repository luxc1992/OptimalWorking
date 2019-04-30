package com.yst.onecity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.home.HomeProjectNewsBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页资讯适配器
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/18
 */
public class HomeChildAdapter extends BaseAdapter {

    private List<HomeProjectNewsBean> childList;
    private Activity context;

    public HomeChildAdapter(Activity context, List<HomeProjectNewsBean> childList) {
        this.childList = childList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public Object getItem(int i) {
        return childList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_child_home, null);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        HomeProjectNewsBean newsBean = childList.get(i);
        viewHolder.tvChildTitle.setText(ConstUtils.getStringNoEmpty(newsBean.getTitle()));
        Glide.with(context).load(newsBean.getImgUrl()).error(R.mipmap.default_product_icon).into(viewHolder.ivNewsImg);
        return view;
    }

    class MyViewHolder {
        @BindView(R.id.tv_child_title)
        TextView tvChildTitle;
        @BindView(R.id.iv_news_img)
        RoundedImageView ivNewsImg;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
