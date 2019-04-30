package com.yst.onecity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.order.AfterSalesDetailsActivity;
import com.yst.onecity.bean.order.AfterSalesProductBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 退款/售后 适配器
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/24
 */
public class ReturnMoneyAfterSalesAdapter extends BaseExpandableListAdapter {

    private List<AfterSalesProductBean> data;
    private Activity context;

    public ReturnMoneyAfterSalesAdapter(List<AfterSalesProductBean> data, Activity context) {
        this.data = data;
        this.context = context;
    }

    public void onRefresh(List<AfterSalesProductBean> mData) {
        if (mData != null) {
            this.data.clear();
            this.data.addAll(mData);
            notifyDataSetChanged();
        }
    }

    public void addData(List<AfterSalesProductBean> mData) {
        if (mData != null) {
            this.data.addAll(mData);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getDetail().size();
    }

    @Override
    public AfterSalesProductBean getGroup(int i) {
        return data.get(i);
    }

    @Override
    public AfterSalesProductBean.DetailBean getChild(int i, int i1) {
        return data.get(i).getDetail().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_group, null);
            groupViewHolder = new GroupViewHolder(view);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        final AfterSalesProductBean groupBean = getGroup(i);
        if (Constant.userType == Constant.NO1) {
            groupViewHolder.ivShop.setVisibility(View.GONE);
            groupViewHolder.ivNext.setVisibility(View.GONE);
            groupViewHolder.ivOrderNo.setVisibility(View.VISIBLE);
            groupViewHolder.tvOrderNo.setText("订单编号：" + groupBean.getReturnOrderNo());
        } else {
            groupViewHolder.ivShop.setVisibility(View.VISIBLE);
            groupViewHolder.ivNext.setVisibility(View.VISIBLE);
            groupViewHolder.ivOrderNo.setVisibility(View.GONE);
            Glide.with(context).load(groupBean.getMerchantAddress()).error(R.mipmap.h_moren).into(groupViewHolder.ivShop);
            groupViewHolder.tvOrderNo.setText(ConstUtils.getStringNoEmpty(groupBean.getMerchantName()));
        }
        groupViewHolder.tvOrderState.setText(ConstUtils.getReturnStatus(groupBean.getStatus()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("groupBean", groupBean);
                JumpIntent.jump(context, AfterSalesDetailsActivity.class, bundle);
            }
        });
        return view;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_return_money_after_sales, null);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        if (i1 == data.get(i).getDetail().size() - 1) {
            childViewHolder.llOrderInfo.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.llOrderInfo.setVisibility(View.GONE);
        }
        AfterSalesProductBean.DetailBean childBean = getChild(i, i1);
        final AfterSalesProductBean groupBean = getGroup(i);
        Glide.with(context).load(childBean.getProductAddress()).error(R.mipmap.moren).into(childViewHolder.ivProduct);
        childViewHolder.tvProductName.setText(ConstUtils.getStringNoEmpty(childBean.getProductName()));
        childViewHolder.tvCount.setText("x" + childBean.getNum());
        childViewHolder.tvProductPrice.setVisibility(View.VISIBLE);
        childViewHolder.tvProductPrice.setText("¥" + childBean.getPrice());
        childViewHolder.tvPayMoney.setText("¥" + groupBean.getPayMoney());
        childViewHolder.tvFreight.setText("(运费" + groupBean.getFreight() + "元)");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("groupBean", groupBean);
                JumpIntent.jump(context, AfterSalesDetailsActivity.class, bundle);
            }
        });
        return view;
    }

    class GroupViewHolder {
        @BindView(R.id.iv_shop)
        RoundedImageView ivShop;
        @BindView(R.id.iv_order_no)
        ImageView ivOrderNo;
        @BindView(R.id.iv_next)
        ImageView ivNext;
        @BindView(R.id.tv_order_no)
        TextView tvOrderNo;
        @BindView(R.id.tv_order_state)
        TextView tvOrderState;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_pay_money)
        TextView tvPayMoney;
        @BindView(R.id.tv_freight)
        TextView tvFreight;
        @BindView(R.id.tv_check_details)
        TextView tvCheckDetails;
        @BindView(R.id.ll_order_info)
        LinearLayout llOrderInfo;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
