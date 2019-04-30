package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.bean.home.PublishInfoAddProductPlanBean;
import com.yst.onecity.bean.mine.MyCollectProductPlan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 产品计划item 里recyclerview Adapter
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/23
 */
public class ProductPlanRecyclerViewAdapter extends RecyclerView.Adapter<ProductPlanRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ProductPlanBean.ContentBean.ProductSBean> myIssue;
    private List<MyCollectProductPlan.ContentBean.ProductVOListBean> myCollect;
    private List<PublishInfoAddProductPlanBean.ContentBean.ProductSBean> myHome;
    private OnItemClickListener mOnItemClickListener;
    private View view;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProductPlanRecyclerViewAdapter(Context context, List<ProductPlanBean.ContentBean.ProductSBean> myIssue, List<MyCollectProductPlan.ContentBean.ProductVOListBean> myCollect, List<PublishInfoAddProductPlanBean.ContentBean.ProductSBean> myHome) {
        this.context = context;
        this.myIssue = myIssue;
        this.myHome = myHome;
        this.myCollect = myCollect;
    }

    @Override
    public ProductPlanRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_productplan_recyclerview, parent, false);
        ProductPlanRecyclerViewAdapter.ViewHolder viewHolder = new ProductPlanRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductPlanRecyclerViewAdapter.ViewHolder holder, final int position) {
        if (myIssue != null && myIssue.get(position) != null) {
            Glide.with(context).load(myIssue.get(position).getImageAttachmentAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(holder.imageView);
            holder.name.setText(myIssue.get(position).getName() + "");
            holder.title.setText(myIssue.get(position).getTitle() + "");
            //设置商品的价格
            if (myIssue.get(position).getMinPrice().equals(myIssue.get(position).getMaxPrice())) {
                holder.price.setText("￥" + myIssue.get(position).getMinPrice());
            } else {
                holder.price.setText("￥" + myIssue.get(position).getMinPrice() + "-" + myIssue.get(position).getMaxPrice());
            }
        } else if (myCollect != null) {
            Glide.with(context).load(myCollect.get(position).getImageAttachmentAddress()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(holder.imageView);
            //设置商品的价格
            if (myCollect.get(position).getMinPrice() == myCollect.get(position).getMaxPrice()) {
                holder.price.setText("￥" + myCollect.get(position).getMinPrice() + "");
            } else {
                holder.price.setText("￥" + myCollect.get(position).getMinPrice() + "-" + myCollect.get(position).getMaxPrice());
            }
            holder.title.setText(myCollect.get(position).getTitle() + "");
            holder.name.setText(myCollect.get(position).getName() + "");
        }
        if (mOnItemClickListener != null) {
            //item的点击监听
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (myCollect != null) {
            return myCollect.size();
        } else {
            return myIssue == null ? 0 : myIssue.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_recyview)
        ImageView imageView;
        @BindView(R.id.tv_recyview_name)
        TextView name;
        @BindView(R.id.tv_recyview_title)
        TextView title;
        @BindView(R.id.tv_recyview_price)
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param view
         */
        void onItemClick(View view);
    }
}
