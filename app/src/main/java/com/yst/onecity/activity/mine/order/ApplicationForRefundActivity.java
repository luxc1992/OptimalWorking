package com.yst.onecity.activity.mine.order;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.bean.order.ReasonBean;
import com.yst.onecity.bean.order.ReturnReasonContent;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.dialog.AbstractReturnReasonDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请退款
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/19
 */
public class ApplicationForRefundActivity extends BaseActivity {

    @BindView(R.id.application_for_refund_iv_service_img)
    ImageView ivServiceImg;
    @BindView(R.id.application_for_refund_tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.application_for_refund_tv_refund_price)
    TextView tvRefundPrice;
    @BindView(R.id.application_for_refund_tv_service_number)
    TextView tvServiceNumber;
    @BindView(R.id.application_for_refund_tv_refund_explain)
    TextView tvRefundExplain;
    @BindView(R.id.application_for_refund_tv_refund_explain_optional)
    TextView tvRefundExplainOptional;

    private List<ReasonBean> reasonData = new ArrayList<>();
    private String refundReasonId;
    private String orderId;
    private int from;
    private String num;
    /**
     * 当前退款数
     */
    private int currentNum;
    private String orderStatus;
    private OrderGroupBean groupBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_application_for_refund;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.application_for_refund));
        setRightText(getString(R.string.contact_teams));
        setRightTextViewClickable(true);
        Intent intent = getIntent();
        groupBean = (OrderGroupBean) intent.getSerializableExtra("groupBean");
        orderId = groupBean.getId();
        orderStatus = Integer.toString(groupBean.getStatus());
        from = intent.getIntExtra("from",0);
        setData(groupBean);
        //获取退款原因列表
        getAfterSalesReasonList();
    }

    /**
     * 设置数据
     */
    public void setData(OrderGroupBean groupBean){
        Glide.with(context).load(groupBean.getSon().get(0).getAddress()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(ivServiceImg);

        tvServiceName.setText(groupBean.getSon().get(0).getName());
        tvRefundPrice.setText("￥" + groupBean.getSon().get(0).getSPrice());
        num = Integer.toString(groupBean.getSon().get(0).getNum());
        tvServiceNumber.setText(num);
    }


    @OnClick({R.id.application_for_refund_tv_minus, R.id.application_for_refund_tv_plus, R.id.application_for_refund_tv_refund_explain
    , R.id.application_for_refund_tv_commit})
    public void onClick(View view){
        switch (view.getId()){
            //数量减
            case R.id.application_for_refund_tv_minus:
                int n = Integer.parseInt(tvServiceNumber.getText().toString());
                if(n > 1){
                    currentNum = --n;
                    tvServiceNumber.setText(currentNum);
                }
                break;
            //数量加
            case R.id.application_for_refund_tv_plus:
                int m = 0;
                m = Integer.parseInt(tvServiceNumber.getText().toString());
                if(m < Integer.parseInt(num)){
                    currentNum = ++m;
                    tvServiceNumber.setText(currentNum);
                }
                break;
            //选择退款原因
            case R.id.application_for_refund_tv_refund_explain:
                if (!Utils.isClickable()) {
                    return;
                }
                initRefundReasonDialog();
                break;
            //提交
            case  R.id.application_for_refund_tv_commit:
                refund();
                break;
            default:
                break;
        }
    }

    /**
     * 退款原因弹窗
     */
    private void initRefundReasonDialog() {
        AbstractReturnReasonDialog returnReasonDialog = new AbstractReturnReasonDialog(this, reasonData) {
            @Override
            public void onItemClickListener(List<ReasonBean> mReasonData, String reason, String mReturnReasonId) {
                reasonData = mReasonData;
                refundReasonId = mReturnReasonId;
                tvRefundExplain.setText(reason);
            }
        };
        returnReasonDialog.showDialog();
    }

    /**
     * 提交退货或退款申请
     */
    private void refund() {
        String reason = tvRefundExplain.getText().toString().trim();
        if (TextUtils.isEmpty(reason)) {
            ToastUtils.show("请选择退款原因");
            return;
        }
        String optional = tvRefundExplainOptional.getText().toString().trim();
        RequestApi.applicationForRefund(App.manager.getUuid(), App.manager.getPhoneNum(), App.manager.getId()
                ,orderId, refundReasonId, optional, orderStatus,"1", new AbstractNetWorkCallback<MsgBean>() {
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
                        ToastUtils.show(msgBean.getMsg());
                        if (msgBean.getCode() == 1) {
                            EventBus.getDefault().post(new EventBean("MyServiceOrderFragment"));
                            ApplicationForRefundActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });

    }

    /**
     * 获取售后原因列表
     */
    private void getAfterSalesReasonList() {
        RequestApi.getAfterSalesReasonList(App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<ReturnReasonContent>() {
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
            public void onSuccess(ReturnReasonContent bean) {
                if (bean.getCode() == 1) {
                    if (bean.getContent() != null) {
                        reasonData = bean.getContent();
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
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
                ChatScreenActivity.getJumpChatSource(ApplicationForRefundActivity.this, intentChatEntity);
            }
        });
    }
}
