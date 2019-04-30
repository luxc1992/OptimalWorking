package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView万能适配器
 *
 * @author jiaofan
 * @date 2017/12/13
 * @version 1.0.1
 */
public abstract class AbstractCommonAdapter<T> extends RecyclerView.Adapter<MyViewHolder>{
    private Context mContext;
    private int mLayoutId;
    private List<T> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    public AbstractCommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    /**
     *  在RecyclerView提供数据的时候调用
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        /**
         * 条目点击事件
         *
         * @param parent
         * @param view
         * @param position
         */
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return MyViewHolder.get(mContext, parent ,mLayoutId);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        bindClick(holder, mDatas.get(holder.getLayoutPosition()));
        convert(holder, mDatas.get(position));
        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    listener.onItemClick(recyclerView, view, position);
                }
            }
        });
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     *
     * @param holder holder对象
     * @param item 实体类
     */
    public abstract void convert(MyViewHolder holder, T item);

    /**
     * 条目中子控件点击事件
     *
     * @param holder holder对象
     * @param item 实体类
     */
    public abstract void bindClick(MyViewHolder holder, T item);
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

}
