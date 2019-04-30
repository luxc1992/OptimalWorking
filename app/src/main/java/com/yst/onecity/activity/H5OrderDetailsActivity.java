package com.yst.onecity.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.order.CheckEvaluateListActivity;
import com.yst.onecity.activity.mine.order.ChooseAfterSalesProductListActivity;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.MyLog;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;

/**
 * H5订单详情页面
 * 
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/4/11
 */

public class H5OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_h5oderdetails;
    }
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initData() {

        String orderNo = getIntent().getStringExtra("orderNo");
        int type = Integer.valueOf(getIntent().getStringExtra("type"));
        if (type == NO1) {
            CommonUtils.setWebSettings(H5OrderDetailsActivity.this, webView, H5Const.GOODS_DDFK + "?orderNo=" + orderNo);
        } else if (type == NO2) {
            CommonUtils.setWebSettings(H5OrderDetailsActivity.this, webView, H5Const.GOODS_DDFH + "?orderNo=" + orderNo);
        } else if (type == NO3) {
            CommonUtils.setWebSettings(H5OrderDetailsActivity.this, webView, H5Const.GOODS_DDSH + "?orderNo=" + orderNo);
        } else if (type == NO4) {
            CommonUtils.setWebSettings(H5OrderDetailsActivity.this, webView, H5Const.GOODS_JYWC + "?orderNo=" + orderNo);
        } else if (type == NO5) {
            CommonUtils.setWebSettings(H5OrderDetailsActivity.this, webView, H5Const.GOODS_JYGB + "?orderNo=" + orderNo);
        }
        MyLog.e("tag==Url",H5Const.GOODS_DDSH + "?" + orderNo);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webView.addJavascriptInterface(new JsToJava(), "stub");
    }

    private class JsToJava {
        /**
         * 申请售后
         *
         * @author chejiianqi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientAfterSale(String jsonData) {
            //30申请售后，31评价商品, 32联系团队，33查看评价
            com.yst.basic.framework.utils.MyLog.e("jsonData", "jsonData------------" + jsonData);
            String sales = "30";
            String evaluate = "31";
            String contact = "32";
            String check = "33";
            H5OrderBean orderBean = new Gson().fromJson(jsonData, H5OrderBean.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", orderBean);
            if (orderBean.getData().equals(sales)) {
                Constant.isMember = true;
                JumpIntent.jump(H5OrderDetailsActivity.this, ChooseAfterSalesProductListActivity.class, bundle);
            } else if (orderBean.getData().equals(evaluate)) {
                Constant.isMember = true;
                bundle.putString("orderNo", orderBean.getOrderNo());
                bundle.putSerializable("bean",orderBean);
                JumpIntent.jump(H5OrderDetailsActivity.this, CheckEvaluateListActivity.class, bundle);
            } else if (orderBean.getData().equals(contact)) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(orderBean.getNickName());
                intentChatEntity.setAcceptId(orderBean.getUserId());
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(H5OrderDetailsActivity.this, intentChatEntity);
            } else if (orderBean.getData().equals(check)) {
                Constant.isMember = true;
                bundle.putString("orderNo", orderBean.getOrderNo());
                bundle.putSerializable("bean",orderBean);
                JumpIntent.jump(H5OrderDetailsActivity.this, CheckEvaluateListActivity.class, bundle);
            }
        }

        /**
         * 关闭
         *
         * @author chejiianqi
         * version 1.0.1
         */
        @JavascriptInterface
        public void goBack(){
            H5OrderDetailsActivity.this.finish();
        }
    }
}
