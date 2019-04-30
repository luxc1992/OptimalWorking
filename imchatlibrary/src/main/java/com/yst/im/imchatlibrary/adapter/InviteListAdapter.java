package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.bean.InviteEntity;

import java.util.List;

/**
 * 被邀请者适配器
 *
 * @author Lierpeng
 * @date 2018/04/12.
 * @version 1.0.0
 */
public class InviteListAdapter extends BaseAdapter {
    private Context context;
    private List<InviteEntity.ContentBean> mInviteList;
    private List<InviteEntity.ContentBean> mInviteIntentList;
    private LayoutInflater mInflater;

    /**
     * 通过构造器传参 匹配适配器
     */
    public InviteListAdapter(Context context, List<InviteEntity.ContentBean> mInviteList) {
        this.context = context;
        this.mInviteList = mInviteList;
        this.mInflater = LayoutInflater.from(context);
        mInviteIntentList = MyApp.getInviteList();
    }

    @Override
    public int getCount() {
        return null == mInviteList ? 0 :mInviteList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInviteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_intvitee, null);
            holder.mImgGroupMembersUserIcon = (ImageView) convertView.findViewById(R.id.img_invite_UserIcon);
            holder.mImgGroupMembersUserName = (TextView) convertView.findViewById(R.id.img_invite_UserName);
            holder.mImgGroupMembersUserTopic = (TextView) convertView.findViewById(R.id.img_invite_UserTopic);
            holder.mImgGroupMembersUserCompany = (TextView) convertView.findViewById(R.id.img_invite_UserCompany);
            holder.mTxtInviteSelect = (TextView) convertView.findViewById(R.id.txt_invite_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(mInviteList.get(position).getUserIcon())
                .error(context.getResources().getDrawable(R.mipmap.icon_default))
                .into(holder.mImgGroupMembersUserIcon);
        holder.mImgGroupMembersUserName.setText(mInviteList.get(position).getNickName());
        holder.mImgGroupMembersUserTopic.setText(mInviteList.get(position).getCompanyName());
        holder.mImgGroupMembersUserCompany.setText(mInviteList.get(position).getJob());

        if (mInviteIntentList.size() > 0) {
            for (int i = 0; i < mInviteIntentList.size(); i++) {
                if(mInviteIntentList.get(i).getUserId().equals(mInviteList.get(position).getUserId())){
                    mInviteList.get(position).setCheck(true);
                    holder.mTxtInviteSelect.setBackground(context.getResources().getDrawable(R.drawable.invitee_checked));
                }
            }
        }

        final ViewHolder finalHolder = holder;
        holder.mTxtInviteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mInviteList.get(position).isCheck()){
                    for (int i = 0; i < MyApp.getInviteList().size(); i++) {
                        if (MyApp.getInviteList().get(i).getUserId().equals(mInviteList.get(position).getUserId())) {
                            MyApp.getInviteList().remove(i);
                        }
                    }
                    mInviteList.get(position).setCheck(false);
                    finalHolder.mTxtInviteSelect.setBackground(context.getResources().getDrawable(R.drawable.invitee_circle));
                }else{
                    mInviteList.get(position).setCheck(true);
                    mInviteIntentList.add(mInviteList.get(position));
                    finalHolder.mTxtInviteSelect.setBackground(context.getResources().getDrawable(R.drawable.invitee_checked));
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView mImgGroupMembersUserIcon;
        private TextView mImgGroupMembersUserName;
        private TextView mImgGroupMembersUserTopic;
        private TextView mImgGroupMembersUserCompany;
        private TextView mTxtInviteSelect;
    }
}
