package com.yst.im.imchatlibrary.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.bean.SearchResultEntity;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.ChatType;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.LEFT_SQUARE_BRACKET;

/**
 * 搜索结果adapter
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2017/04/09.
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private ChatType chatType;
    private List<SearchResultEntity> searchResultList;
    private String searchName = "";

    public SearchResultAdapter(Context context, List<SearchResultEntity> searchResultList, ChatType chatType) {
        this.context = context;
        this.searchResultList = searchResultList;
        this.chatType = chatType;
    }

    @Override
    public int getCount() {
        return null == searchResultList ? 0 : searchResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResultList.get(position);
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
            holder.mImgsearchResultListUserIcon = (NineGridImageView) convertView.findViewById(R.id.img_friend_list_user_icon);
            holder.mTxtsearchResultListUserName = (TextView) convertView.findViewById(R.id.txt_friend_list_user_name);
            holder.mTxtsearchResultListGroupNum = (TextView) convertView.findViewById(R.id.txt_friend_list_group_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTxtsearchResultListUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SearchResultEntity contentBean = searchResultList.get(position);
        if (chatType == ChatType.GROUP) {
            setTextChangeColor(contentBean.getGroupName(), holder.mTxtsearchResultListUserName);
            holder.mTxtsearchResultListGroupNum.setVisibility(View.VISIBLE);
            holder.mTxtsearchResultListGroupNum.setText(null == contentBean.getGroupNumberByCurrent() ? "(0)" :"("+ contentBean.getGroupNumberByCurrent()+")");
            if (!TextUtils.isEmpty(contentBean.getImageUrl()) && contentBean.getImageUrl().contains(LEFT_SQUARE_BRACKET)) {
                JSONArray objects = JSONObject.parseArray(contentBean.getImageUrl());
                List<String> list = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    list.add(objects.get(i).toString());
                }
                SettingNineGroupIcon.setGroupIcon(holder.mImgsearchResultListUserIcon, list);
            }
        } else {
            holder.mTxtsearchResultListGroupNum.setVisibility(View.GONE);
            String name = contentBean.getRemark() == null ? contentBean.getNickName() : contentBean.getRemark();
            setTextChangeColor(name, holder.mTxtsearchResultListUserName);
            List<String> list = new ArrayList<>();
           list.add(searchResultList.get(position).getUserIcon());
            SettingNineGroupIcon.setGroupIcon(holder.mImgsearchResultListUserIcon,list);
        }
        return convertView;
    }

    /**
     * 好友列表ViewHolder
     */
    private class ViewHolder {
        private NineGridImageView mImgsearchResultListUserIcon;
        private TextView mTxtsearchResultListUserName;
        private TextView mTxtsearchResultListGroupNum;
    }

    public void setSearchName(String searchName, List<SearchResultEntity> searchResultList) {
        this.searchName = searchName;
        this.searchResultList.clear();
        if (searchResultList != null) {
            this.searchResultList.addAll(searchResultList);
        }
        notifyDataSetChanged();
    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
        notifyDataSetChanged();
    }

    public void setTextChangeColor(String nickName, TextView view) {
        String content = nickName;
        if (!TextUtils.isEmpty(searchName) && !TextUtils.isEmpty(nickName) && nickName.contains(searchName)) {
            if (nickName.indexOf(searchName) == 0 && searchName.length() == nickName.length()) {
                content = nickName;
                content = "<font color=\"#FF943E\">" + content + "</font>";
            } else if (nickName.indexOf(searchName) == 0) {
                String substring = nickName.substring(0, searchName.length());
                String substring1 = nickName.substring(searchName.length(), nickName.length());
                content = "<font color=\"#FF943E\">" + substring + "</font>" + substring1;
            } else if (nickName.indexOf(searchName) + searchName.length() == nickName.length()) {
                String substring = nickName.substring(0, nickName.indexOf(searchName));
                String substring1 = nickName.substring(nickName.indexOf(searchName), nickName.length());
                content = substring + "<font color=\"#FF943E\">" + substring1 + "</font>";
            } else {
                String substring = nickName.substring(0, nickName.indexOf(searchName));
                String substring1 = nickName.substring(nickName.indexOf(searchName), nickName.indexOf(searchName) + searchName.length());
                String substring2 = nickName.substring(nickName.indexOf(searchName) + searchName.length(), nickName.length());
                content = substring + "<font color=\"#FF943E\">" + substring1 + "</font>" + substring2;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
        } else {
            view.setText(Html.fromHtml(content));
        }
    }
}
