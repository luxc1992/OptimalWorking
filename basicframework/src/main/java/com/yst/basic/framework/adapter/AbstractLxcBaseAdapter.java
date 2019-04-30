package com.yst.basic.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yst.basic.framework.entity.BaseHoler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通用适配器
 *
 * @author lixiangchao
 * @date 2017/3/14
 * @version 1.0.1
 */
public abstract class AbstractLxcBaseAdapter<E, T extends BaseHoler> extends BaseAdapter {

    public List<E> mDatas;

    public Context mContext;

    /**
     * 通用适配器
     *
     * @param context 上下文对象
     */
    public AbstractLxcBaseAdapter(Context context) {
        super();
        this.mContext = context;
        this.mDatas = new ArrayList<E>();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public E getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T holder = null;
        E item = this.getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutResId(), parent, false);
            holder = bindHoler();
            holder.setConvertView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (T) convertView.getTag();
        }
        bindData(holder, position, item);
        return convertView;
    }

    /**
     * 绑定视图
     * @return
     */
    public abstract T bindHoler();

    /**
     * 获取item的视图
     * @return
     */
    public abstract int getLayoutResId();

    /**
     * 数据绑定
     * @param holder
     * @param position
     * @param item
     */
    public abstract void bindData(T holder, int position, E item);

    /**
     * 获取当前所有的内容信息
     *
     * @return 所有内容
     */
    public List<E> getData() {
        return this.mDatas;
    }

    /**
     * 刷新新内容到第一条
     *
     * @param data 数据源列表
     */
    public void addNewData(List<E> data) {
        if (data != null) {
            this.mDatas.addAll(0, data);
            this.notifyDataSetChanged();
        }
    }

    /**
     * 添加新内容
     *
     * @param data 加到末尾数据
     */
    public void addMoreData(List<E> data) {
        if (data != null) {
            this.mDatas.addAll(this.mDatas.size(), data);
            this.notifyDataSetChanged();
        }
    }

    /**
     * 设置内容
     *
     * @param data 数据源列表
     */
    public void setData(List<E> data) {
        if (data != null) {
            this.mDatas = data;
        } else {
            this.mDatas.clear();
        }
        this.notifyDataSetChanged();
    }

    /**
     * 清除内容
     */
    public void clear() {
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 移除某个位置
     *
     * @param position 位置
     */
    public void removeItem(int position) {
        this.mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    /**
     * 移除某一条数据
     *
     * @param model 数据
     */
    public void removeItem(E model) {
        this.mDatas.remove(model);
        this.notifyDataSetChanged();
    }

    /**
     * 固定位置添加
     *
     * @param position
     * @param model
     */
    public void addItem(int position, E model) {
        this.mDatas.add(position, model);
        this.notifyDataSetChanged();
    }

    /**
     * 首位置添加
     *
     * @param model
     */
    public void addFirstItem(E model) {
        this.addItem(0, model);
    }

    /**
     * 添加到最后一条
     *
     * @param model
     */
    public void addLastItem(E model) {
        this.addItem(this.mDatas.size(), model);
    }

    /**
     * 设置Item
     *
     * @param location 位置
     * @param newModel 数据
     */
    public void setItem(int location, E newModel) {
        this.mDatas.set(location, newModel);
        this.notifyDataSetChanged();
    }

    /**
     * 设置item
     *
     * @param oldModel 原始数据
     * @param newModel 新的数据
     */
    public void setItem(E oldModel, E newModel) {
        this.setItem(this.mDatas.indexOf(oldModel), newModel);
    }

    /**
     * 交换位置
     *
     * @param fromPosition 起始位置
     * @param toPosition   移动到的位置
     */
    public void moveItem(int fromPosition, int toPosition) {
        Collections.swap(this.mDatas, fromPosition, toPosition);
        this.notifyDataSetChanged();
    }

}
