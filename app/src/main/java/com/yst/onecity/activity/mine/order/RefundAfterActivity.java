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
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.R;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.bean.order.ServiceOrderRefundDetailBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;

/**
 * 退款/售后 详情（买家）
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/19
 */
public class RefundAfterActivity extends BaseActivity {

    @BindView(R.id.refund_after_tv_refund_type)
    TextView tvRefundType;
    @BindView(R.id.refund_after_iv_service_img)
    ImageView ivServiceImg;
    @BindView(R.id.refund_after_tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.refund_after_ll_refuse_reason)
    LinearLayout llRefuseReason;
    @BindView(R.id.refund_after_tv_refund_reason)
    TextView tvRefundReason;
    @BindView(R.id.refund_after_tv_refund_price)
    TextView tvRefundPrice;
    @BindView(R.id.refund_after_tv_service_number)
    TextView tvServiceNumber;
    @BindView(R.id.refund_after_tv_refund_explain)
    TextView tvRefundExplain;
    @BindView(R.id.refund_after_tv_refund_explain_optional)
    TextView tvRefundExplainOptional;

    private int from;
    private String num;
    private OrderGroupBean groupBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_after;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.my_order_exchange));
        setRightText(getString(R.string.contact_teams));
        setRightTextViewClickable(true);
        Intent intent = getIntent();
        groupBean = (OrderGroupBean) intent.getSerializableExtra("groupBean");
        from = intent.getIntExtra("from",0);
        getRefundDetail();
    }

    /**
     * 获取售后详情
     */
    public void getRefundDetail(){
        String orderId = groupBean.getId();
        RequestApi.getRefundDetail(App.manager.getUuid(), App.manager.getPhoneNum(), orderId, new AbstractNetWorkCallback<ServiceOrderRefundDetailBean>() {
            @Override
            public void onSuccess(ServiceOrderRefundDetailBean bean) {
                if(null != bean) {
                    if (bean.getCode() == NO1) {
                        if(null != bean.getContent()) {
                            setData(bean);
                        }
                    } else {
                        ToastUtils.show(bean.getMsg());
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
            llRefuseReason.setVisibility(View.GONE);
            tvRefundType.setText("申请退款");
        }else if(bean.getContent().getReturnOrderStatus() == NO3){
            llRefuseReason.setVisibility(View.GONE);
            tvRefundType.setText("已确认退款");
        }else if(bean.getContent().getReturnOrderStatus() == NO5){
            tvRefundType.setText("已拒绝退款");
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

    @Override
    public void setListener() {
        //联系团队
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(groupBean.getNickName());
                intentChatEntity.setAcceptId(groupBean.getUuid());
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(RefundAfterActivity.this, intentChatEntity);
            }
        });
    }

}
