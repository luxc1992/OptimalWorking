package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.FansOrAttentionsListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;

/**
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/5/17.
 */

public class NewAttentionAdapter extends BaseAdapter {
    private Context context;
    List<FansOrAttentionsListBean.ContentBean.ListBean> chatBeanList;

    public NewAttentionAdapter(Context context, List<FansOrAttentionsListBean.ContentBean.ListBean> chatBeens) {
        this.context = context;
        if (null != chatBeens) {
            this.chatBeanList = chatBeens;
        } else {
            this.chatBeanList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return chatBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_new_attention, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.txtItemNewAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttentionCallBack != null) {
                    mAttentionCallBack.mAttentionCallBack(position);
                }
            }
        });
        if (chatBeanList.get(position).getStatus() == NO0) {
            mViewHolder.txtItemNewAttention.setText("+ 关注 ");
        } else {
            mViewHolder.txtItemNewAttention.setText(" 互相关注 ");
        }
        Glide.with(context)
                .load(chatBeanList.get(position).getImg())
                .error(R.mipmap.headimage)
                .into(mViewHolder.ivItemNewAttentionHead);
        mViewHolder.txtItemNewAttentionName.setText(String.valueOf(chatBeanList.get(position).getNickName()));
        mViewHolder.txtItemNewAttentionTime.setText(Utils.getStrTime(chatBeanList.get(position).getCreatedTime()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_item_new_attention_head)
        RoundedImageView ivItemNewAttentionHead;
        @BindView(R.id.txt_item_new_attention_name)
        TextView txtItemNewAttentionName;
        @BindView(R.id.txt_item_new_attention_time)
        TextView txtItemNewAttentionTime;
        @BindView(R.id.txt_item_new_attention)
        TextView txtItemNewAttention;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private AttentionCallBack mAttentionCallBack;

    public void setAttentionCallBack(AttentionCallBack mAttentionCallBack) {
        this.mAttentionCallBack = mAttentionCallBack;
    }

    public interface AttentionCallBack {
        /**
         * 关注回调
         * @param position 条目
         */
        void mAttentionCallBack(int position);
    }
}
