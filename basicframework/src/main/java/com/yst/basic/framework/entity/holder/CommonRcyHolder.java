package com.yst.basic.framework.entity.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * RecyclerView通用ViewHolder
 *
 * @author lixiangchao
 * @date 2017/12/13
 * @version 1.0.1
 */
public class CommonRcyHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;
    private static boolean conver = true;
    private View mConverView;

    public CommonRcyHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mConverView = itemView;
    }

    /**
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static CommonRcyHolder get(Context context, ViewGroup parent, int layoutId) {
        View mConverView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return  new CommonRcyHolder(mConverView);
    }

    /**
     * 通过id获取组件 注意：不要将组件声明到全局，否则获取到的永远是最后一个组件
     * @param viewId 控件ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return this.mConverView;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param text   string
     * @return
     */
    public CommonRcyHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRcyHolder setVisiable(int viewId, int flag) {
        TextView tv = getView(viewId);
        tv.setVisibility(flag);
        return this;
    }

    public CommonRcyHolder setRlVisiable(int viewId, int flag) {
        RelativeLayout rl = getView(viewId);
        rl.setVisibility(flag);
        return this;
    }

    public CommonRcyHolder setTextColor(int viewId, int textColor) {
        TextView tv = getView(viewId);
        tv.setTextColor(textColor);
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number float
     * @return
     */
    public CommonRcyHolder setText(int viewId, float number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * TextView
     * @param viewId
     * @param number int
     * @return
     */
    public CommonRcyHolder setText(int viewId, int number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number double
     * @return
     */
    public CommonRcyHolder setText(int viewId, Double number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * Button
     *
     * @param viewId
     * @param text   string
     * @return
     */
    public CommonRcyHolder setButton(int viewId, String text) {
        Button btn = getView(viewId);
        btn.setText(text);
        return this;
    }

    /**
     * 通过资源ID设置ImageView
     * @param viewId
     * @param resId  资源ID
     * @return
     */
    public CommonRcyHolder setImageView(int viewId, int resId) {
        ImageView image = getView(viewId);
        image.setImageResource(resId);
        return this;
    }

    /**
     * 通过bimap设置ImageView
     * @param viewId
     * @param bitmap
     * @return
     */
    public CommonRcyHolder setImageView(int viewId, Bitmap bitmap) {
        ImageView image = getView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 通过url设置ImageView
     * @param viewId
     * @param url    图片地址
     * @param resId  默认资源图片
     * @return
     */
    public CommonRcyHolder setImageView(Context context, int viewId, String url, int resId) {
        ImageView image = getView(viewId);
        Glide.with(context).load(url).placeholder(resId).into(image);
        return this;
    }


    /**
     * View的ITEM点击事件
     * @param listener
     */
    public void setItemClickListener(View.OnClickListener listener) {
        getConvertView().setOnClickListener(listener);
    }

    /**
     * View的ITEM长按点击事件
     * @param listener
     */
    public void setLongItemClickListener(View.OnLongClickListener listener) {
        getConvertView().setOnLongClickListener(listener);
    }

    /**
     * View点击事件
     * @param viewId
     * @param listener
     */
    public CommonRcyHolder setClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * View长按点击事件
     * @param viewId
     * @param listener
     */
    public CommonRcyHolder setLongClickListener(int viewId, View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    public CommonRcyHolder setRatingBar(int viewId, int rating) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        return this;
    }
}
