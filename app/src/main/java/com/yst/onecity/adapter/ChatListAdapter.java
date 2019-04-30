package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.ChatBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 联系人列表
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/17.
 */

public class ChatListAdapter extends BaseAdapter {
    private Context context;
    List<ChatBean> chatBeanList;

    public ChatListAdapter(Context context, List<ChatBean> chatBeens) {
        this.context = context;
        if (null != chatBeens) {
            this.chatBeanList = chatBeens;
        }else {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_chat, null);
        }
        ViewHolder mViewHolder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_chat, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_item_chat_head)
        RoundedImageView ivItemChatHead;
        @BindView(R.id.txt_item_chat_name)
        TextView txtItemChatName;
        @BindView(R.id.txt_item_chat_content)
        TextView txtItemChatContent;
        @BindView(R.id.txt_item_chat_time)
        TextView txtItemChatTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
