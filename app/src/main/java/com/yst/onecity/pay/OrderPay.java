package com.yst.onecity.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yst.onecity.utils.ContactUtils;
import com.yst.onecity.utils.MyLog;

import java.util.Map;

/**
 * 三方支付
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/3/6
 */
public class OrderPay {
    private static final int SDK_PAY_FLAG = 1;
    private IPayResult iPayResult;
    private String wechatId = "wxe3b31c57adf0bde3";

    /**
     * 微信支付
     */
    public void pay(Context context, OrderBean orderBean) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, wechatId);

        api.registerApp(wechatId);
        PayReq req = new PayReq();
        req.appId = orderBean.getAppid();
        req.partnerId = orderBean.getPartid();
        req.prepayId = orderBean.getPrepayid();
        req.nonceStr = orderBean.getNoncestr();
        req.timeStamp = orderBean.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = orderBean.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    /**
     * 支付宝支付
     */
    public void pay(final String orderInfo, final Activity activity, IPayResult iPayResult) {
        this.iPayResult = iPayResult;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        ContactUtils.Executorsa.S_THREAD_FACTORY.newThread(payRunnable).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    // 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    MyLog.e("resultStatus", "resultStatus:" + resultStatus + "==" + payResult.getMemo());
                    // 判断resultStatus 为9000则代表支付成功
                    if (iPayResult != null) {
                        iPayResult.result(TextUtils.equals(resultStatus, "9000"));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public interface IPayResult {
        /**
         * 支付结果
         *
         * @param success 支付结果
         */
        void result(boolean success);
    }
}
