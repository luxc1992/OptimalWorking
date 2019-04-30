package com.yst.onecity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;

/**
 * ViewHolder基类
 *
 * @author jiaofan
 * @date 2017/12/13
 * @version 1.0.1
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    public MyViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static MyViewHolder get(Context context, ViewGroup parent, int layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        MyViewHolder holder = new MyViewHolder(context, itemView, parent);

        return holder;
    }

    /**
     * 通过id获取view
     *
     * @param viewId 控件id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            // 直接从ItemView中找
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView文本
     */
    public MyViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }



    /**
     * 设置TextView文本
     *
     * @param viewId 控件的id
     * @param text 控件展示内容
     * @param resourceId 资源样式
     * @param textColor 展示文本的颜色
     * @return
     */
    public MyViewHolder setText(int viewId, CharSequence text, int resourceId, String textColor) {
        TextView tv = getView(viewId);
        tv.setBackgroundResource(resourceId);
        tv.setTextColor(Color.parseColor(textColor));
        tv.setText(text);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param viewId 控件的id
     * @param textColor 文本颜色
     * @return
     */
    public MyViewHolder setTextColor(int viewId, int textColor) {
        TextView tv = getView(viewId);
        tv.setTextColor(textColor);
        return this;
    }

    /**
     * 设置View的Visibility
     */
    public MyViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }


    /**
     * 设置ImageView的资源
     */
    public MyViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置ImageView的网络url
     */
    public MyViewHolder setImageHttpUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        Glide.with(mContext).load(url).error(R.mipmap.loading).into(imageView);
        return this;
    }

    /**
     * 设置条目点击事件
     */
    public MyViewHolder setOnInClickListener(int viewId, Object o, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setTag(o);
        view.setOnClickListener(listener);
        return this;
    }
}
