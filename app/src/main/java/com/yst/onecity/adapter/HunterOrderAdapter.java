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
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.order.CheckEvaluateListActivity;
import com.yst.onecity.activity.mine.order.HunterOrderDetailsActivity;
import com.yst.onecity.activity.mine.order.TransportInformationActivity;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.FLAG;

/**
 * 猎头我的订单适配器
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/6
 */
public class HunterOrderAdapter extends BaseExpandableListAdapter {
    private List<OrderGroupBean> data;
    private Activity context;
    private OrderClickListener orderClickListener;
    private int states;

    public HunterOrderAdapter(List<OrderGroupBean> data, Activity context, OrderClickListener orderClickListener) {
        this.data = data;
        this.context = context;
        this.orderClickListener = orderClickListener;
    }

    public void onRefresh(List<OrderGroupBean> mData) {
        if (mData != null) {
            this.data.clear();
            this.data.addAll(mData);
            notifyDataSetChanged();
        }
    }

    public void onLoad(List<OrderGroupBean> mData) {
        if (mData != null && mData.size() != 0) {
            this.data.addAll(mData);
            notifyDataSetChanged();
        }
    }

    public void setData(List<OrderGroupBean> mData) {
        this.data = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getSon().size();
    }

    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getSon().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
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
        final OrderGroupBean groupBean = (OrderGroupBean) getGroup(i);
        groupViewHolder.tvOrderNo.setText("订单编号：" + groupBean.getOrderNo());
        //订单状态 0待付款 2待发货（已付款） 3待收货 4已评价 6已撤销 7已收货（待评价）8 全部
        states = groupBean.getStatus();
        switch (states) {
            case 0:
                groupViewHolder.tvOrderState.setText("等待付款");
                break;
            case 2:
                groupViewHolder.tvOrderState.setText("等待发货");
                break;
            case 3:
                groupViewHolder.tvOrderState.setText("等待收货");
                break;
            case 4:
                groupViewHolder.tvOrderState.setText("交易完成");
                break;
            case 6:
                groupViewHolder.tvOrderState.setText("交易关闭");
                break;
            case 7:
                groupViewHolder.tvOrderState.setText("等待评价");
                break;
            default:
                break;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(FLAG, states);
                bundle.putString("orderNo", groupBean.getOrderNo());
                bundle.putString("merchantId", groupBean.getMerchantId());
                JumpIntent.jump(context, HunterOrderDetailsActivity.class, bundle);
            }
        });
        return view;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_child, null);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        if (i1 == data.get(i).getSon().size() - 1) {
            childViewHolder.llOrderInfo.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.llOrderInfo.setVisibility(View.GONE);
        }
        childViewHolder.tvProductPrice.setVisibility(View.VISIBLE);
        final OrderChildBean childBean = (OrderChildBean) getChild(i, i1);
        final OrderGroupBean groupBean = (OrderGroupBean) getGroup(i);
        //订单状态 0待付款 2待发货（已付款） 3待收货 4已评价 6已撤销 7已收货（待评价）8 全部
        final int states = childBean.getStatus();
        switch (states) {
            case 0:
                childViewHolder.tvBtn1.setVisibility(View.VISIBLE);
                childViewHolder.tvBtn1.setText("取消订单");
                childViewHolder.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderClickListener.clickListener(1, groupBean);
                    }
                });
                childViewHolder.tvBtn2.setVisibility(View.GONE);
                break;
            case 2:
                childViewHolder.tvBtn1.setVisibility(View.VISIBLE);
                childViewHolder.tvBtn1.setText("取消订单");
                childViewHolder.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderClickListener.clickListener(1, groupBean);
                    }
                });
                childViewHolder.tvBtn2.setVisibility(View.VISIBLE);
                //isConfirmSendButton=1显示确认发货按钮，0不显示
                if (groupBean.getIsConfirmSendButton() == 1) {
                    childViewHolder.tvBtn2.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn2.setText("确认发货");
                } else {
                    childViewHolder.tvBtn2.setVisibility(View.GONE);
                }
                childViewHolder.tvBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNo", groupBean.getOrderNo());
                        JumpIntent.jump(context, TransportInformationActivity.class, bundle);
                    }
                });
                break;
            case 3:
                childViewHolder.tvBtn1.setVisibility(View.GONE);
                childViewHolder.tvBtn1.setText("查看物流");
                childViewHolder.tvBtn2.setVisibility(View.GONE);
                break;
            case 4:
            case 7:
                childViewHolder.tvBtn1.setVisibility(View.VISIBLE);
                childViewHolder.tvBtn1.setText("删除订单");
                childViewHolder.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderClickListener.clickListener(0, groupBean);
                    }
                });
                childViewHolder.tvBtn2.setVisibility(View.VISIBLE);
                childViewHolder.tvBtn2.setText("查看评价");
                childViewHolder.tvBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Constant.isMember = false;
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNo", groupBean.getOrderNo());
                        JumpIntent.jump(context, CheckEvaluateListActivity.class, bundle);
                    }
                });
                break;
            default:
                childViewHolder.tvBtn1.setVisibility(View.VISIBLE);
                childViewHolder.tvBtn1.setText("删除订单");
                childViewHolder.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderClickListener.clickListener(0, groupBean);
                    }
                });
                childViewHolder.tvBtn2.setVisibility(View.GONE);
                break;
        }
        childViewHolder.tvProductName.setText(ConstUtils.getStringNoEmpty(childBean.getName()));
        childViewHolder.tvProductPrice.setText("¥" + childBean.getSPrice());
        childViewHolder.tvDes.setText(ConstUtils.getStringNoEmpty(childBean.getContent()));
        childViewHolder.tvCount.setText("x" + childBean.getNum());
        childViewHolder.tvOrderMoney.setText("¥" + groupBean.getTotal_price());
        childViewHolder.tvFreightMoney.setText("(运费" + groupBean.getProductFreight() + "元)");
        Glide.with(context).load(childBean.getAddress()).error(R.mipmap.more).into(childViewHolder.ivProduct);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(FLAG, states);
                bundle.putString("orderNo", groupBean.getOrderNo());
                bundle.putString("merchantId", groupBean.getMerchantId());
                JumpIntent.jump(context, HunterOrderDetailsActivity.class, bundle);
            }
        });
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupViewHolder {
        @BindView(R.id.iv_order_no)
        ImageView ivOrderNo;
        @BindView(R.id.tv_order_no)
        TextView tvOrderNo;
        @BindView(R.id.tv_order_state)
        TextView tvOrderState;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.ll_child)
        LinearLayout llChild;
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ll_order_info)
        LinearLayout llOrderInfo;
        @BindView(R.id.tv_order_money)
        TextView tvOrderMoney;
        @BindView(R.id.tv_freight_money)
        TextView tvFreightMoney;
        @BindView(R.id.tv_btn1)
        TextView tvBtn1;
        @BindView(R.id.tv_btn2)
        TextView tvBtn2;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OrderClickListener {
        /**
         * 不同按钮点击监听
         *
         * @param flag      标识（0删除，1取消，2查看物流）
         * @param groupBean 实体类
         */
        void clickListener(int flag, OrderGroupBean groupBean);
    }
}
