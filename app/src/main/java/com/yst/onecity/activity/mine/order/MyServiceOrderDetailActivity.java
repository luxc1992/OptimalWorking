package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.bean.order.ServiceOrderDetailBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.ZxingUtils;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;

/**
 * 我的服务订单详情
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/18
 */
public class MyServiceOrderDetailActivity extends BaseActivity {

    @BindView(R.id.my_service_order_detail_iv_mark)
    ImageView ivMark;
    @BindView(R.id.my_service_order_detail_tv_mark_describe)
    TextView tvMarkDescribe;
    @BindView(R.id.my_service_order_detail_tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.my_service_order_detail_iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.my_service_order_detail_tv_merchant)
    TextView tvMerchant;
    @BindView(R.id.my_service_order_detail_iv_goods)
    ImageView ivImg;
    @BindView(R.id.my_service_order_detail_tv_title)
    TextView tvTitle;
    @BindView(R.id.my_service_order_detail_tv_price)
    TextView tvPrice;
    @BindView(R.id.my_service_order_detail_tv_time)
    TextView tvServiceTime;
    @BindView(R.id.my_service_order_detail_tv_type)
    TextView tvServiceType;
    @BindView(R.id.my_service_order_detail_rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.my_service_order_detail_tv_code)
    TextView tvCode;
    @BindView(R.id.my_service_order_detail_iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.my_service_order_detail_tv_serial)
    TextView tvSerial;
    @BindView(R.id.my_service_order_detail_tv_service_phone)
    TextView tvServicePhone;
    @BindView(R.id.my_service_order_detail_ll_payTime)
    LinearLayout llPayTime;
    @BindView(R.id.my_service_order_detail_tv_payment_time)
    TextView tvPayTime;
    @BindView(R.id.my_service_order_detail_tv_number)
    TextView tvNumber;
    @BindView(R.id.my_service_order_detail_tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.my_service_order_detail_tv_pay_price_des)
    TextView tvPayPriceDes;
    @BindView(R.id.my_service_order_detail_tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.my_service_order_detail_ll_address)
    LinearLayout llAddress;
    @BindView(R.id.my_service_order_detail_tv_address_type)
    TextView tvAddressType;
    @BindView(R.id.my_service_order_detail_tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.my_service_order_detail_tv_address)
    TextView tvAddress;
    @BindView(R.id.my_service_order_detail_tv_service_content)
    TextView tvServiceContent;
    @BindView(R.id.my_service_order_detail_tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.my_service_order_detail_tv_btn)
    TextView tvBtn;

