package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.GroupMemberBean;

import java.util.List;

/**
 * 通讯录列表适配器
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/26.
 */

public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    private List<GroupMemberBean> list = null;
    private Context mContext;

    public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list 数据集合
     */
    public void updateListView(List<GroupMemberBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final GroupMemberBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_group_member_item, null);
            viewHolder.tvTitle = view.findViewById(R.id.title);
            viewHolder.tvPhone = view.findViewById(R.id.phone);
            viewHolder.ivChecked = view.findViewById(R.id.iv_check);
            viewHolder.tvLetter = view.findViewById(R.id.catalog);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        if (list.get(position).isChecked()) {
            viewHolder.ivChecked.setBackgroundResource(R.mipmap.am_check);
        } else {
            viewHolder.ivChecked.setBackgroundResource(R.mipmap.am_uncheck);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());
        viewHolder.tvPhone.setText(this.list.get(position).getPhone());

        return view;
    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        TextView tvPhone;
        ImageView ivChecked;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
