package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;

import java.util.List;

/**
 * 添加产品计划封面图适配器
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/3/1
 */
public class AddProductPlanViewPagerAdapter extends PagerAdapter {
    List<String> listCover;
    Context context;
    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    public AddProductPlanViewPagerAdapter(Context context, List<String> listCover) {
        this.listCover = listCover;
        this.context = context;
    }

    public void setDatas(List<String> listCover){
        this.listCover = listCover;
        setImgToArray();
        notifyDataSetChanged();
    }

    /**
     * 将图片装载到数组中
     */
    public void setImgToArray(){
        mImageViews = new ImageView[listCover.size()];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews[i] = imageView;
            Glide.with(context)
                    .load(listCover.get(i))
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.loading)
                    .into(imageView);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView iv = mImageViews[position];
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(iv);
            }
        });
       container.addView(mImageViews[position % mImageViews.length], 0);
        return mImageViews[position % mImageViews.length];
    }

    @Override
    public int getCount() {
        return listCover.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 点击监听
     */
    public interface OnVpItemClickListener {
        /**
         * 条目点击监听OnClick
         * @param imageView 控件
         */
        void onClick(ImageView imageView);
    }
    private OnVpItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnVpItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
