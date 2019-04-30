package com.yst.onecity.activity.mine.order;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.utils.ConstUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择售后类型
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/08
 */
public class ChooseAfterSalesTypeActivity extends BaseActivity {

    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.ll_item_product)
    LinearLayout llItemProduct;
    private OrderChildBean bean;
    private H5OrderBean h5OrderBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_after_sales_type;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        initTitleBar("选择售后类型");
        EventBus.getDefault().register(this);
        llItemProduct.setBackgroundColor(Color.WHITE);
        tvProductPrice.setVisibility(View.VISIBLE);
        if (getIntent().getExtras() != null) {
            bean = (OrderChildBean) getIntent().getExtras().getSerializable("data");
            h5OrderBean= (H5OrderBean) getIntent().getExtras().getSerializable("bean");
            if (bean != null) {
                tvProductName.setText(ConstUtils.getStringNoEmpty(bean.getName()));
                tvProductPrice.setText("¥" + bean.getSalePrice());
                tvCount.setText("x" + bean.getpNum());
                Glide.with(context).load(bean.getpImg()).into(ivProduct);
            }
        }
    }

    @OnClick({R.id.ll_return_money, R.id.ll_return_goods})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        int returnOrderType;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //退款
            case R.id.ll_return_money:
                //0退货退款 1仅退款
                returnOrderType = 1;
                bundle.putInt("returnOrderType", returnOrderType);
                bundle.putSerializable("bean", bean);
                bundle.putSerializable("h5OrderBean", h5OrderBean);
                JumpIntent.jump(this, ApplyReturnMoneyGoodsActivity.class, bundle);
                break;
            //退货退款
            case R.id.ll_return_goods:
                returnOrderType = 0;
                bundle.putInt("returnOrderType", returnOrderType);
                bundle.putSerializable("bean", bean);
                bundle.putSerializable("h5OrderBean", h5OrderBean);
                JumpIntent.jump(this, ApplyReturnMoneyGoodsActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (event.getFlag() == Constant.NO1) {
            ChooseAfterSalesTypeActivity.this.finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
