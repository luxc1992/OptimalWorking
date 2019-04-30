package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.R;
import com.yst.onecity.bean.MyCardBagDetailBean;
import com.yst.onecity.view.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * (我的)卡包详情-可用团队适配器
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/19
 */
public class CardDetailTeamAdapter extends BaseAdapter {
    private Context context;
    private List<MyCardBagDetailBean.ContentBean.TeamMsgListBean> list;

    public CardDetailTeamAdapter(Context context, List<MyCardBagDetailBean.ContentBean.TeamMsgListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setTeamList(List<MyCardBagDetailBean.ContentBean.TeamMsgListBean> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CardDetailTeamViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card_team, null);
            vh = new CardDetailTeamViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (CardDetailTeamViewHolder) convertView.getTag();
        }
        vh.tvName.setText(list.get(position).getNikeName());
        vh.tvAddress.setText(list.get(position).getServiceAdd());
        Glide.with(context).load(list.get(position).getImgAddress()).error(R.mipmap.default_product_icon).into(vh.imageView);
        vh.tvRelationTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("联系团队");
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(list.get(position).getNikeName());
                intentChatEntity.setAcceptId(list.get(position).getMerchantUUID());
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(context, intentChatEntity);
            }
        });
        vh.star.setStar(list.get(position).getStarNum());
        return convertView;
    }

    class CardDetailTeamViewHolder {
        @BindView(R.id.img_relation_team)
        RoundedImageView imageView;
        @BindView(R.id.tv_relation_team_name)
        TextView tvName;
        @BindView(R.id.tv_relation_team)
        TextView tvRelationTeam;
        @BindView(R.id.star_relation_team)
        RatingBar star;
        @BindView(R.id.tv_relation_team_address)
        TextView tvAddress;

        CardDetailTeamViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
