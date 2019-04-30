package com.yst.onecity.activity.mine.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.order.EvaluateListBean;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单评价选择商品
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/07
 */
public class CheckEvaluateListActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    private List<OrderChildBean> data = new ArrayList<>();
    private AbstractCommonAdapter<OrderChildBean> adapter;
    private Bundle mBundle;
    private H5OrderBean orderBean;
    private String orderNo;
    private String userId;
    private String userName;
    private String userUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate_choose_product;
    }

    @Override
    public void initData() {
        initTitleBar("评价商品");
        EventBus.getDefault().register(this);
        if (Constant.isMember) {
            setRightText("联系团队");
        }
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            orderNo = getIntent().getExtras().getString("orderNo");
            orderBean = (H5OrderBean) getIntent().getExtras().getSerializable("bean");
            if (Constant.isMember) {
                userId = orderBean.getUserId();
                userName = orderBean.getNickName();
                userUrl = orderBean.getImg();
            }
        }
        getEvaluateList(orderNo);
    }

    /**
     * 获取评价列表接口
     */
    private void getEvaluateList(String orderNo) {
        RequestApi.getEvaluateList(App.manager.getUuid(), App.manager.getPhoneNum(), orderNo, new AbstractNetWorkCallback<EvaluateListBean>() {
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
            public void onSuccess(EvaluateListBean bean) {
                MyLog.e("sss", bean.toString());
                if (bean.getCode() == 1) {
                    if (bean.getContent() != null) {
                        adapter.onRefresh(bean.getContent());
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    @Override
    public void initCtrl() {
        adapter = new AbstractCommonAdapter<OrderChildBean>(context, data, R.layout.item_evaluate_product) {
            @Override
            public void convert(CommonViewHolder holder, int position, final OrderChildBean item) {
                LinearLayout llEvaluate = holder.getView(R.id.ll_evaluate_state);
                ImageView ivEvaluateState = holder.getView(R.id.iv_evaluate_state);
                final TextView tvEvaluateState = holder.getView(R.id.tv_evaluate_state);
                Glide.with(context).load(item.getpImg()).error(R.mipmap.moren).into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_product_name, ConstUtils.getStringNoEmpty(item.getName()));
                holder.setText(R.id.tv_order_time, item.getCreatedTime() + "下单");
                if (Constant.isMember) {
                    if (item.getStatus() == Constant.NO4) {
                        tvEvaluateState.setText("查看评价");
                        ivEvaluateState.setImageResource(R.mipmap.chakan);
                        tvEvaluateState.setTextColor(0xFF666666);
                        llEvaluate.setBackgroundResource(R.drawable.shape_text_bg);
                    } else {
                        tvEvaluateState.setText("去评价");
                        ivEvaluateState.setImageResource(R.mipmap.evalute_red);
                        tvEvaluateState.setTextColor(0xFFED5452);
                        llEvaluate.setBackgroundResource(R.drawable.shape_text_bg_red);
                    }
                    llEvaluate.setEnabled(true);
                } else {
                    //statues=4已评价，7未评价
                    if (item.getStatus() == Constant.NO4) {
                        tvEvaluateState.setText("查看评价");
                        ivEvaluateState.setImageResource(R.mipmap.chakan);
                        llEvaluate.setEnabled(true);
                    } else {
                        tvEvaluateState.setText("未评价");
                        ivEvaluateState.setImageResource(R.mipmap.weipingjia);
                        llEvaluate.setEnabled(false);
                    }
                }
                llEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", item);
                        if (item.getStatus() == Constant.NO7) {
                            if (Constant.isMember) {
                                bundle.putString("userId", userId);
                                bundle.putString("userName", userName);
                                bundle.putString("userUrl", userUrl);
                                JumpIntent.jump(CheckEvaluateListActivity.this, EvaluateProductActivity.class, bundle);
                            }
                        } else {
                            JumpIntent.jump(CheckEvaluateListActivity.this, CheckEvaluteActivity.class, bundle);
                        }
                    }
                });
            }
        };
        listView.setAdapter(adapter);
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (event.getFlag() == Constant.NO1) {
            getEvaluateList(orderBean.getOrderNo());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        IntentChatEntity intentChatEntity = new IntentChatEntity();
        intentChatEntity.setAcceptName(orderBean.getNickName());
        intentChatEntity.setAcceptId(orderBean.getUserId());
        intentChatEntity.setChatType(ChatType.C2C);
        ChatScreenActivity.getJumpChatSource(CheckEvaluateListActivity.this, intentChatEntity);
    }
}
