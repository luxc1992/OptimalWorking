package com.yst.onecity.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.MyCollectShareBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * (我的)收藏-分享适配器
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/29
 */
public class MyCollectShareAdapter extends BaseAdapter {
    private Context context;
    private List<MyCollectShareBean.ContentBean> mList;
    private LayoutInflater mInflater;

    public MyCollectShareAdapter(Context context, List<MyCollectShareBean.ContentBean> list) {
        this.context = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        switch (mList.get(position).getModelType()) {
            case 1:
                OneImageViewHolder oneImageViewHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_recommended_list, null);
                    oneImageViewHolder = new OneImageViewHolder(convertView);
                    convertView.setTag(oneImageViewHolder);
                } else {
                    oneImageViewHolder = (OneImageViewHolder) convertView.getTag();
                }

                oneImageViewHolder.name.setText(ConstUtils.getStringNoEmpty(mList.get(position).getTitle()));
                oneImageViewHolder.description.setText(ConstUtils.getStringNoEmpty(mList.get(position).getDescription()));
                oneImageViewHolder.image.setVisibility(View.VISIBLE);
                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(0).getAddress()).error(R.mipmap.default_product_icon).into(oneImageViewHolder.image);

                oneImageViewHolder.tvShareNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getShareNum())));
                oneImageViewHolder.tvCommentNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getCommentNum())));
                oneImageViewHolder.tvLikeNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getFabulousNum())));
                break;
            case 3:
                ThreeImageViewHolder threeImageViewHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_recommended_list_three_pic, null);
                    threeImageViewHolder = new ThreeImageViewHolder(convertView);
                    convertView.setTag(threeImageViewHolder);
                } else {
                    threeImageViewHolder = (ThreeImageViewHolder) convertView.getTag();
                }

                threeImageViewHolder.name.setText(ConstUtils.getStringNoEmpty(mList.get(position).getTitle()));
                threeImageViewHolder.description.setText(ConstUtils.getStringNoEmpty(mList.get(position).getDescription()));

                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(0).getAddress()).error(R.mipmap.default_product_icon).into(threeImageViewHolder.image);
                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(1).getAddress()).error(R.mipmap.default_product_icon).into(threeImageViewHolder.image1);
                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(2).getAddress()).error(R.mipmap.default_product_icon).into(threeImageViewHolder.image2);

                threeImageViewHolder.tvShareNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getShareNum())));
                threeImageViewHolder.tvCommentNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getCommentNum())));
                threeImageViewHolder.tvLikeNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getFabulousNum())));
                break;
            default:
                OneImageViewHolder oneHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_recommended_list, null);
                    oneHolder = new OneImageViewHolder(convertView);
                    convertView.setTag(oneHolder);
                } else {
                    oneHolder = (OneImageViewHolder) convertView.getTag();
                }

                oneHolder.name.setText(ConstUtils.getStringNoEmpty(mList.get(position).getTitle()));
                oneHolder.description.setText(ConstUtils.getStringNoEmpty(mList.get(position).getDescription()));
                oneHolder.image.setVisibility(View.GONE);

                oneHolder.tvShareNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getShareNum())));
                oneHolder.tvCommentNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getCommentNum())));
                oneHolder.tvLikeNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getFabulousNum())));
                break;
        }


        return convertView;
    }

    static class OneImageViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.image)
        RoundedImageView image;
        @BindView(R.id.tv_share_num)
        TextView tvShareNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_like_num)
        TextView tvLikeNum;

        OneImageViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ThreeImageViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.image)
        RoundedImageView image;
        @BindView(R.id.image1)
        RoundedImageView image1;
        @BindView(R.id.image2)
        RoundedImageView image2;
        @BindView(R.id.tv_share_num)
        TextView tvShareNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_like_num)
        TextView tvLikeNum;

        ThreeImageViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
