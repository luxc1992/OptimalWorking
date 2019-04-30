package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.ImRoundedImageView;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.ChatType;

import java.util.ArrayList;
import java.util.List;


/**
 * 好友列表adapter
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/04/09.
 */
public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private ChatType chatType;
    private List<FriendListEntity.ContentBean> friendList;

    public FriendListAdapter(Context context, List<FriendListEntity.ContentBean> friendList, ChatType chatType) {
        this.context = context;
        this.friendList = friendList;
        this.chatType = chatType;
    }

    @Override
    public int getCount() {
        return null == friendList ? 0 : friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_friendlist, null);
            holder.mImgFriendListUserIcon = (NineGridImageView) convertView.findViewById(R.id.img_friend_list_user_icon);
            holder.mTxtFriendListUserName = (TextView) convertView.findViewById(R.id.txt_friend_list_user_name);
            holder.mTxtFriendListGroupNum = (TextView) convertView.findViewById(R.id.txt_friend_list_group_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTxtFriendListUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FriendListEntity.ContentBean contentBean = friendList.get(position);
        if (contentBean.isGroup()) {
            holder.mTxtFriendListGroupNum.setVisibility(View.VISIBLE);
        } else {
            holder.mTxtFriendListGroupNum.setVisibility(View.GONE);
            List<String> list=new ArrayList<>();
            list.add(friendList.get(position).getUserIcon());
            SettingNineGroupIcon.setGroupIcon(holder.mImgFriendListUserIcon,list);
        }
        String name = contentBean.getRemark() == null ? contentBean.getNickName() : contentBean.getRemark();
        holder.mTxtFriendListUserName.setText(name);
        return convertView;
    }

    /**
     * 好友列表ViewHolder
     */
    private class ViewHolder {
        private NineGridImageView mImgFriendListUserIcon;
        private TextView mTxtFriendListUserName;
        private TextView mTxtFriendListGroupNum;
    }

}
