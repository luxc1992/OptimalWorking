package com.yst.im.imchatlibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.NineGridImageViewAdapter;
import com.yst.im.imchatlibrary.widget.NineGridImageView;

import java.util.List;

/**
 *
 * @author wangj
 * @date 2018/5/17
 */

public class SettingNineGroupIcon {

    public static void setGroupIcon(NineGridImageView groupIcon, List<String> mPostList) {
        NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String s) {
                //显示图片
                Glide.with(context).load(s).into(imageView);
                //显示圆形图片
                //Picasso.with(context).load(s).transform(new CircleImageTransformation()).placeholder(R.mipmap.ic_holding).error(R.mipmap.ic_error).into(imageView);
            }

            @Override
            public ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }
        };

        groupIcon.setAdapter(mAdapter);
        groupIcon.setImagesData(mPostList);
    }

    public static void setSingleIcon(NineGridImageView groupIcon, List<Integer> mPostList) {
        NineGridImageViewAdapter<Integer> mAdapter = new NineGridImageViewAdapter<Integer>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, Integer s) {
                //显示图片
                Glide.with(context).load(s).into(imageView);
                //显示圆形图片
                //Picasso.with(context).load(s).transform(new CircleImageTransformation()).placeholder(R.mipmap.ic_holding).error(R.mipmap.ic_error).into(imageView);
            }

            @Override
            public ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }
        };

        groupIcon.setAdapter(mAdapter);
        groupIcon.setImagesData(mPostList);
    }

}
