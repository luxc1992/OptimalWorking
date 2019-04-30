package com.yst.basic.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yst.basic.framework.entity.holder.CommonViewHolder;

import java.util.List;

/**
 * ListView通用适配器
 *
 * @author lixiangchao
 * @date 2018/01/24
 * @version 1.0.1
 */
public abstract class AbstractCommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected int mItemLayoutId;
    protected List<T> mList;
    protected Context context;

    public AbstractCommonAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        this.context = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    public void onRefresh(List<T> mList) {
        if (mList != null) {
            this.mList.clear();
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> mList) {
        if (mList != null && mList.size() != 0) {
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.get(context,
                convertView, parent, mItemLayoutId, position);
        convert(viewHolder, position, getItem(position));
        return viewHolder.getConvertView();
    }

    /**
     * 渲染item的视图
     *
     * @param holder 传过来的holder
     * @param position 视图位置
     * @param item 真正的视图菜单
     */
    public abstract void convert(CommonViewHolder holder, int position, T item);

}
