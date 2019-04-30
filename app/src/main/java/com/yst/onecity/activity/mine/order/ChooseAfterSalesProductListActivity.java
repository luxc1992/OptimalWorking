package com.yst.onecity.activity.mine.order;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.yst.onecity.bean.order.ChooseAfterContentBean;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择申请售后的商品
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/26
 */
public class ChooseAfterSalesProductListActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    private List<OrderChildBean> data = new ArrayList<>();
    private AbstractCommonAdapter<OrderChildBean> adapter;
    private H5OrderBean h5OrderBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_after_sales_product;
    }

    @Override
    public void initData() {
        initTitleBar("选择商品");
        EventBus.getDefault().register(this);
        if (getIntent().getExtras() != null) {
            h5OrderBean = (H5OrderBean) getIntent().getExtras().getSerializable("bean");
            if (h5OrderBean != null) {
                getProductList(h5OrderBean.getOrderNo());
            }
        }
    }

    /**
     * 获取申请售后商品列表
     */
    private void getProductList(String orderNo) {
        RequestApi.getApplyAfterSalesList(App.manager.getPhoneNum(), App.manager.getUuid(), orderNo, new AbstractNetWorkCallback<ChooseAfterContentBean>() {

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
            public void onSuccess(ChooseAfterContentBean bean) {
                if (bean.getCode() == 1) {
                    if (bean.getContent() != null) {
                        data = bean.getContent();
                        adapter.onRefresh(data);
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void initCtrl() {
        adapter = new AbstractCommonAdapter<OrderChildBean>(context, data, R.layout.item_evaluate_product) {
            @Override
            public void convert(CommonViewHolder holder, final int position, OrderChildBean item) {
                holder.setLinearLayoutVisiable(R.id.ll_evaluate_state, View.GONE);
                Glide.with(context).load(item.getpImg()).error(R.mipmap.moren).into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_product_name, ConstUtils.getStringNoEmpty(item.getName()));
                holder.setText(R.id.tv_order_time, ConstUtils.getStringNoEmpty(item.getCreatedTime()));
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //afterSale 0没有售后过 1售后过
                if (data.get(i).getAfterSale() == Constant.NO0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data.get(i));
                    bundle.putSerializable("bean", h5OrderBean);
                    JumpIntent.jump(ChooseAfterSalesProductListActivity.this, ChooseAfterSalesTypeActivity.class, bundle);
                } else {
                    ToastUtils.show("该商品已申请过售后");
                }
            }
        });
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        IntentChatEntity intentChatEntity = new IntentChatEntity();
        intentChatEntity.setAcceptName(h5OrderBean.getNickName());
        intentChatEntity.setAcceptId(h5OrderBean.getUserId());
        intentChatEntity.setChatType(ChatType.C2C);
        ChatScreenActivity.getJumpChatSource(this, intentChatEntity);

    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (event.getFlag() == Constant.NO1) {
            ChooseAfterSalesProductListActivity.this.finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
