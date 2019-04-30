package com.yst.onecity.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.utils.ConstUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 服务评价详情grid图片适配器
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/26
 */
public class ServiceEvaluateDetailGridAdapter extends BaseAdapter {
    private String[] imgList;
    private Activity context;
    public ServiceEvaluateDetailGridAdapter(String[] imgList, Activity context) {
        this.imgList = imgList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgList.length;
    }

    @Override
    public Object getItem(int i) {
        return imgList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.service_order_evaluate_detail_gv_image_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(ConstUtils.isValidContextForGlide(context)){
            if(!TextUtils.isEmpty(imgList[i])){
                Glide.with(context)
                        .load(imgList[i])
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.loading)
                        .into(viewHolder.imageView);
            }
        }
        return view;
    }

    class ViewHolder{
        @BindView(R.id.service_order_evaluate_detail_gv_image_item_iv)
        ImageView imageView;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
