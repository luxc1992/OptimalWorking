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
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.order.MyServiceOrderDetailActivity;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO4;

/**
 * 我的-订单-服务订单
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/21
 */
public class ServiceOrderAdapter extends BaseExpandableListAdapter {
    private List<OrderGroupBean> data;
    private Activity context;
    private OrderClickListener orderClickListener;

    /**
     * 0.卖家 1.买家
     */
    private int from;
    /**
     * 请求列表数据参数  0、待付款   1、待使用  2、待评价 4、退款售后 8全部订单
     * 当type 为 4 的时候判断status 1.申请退款 3.已退款 5.拒绝退款
     */
    private int type;

    public ServiceOrderAdapter(List<OrderGroupBean> data, Activity context, OrderClickListener orderClickListener,int from, int type) {
        this.data = data;
        this.context = context;
        this.orderClickListener = orderClickListener;
        this.from = from;
        this.type = type;
    }

    /**
     * 刷新
     * @param mData 数据
     */
    public void onRefresh(List<OrderGroupBean> mData,int from, int type) {
        if (mData != null) {
            this.data = mData;
            this.from = from;
            this.type = type;
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多
     * @param mData 数据
     */
    public void onLoad(List<OrderGroupBean> mData,int from, int type) {
        this.data.addAll(mData);
        this.from = from;
        this.type = type;
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     * @param mData 数据
     */
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
            view = LayoutInflater.from(context).inflate(R.layout.item_service_order_group, null);
            groupViewHolder = new GroupViewHolder(view);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        final OrderGroupBean groupBean = (OrderGroupBean) getGroup(i);
        groupViewHolder.tvServiceName.setText(groupBean.getNickName());
        //订单状态 0待付款 1待使用 2交易成功 3交易关闭 4申请退款 5已确认退款 6已拒绝退款
        //0、待付款   1、待使用  2、待评价  3、已评价   4、申请退款   5、已退款  6、拒绝退款  7、已取消 8、已过期
        int status = groupBean.getStatus();
        switch (status) {
            case 0:
                groupViewHolder.tvOrderState.setText("等待付款");
                break;
            case 1:
                if(type == NO4){
                    groupViewHolder.tvOrderState.setText("申请退款");
                }else {
                    groupViewHolder.tvOrderState.setText("等待使用");
                }
                break;
            case 2:
            case 3:
                if(type == NO4){
                    groupViewHolder.tvOrderState.setText("已确认退款");
                }else {
                    groupViewHolder.tvOrderState.setText("交易成功");
                }
                break;
            case 5:
                if(type == NO4){
                    groupViewHolder.tvOrderState.setText("已拒绝退款");
                }
                break;
            case 7:
                groupViewHolder.tvOrderState.setText("交易关闭");
                break;
            case 8:
                groupViewHolder.tvOrderState.setText("订单过期");
                break;
            default:
                break;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_service_order_child, null);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        final int status = data.get(i).getStatus();

        childViewHolder.tvProductPrice.setVisibility(View.VISIBLE);
        final OrderChildBean childBean = (OrderChildBean) getChild(i, i1);
        final OrderGroupBean groupBean = (OrderGroupBean) getGroup(i);

        //status 订单状态:0、待付款   1、待使用  2、待评价  3、已评价   4、申请退款   5、已退款  6、拒绝退款  7、已取消 8、已过期
        if(from == 0){
            //联系团队
            childViewHolder.tvBtn1.setVisibility(View.VISIBLE);
            childViewHolder.tvBtn1.setText(R.string.contact_teams);
            childViewHolder.tvBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderClickListener.clickListener(0, groupBean);
                }
            });
            switch (status) {
                case 0:
                    childViewHolder.tvBtn2.setVisibility(View.GONE);
                    //立即付款
                    childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn.setText(R.string.now_pay);
                    childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(3, groupBean);
                        }
                    });
                    break;
                case 1:
                    if(type == NO4){
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //查看详情
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_detail);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(7, groupBean);
                            }
                        });
                    }else {
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //申请退款
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.application_for_refund);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(4, groupBean);
                            }
                        });
                    }
                    break;
                case 2:
                    //删除订单
                    childViewHolder.tvBtn2.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn2.setText(R.string.delete_order);
                    childViewHolder.tvBtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(1, groupBean);
                        }
                    });
                    //评价
                    childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn.setText(R.string.evaluate);
                    childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(5, groupBean);
                        }
                    });
                    break;
                case 3:
                    if(type == NO4){
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //查看详情
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_detail);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(7, groupBean);
                            }
                        });
                    }else {
                        //删除订单
                        childViewHolder.tvBtn2.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn2.setText(R.string.delete_order);
                        childViewHolder.tvBtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(1, groupBean);
                            }
                        });
                        //查看评价
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_evaluate);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(6, groupBean);
                            }
                        });
                    }
                    break;
                case 5:
                    if(type == NO4){
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //查看详情
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_detail);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(7, groupBean);
                            }
                        });
                    }
                    break;
                case 7:
                case 8:
                    childViewHolder.tvBtn2.setVisibility(View.GONE);
                    //删除订单
                    childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn.setText(R.string.delete_order);
                    childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(1, groupBean);
                        }
                    });
                    break;
                default:
                    break;
            }
        }else {
            childViewHolder.tvBtn1.setVisibility(View.GONE);
            switch (status) {
                case 0:
                    childViewHolder.tvBtn2.setVisibility(View.GONE);
                    //取消订单
                    childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn.setText(R.string.cancel_order);
                    childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(2, groupBean);
                        }
                    });
                    break;
                case 1:
                    if(type == NO4){
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //查看详情
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_detail);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(8, groupBean);
                            }
                        });
                    }else {
                        //无
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        childViewHolder.tvBtn.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    childViewHolder.tvBtn2.setVisibility(View.GONE);
                    //删除订单
                    childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn.setText(R.string.delete_order);
                    childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(1, groupBean);
                        }
                    });
                    break;
                case 3:
                    if(type == NO4){
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //查看详情
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_detail);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(8, groupBean);
                            }
                        });
                    }else {
                        //删除订单
                        childViewHolder.tvBtn2.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn2.setText(R.string.delete_order);
                        childViewHolder.tvBtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(1, groupBean);
                            }
                        });
                        //查看评价
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_evaluate);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(6, groupBean);
                            }
                        });
                    }
                    break;
                case 5:
                    if(type == NO4){
                        childViewHolder.tvBtn2.setVisibility(View.GONE);
                        //查看详情
                        childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                        childViewHolder.tvBtn.setText(R.string.look_detail);
                        childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                orderClickListener.clickListener(8, groupBean);
                            }
                        });
                    }
                    break;
                case 7:
                case 8:
                    childViewHolder.tvBtn2.setVisibility(View.GONE);
                    //删除订单
                    childViewHolder.tvBtn.setVisibility(View.VISIBLE);
                    childViewHolder.tvBtn.setText(R.string.delete_order);
                    childViewHolder.tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderClickListener.clickListener(1, groupBean);
                        }
                    });
                    break;
                default:
                    break;
            }
        }

        //服务名称
        childViewHolder.tvProductName.setText(ConstUtils.getStringNoEmpty(childBean.getName()));
        //数量
        childViewHolder.tvNumber.setText(Integer.toString(childBean.getNum()));
        //总价
        childViewHolder.tvProductPrice.setText("¥" + childBean.getSPrice());
        //应付、已付金额
        childViewHolder.tvOrderMoney.setText("¥" + groupBean.getTotal_price());
        //服务图片
        Glide.with(context)
                .load(childBean.getAddress())
                .dontAnimate()
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .into(childViewHolder.ivProduct);
        //点击跳转服务详情
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("status", status);
                bundle.putString("orderId", groupBean.getId());
                bundle.putInt("from", from);
                JumpIntent.jump(context, MyServiceOrderDetailActivity.class, bundle);
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
        @BindView(R.id.service_order_iv_order_no)
        ImageView ivOrderNo;
        @BindView(R.id.service_order_iv_service_head)
        ImageView ivServiceHead;
        @BindView(R.id.service_order_tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.service_order_tv_order_state)
        TextView tvOrderState;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.item_service_order_iv_product)
        ImageView ivProduct;
        @BindView(R.id.item_service_order_tv_product_name)
        TextView tvProductName;
        @BindView(R.id.item_service_order_number)
        TextView tvNumber;
        @BindView(R.id.item_service_order_price)
        TextView tvProductPrice;

        @BindView(R.id.item_service_order_ll_order_info)
        LinearLayout llOrderInfo;
        @BindView(R.id.item_service_order_tv_order_money)
        TextView tvOrderMoney;
        @BindView(R.id.item_service_order_tv_btn1)
        TextView tvBtn1;
        @BindView(R.id.item_service_order_tv_btn2)
        TextView tvBtn2;
        @BindView(R.id.item_service_order_tv_btn)
        TextView tvBtn;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OrderClickListener {
        /**
         * 不同按钮点击监听
         *
         * @param flag      标识（0.联系团队 ）
         * @param groupBean 实体类
         */
        void clickListener(int flag, OrderGroupBean groupBean);
    }
}
