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
import com.yst.onecity.bean.RecommendedBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO3;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/16
 */
public class RecommendedAdapter extends BaseAdapter {
    private final static int TYPE_COUNT = 2;
    private final static int TYPE_ONE_PIC = 0;
    private final static int TYPE_THREE_PIC = 1;
    private Context context;
    private List<RecommendedBean.ContentBean> mList;
    private LayoutInflater mInflater;

    public RecommendedAdapter(Context context, List<RecommendedBean.ContentBean> list) {
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
    public int getItemViewType(int position) {
        if (mList.get(position).getCurrencyAttachmentVos() == null || mList.get(position).getCurrencyAttachmentVos().size() == 0) {
            return TYPE_ONE_PIC;
        }
        return mList.get(position).getCurrencyAttachmentVos().size() >= NO3 ? TYPE_THREE_PIC : TYPE_ONE_PIC;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case TYPE_ONE_PIC:
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
                if (mList.get(position).getCurrencyAttachmentVos().size() == 1){
                    Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(0).getAddress()).into(oneImageViewHolder.image);
                }

                oneImageViewHolder.tvShareNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getShareNum())));
                oneImageViewHolder.tvCommentNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getCommentNum())));
                oneImageViewHolder.tvLikeNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getFabulousNum())));
                break;
            case TYPE_THREE_PIC:
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

                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(0).getAddress()).into(threeImageViewHolder.image);
                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(1).getAddress()).into(threeImageViewHolder.image1);
                Glide.with(context).load(mList.get(position).getCurrencyAttachmentVos().get(2).getAddress()).into(threeImageViewHolder.image2);

                threeImageViewHolder.tvShareNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getShareNum())));
                threeImageViewHolder.tvCommentNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getCommentNum())));
                threeImageViewHolder.tvLikeNum.setText(ConstUtils.changeEmptyStringToZero(String.valueOf(mList.get(position).getFabulousNum())));
                break;
            default:
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
