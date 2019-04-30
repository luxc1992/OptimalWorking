package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.yst.im.imsdk.utils.BaseUtils;

/**
 * 九宫格头像
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/05/18
 */
public abstract class NineGridImageViewAdapter<T> {
    public abstract void onDisplayImage(Context context, ImageView imageView, T t);

    public ImageView generateImageView(Context context) {

        //设置图片宽高
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(BaseUtils.dip2px(context,65), BaseUtils.dip2px(context,65)));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
}