    private AbstractDeleteDialog deleteDialog;
    /**
     * 0买家  1卖家
     */
    private int from;
    private int status;
    private String linkPhone;
    private String phone;
    private String orderId;
    Bundle bundle = new Bundle();
    int flag;
    private String advisorId;
    private String serviceProjectId;
    /**
     * 倒计时的总时间(单位:毫秒)
     */
    private long countdownTime;
    private Handler handler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_service_order_detail;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.my_service_order));
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        from = intent.getIntExtra("from",0);
        status = intent.getIntExtra("status",8);
        bundle.putInt("from",from);
        //获取详情
        getServiceOrderDetail(orderId);
        initDialog();
    }

    @OnClick({R.id.my_service_order_detail_rl_merchant, R.id.my_service_order_detail_rl_service, R.id.my_service_order_detail_tv_copy
            ,R.id.my_service_order_detail_iv_call})
    public void onClick(View view){
        switch (view.getId()){
            //跳转至服务团队主页
            case R.id.my_service_order_detail_rl_merchant:
                Bundle bundle = new Bundle();
                //服务团队id
                bundle.putString("id", advisorId);
                bundle.putString("url", H5Const.SERVER_TEAM_PAGE);
                JumpIntent.jump(MyServiceOrderDetailActivity.this, ServerTeamPageDetailActivity.class, bundle);
                break;
            //跳转至服务项目详情页
            case  R.id.my_service_order_detail_rl_service:
                Bundle bundle2 = new Bundle();
                //服务项目id
                bundle2.putString("id", serviceProjectId);
                bundle2.putString("url", H5Const.SERVE_TEM_DETAILS);
                JumpIntent.jump(MyServiceOrderDetailActivity.this, ServerTeamPageDetailActivity.class, bundle2);
                break;
            //复制
            case R.id.my_service_order_detail_tv_copy:
                ToastUtils.show("复制成功!");
                ConstUtils.copy(ConstUtils.getStringNoEmpty(tvSerial.getText().toString().trim()),context);
                break;
            //调用拨号
            case R.id.my_service_order_detail_iv_call:
                String phoneNumber;
                if(from == 0){
                    phoneNumber = phone;
                }else {
                    phoneNumber = linkPhone;
                }
                if(!TextUtils.isEmpty(phoneNumber)) {
                    ConstUtils.call(phoneNumber, MyServiceOrderDetailActivity.this);
                }else {
                    ToastUtils.show("未获得手机号");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取订单列表数据
     */
    private void getServiceOrderDetail(final String orderId) {
        RequestApi.getServiceOrderDetail(App.manager.getUuid(),App.manager.getPhoneNum(),orderId, from,App.manager.getId(), new AbstractNetWorkCallback<ServiceOrderDetailBean>() {
            @Override
            public void onSuccess(ServiceOrderDetailBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent() != null && bean.getContent().size() > 0) {
                        advisorId = Long.toString(bean.getContent().get(0).getAdvisorId());
                        serviceProjectId = Long.toString(bean.getContent().get(0).getServiceProjectId());
                        List<OrderChildBean> cList = new ArrayList<>();
                        OrderChildBean ocb = new OrderChildBean();
                        ocb.setName(bean.getContent().get(0).getTitle());
                        ocb.setNum(bean.getContent().get(0).getNum());
                        ocb.setSPrice(bean.getContent().get(0).getPrice());
                        ocb.setAddress(bean.getContent().get(0).getProjectImage());
                        cList.add(ocb);
                        OrderGroupBean ogb = new OrderGroupBean();
                        ogb.setStatus(bean.getContent().get(0).getStatus());
                        ogb.setNickName(bean.getContent().get(0).getProjectName());
                        ogb.setTotal_price(bean.getContent().get(0).getPrice());
                        ogb.setSon(cList);
                        ogb.setId(Integer.toString(bean.getContent().get(0).getId()));
                        bundle.putSerializable("groupBean",ogb);

                        setData(bean);
                    }else {
                        ToastUtils.show("未查询到数据");
                    }
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

    /**
     * 设置数据
     */
    public void setData(final ServiceOrderDetailBean bean){
        //卖家手机号
        phone = bean.getContent().get(0).getPhone();
        Glide.with(context)
                .load(bean.getContent().get(0).getImgUrl())
                .dontAnimate()
                .placeholder(R.mipmap.headimage)
                .error(R.mipmap.headimage)
                .into(ivHead);
        Glide.with(context)
                .load(bean.getContent().get(0).getProjectImage())
                .dontAnimate()
                .placeholder(R.mipmap.logo)
                .error(R.mipmap.logo)
                .into(ivImg);

        tvMerchant.setText(bean.getContent().get(0).getProjectName());
        tvTitle.setText(bean.getContent().get(0).getTitle());
        tvPrice.setText(bean.getContent().get(0).getPrice());
        tvServiceTime.setText(bean.getContent().get(0).getServiceTime());

        tvSerial.setText(bean.getContent().get(0).getOrderNo());
        //买家手机号
        linkPhone = bean.getContent().get(0).getLinkphone();
        tvServicePhone.setText(linkPhone);
        String number = Integer.toString(bean.getContent().get(0).getNum());
        tvNumber.setText(number);
        tvOrderPrice.setText(bean.getContent().get(0).getPrice());
        if(from == 0){
            //服务类型 0 到店 1 到家
            if(bean.getContent().get(0).getServiceType() == 1){
                tvServiceType.setText("到家服务");
            }else {
                tvServiceType.setText("到店服务");
            }
            llAddress.setVisibility(View.VISIBLE);
            tvAddressType.setText("商家地址");
            tvMerchantName.setText(bean.getContent().get(0).getProjectName());
            StringBuilder sb = new StringBuilder();
            sb.append(bean.getContent().get(0).getProvinceName());
            sb.append(bean.getContent().get(0).getCountyName());
            sb.append(bean.getContent().get(0).getCityName());
            sb.append(bean.getContent().get(0).getAddress());
            tvAddress.setText(sb.toString().trim());
        }else {
            tvAddressType.setText("买家地址");
            //服务类型 0 到店 1 到家
            if(bean.getContent().get(0).getServiceType() == 1){
                llAddress.setVisibility(View.VISIBLE);
                tvServiceType.setText("到家服务");
                StringBuilder sb = new StringBuilder();
                sb.append(bean.getContent().get(0).getServiceAdd());
                sb.append(bean.getContent().get(0).getDetailAdd());
                tvMerchantName.setText(bean.getContent().get(0).getLinkman());
                tvAddress.setText(sb.toString());
            }else {
                tvServiceType.setText("到店服务");
                llAddress.setVisibility(View.GONE);
            }
        }

        tvServiceContent.setText(bean.getContent().get(0).getContent());

        //0、待付款   1、待使用  2、待评价  3、已评价   4、申请退款   5、已退款  6、拒绝退款  7、已取消 8、全部
        int status = bean.getContent().get(0).getStatus();

        switch (status){
            //买家 待付款
            case NO0:
                ivMark.setBackgroundResource(R.mipmap.detail_wait);
                tvMarkDescribe.setText(getString(R.string.wait_pay));
                tvCountDown.setVisibility(View.VISIBLE);
                getTimeDuring(bean.getContent().get(0).getCreatedTime());
                rlCode.setVisibility(View.GONE);
                llPayTime.setVisibility(View.GONE);
                tvPayPriceDes.setVisibility(View.GONE);
                tvPayPrice.setVisibility(View.GONE);
                tvBtn1.setVisibility(View.GONE);
                tvBtn.setVisibility(View.VISIBLE);
                if(from == 0){
                    tvBtn.setText("立即付款");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bundle.putString("orderId", orderId);
                            bundle.putString("payMoney", bean.getContent().get(0).getPayPrice());
                            bundle.putString("createdTime", Long.toString(bean.getContent().get(0).getCreatedTime()));
                            bundle.putString("orderNo", bean.getContent().get(0).getOrderNo());
                            bundle.putString("url", H5Const.PAYMENT);
                            JumpIntent.jump(MyServiceOrderDetailActivity.this, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                }else {
                    tvBtn.setText("取消订单");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            flag = 2;
                            deleteDialog.setText("确定删取消订单？", "确定", "取消");
                            deleteDialog.showDialog();
                        }
                    });
                }
                break;
            //待使用
            case NO1:
                ivMark.setBackgroundResource(R.mipmap.detail_wait);
                tvMarkDescribe.setText("等待使用");
                tvCountDown.setVisibility(View.GONE);
                //from 0买家 1卖家
                if(from == NO0){
                    rlCode.setVisibility(View.VISIBLE);
                    tvCode.setText(bean.getContent().get(0).getCode());
                    StringBuilder qrSb = new StringBuilder();
                    //0服务 1商品 2周期卡消费订单 3次卡消费订单
                    qrSb.append(0);
                    qrSb.append("&");
                    //订单id
                    qrSb.append(bean.getContent().get(0).getId());
                    qrSb.append("&");
                    //验证码
                    qrSb.append(bean.getContent().get(0).getCode());
                    ivQrCode.setImageBitmap(ZxingUtils.createQRCodeBitmap(qrSb.toString(), 500));
                    tvBtn1.setVisibility(View.GONE);
                    tvBtn.setVisibility(View.VISIBLE);
                    tvBtn.setText("申请退款");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JumpIntent.jump(MyServiceOrderDetailActivity.this, ApplicationForRefundActivity.class,bundle);
                        }
                    });
                }else {
                    rlCode.setVisibility(View.GONE);
                    tvBtn1.setVisibility(View.GONE);
                    tvBtn.setVisibility(View.GONE);
                }
                llPayTime.setVisibility(View.VISIBLE);
                tvPayTime.setText(bean.getContent().get(0).getPayTime());
                tvPayPriceDes.setVisibility(View.VISIBLE);
                tvPayPrice.setVisibility(View.VISIBLE);
                tvPayPrice.setText(bean.getContent().get(0).getPayPrice());
                break;
            //待评价
            case NO2:
                ivMark.setBackgroundResource(R.mipmap.detail_ok);
                tvMarkDescribe.setText("交易完成");
                tvCountDown.setVisibility(View.GONE);
                rlCode.setVisibility(View.GONE);
                llPayTime.setVisibility(View.VISIBLE);
                tvPayTime.setText(bean.getContent().get(0).getPayTime());
                tvPayPriceDes.setVisibility(View.VISIBLE);
                tvPayPrice.setVisibility(View.VISIBLE);
                tvPayPrice.setText(bean.getContent().get(0).getPayPrice());
                if(from == 0) {
                    tvBtn1.setVisibility(View.VISIBLE);
                    tvBtn1.setText("删除订单");
                    tvBtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            flag = 1;
                            deleteDialog.setText("确定删除该订单？", "确定", "取消");
                            deleteDialog.showDialog();
                        }
                    });
                    tvBtn.setVisibility(View.VISIBLE);
                    tvBtn.setText("评价");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JumpIntent.jump(MyServiceOrderDetailActivity.this, ServiceOrderEvaluateActivity.class,bundle);
                        }
                    });
                }else {
                    tvBtn1.setVisibility(View.GONE);
                    tvBtn.setVisibility(View.VISIBLE);
                    tvBtn.setText("删除订单");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            flag = 1;
                            deleteDialog.setText("确定删除该订单？", "确定", "取消");
                            deleteDialog.showDialog();
                        }
                    });
                }
                break;
            //3、已评价 4、申请退款 5、已退款 6、拒绝退款
            case NO3:
            case NO4:
            case NO5:
            case NO6:
                ivMark.setBackgroundResource(R.mipmap.detail_ok);
                tvMarkDescribe.setText("交易完成");
                tvCountDown.setVisibility(View.GONE);
                rlCode.setVisibility(View.GONE);
                llPayTime.setVisibility(View.VISIBLE);
                tvPayTime.setText(bean.getContent().get(0).getPayTime());
                tvPayPriceDes.setVisibility(View.VISIBLE);
                tvPayPrice.setVisibility(View.VISIBLE);
                tvPayPrice.setText(bean.getContent().get(0).getPayPrice());
                tvBtn1.setVisibility(View.GONE);
                tvBtn.setVisibility(View.VISIBLE);
                tvBtn.setText("删除订单");
                tvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flag = 1;
                        deleteDialog.setText("确定删除该订单？", "确定", "取消");
                        deleteDialog.showDialog();
                    }
                });
                break;
            case NO7:
                ivMark.setBackgroundResource(R.mipmap.detail_cancel);
                tvMarkDescribe.setText("该订单已取消");
                tvCountDown.setVisibility(View.GONE);
                rlCode.setVisibility(View.GONE);
                llPayTime.setVisibility(View.GONE);
                tvPayPriceDes.setVisibility(View.GONE);
                tvPayPrice.setVisibility(View.GONE);

                if(from == 0) {
                    tvBtn1.setVisibility(View.VISIBLE);
                    tvBtn1.setText("删除订单");
                    tvBtn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            flag = 1;
                            deleteDialog.setText("确定删除该订单？", "确定", "取消");
                            deleteDialog.showDialog();
                        }
                    });
                    tvBtn.setVisibility(View.VISIBLE);
                    tvBtn.setText("再来一单");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //服务项目的id
                            bundle.putString("id", orderId);
                            bundle.putString("url", H5Const.SERVICE_PROJECT_CONFIRM_ORDER);
                            JumpIntent.jump(MyServiceOrderDetailActivity.this, ServerTeamPageDetailActivity.class, bundle);
                        }
                    });
                }else {
                    tvBtn1.setVisibility(View.GONE);
                    tvBtn.setVisibility(View.VISIBLE);
                    tvBtn.setText("删除订单");
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            flag = 1;
                            deleteDialog.setText("确定删除该订单？", "确定", "取消");
                            deleteDialog.showDialog();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化 删除/取消 订单 弹框
     */
    private void initDialog() {
        deleteDialog = new AbstractDeleteDialog(MyServiceOrderDetailActivity.this) {
            @Override
            public void sureClick() {
                //1删除订单，2取消订单
                if (flag == NO1) {
                    deleteOrder();
                } else if (flag == NO2) {
                    cancelServiceOrder();
                }
            }
        };
    }

    /**
     * 删除订单
     */
    private void deleteOrder() {
        RequestApi.deleteServiceOrder(App.manager.getUuid(),App.manager.getPhoneNum(), App.manager.getId(), orderId, Integer.toString(from), new AbstractNetWorkCallback<MsgBean>() {
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
                    EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
                    MyServiceOrderDetailActivity.this.finish();
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
    private void cancelServiceOrder() {
        RequestApi.cancelServiceOrder(App.manager.getPhoneNum(), App.manager.getUuid(),App.manager.getId(), orderId, new AbstractNetWorkCallback<MsgBean>() {
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
                        EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
                        MyServiceOrderDetailActivity.this.finish();
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

    /**
     * 待付款倒计时
     * @param createTime 创建时间
     */
    private void getTimeDuring(long createTime) {
        // 有效时间
        long passTime = 30 * 60 * 1000;
        // 计算当前时间和从服务器获取的订单生成时间的时间差
        long duringTime = System.currentTimeMillis() - createTime;
        // 计算倒计时的总时间
        countdownTime = passTime - duringTime;
        if (countdownTime > Constant.NO1000) {
            handler.postDelayed(runnable, 1000);
            tvCountDown.setVisibility(View.VISIBLE);
        } else {
            EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
            tvCountDown.setVisibility(View.GONE);
            tvMarkDescribe.setText("该订单已取消");
            tvBtn1.setVisibility(View.GONE);
            ivMark.setBackgroundResource(R.mipmap.detail_cancel);
            if(from == 0) {
                tvBtn1.setVisibility(View.VISIBLE);
                tvBtn1.setText("删除订单");
                tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flag = 1;
                        deleteDialog.setText("确定删除该订单？", "确定", "取消");
                        deleteDialog.showDialog();
                    }
                });
                tvBtn.setVisibility(View.VISIBLE);
                tvBtn.setText("再来一单");
                tvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //服务项目的id
                        bundle.putString("id", orderId);
                        bundle.putString("url", H5Const.SERVICE_PROJECT_CONFIRM_ORDER);
                        JumpIntent.jump(MyServiceOrderDetailActivity.this, ServerTeamPageDetailActivity.class, bundle);
                    }
                });
            }else {
                tvBtn1.setVisibility(View.GONE);
                tvBtn.setVisibility(View.VISIBLE);
                tvBtn.setText("删除订单");
                tvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flag = 1;
                        deleteDialog.setText("确定删除该订单？", "确定", "取消");
                        deleteDialog.showDialog();
                    }
                });
            }
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
            tvCountDown.setText("剩余" + hms + "自动关闭");
            handler.postDelayed(this, 1000);
        }
    };
}
