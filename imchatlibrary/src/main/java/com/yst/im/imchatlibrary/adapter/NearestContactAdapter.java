package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.LEFT_SQUARE_BRACKET;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_13;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_6;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.NUM_70;
import static com.yst.im.imsdk.MessageConstant.NUM_71;
import static com.yst.im.imsdk.MessageConstant.NUM_99;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;
import static com.yst.im.imsdk.MessageConstant.TYPE_INVITE_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_JION_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL_HISTORY;
import static com.yst.im.imsdk.utils.BaseUtils.dp2px;
import static com.yst.im.imsdk.utils.BaseUtils.getTime;

/**
 * 好友列表 adapter
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/27.
 */
public class NearestContactAdapter extends BaseAdapter {
    private Context context;
    private List<ContactsEntity> friendList;

    public NearestContactAdapter(Context context, List<ContactsEntity> friendList) {
        this.context = context;
        this.friendList = friendList;
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_nearest_list, null);
            holder.mLayoutNearest = (LinearLayout) convertView.findViewById(R.id.layout_nearest);
            holder.mImgNearestListIcon = (NineGridImageView) convertView.findViewById(R.id.img_nearest_list_icon);
            holder.mTxtNearestListUserName = (TextView) convertView.findViewById(R.id.txt_nearest_list_user_name);
            holder.mTxtNearestListGroupNum = (TextView) convertView.findViewById(R.id.txt_nearest_list_group_num);
            holder.mTxtNearestListTime = (TextView) convertView.findViewById(R.id.txt_nearest_list_time);
            holder.mImgNearestListMute = (ImageView) convertView.findViewById(R.id.img_nearest_list_mute);
            holder.txtHomeNewsNum = (TextView) convertView.findViewById(R.id.txt_home_news_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        boolean isNotice = friendList.get(position).getType() == NUM_65
                || friendList.get(position).getType() == NUM_70
                || friendList.get(position).getType() == TYPE_DELETE_USER;
        if (friendList.get(position).getType() == NUM_2) {
            holder.mTxtNearestListGroupNum.setText("[图片]");
        } else if (friendList.get(position).getType() == NUM_3) {
            holder.mTxtNearestListGroupNum.setText("[视频]");
        } else if (friendList.get(position).getType() == NUM_6) {
            holder.mTxtNearestListGroupNum.setText("[语音]");
        } else if (friendList.get(position).getType() == TYPE_RECALL_HISTORY && MyApp.manager.getId().equals(friendList.get(position).getSenderId())) {
            holder.mTxtNearestListGroupNum.setText("你撤回一条消息");
        } else if (friendList.get(position).getType() == TYPE_INVITE_JINO_GROUP && MyApp.manager.getId().equals(friendList.get(position).getSenderId())) {
            holder.mTxtNearestListGroupNum.setText("你" + friendList.get(position).getContent().substring(MyApp.manager.getNickName().length(), friendList.get(position).getContent().length()));
        } else if (friendList.get(position).getType() == TYPE_JION_GROUP && MyApp.manager.getId().equals(friendList.get(position).getSenderId())) {
            holder.mTxtNearestListGroupNum.setText("你创建了群聊");
        } else {
            holder.mTxtNearestListGroupNum.setText(friendList.get(position).getContent());
        }

        holder.mTxtNearestListTime.setText(BaseUtils.stampToTime(friendList.get(position).getOccureTime()));
        holder.mTxtNearestListTime.setText(getTime(friendList.get(position).getOccureTime()));

        if (friendList.get(position).getCount() == 0) {
            holder.txtHomeNewsNum.setVisibility(View.GONE);
        } else {
            RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) holder.txtHomeNewsNum.getLayoutParams();
            if (friendList.get(position).getIsShield() == NUM_0) {
                holder.txtHomeNewsNum.setVisibility(View.VISIBLE);
                mLayoutParams.width = dp2px(context, 8);
                mLayoutParams.height = dp2px(context, 8);
                mLayoutParams.bottomMargin = -dp2px(context, 20);
                mLayoutParams.leftMargin = -dp2px(context, 20);
                holder.txtHomeNewsNum.setLayoutParams(mLayoutParams);
                holder.txtHomeNewsNum.setText("");
            } else {
                String count = friendList.get(position).getCount() > 99 ? "99+" : String.valueOf(friendList.get(position).getCount());
                holder.txtHomeNewsNum.setVisibility(View.VISIBLE);
                mLayoutParams.width = dp2px(context, 15);
                mLayoutParams.height = dp2px(context, 15);
                holder.txtHomeNewsNum.setLayoutParams(mLayoutParams);
                holder.txtHomeNewsNum.setText(count);
            }
        }
        if (isNotice) {
            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.icon_notice);
            SettingNineGroupIcon.setSingleIcon(holder.mImgNearestListIcon, list);
            holder.mTxtNearestListUserName.setText("系统通知");
            holder.mLayoutNearest.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWrite));
            holder.mImgNearestListMute.setVisibility(View.GONE);
        } else {
            if (friendList.get(position).getIsStick() == NUM_0) {
                holder.mLayoutNearest.setBackgroundColor(ContextCompat.getColor(context, R.color.colorffe9f4ff));
            } else {
                holder.mLayoutNearest.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWrite));
            }
            if (friendList.get(position).getIsShield() == NUM_0) {
                holder.mImgNearestListMute.setVisibility(View.VISIBLE);
            } else {
                holder.mImgNearestListMute.setVisibility(View.GONE);
            }
            if(friendList.get(position).getNickName().length()>NUM_13){
                String subName = friendList.get(position).getNickName().substring(0, 13);
                holder.mTxtNearestListUserName.setText(subName+"...");
            }else {
                holder.mTxtNearestListUserName.setText(friendList.get(position).getNickName());
            }
            if (!TextUtils.isEmpty(friendList.get(position).getPortrait()) && friendList.get(position).getPortrait().contains(LEFT_SQUARE_BRACKET)) {
                JSONArray objects = JSONObject.parseArray(friendList.get(position).getPortrait());
                List<String> list = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    list.add(objects.get(i).toString());
                }
                SettingNineGroupIcon.setGroupIcon(holder.mImgNearestListIcon, list);
            }
        }
        return convertView;
    }

    /**
     * 好友列表ViewHolder
     */
    private class ViewHolder {
        private NineGridImageView mImgNearestListIcon;
        private TextView mTxtNearestListUserName;
        private TextView mTxtNearestListGroupNum;
        private TextView mTxtNearestListTime;
        private ImageView mImgNearestListMute;
        private TextView txtHomeNewsNum;
        private LinearLayout mLayoutNearest;
    }
}
