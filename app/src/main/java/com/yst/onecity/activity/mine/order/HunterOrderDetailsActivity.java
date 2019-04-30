package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.OrderDetailsBean;
import com.yst.onecity.bean.order.OrderDetailsContentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.MyListView;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;

/**
 * 订单详情
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/07
 */
public class HunterOrderDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;
    @BindView(R.id.tv_receive_people)
    TextView tvReceivePeople;
    @BindView(R.id.tv_receive_phone)
    TextView tvReceivePhone;
    @BindView(R.id.tv_receive_address)
    TextView tvReceiveAddress;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_freight_money)
    TextView tvFreightMoney;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.tv_sale_money)
    TextView tvSaleMoney;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_copy_orderNo)
    TextView tvCopyOrderNo;
    @BindView(R.id.tv_commit_time)
    TextView tvCommitTime;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.ll_pay_money)
    LinearLayout llPayMoney;
    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_product_type)
    TextView tvProductType;
    @BindView(R.id.tv_logistics_company)
    TextView tvLogisticsCompany;
    @BindView(R.id.ll_logistics_no)
    LinearLayout llLogisticsNo;
    @BindView(R.id.tv_logistics_no)
    TextView tvLogisticsNo;
    @BindView(R.id.tv_actual_pay_money)
    TextView tvActualPayMoney;
    @BindView(R.id.tv_pay_money_time)
    TextView tvPayMoneyTime;
    @BindView(R.id.ll_order_bottom)
    LinearLayout llOrderBottom;
    @BindView(R.id.ll_should_pay_money)
    LinearLayout llShouldPayMoney;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_should_pay_money)
    TextView tvShouldPayMoney;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.tv_express_type)
    TextView tvExpressType;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private List<OrderChildBean> data = new ArrayList<>();
    private AbstractDeleteDialog deleteDialog;
    private int status;
    private String orderNo;
    private AbstractCommonAdapter<OrderChildBean> adapter;
    private String oIds;
    /**
     * 倒计时的总时间(单位:毫秒)
     */
    private long countdownTime;
    private Handler handler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_order_details;
    }

    @Override
    public void initData() {
        initTitleBar("订单详情");
        initDialog();
        if (getIntent().getExtras() != null) {
            status = getIntent().getExtras().getInt(Constant.FLAG);
            orderNo = getIntent().getExtras().getString("orderNo");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        RequestApi.getHunterOrderDetails(App.manager.getPhoneNum(), App.manager.getUuid(), orderNo, status, new AbstractNetWorkCallback<OrderDetailsContentBean>() {
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

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(OrderDetailsContentBean bean) {
                if (bean.getCode() == Constant.NO1) {
                    if (bean.getContent() != null) {
                        OrderDetailsBean detailsBean = bean.getContent();
                        List<OrderChildBean> childData = detailsBean.getProductList();
                        StringBuilder str = new StringBuilder();
                        for (OrderChildBean childBean : childData) {
                            str.append(childBean.getId() + ",");
                        }
                        oIds = str.substring(0, str.length() - 1);
                        adapter.onRefresh(detailsBean.getProductList());
                        tvProductType.setText("商品类型：" + childData.get(0).getProductType());
                        setOrderDetails(detailsBean);
                    }
                } else {
                    scrollView.setVisibility(View.GONE);
                    llOrderBottom.setVisibility(View.GONE);
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                scrollView.setVisibility(View.GONE);
                llOrderBottom.setVisibility(View.GONE);
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 设置订单详情数据
     *
     * @param detailsBean detailsBean
     */
    @SuppressLint("SetTextI18n")
    private void setOrderDetails(OrderDetailsBean detailsBean) {
        tvReceivePeople.setText("收货人：" + detailsBean.getReceiveName());
        tvReceivePhone.setText(ConstUtils.getStringNoEmpty(detailsBean.getReceivePhone()));
        tvReceiveAddress.setText("收货地址：" + detailsBean.getReceiveAddress());
        tvTotalMoney.setText("¥" + detailsBean.getTotalPrice());
        // 运费
        tvFreightMoney.setText("¥" + detailsBean.getOrderFreight());
        // 优惠券和活动优惠暂时不做
        tvCouponMoney.setText("¥0.00");
        tvSaleMoney.setText("-¥0.00");
        tvOrderNo.setText(ConstUtils.getStringNoEmpty(detailsBean.getOrderNo()));
        tvCommitTime.setText("提交时间：" + detailsBean.getCreateTime());
        tvPayWay.setText("支付方式：" + detailsBean.getPayType());
        switch (status) {
            case NO0:
                tvOrderState.setText("等待付款");
                tvBtn1.setText("取消订单");
                tvBtn2.setVisibility(View.GONE);
                tvLogisticsCompany.setVisibility(View.GONE);
                llLogisticsNo.setVisibility(View.GONE);
                tvActualPayMoney.setVisibility(View.GONE);
                tvPayMoney.setVisibility(View.GONE);
                tvPayMoneyTime.setVisibility(View.GONE);
                llShouldPayMoney.setVisibility(View.VISIBLE);
                ivOrderState.setImageResource(R.mipmap.daifukuan);
                tvPrice.setText("应付金额：");
                tvShouldPayMoney.setText("¥" + detailsBean.getTotalPrice());
                getTimeDuring(detailsBean.getCreateTime());
                break;
            case NO2:
                tvOrderState.setText("等待发货");
                tvBtn1.setText("取消订单");
                if (detailsBean.getIsConfirmSendButton() == 1) {
                    tvBtn2.setText("确认发货");
                } else {
                    tvBtn2.setVisibility(View.GONE);
                }
                tvLogisticsCompany.setVisibility(View.GONE);
                llLogisticsNo.setVisibility(View.GONE);
                tvPrice.setText("已付金额");
                llShouldPayMoney.setVisibility(View.VISIBLE);
                ivOrderState.setImageResource(R.mipmap.daifahuo);
                tvActualPayMoney.setText("实付金额：¥" + detailsBean.getTotalPrice());
                tvPayMoneyTime.setText("付款时间：" + detailsBean.getPayTime());
                // 已付金额
                tvPrice.setText("已付金额：");
                tvShouldPayMoney.setText("¥" + detailsBean.getTotalPrice());
                break;
            case NO3:
                tvOrderState.setText("等待收货");
                llOrderBottom.setVisibility(View.GONE);
                ivOrderState.setImageResource(R.mipmap.putong);
                tvExpressType.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(detailsBean.getLogisticsCompanyName())) {
                    tvLogisticsNo.setVisibility(View.VISIBLE);
                    llLogisticsNo.setVisibility(View.VISIBLE);
                    tvLogisticsCompany.setText("物流公司：" + detailsBean.getLogisticsCompanyName());
                    tvLogisticsNo.setText(ConstUtils.getStringNoEmpty(detailsBean.getLogisticsNo()));
                }
                tvActualPayMoney.setText("实付金额：¥" + detailsBean.getTotalPrice());
                tvPayMoneyTime.setText("付款时间：" + detailsBean.getPayTime());
                break;
            case NO4:
                tvOrderState.setText("交易完成");
                tvBtn1.setText("删除订单");
                tvBtn2.setText("查看评价");
                ivOrderState.setImageResource(R.mipmap.wancheng);
                if (!TextUtils.isEmpty(detailsBean.getLogisticsCompanyName())) {
                    tvLogisticsNo.setVisibility(View.VISIBLE);
                    llLogisticsNo.setVisibility(View.VISIBLE);
                    tvLogisticsCompany.setText("物流公司：" + detailsBean.getLogisticsCompanyName());
                    tvLogisticsNo.setText(ConstUtils.getStringNoEmpty(detailsBean.getLogisticsNo()));
                }
                tvActualPayMoney.setText("实付金额：¥" + detailsBean.getTotalPrice());
                tvPayMoneyTime.setText("付款时间：" + detailsBean.getPayTime());
                break;
            case NO6:
                tvOrderState.setText("交易关闭");
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setText("删除订单");
                tvLogisticsCompany.setVisibility(View.GONE);
                llLogisticsNo.setVisibility(View.GONE);
                tvActualPayMoney.setVisibility(View.GONE);
                tvPayMoney.setVisibility(View.GONE);
                tvPayMoneyTime.setVisibility(View.GONE);
                ivOrderState.setImageResource(R.mipmap.yiquxiao);
                if (!TextUtils.isEmpty(detailsBean.getLogisticsCompanyName())) {
                    tvLogisticsNo.setVisibility(View.VISIBLE);
                    llLogisticsNo.setVisibility(View.VISIBLE);
                    tvLogisticsCompany.setText("物流公司：" + detailsBean.getLogisticsCompanyName());
                    tvLogisticsNo.setText(ConstUtils.getStringNoEmpty(detailsBean.getLogisticsNo()));
                }
                tvActualPayMoney.setText("实付金额：¥" + detailsBean.getTotalPrice());
                tvPayMoneyTime.setText("付款时间：" + detailsBean.getPayTime());
                break;
            case NO7:
                tvOrderState.setText("等待评价");
                tvBtn1.setText("删除订单");
                tvBtn2.setText("查看评价");
                ivOrderState.setImageResource(R.mipmap.wancheng);
                if (!TextUtils.isEmpty(detailsBean.getLogisticsCompanyName())) {
                    tvLogisticsNo.setVisibility(View.VISIBLE);
                    llLogisticsNo.setVisibility(View.VISIBLE);
                    tvLogisticsCompany.setText("物流公司：" + detailsBean.getLogisticsCompanyName());
                    tvLogisticsNo.setText(ConstUtils.getStringNoEmpty(detailsBean.getLogisticsNo()));
                }
                tvActualPayMoney.setText("实付金额：¥" + detailsBean.getTotalPrice());
                tvPayMoneyTime.setText("付款时间：" + detailsBean.getPayTime());
                break;
            default:
                break;
        }
    }

    private void getTimeDuring(String createTime) {
        // 有效时间
        long passTime = 30 * 60 * 1000;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date serverDate = df.parse(createTime);
            // 计算当前时间和从服务器获取的订单生成时间的时间差
            long duringTime = System.currentTimeMillis() - serverDate.getTime();
            // 计算倒计时的总时间
            countdownTime = passTime - duringTime;
            if (countdownTime > Constant.NO1000) {
                handler.postDelayed(runnable, 1000);
                tvCloseTime.setVisibility(View.VISIBLE);
            } else {
                tvCloseTime.setVisibility(View.GONE);
                tvOrderState.setText("交易关闭");
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setText("删除订单");
                ivOrderState.setImageResource(R.mipmap.yiquxiao);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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

    private void initDialog() {
        deleteDialog = new AbstractDeleteDialog(this) {
            @Override
            public void sureClick() {
                if (!Utils.isClickable()) {
                    return;
                }
                if (status == NO4 || status == NO6 || status == NO7) {
                    deleteOrder();
                }
                if (status == NO0 || status == NO2) {
                    if (!TextUtils.isEmpty(oIds)) {
                        cancleOrder(oIds);
                    }
                }
            }
        };
    }

    @Override
    public void initCtrl() {
        adapter = new AbstractCommonAdapter<OrderChildBean>(context, data, R.layout.item_order_product) {
            @Override
            public void convert(CommonViewHolder holder, int position, OrderChildBean item) {
                LinearLayout llItemProduct = holder.getView(R.id.ll_item_product);
                llItemProduct.setBackgroundColor(Color.WHITE);
                holder.setVisiable(R.id.tv_product_price, View.VISIBLE);
                holder.setText(R.id.tv_product_name, ConstUtils.getStringNoEmpty(item.getName()));
                holder.setText(R.id.tv_des, ConstUtils.getStringNoEmpty(item.getContent()));
                holder.setText(R.id.tv_product_price, "¥" + item.getSPrice());
                holder.setText(R.id.tv_count, "x" + item.getNum());
                Glide.with(context).load(item.getAddress()).into((ImageView) holder.getView(R.id.iv_product));
            }
        };
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_copy_orderNo, R.id.tv_copy_logisticsNo, R.id.tv_btn1, R.id.tv_btn2})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_right:
                ToastUtils.show("联系团队");
                break;
            //复制订单号
            case R.id.tv_copy_orderNo:
                ConstUtils.copyNo(tvOrderNo.getText().toString(), this);
                break;
            //复制物流编号
            case R.id.tv_copy_logisticsNo:
                ConstUtils.copyNo(tvLogisticsNo.getText().toString(), this);
                break;
            case R.id.tv_btn1:
                if (status == NO0 || status == NO2) {
                    deleteDialog.setText("确定取消该订单？", "确定", "取消");
                } else if (status == NO4 || status == NO7) {
                    deleteDialog.setText("确定删除该订单？", "确定", "取消");
                }
                deleteDialog.showDialog();
                break;
            case R.id.tv_btn2:
                if (status == NO6) {
                    deleteDialog.setText("确定删除该订单？", "确定", "取消");
                    deleteDialog.showDialog();
                } else if (status == NO2) {
                    if (!Utils.isClickable()) {
                        return;
                    }
                    bundle.putString("orderNo", orderNo);
                    JumpIntent.jump(this, TransportInformationActivity.class, bundle);
                } else if (status == NO4 || status == NO7) {
                    bundle.putString("orderNo", orderNo);
                    JumpIntent.jump(this, CheckEvaluateListActivity.class, bundle);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 删除订单
     */
    private void deleteOrder() {
        RequestApi.deleteOrder(App.manager.getPhoneNum(), App.manager.getUuid(), orderNo, 2, new AbstractNetWorkCallback<MsgBean>() {
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

            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    EventBus.getDefault().post(new EventBean(msgBean.getCode()));
                    HunterOrderDetailsActivity.this.finish();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 取消订单
     */
    private void cancleOrder(String oIds) {
        RequestApi.cancleOrderForHunter(App.manager.getPhoneNum(), App.manager.getUuid(), oIds, 1, new AbstractNetWorkCallback<MsgBean>() {
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

            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean != null) {
                    if (msgBean.getCode() == 1) {
                        EventBus.getDefault().post(new EventBean(msgBean.getCode()));
                        HunterOrderDetailsActivity.this.finish();
                    }
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}