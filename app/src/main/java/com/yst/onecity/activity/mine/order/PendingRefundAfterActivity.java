package com.yst.onecity.activity.mine.order;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.bean.order.ServiceOrderRefundDetailBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.dialog.AbstractRefuseDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;

/**
 * 退款/售后 详情（卖家：待处理的退款【暂未拒绝或同意】）
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/23
 */
public class PendingRefundAfterActivity extends BaseActivity {

    @BindView(R.id.pending_refund_after_tv_refund_type)
    TextView tvRefundType;
    @BindView(R.id.pending_refund_after_iv_service_img)
    ImageView ivServiceImg;
    @BindView(R.id.pending_refund_after_tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.pending_refund_after_tv_refund_price)
    TextView tvRefundPrice;
    @BindView(R.id.pending_refund_after_tv_service_number)
    TextView tvServiceNumber;
    @BindView(R.id.pending_refund_after_tv_refund_explain)
    TextView tvRefundExplain;
    @BindView(R.id.pending_refund_after_tv_refund_explain_optional)
    TextView tvRefundExplainOptional;
    @BindView(R.id.pending_refund_after_ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.pending_refund_after_ll_refuse_reason)
    LinearLayout llRefuseReason;
    @BindView(R.id.pending_refund_after_tv_refund_reason)
    TextView tvRefundReason;

    private AbstractRefuseDialog refuseDialog;
    private int from;
    private String num;
    private String orderId;
    private String orderNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pending_refund_after;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.my_order_exchange));
        initDialog();
        Intent intent = getIntent();
        OrderGroupBean groupBean = (OrderGroupBean) intent.getSerializableExtra("groupBean");
        orderId = groupBean.getId();
        from = intent.getIntExtra("from",0);
        getRefundDetail();
    }

    /**
     * 获取售后详情
     */
    public void getRefundDetail(){
        RequestApi.getRefundDetail(App.manager.getUuid(), App.manager.getPhoneNum(), orderId, new AbstractNetWorkCallback<ServiceOrderRefundDetailBean>() {
            @Override
            public void onSuccess(ServiceOrderRefundDetailBean bean) {
                if(null != bean) {
                    if (bean.getCode() == NO1) {
                        if(null != bean.getContent()) {
                            orderNo = bean.getContent().getOrderNo();
                            setData(bean);
                        }
                    } else {
                        ToastUtils.show(bean.getMsg());
                        llBottom.setVisibility(View.GONE);
                    }
                }else {
                    ToastUtils.show("查询失败");
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
    public void setData(ServiceOrderRefundDetailBean bean){
        if(bean.getContent().getReturnOrderStatus() == NO1){
            tvRefundType.setText("申请退款");
            llRefuseReason.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
        }else if(bean.getContent().getReturnOrderStatus() == NO3){
            tvRefundType.setText("已确认退款");
            llRefuseReason.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
        }else if(bean.getContent().getReturnOrderStatus() == NO5){
            tvRefundType.setText("已拒绝退款");
            llBottom.setVisibility(View.GONE);
            llRefuseReason.setVisibility(View.VISIBLE);
            tvRefundReason.setText(bean.getContent().getRefuseReason());
        }

        Glide.with(context).load(bean.getContent().getProductImg()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivServiceImg);

        tvServiceName.setText(bean.getContent().getProductName());
        tvRefundPrice.setText("￥" + bean.getContent().getReturnPrice());
        num = Integer.toString(bean.getContent().getNum());
        tvServiceNumber.setText("x" + num);
        tvRefundExplain.setText(bean.getContent().getReturnReason());
        tvRefundExplainOptional.setText(bean.getContent().getRemark());
    }

    @OnClick({R.id.pending_refund_after_tv_disagree,R.id.pending_refund_after_tv_agree})
    public void onViewClick(View view){
        switch (view.getId()){
            //拒绝退款
            case R.id.pending_refund_after_tv_disagree:
                refuseDialog.showDialog();
                break;
            //同意退款
            case R.id.pending_refund_after_tv_agree:
                confirm();
                break;
            default:
                break;
        }
    }

    private void initDialog() {
        refuseDialog = new AbstractRefuseDialog(this) {
            @Override
            public void sureClick(String reason) {
                // 拒绝退款
                refuseRefund(reason);
            }
        };
    }

    /**
     * 确认退款
     */
    private void confirm(){
        RequestApi.confirmRefund(App.manager.getUuid(), App.manager.getPhoneNum(), orderNo, new AbstractNetWorkCallback<MsgBean>() {

            @Override
            public void onSuccess(MsgBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if(msgBean.getCode() == NO1){
                    EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
                    PendingRefundAfterActivity.this.finish();
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
     * 拒绝退款
     */
    private void refuseRefund(String reason) {
        RequestApi.refuseRefund(App.manager.getUuid(), App.manager.getPhoneNum(), orderId, reason, new AbstractNetWorkCallback<MsgBean>() {
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
                if (msgBean.getCode() == Constant.NO1) {
                    EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
                    PendingRefundAfterActivity.this.finish();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
