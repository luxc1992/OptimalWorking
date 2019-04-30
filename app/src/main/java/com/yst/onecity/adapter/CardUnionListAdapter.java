package com.yst.onecity.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.CardUnionDetailActivity;
import com.yst.onecity.bean.CardUnionUsedBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/18
 */
public class CardUnionListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CardUnionUsedBean.ContentBean> mList;
    private LayoutInflater mInflater;

    public CardUnionListAdapter(Context context, List<CardUnionUsedBean.ContentBean> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getServiceOrderVOList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getServiceOrderVOList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_item_card_union_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvUseTime.setText(mList.get(groupPosition).getCreateTime());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.child_item_card_union_list, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        if (mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getType() == NO2 ) {
            childViewHolder.tvCycleCard.setVisibility(View.VISIBLE);
            childViewHolder.tvNumberCard.setVisibility(View.GONE);
        } else if (mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getType() == NO3){
            childViewHolder.tvCycleCard.setVisibility(View.GONE);
            childViewHolder.tvNumberCard.setVisibility(View.VISIBLE);
        }

        if (isLastChild) {
            childViewHolder.diverLine.setVisibility(View.GONE);
        } else {
            childViewHolder.diverLine.setVisibility(View.VISIBLE);
        }

        childViewHolder.tvName.setText(mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getNickName());

        Glide.with(context).load(ConstUtils.getStringNoEmpty(mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getLogoAttachmentAddress())).error(R.mipmap.default_head).into(childViewHolder.rivHead);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getServiceOrderId()));
                if (mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getType() == NO2 ) {
                    bundle.putInt("type", NO2);
                    JumpIntent.jump((Activity) context, CardUnionDetailActivity.class, bundle);
                } else if (mList.get(groupPosition).getServiceOrderVOList().get(childPosition).getType() == NO3){
                    bundle.putInt("type", NO3);
                    JumpIntent.jump((Activity) context, CardUnionDetailActivity.class, bundle);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    static class ViewHolder {
        @BindView(R.id.tv_use_time)
        TextView tvUseTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.riv_head)
        RoundedImageView rivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_cycle_card)
        TextView tvCycleCard;
        @BindView(R.id.tv_number_card)
        TextView tvNumberCard;
        @BindView(R.id.diver_line)
        View diverLine;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
