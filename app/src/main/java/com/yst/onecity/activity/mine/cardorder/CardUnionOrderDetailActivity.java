package com.yst.onecity.activity.mine.cardorder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.mine.cardbag.CardBagDetailActivity;
import com.yst.onecity.bean.CardUnionOrderDetailBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.ConstUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 卡联盟订单详情
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CardUnionOrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_trade_status)
    TextView tvTradeStatus;
    @BindView(R.id.iv_status_tag)
    ImageView ivStatusTag;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.iv_card)
    RoundedImageView ivCard;
    @BindView(R.id.card_name)
    TextView tvCardName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_specifications)
    TextView tvSpecifications;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_order_sum)
    TextView tvOrderSum;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rl_now_pay)
    RelativeLayout rlNowPay;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.delete_order)
    TextView deleteOrder;
    @BindView(R.id.now_pay)
    TextView nowPay;
    @BindView(R.id.watch_coupon)
    TextView watchCoupon;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_pay_success)
    TextView tvPaySuccess;

    /**
     * 订单状态 2 3 已完成  0 待付款 7已关闭
     */
    private int orderStatus;
    /**
     * 倒计时的总时间(单位:毫秒)
     */
    private long countdownTime;
    private Handler handler = new Handler();
    private String createTime;
    private String serviceOrderId;
    private String price;
    private String orderNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_union_order_detail;
    }

    @Override
    public void initData() {
        orderStatus = getIntent().getIntExtra("status", 0);
        serviceOrderId = getIntent().getStringExtra("serviceId");
        getCardUnionOrderDetail(serviceOrderId);

        initTitleBar("卡联盟订单");
    }

    /**
     * 获取订单列表数据
     */
    private void getCardUnionOrderDetail(final String orderId) {
        RequestApi.getCardUnionOrderDetail(App.manager.getUuid(), App.manager.getPhoneNum(), orderId, 1, 0, App.manager.getId(), new AbstractNetWorkCallback<CardUnionOrderDetailBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(CardUnionOrderDetailBean bean) {

                if (bean.getCode() == NO1) {
                    orderNo = bean.getContent().get(0).getOrderNo();
                    createTime = bean.getContent().get(0).getCreatedTime();
                    price = bean.getContent().get(0).getPayPrice();
                    orderStatus = bean.getContent().get(0).getStatus();
                    switch (orderStatus) {
                        case 1:
                        case 2:
                        case 3:
                        case 6:
                            deleteOrder.setVisibility(View.GONE);
                            watchCoupon.setVisibility(View.VISIBLE);
                            tvTradeStatus.setText("交易完成");
                            tvOrderStatus.setText("付款状态: 交易完成" );
                            ivStatusTag.setBackgroundResource(R.mipmap.sure);
                            tvPayTime.setVisibility(View.VISIBLE);
                            tvPaySuccess.setVisibility(View.VISIBLE);
                            break;
                        case 0:
                            getTimeDuring(createTime);
                            tvCloseTime.setVisibility(View.VISIBLE);
                            rlNowPay.setVisibility(View.VISIBLE);
                            nowPay.setVisibility(View.VISIBLE);
                            tvTradeStatus.setText("等待付款");
                            tvOrderStatus.setText("付款状态: 等待付款" );
                            ivStatusTag.setBackgroundResource(R.mipmap.waitting);
                            deleteOrder.setVisibility(View.GONE);
                            tvPayTime.setVisibility(View.GONE);
                            tvPaySuccess.setVisibility(View.GONE);
                            break;
                        case 5:
                        case 7:
                            ivStatusTag.setBackgroundResource(R.mipmap.cancel);
                            tvOrderStatus.setText("付款状态: 已关闭" );
                            tvPayTime.setVisibility(View.GONE);
                            tvPaySuccess.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }

                    Glide.with(getApplicationContext()).load(bean.getContent().get(0).getImageAddress()).into(ivCard);
                    tvCardName.setText(ConstUtils.getStringNoEmpty(bean.getContent().get(0).getCardName()));
                    tvPrice.setText(bean.getContent().get(0).getPrice());
                    tvSpecifications.setText(String.valueOf(bean.getContent().get(0).getNum()));
                    tvOrderNo.setText(bean.getContent().get(0).getOrderNo());
                    tvPhoneNum.setText("手机号:" + bean.getContent().get(0).getLinkphone());
                    tvMoney.setText("¥ "+ConstUtils.changeEmptyStringToZero(bean.getContent().get(0).getPayPrice()));
                    tvOrderNum.setText("数量:" + bean.getContent().get(0).getNum());
                    tvCoupon.setText("优惠券: ¥" + bean.getContent().get(0).getDiscountPrice());
                    tvOrderSum.setText(getString(R.string.card_union_order_sum) + bean.getContent().get(0).getPayPrice());
                    tvPayTime.setText("付款时间:  "+bean.getContent().get(0).getPayTime());
                    tvPaySuccess.setText("已付金额:¥ "+ConstUtils.changeEmptyStringToZero(bean.getContent().get(0).getPayPrice()));
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }
        });
    }

    @OnClick({R.id.btn_copy_order_num, R.id.delete_order, R.id.now_pay, R.id.watch_coupon})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_copy_order_num:
                CommonUtils.onClickCopy(this, tvOrderNo);
                break;
            case R.id.delete_order:
                deleteOrder(serviceOrderId);
                break;
            case R.id.now_pay:

                bundle.putString("orderId", serviceOrderId);
                bundle.putString("payMoney", price);
                bundle.putString("createdTime", createTime);
                bundle.putString("orderNo", orderNo);
                bundle.putString("url", H5Const.PAYMENT);
                JumpIntent.jump(this, ServerTeamPageDetailActivity.class, bundle);
                break;
            case R.id.watch_coupon:
                bundle.putInt("id", Integer.parseInt(serviceOrderId));
                JumpIntent.jump(this, CardBagDetailActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    Runnable runnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            // 倒计时总时间减1
            countdownTime -= 1000;
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm分钟ss秒");
            // 格式化倒计时的总时间
            String hms = simpleDateFormat.format(countdownTime);
            tvCloseTime.setText("剩余" + hms + "自动关闭");
            handler.postDelayed(this, 1000);
        }
    };

    private void getTimeDuring(String createTime) {
        // 有效时间
        long passTime = 30 * 60 * 1000;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String mTempTimeString = df.format(Long.parseLong(createTime));
            Date serverDate = df.parse(mTempTimeString);
            // 计算当前时间和从服务器获取的订单生成时间的时间差
            long duringTime = System.currentTimeMillis() - serverDate.getTime();
            // 计算倒计时的总时间
            countdownTime = passTime - duringTime;
            if (countdownTime > Constant.NO1000) {
                handler.postDelayed(runnable, 1000);
                tvCloseTime.setVisibility(View.VISIBLE);
            } else {
                handler.removeCallbacks(runnable);
                tvCloseTime.setVisibility(View.GONE);
                rlNowPay.setVisibility(View.GONE);
                nowPay.setVisibility(View.GONE);
                tvTradeStatus.setText("已关闭");
                ivStatusTag.setBackgroundResource(R.mipmap.cancel);
                deleteOrder.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除 订单
     *
     * @param serviceOrderId
     */
    private void deleteOrder(String serviceOrderId) {
        RequestApi.deleteCardUnionOrder(String.valueOf(App.manager.getId()), serviceOrderId, "0", new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    ToastUtils.show("删除成功!");
                    finish();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
