package com.yst.onecity.activity.mine.order;

import android.os.Bundle;
import android.view.View;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.mine.cardorder.CardUnionOrderActivity;

import butterknife.OnClick;

/**
 * 我的-订单(买家)
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/17
 */
public class MyOrderActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.my_order));
    }

    @OnClick({ R.id.my_order_tv_service_all, R.id.my_order_ll_wait_pay, R.id.my_order_wait_use, R.id.my_order_ll_wait_evaluate
            , R.id.my_order_ll_exchange, R.id.my_order_tv_goods_all, R.id.ll_my_pay, R.id.ll_my_drliver, R.id.ll_my_take
            , R.id.ll_my_appraise, R.id.ll_my_after,R.id.my_order_rl_card})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //服务订单:查看全部订单
            case R.id.my_order_tv_service_all:
                bundle.putInt("flag",0);
                bundle.putInt("from",0);
                JumpIntent.jump(MyOrderActivity.this, MyServiceOrderActivity.class,bundle);
                break;
            //服务订单:待付款
            case R.id.my_order_ll_wait_pay:
                bundle.putInt("flag", 1);
                bundle.putInt("from",0);
                JumpIntent.jump(MyOrderActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //服务订单:待使用
            case R.id.my_order_wait_use:
                bundle.putInt("flag", 2);
                bundle.putInt("from",0);
                JumpIntent.jump(MyOrderActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //服务订单:待评价
            case R.id.my_order_ll_wait_evaluate:
                bundle.putInt("flag", 3);
                bundle.putInt("from",0);
                JumpIntent.jump(MyOrderActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //服务订单:退货/售后
            case R.id.my_order_ll_exchange:
//                Constant.userType = 1;
                bundle.putInt("flag", 4);
                bundle.putInt("from",0);
                JumpIntent.jump(MyOrderActivity.this, MyServiceOrderActivity.class, bundle);
                break;

            //商品订单:查看全部订单
            case R.id.my_order_tv_goods_all:
                bundle.putString("status", "8");
                bundle.putString("url", H5Const.BUYER_GOODS_LIST);
                JumpIntent.jump(MyOrderActivity.this, ServerTeamPageDetailActivity.class,bundle);
                break;
            //商品订单:待付款
            case R.id.ll_my_pay:
                bundle.putString("status", "0");
                bundle.putString("url", H5Const.BUYER_GOODS_LIST);
                JumpIntent.jump(MyOrderActivity.this, ServerTeamPageDetailActivity.class,bundle);
                break;
            //商品订单:待发货
            case R.id.ll_my_drliver:
                bundle.putString("status", "2");
                bundle.putString("url", H5Const.BUYER_GOODS_LIST);
                JumpIntent.jump(MyOrderActivity.this, ServerTeamPageDetailActivity.class,bundle);
                break;
            //商品订单:待收货
            case R.id.ll_my_take:
                bundle.putString("status", "3");
                bundle.putString("url", H5Const.BUYER_GOODS_LIST);
                JumpIntent.jump(MyOrderActivity.this, ServerTeamPageDetailActivity.class,bundle);
                break;
            //商品订单:待评价
            case R.id.ll_my_appraise:
                bundle.putString("status", "7");
                bundle.putString("url", H5Const.BUYER_GOODS_LIST);
                JumpIntent.jump(MyOrderActivity.this, ServerTeamPageDetailActivity.class,bundle);
                break;
            //商品订单:退款/售后
            case R.id.ll_my_after:
                Constant.userType = 0;
                JumpIntent.jump(MyOrderActivity.this, AfterSalesListActivity.class);
                break;
            case R.id.my_order_rl_card:
                JumpIntent.jump(MyOrderActivity.this, CardUnionOrderActivity.class);
                break;
            default:
                break;
        }
    }
}
