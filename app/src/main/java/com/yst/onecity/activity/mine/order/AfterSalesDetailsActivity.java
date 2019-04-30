package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ShowImageAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.AfterSalesDetailBean;
import com.yst.onecity.bean.order.AfterSalesDetailContentBean;
import com.yst.onecity.bean.order.AfterSalesProductBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.MyGridView;
import com.yst.onecity.view.MyListView;
import com.yst.onecity.view.dialog.AbstractAddressDialog;
import com.yst.onecity.view.dialog.AbstractRefuseDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 售后详情
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/24
 */
public class AfterSalesDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_return_money)
    ImageView ivReturnMoney;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_return_state)
    TextView tvReturnState;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_return_reason)
    TextView tvReturnReason;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.griView)
    MyGridView gridView;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_disagree)
    TextView tvDisagree;
    @BindView(R.id.tv_refuse_reason)
    TextView tvRefuseReason;
    @BindView(R.id.tv_receive_name)
    TextView tvReceiveName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_return_info)
    LinearLayout llReturnInfo;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.ll_return_reason)
    LinearLayout llReturnReason;
    @BindView(R.id.tv_return_money)
    TextView tvReturnMoney;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.ll_imgs)
    LinearLayout llImgs;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private List<AfterSalesProductBean.DetailBean> data = new ArrayList<>();
    private Spinner mSp1;
    private Spinner mSp2;
    private Spinner mSp3;
    private AbstractRefuseDialog refuseDialog;
    private int orderStatus;
    private String orderNums;
    /**
     * 退货订单id
     */
    private String returnId;

    /**
     * 猎头/客服头像
     */
    private String headUrl;
    /**
     * 猎头/客服的唯一标识
     */
    private String userId;
    /**
     * 猎头/客服昵称
     */
    private String nickName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_after_sales_details;
    }

    @Override
    public void initData() {
        initTitleBar("退款/售后");
        initDialog();
        if (getIntent().getExtras() != null) {
            AfterSalesProductBean groupBean = (AfterSalesProductBean) getIntent().getExtras().getSerializable("groupBean");
            if (groupBean != null) {
                nickName = groupBean.getNickName();
                headUrl = groupBean.getMerchantAddress();
                userId = groupBean.getUuid();
                data = groupBean.getDetail();
                if (Constant.userType == 0) {
                    setRightText("联系团队");
                    llBottom.setVisibility(View.GONE);
                } else {
                    llReturnInfo.setVisibility(View.GONE);
                }
                returnId = String.valueOf(groupBean.getReturnId());
                getAfterSalesDetail(returnId);
            }
        }
    }

    /**
     * 查看售后详情
     */
    private void getAfterSalesDetail(String returnOrderId) {
        RequestApi.afterSalesDetail(returnOrderId, new AbstractNetWorkCallback<AfterSalesDetailContentBean>() {
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
            public void onSuccess(AfterSalesDetailContentBean bean) {
                if (bean.getCode() == 1) {
                    AfterSalesDetailBean detailBean = bean.getContent();
                    orderNums = detailBean.getOrderNo();
                    int returnOrderType = detailBean.getReturnOrderType();
                    //0退货退款 1仅退款
                    if (returnOrderType == 0) {
                        tvTitle.setText("退款退货商品");
                    } else {
                        tvTitle.setText("退货商品");
                    }
                    //申请状态 0申请退货退款中 1 申请退款中 2已确认退货退款 3已确认退款 4已拒绝退货退款 5已拒绝退款 6 同意退货
                    orderStatus = detailBean.getReturnOrderStatus();
                    tvReturnState.setText(ConstUtils.getReturnStatus(detailBean.getReturnOrderStatus()));
                    if (orderStatus == Constant.NO4 || orderStatus == Constant.NO5) {
                        line1.setVisibility(View.VISIBLE);
                        llReturnReason.setVisibility(View.VISIBLE);
                        tvRefuseReason.setText(ConstUtils.getStringNoEmpty(detailBean.getRefuseReason()));
                    }
                    if (Constant.userType == 1) {
                        if (orderStatus == Constant.NO0) {
                            tvAgree.setText("确认退货");
                            tvDisagree.setText("拒绝退货");
                        } else if (orderStatus == Constant.NO1 || orderStatus == Constant.NO6) {
                            tvAgree.setText("确认退款");
                            tvDisagree.setText("拒绝退款");
                        } else {
                            llBottom.setVisibility(View.GONE);
                        }
                    } else {
                        if (orderStatus == Constant.NO2 || orderStatus == Constant.NO6) {
                            llReturnInfo.setVisibility(View.VISIBLE);
                            tvReceiveName.setText("收货人姓名：" + ConstUtils.getStringNoEmpty(detailBean.getReceiveName()));
                            tvAddress.setText("所在区域：" + ConstUtils.getStringNoEmpty(detailBean.getReceiveAddress()));
                            tvPhone.setText("联系电话：" + ConstUtils.getStringNoEmpty(detailBean.getReceivePhone()));
                        } else {
                            llReturnInfo.setVisibility(View.GONE);
                        }
                    }
                    tvOrderState.setText(detailBean.getOrderStatus() == 0 ? "订单已完成" : "订单未完成");
                    tvReturnReason.setText(ConstUtils.getStringNoEmpty(detailBean.getReturnReason()));
                    tvCount.setText(String.valueOf(detailBean.getNum()));
                    tvReturnMoney.setText("¥" + detailBean.getReturnPrice());
                    tvRemark.setText(ConstUtils.getStringNoEmpty(detailBean.getRemark()));
                    if (detailBean.getAttachment() != null) {
                        if (detailBean.getAttachment().size() == 0) {
                            llImgs.setVisibility(View.GONE);
                        } else {
                            gridView.setAdapter(new ShowImageAdapter(detailBean.getAttachment(), AfterSalesDetailsActivity.this));
                        }
                    }
                } else {
                    scrollView.setVisibility(View.GONE);
                    llBottom.setVisibility(View.GONE);
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                scrollView.setVisibility(View.GONE);
                llBottom.setVisibility(View.GONE);
                ToastUtils.show(errorMsg);
            }
        });
    }

    private void initDialog() {
        refuseDialog = new AbstractRefuseDialog(this) {
            @Override
            public void sureClick(String reason) {
                // 拒绝退款、拒绝退货
                refuseReturnMoney(reason);
            }
        };
    }

    @Override
    public void initCtrl() {
        AbstractCommonAdapter<AfterSalesProductBean.DetailBean> adapter = new AbstractCommonAdapter<AfterSalesProductBean.DetailBean>(context, data, R.layout.item_order_product) {
            @Override
            public void convert(CommonViewHolder holder, int position, AfterSalesProductBean.DetailBean item) {
                LinearLayout llItemProduct = holder.getView(R.id.ll_item_product);
                llItemProduct.setBackgroundColor(Color.WHITE);
                holder.setVisiable(R.id.tv_count, View.GONE);
                Glide.with(context).load(item.getProductAddress()).error(R.mipmap.moren).into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_product_name, ConstUtils.getStringNoEmpty(item.getProductName()));
            }
        };
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_agree, R.id.tv_disagree, R.id.tv_right})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_agree:
                // 1、6确认退款，0确认退货
                if (orderStatus == Constant.NO1 || orderStatus == Constant.NO6) {
                    if (orderNums != null) {
                        agreeReturnMoney();
                    }
                } else if (orderStatus == Constant.NO0) {
                    showAddressDialog();
                }
                break;
            case R.id.tv_disagree:
                refuseDialog.showDialog();
                break;
            case R.id.tv_right:
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(nickName);
                intentChatEntity.setAcceptId(userId);
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(AfterSalesDetailsActivity.this, intentChatEntity);
                break;
            default:
                break;
        }
    }

    /**
     * 同意退款
     */
    private void agreeReturnMoney() {
        RequestApi.agreeReturnMoney(App.manager.getPhoneNum(), App.manager.getUuid(), orderNums, new AbstractNetWorkCallback<MsgBean>() {
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
                    EventBus.getDefault().post(new EventBean(Constant.NO1));
                    AfterSalesDetailsActivity.this.finish();
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
     * 拒绝退款
     */
    private void refuseReturnMoney(String reason) {
        RequestApi.refuseReturnMoney(App.manager.getPhoneNum(), App.manager.getUuid(), returnId, reason, 1, new AbstractNetWorkCallback<MsgBean>() {
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
                    EventBus.getDefault().post(new EventBean(Constant.NO1));
                    AfterSalesDetailsActivity.this.finish();
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
     * 收货人地址弹框
     */
    private void showAddressDialog() {
        AbstractAddressDialog dialog = new AbstractAddressDialog(this) {
            @Override
            public void sureClick(String name, String detail, String phone) {
                //获取spinner中的省市区
                mSp1 = dialog.findViewById(R.id.sp1_dialog_address);
                mSp2 = dialog.findViewById(R.id.sp2_dialog_address);
                mSp3 = dialog.findViewById(R.id.sp3_dialog_address);
                String sp1 = mSp1.getSelectedItem().toString();
                String sp2 = mSp2.getSelectedItem().toString();
                String sp3 = mSp3.getSelectedItem().toString();
                confirmReturnGoods(name, sp1 + sp2 + sp3 + detail, phone);
            }
        };
        dialog.showDialog();
    }

    /**
     * 同意退货
     *
     * @param receiveName    收货人
     * @param receiveAddress 收货地址
     * @param receivePhone   联系方式
     */
    private void confirmReturnGoods(String receiveName, String receiveAddress, String receivePhone) {
        if (receivePhone.length() != Constant.NO11) {
            ToastUtils.show("请输入正确的联系电话");
            return;
        }
        RequestApi.agreeReturnGoods(App.manager.getPhoneNum(), App.manager.getUuid(), returnId, receiveAddress, receiveName, receivePhone, new AbstractNetWorkCallback<MsgBean>() {
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
                    EventBus.getDefault().post(new EventBean(Constant.NO1));
                    AfterSalesDetailsActivity.this.finish();
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
