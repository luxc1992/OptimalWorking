package com.yst.onecity.wxapi;

import android.annotation.SuppressLint;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信支付回调
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/03/07
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @Override
    public int getLayoutId() {
        return R.layout.activity_wxpay_entry;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        IWXAPI api = WXAPIFactory.createWXAPI(this, "wxe3b31c57adf0bde3");
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int fail = -1;
        int cancel = -2;
        if (baseResp.errCode == 0) {
            EventBus.getDefault().post(new EventBean("paySuccess"));
            WXPayEntryActivity.this.finish();
            ToastUtils.show("支付成功");
        } else if (baseResp.errCode == fail) {
            ToastUtils.show("支付失败");
            WXPayEntryActivity.this.finish();
        } else if (baseResp.errCode == cancel) {
            ToastUtils.show("支付取消");
            WXPayEntryActivity.this.finish();
        }
    }

}
