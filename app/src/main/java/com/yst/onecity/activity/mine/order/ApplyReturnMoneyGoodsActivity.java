package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ChooseImageAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ImageBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.ReasonBean;
import com.yst.onecity.bean.order.ReturnReasonContent;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.AddImageOrVideo;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.dialog.AbstractOrderStateDialog;
import com.yst.onecity.view.dialog.AbstractReturnReasonDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.yst.onecity.Constant.NO4;

/**
 * 申请退款/申请退货退款
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/08
 */
public class ApplyReturnMoneyGoodsActivity extends BaseActivity implements AddImageOrVideo.UploadImgListener {

    private static final int REQUEST_IMAGE = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.ll_order_state)
    LinearLayout llOrderState;
    @BindView(R.id.tv_return_reason)
    TextView tvReturnReason;
    @BindView(R.id.ll_return_reason)
    LinearLayout llReturnReason;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.griView)
    GridView griView;
    @BindView(R.id.iv_return_money)
    ImageView ivReturnMoney;
    @BindView(R.id.tv_refuse_money)
    TextView tvRefuseMoney;
    @BindView(R.id.tv_reduce)
    TextView tvReduce;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.ll_item_product)
    LinearLayout llItemProduct;
    @BindView(R.id.et_remark)
    ContainsEmojiEditText etRemark;
    @BindView(R.id.tv_product_count)
    TextView tvProductCount;
    private int count = 1;
    private List<ImageBean> imgData = new ArrayList<>();
    private ChooseImageAdapter imageAdapter;
    private OrderChildBean childBean;
    private AbstractOrderStateDialog orderStateDialog;
    private List<ReasonBean> reasonData = new ArrayList<>();
    /**
     * 0退货退款 1仅退款
     */
    private int returnOrderType;
    /**
     * 选择订单状态 0 已完成 1 未完成
     */
    private int orderStatus;
    private String returnReasonId;
    private H5OrderBean h5OrderBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_return_money_goods;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (getIntent().getExtras() != null) {
            h5OrderBean = (H5OrderBean) getIntent().getExtras().getSerializable("h5OrderBean");
            returnOrderType = getIntent().getExtras().getInt("returnOrderType");
            if (returnOrderType == Constant.NO1) {
                initTitleBar("申请退款");
                tvTitle.setText("退货商品");
            } else {
                initTitleBar("申请退款退货");
                tvTitle.setText("退货退款商品");
            }
            setRightText("联系团队");
        }
        etRemark.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        llItemProduct.setBackgroundColor(Color.WHITE);
        tvCount.setVisibility(View.GONE);
        setGridViewAdapter();
        initDialog();
        if (getIntent().getExtras() != null) {
            childBean = (OrderChildBean) getIntent().getExtras().getSerializable("bean");
            if (childBean != null) {
                Glide.with(context).load(childBean.getpImg()).error(R.mipmap.h_moren).into(ivProduct);
                tvProductName.setText(ConstUtils.getStringNoEmpty(childBean.getName()));
                count = childBean.getpNum();
                String money = new DecimalFormat("0.00").format(Double.parseDouble(childBean.getSalePrice()) * count);
                tvRefuseMoney.setText("¥" + money);
                tvProductCount.setText(String.valueOf(count));
            }
        }
        getAfterSalesReasonList();
    }

    private void initDialog() {
        orderStateDialog = new AbstractOrderStateDialog(this) {
            @Override
            public void noFinishClick() {
                tvOrderState.setText("订单未完成");
                orderStatus = 1;
            }

            @Override
            public void finishClick() {
                orderStatus = 0;
                tvOrderState.setText("订单已完成");
            }
        };
    }

    /**
     * 添加图片GridView
     */
    private void setGridViewAdapter() {
        imageAdapter = new ChooseImageAdapter(imgData, context, 3);
        griView.setAdapter(imageAdapter);
        griView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imgData.size() < NO4) {
                    ConstUtils.selectImg(ApplyReturnMoneyGoodsActivity.this, 3, imgData.size(), REQUEST_IMAGE);
                }
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

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.tv_right, R.id.ll_order_state, R.id.ll_return_reason, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(h5OrderBean.getNickName());
                intentChatEntity.setAcceptId(h5OrderBean.getUserId());
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(ApplyReturnMoneyGoodsActivity.this, intentChatEntity);
                break;
            case R.id.ll_order_state:
                orderStateDialog.showDialog();
                break;
            case R.id.ll_return_reason:
                if (!Utils.isClickable()) {
                    return;
                }
                initReturnReasonDialog();
                break;
            case R.id.tv_reduce:
                if (count > 1) {
                    count--;
                    tvProductCount.setText(String.valueOf(count));
                    tvRefuseMoney.setText("¥" + Double.parseDouble(childBean.getSalePrice()) * count);
                } else {
                    ToastUtils.show("不能再少啦");
                }
                break;
            case R.id.tv_add:
                if (count < childBean.getpNum()) {
                    count++;
                    tvProductCount.setText(String.valueOf(count));
                    tvRefuseMoney.setText("¥" + Double.parseDouble(childBean.getSalePrice()) * count);
                } else {
                    ToastUtils.show("最多只能选" + childBean.getpNum() + "个");
                }
                break;
            case R.id.tv_commit:
                if (!Utils.isClickable()) {
                    return;
                }
                commit();
                break;
            default:
                break;
        }
    }

    private void initReturnReasonDialog() {
        AbstractReturnReasonDialog returnReasonDialog = new AbstractReturnReasonDialog(this, reasonData) {
            @Override
            public void onItemClickListener(List<ReasonBean> mReasonData, String reason, String mReturnReasonId) {
                reasonData = mReasonData;
                returnReasonId = mReturnReasonId;
                tvReturnReason.setText(reason);
            }
        };
        returnReasonDialog.showDialog();
    }

    /**
     * 提交退货或退款申请
     */
    private void commit() {
        String orderState = tvOrderState.getText().toString().trim();
        String returnReason = tvReturnReason.getText().toString().trim();
        if (TextUtils.isEmpty(orderState)) {
            ToastUtils.show("请选择订单状态");
            return;
        }
        if (TextUtils.isEmpty(returnReason)) {
            ToastUtils.show("请选择退货原因");
            return;
        }
        String remark = TextUtils.isEmpty(etRemark.getText().toString().trim()) ? " " : etRemark.getText().toString().trim();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < imgData.size(); i++) {
            str.append(imgData.get(i).getAddress() + ",");
        }
        String address = "";
        if (imgData.size() != 0) {
            address = str.substring(0, str.length() - 1);
        }

        RequestApi.applyAfterSales(App.manager.getPhoneNum(), App.manager.getUuid(), childBean.getoId(), returnOrderType, returnReasonId,
                remark, address, orderStatus, count, new AbstractNetWorkCallback<MsgBean>() {
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
                            JumpIntent.jump(ApplyReturnMoneyGoodsActivity.this, AfterSalesListActivity.class);
                            ApplyReturnMoneyGoodsActivity.this.finish();
                        }
                        ToastUtils.show(msgBean.getMsg());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_IMAGE:
                ArrayList<String> urlList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int i = 0; i < urlList.size(); i++) {
                    AddImageOrVideo.upLoadImg(this, urlList.get(i), ApplyReturnMoneyGoodsActivity.this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void uploadImgListener(String msg) {
        imgData.add(new ImageBean(msg));
        imageAdapter.notifyDataSetChanged();
    }
}
