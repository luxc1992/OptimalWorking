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
import com.yst.onecity.bean.MyCardBagDetailBean;
import com.yst.onecity.view.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * (我的)卡包详情-打卡记录适配器
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/19
 */
public class CardDetailRecordAdapter extends BaseAdapter {
    private Context context;
    private List<MyCardBagDetailBean.ContentBean.PunchMsgListBean> punchMsgList;

    public CardDetailRecordAdapter(Context context, List<MyCardBagDetailBean.ContentBean.PunchMsgListBean> punchMsgList) {
        this.context = context;
        this.punchMsgList = punchMsgList;
    }

    public void setRecordList(List<MyCardBagDetailBean.ContentBean.PunchMsgListBean> list) {
        if (list != null) {
            this.punchMsgList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return punchMsgList.size();
    }

    @Override
    public Object getItem(int i) {
        return punchMsgList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardDetailRecordViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card_record, null);
            vh = new CardDetailRecordViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (CardDetailRecordViewHolder) convertView.getTag();
        }
        vh.tvName.setText(punchMsgList.get(position).getNikeName());
        vh.tvTime.setText(punchMsgList.get(position).getUserTime());
        vh.tvAddress.setText(punchMsgList.get(position).getServiceAdd());
        Glide.with(context).load(punchMsgList.get(position).getImgAddress()).error(R.mipmap.default_product_icon).into(vh.imageView);
        vh.star.setStar(punchMsgList.get(position).getStarNum());
        return convertView;
    }

    class CardDetailRecordViewHolder {
        @BindView(R.id.img_record)
        RoundedImageView imageView;
        @BindView(R.id.tv_record_name)
        TextView tvName;
        @BindView(R.id.tv_record_time)
        TextView tvTime;
        @BindView(R.id.star_record)
        RatingBar star;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        CardDetailRecordViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
