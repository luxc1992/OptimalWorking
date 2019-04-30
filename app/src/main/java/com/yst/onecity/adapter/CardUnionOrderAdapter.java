package com.yst.onecity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.mine.cardbag.CardBagDetailActivity;
import com.yst.onecity.activity.mine.order.EvaluateProductActivity;
import com.yst.onecity.bean.CardUnionOrderBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO8;

/**
 * 描述
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CardUnionOrderAdapter extends BaseAdapter {
    private Context context;
    private List<CardUnionOrderBean.ContentBean.CardOrderVOListBean> mList;
    private LayoutInflater mInflater;

    /**
     * 待支付
     */
    private static final int TRADE_WAIT_PAY = 0;
    /**
     * 待使用
     */
    private static final int WAITING_USED = 1;
    /**
     * 交易成功
     */
    private static final int TRADE_SUCCESS = 2;
    /**
     * 交易成功
     */
    private static final int COMMENTED = 3;
    /**
     * 已退款
     */
    private static final int REFUNDED = 5;
    /**
     * 拒绝退款
     */
    private static final int REFUSED_REFUNDED = 6;

    /**
     * 交易关闭
     */
    private static final int TRADE_CLOSE = 7;


    public CardUnionOrderAdapter(Context context, List<CardUnionOrderBean.ContentBean.CardOrderVOListBean> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
//      订单状态  0、待付款   1、待使用  2、待评价  3、已评价   4、申请退款   5、已退款  6、拒绝退款  7、已取消 8、已到期
        if (mList.get(position).getStatus() == NO0) {
            return TRADE_WAIT_PAY;
        } else if (mList.get(position).getStatus() == NO1 || mList.get(position).getStatus() == NO2) {
            return TRADE_SUCCESS;
        } else if (mList.get(position).getStatus() == NO3 || mList.get(position).getStatus() == NO6 || mList.get(position).getStatus() == NO8) {
            return COMMENTED;
        } else {
            return TRADE_CLOSE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 9;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case TRADE_SUCCESS:
                TradeSuccessViewHolder tradeSuccessViewHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_lv_card_union_trade_success, null);
                    tradeSuccessViewHolder = new TradeSuccessViewHolder(convertView);
                    convertView.setTag(tradeSuccessViewHolder);
                } else {
                    tradeSuccessViewHolder = (TradeSuccessViewHolder) convertView.getTag();
                }
                final CardUnionOrderBean.ContentBean.CardOrderVOListBean cardBean = mList.get(position);
                tradeSuccessViewHolder.orderNum.setText(ConstUtils.getStringNoEmpty(cardBean.getOrderNo()));
                tradeSuccessViewHolder.cardName.setText(ConstUtils.getStringNoEmpty(cardBean.getCardName()));
                switch (mList.get(position).getType()) {
                    case 0:
                    case 1:
                        tradeSuccessViewHolder.cardSpecifications.setText(cardBean.getNum() + "天");
                        break;
                    case 2:
                    case 3:
                        tradeSuccessViewHolder.cardSpecifications.setText(cardBean.getNum() + "次");
                        break;
                    default:
                        break;
                }
                tradeSuccessViewHolder.btnComment.setVisibility(View.VISIBLE);
                tradeSuccessViewHolder.cardPrice.setText("¥ " + ConstUtils.changeEmptyStringToZero(cardBean.getPrice()));
                tradeSuccessViewHolder.needPayMoney.setText("¥ " + ConstUtils.changeEmptyStringToZero(cardBean.getPayPrice()));
                Glide.with(context).load(cardBean.getImageAddress()).into(tradeSuccessViewHolder.ivCard);
                tradeSuccessViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("cardBean", cardBean);
                        JumpIntent.jump((Activity) context, EvaluateProductActivity.class, bundle);
                    }
                });
                tradeSuccessViewHolder.btnWatchCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Integer.parseInt(cardBean.getServiceOrderId()));
                        JumpIntent.jump((Activity) context, CardBagDetailActivity.class, bundle);
                    }
                });
                break;
            case COMMENTED:
                TradeSuccessViewHolder tradeSuccessCommentedViewHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_lv_card_union_trade_success, null);
                    tradeSuccessCommentedViewHolder = new TradeSuccessViewHolder(convertView);
                    convertView.setTag(tradeSuccessCommentedViewHolder);
                } else {
                    tradeSuccessCommentedViewHolder = (TradeSuccessViewHolder) convertView.getTag();
                }
                tradeSuccessCommentedViewHolder.btnComment.setVisibility(View.GONE);
                final CardUnionOrderBean.ContentBean.CardOrderVOListBean bean = mList.get(position);
                tradeSuccessCommentedViewHolder.orderNum.setText(ConstUtils.getStringNoEmpty(bean.getOrderNo()));
                tradeSuccessCommentedViewHolder.cardName.setText(ConstUtils.getStringNoEmpty(bean.getCardName()));
                switch (mList.get(position).getType()) {
                    case 0:
                    case 1:
                        tradeSuccessCommentedViewHolder.cardSpecifications.setText(bean.getNum() + "天");
                        break;
                    case 2:
                    case 3:
                        tradeSuccessCommentedViewHolder.cardSpecifications.setText(bean.getNum() + "次");
                        break;
                    default:
                        break;
                }

                tradeSuccessCommentedViewHolder.cardPrice.setText("¥ " + ConstUtils.changeEmptyStringToZero(bean.getPrice()));
                tradeSuccessCommentedViewHolder.needPayMoney.setText("¥ " + ConstUtils.changeEmptyStringToZero(bean.getPayPrice()));
                Glide.with(context).load(bean.getImageAddress()).into(tradeSuccessCommentedViewHolder.ivCard);
                tradeSuccessCommentedViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("cardBean", bean);
                        JumpIntent.jump((Activity) context, EvaluateProductActivity.class, bundle);
                    }
                });
                tradeSuccessCommentedViewHolder.btnWatchCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Integer.parseInt(bean.getServiceOrderId()));
                        JumpIntent.jump((Activity) context, CardBagDetailActivity.class, bundle);
                    }
                });
                break;
            case TRADE_WAIT_PAY:
                TradeWaitPayViewHolder tradeWaitPayViewHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_lv_card_union_wait_pay, null);
                    tradeWaitPayViewHolder = new TradeWaitPayViewHolder(convertView);
                    convertView.setTag(tradeWaitPayViewHolder);
                } else {
                    tradeWaitPayViewHolder = (TradeWaitPayViewHolder) convertView.getTag();
                }
                final CardUnionOrderBean.ContentBean.CardOrderVOListBean waitCardBean = mList.get(position);
                tradeWaitPayViewHolder.orderNum.setText(ConstUtils.getStringNoEmpty(waitCardBean.getOrderNo()));
                tradeWaitPayViewHolder.cardName.setText(ConstUtils.getStringNoEmpty(waitCardBean.getCardName()));
                switch (waitCardBean.getType()) {
                    case 0:
                    case 1:
                        tradeWaitPayViewHolder.cardSpecifications.setText(waitCardBean.getNum() + "天");
                        break;
                    case 2:
                    case 3:
                        tradeWaitPayViewHolder.cardSpecifications.setText(waitCardBean.getNum() + "次");
                        break;
                    default:
                        break;
                }

                tradeWaitPayViewHolder.cardPrice.setText("¥ " + waitCardBean.getPrice());
                tradeWaitPayViewHolder.needPayMoney.setText("¥ " + waitCardBean.getPayPrice());
                Glide.with(context).load(waitCardBean.getImageAddress()).into(tradeWaitPayViewHolder.ivCard);
                tradeWaitPayViewHolder.btnNowPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //t是创建时间，p是价格，v是订单号 id是订单id  type是订单类型，卡的是2

                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", String.valueOf(mList.get(position).getServiceOrderId()));
                        bundle.putString("payMoney", String.valueOf(mList.get(position).getPrice()));
                        bundle.putString("createdTime", mList.get(position).getCreatedTime());
                        bundle.putString("orderNo", mList.get(position).getOrderNo());
                        bundle.putString("url", H5Const.PAYMENT);
                        JumpIntent.jump((Activity) context, ServerTeamPageDetailActivity.class, bundle);
                    }
                });
                tradeWaitPayViewHolder.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelOrder(waitCardBean.getServiceOrderId(), position);
                    }
                });
                break;
            default:
            case TRADE_CLOSE:
                TradeCloseViewHolder tradeCloseViewHolder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_lv_card_union_trade_close, null);
                    tradeCloseViewHolder = new TradeCloseViewHolder(convertView);
                    convertView.setTag(tradeCloseViewHolder);
                } else {
                    tradeCloseViewHolder = (TradeCloseViewHolder) convertView.getTag();
                }
                final CardUnionOrderBean.ContentBean.CardOrderVOListBean closeCardBean = mList.get(position);
                tradeCloseViewHolder.orderNum.setText(ConstUtils.getStringNoEmpty(closeCardBean.getOrderNo()));
                tradeCloseViewHolder.cardName.setText(ConstUtils.getStringNoEmpty(closeCardBean.getCardName()));
                switch (closeCardBean.getType()) {
                    case 0:
                    case 1:
                        tradeCloseViewHolder.cardSpecifications.setText(closeCardBean.getNum() + "天");
                        break;
                    case 2:
                    case 3:
                        tradeCloseViewHolder.cardSpecifications.setText(closeCardBean.getNum() + "次");
                        break;
                    default:
                        break;
                }

                tradeCloseViewHolder.cardPrice.setText("¥ " + closeCardBean.getPrice());
                tradeCloseViewHolder.needPayMoney.setText("¥ " + closeCardBean.getPayPrice());
                Glide.with(context).load(closeCardBean.getImageAddress()).into(tradeCloseViewHolder.ivCard);
                tradeCloseViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder(closeCardBean.getServiceOrderId(), position);
                    }
                });
                break;
        }

        return convertView;
    }

    /**
     * 删除 订单
     *
     * @param serviceOrderId
     */
    private void deleteOrder(String serviceOrderId, final int position) {
        RequestApi.deleteCardUnionOrder(String.valueOf(App.manager.getId()), serviceOrderId, "0", new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    ToastUtils.show("删除成功!");
                    mList.remove(mList.get(position));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 取消 订单
     *
     * @param serviceOrderId
     */
    private void cancelOrder(String serviceOrderId, final int position) {
        RequestApi.cancelCardUnionOrder(App.manager.getPhoneNum(), App.manager.getUuid(), serviceOrderId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    ToastUtils.show("订单已取消!");
                    mList.get(position).setStatus(7);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    static class TradeSuccessViewHolder {
        @BindView(R.id.tv_order_num)
        TextView orderNum;
        @BindView(R.id.tv_order_status)
        TextView orderStatus;
        @BindView(R.id.tv_card_name)
        TextView cardName;
        @BindView(R.id.tv_card_specifications)
        TextView cardSpecifications;
        @BindView(R.id.tv_price)
        TextView cardPrice;
        @BindView(R.id.tv_need_pay_money)
        TextView needPayMoney;
        @BindView(R.id.comment)
        TextView btnComment;
        @BindView(R.id.btn_watch_coupon)
        TextView btnWatchCoupon;
        @BindView(R.id.iv_card)
        RoundedImageView ivCard;

        TradeSuccessViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class TradeCloseViewHolder {
        @BindView(R.id.tv_order_num)
        TextView orderNum;
        @BindView(R.id.tv_order_status)
        TextView orderStatus;
        @BindView(R.id.tv_card_name)
        TextView cardName;
        @BindView(R.id.tv_card_specifications)
        TextView cardSpecifications;
        @BindView(R.id.tv_price)
        TextView cardPrice;
        @BindView(R.id.tv_need_pay_money)
        TextView needPayMoney;
        @BindView(R.id.btn_delete)
        TextView btnDelete;
        @BindView(R.id.iv_card)
        RoundedImageView ivCard;

        TradeCloseViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class TradeWaitPayViewHolder {
        @BindView(R.id.tv_order_num)
        TextView orderNum;
        @BindView(R.id.tv_order_status)
        TextView orderStatus;
        @BindView(R.id.tv_card_name)
        TextView cardName;
        @BindView(R.id.tv_card_specifications)
        TextView cardSpecifications;
        @BindView(R.id.tv_price)
        TextView cardPrice;
        @BindView(R.id.tv_need_pay_money)
        TextView needPayMoney;
        @BindView(R.id.btn_cancel_order)
        TextView btnCancelOrder;
        @BindView(R.id.btn_now_pay)
        TextView btnNowPay;
        @BindView(R.id.iv_card)
        RoundedImageView ivCard;

        TradeWaitPayViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
