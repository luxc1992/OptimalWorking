package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imchatlibrary.utils.ImLog;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.LEFT_SQUARE_BRACKET;
import static com.yst.im.imsdk.MessageConstant.NUM_3;

/**
 * 好友列表 adapter
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/04/02.
 */
public class DeleteGroupsAdapter extends BaseAdapter {
    private Context context;
    private List<ContactsEntity> mDeleteGroups;
    public boolean isSelectMore;

    public DeleteGroupsAdapter(Context context, List<ContactsEntity> mDeleteGroups) {
        this.context = context;
        this.mDeleteGroups = mDeleteGroups;
    }

    @Override
    public int getCount() {
        return null == mDeleteGroups ? 0 : mDeleteGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mDeleteGroups.get(position);
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
            convertView = View.inflate(context, R.layout.item_delete_groups, null);
            holder.mTxtDeleteGroupsSelete = (TextView) convertView.findViewById(R.id.txt_delete_groups_selete);
            holder.mImgDeleteGroupsUserIcon = (NineGridImageView) convertView.findViewById(R.id.img_delete_groups_user_icon);
            holder.mTxtDeleteGroupsUserName = (TextView) convertView.findViewById(R.id.txt_delete_groups_user_name);
            holder.mTxtDeleteGroupsGroupNum = (TextView) convertView.findViewById(R.id.txt_delete_groups_group_num);
            holder.mTxtDeleteGroupsGroupApply = (TextView) convertView.findViewById(R.id.txt_delete_groups_group_apply);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTxtDeleteGroupsUserName.setText(null == mDeleteGroups.get(position).getNickName() ? "" : mDeleteGroups.get(position).getNickName());
        if (mDeleteGroups.get(position).isGroupChat()) {
            holder.mTxtDeleteGroupsGroupNum.setVisibility(View.VISIBLE);
        } else {
            holder.mTxtDeleteGroupsGroupNum.setVisibility(View.GONE);
        }
        if (mDeleteGroups.get(position).isReCall()) {
            holder.mTxtDeleteGroupsGroupApply.setVisibility(View.GONE);
            if (mDeleteGroups.get(position).getEvent()==NUM_3){
                holder.mTxtDeleteGroupsGroupNum.setVisibility(View.VISIBLE);
                holder.mTxtDeleteGroupsGroupNum.setText(String.valueOf("("+mDeleteGroups.get(position).getGroupNum()+"人)"));
            }else {
                holder.mTxtDeleteGroupsGroupNum.setVisibility(View.GONE);
            }
        } else {
            holder.mTxtDeleteGroupsGroupApply.setVisibility(View.VISIBLE);
            holder.mTxtDeleteGroupsGroupNum.setVisibility(View.VISIBLE);
        }

        if (isSelectMore) {
            holder.mTxtDeleteGroupsSelete.setVisibility(View.VISIBLE);
            if (mDeleteGroups.get(position).isChecked()) {
                holder.mTxtDeleteGroupsSelete.setSelected(true);
            } else {
                holder.mTxtDeleteGroupsSelete.setSelected(false);
            }
        } else {
            holder.mTxtDeleteGroupsSelete.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mDeleteGroups.get(position).getPortrait()) && mDeleteGroups.get(position).getPortrait().contains(LEFT_SQUARE_BRACKET)) {
            JSONArray objects = JSONObject.parseArray(mDeleteGroups.get(position).getPortrait());
            List<String> list = new ArrayList<>();
            for (int i = 0; i < objects.size(); i++) {
                list.add(objects.get(i).toString());
            }
            SettingNineGroupIcon.setGroupIcon(holder.mImgDeleteGroupsUserIcon,list);

        }


        return convertView;
    }

    private class ViewHolder {
        private TextView mTxtDeleteGroupsSelete;
        private NineGridImageView mImgDeleteGroupsUserIcon;
        private TextView mTxtDeleteGroupsUserName;
        private TextView mTxtDeleteGroupsGroupNum;
        private TextView mTxtDeleteGroupsGroupApply;
    }

    public void selectMore(boolean isSelectMore) {
        this.isSelectMore = isSelectMore;
        notifyDataSetChanged();
    }

}
