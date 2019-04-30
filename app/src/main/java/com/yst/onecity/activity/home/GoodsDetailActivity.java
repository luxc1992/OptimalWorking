package com.yst.onecity.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.yst.basic.framework.utils.ToastUtils;
import com.lxc.sharelibrary.ShareActivity;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.UMShareAPI;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.pay.OrderPay;
import com.yst.onecity.pay.PayResultBean;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.JsonUtil;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

import static com.yst.onecity.Constant.LOGOUT;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.PUBLISH;
import static com.yst.onecity.Constant.REFRESH;
import static com.yst.onecity.Constant.SHAREREFRESH;

/**
 * 商品详情
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/24.
 */
public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.web_home)
    WebView webGoodsDetail;
    /**
     * 商品id
     */
    private String id;
    private String userInfoJson;
    public static int payType = Constant.NO3;
    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        App.isLogin = App.manager.getLoginState();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getString("id");
            MyLog.e("//////////", id);
            EventBus.getDefault().register(this);
            jsToClientSetUserInfo();
            CommonUtils.setWebSettings(GoodsDetailActivity.this, webGoodsDetail, H5Const.GOODS_DETAIL + "?id=" + id + "&from=app");
            webGoodsDetail.addJavascriptInterface(new JsToJava(), "stub");
            //阻止出现长按复制
            webGoodsDetail.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });
        }
    }

    /**
     * js与android得交互
     */
    private class JsToJava {
        /**
         * @author chenjiadi
         * version 1.0.1
         * 去登录
         */
        @JavascriptInterface
        public void jsToClientLogin() {
            goLogin();
        }

        /**
         * @author chenjiadi
         * version 1.0.1
         * 原生返回事件
         */
        @JavascriptInterface
        public void jsToClientGoBack() {
            finish();
        }

        /**
         * @author chenjiadi
         * version 1.0.1
         * 从订单列表点击返回按钮回到个人中心页面
         */
        @JavascriptInterface
        public void goBack() {
            MyLog.e("collectionfinish", "finish-------");
            EventBus.getDefault().post(new EventBean("finish"));
            finish();
        }
        /**
         * h5获取本地登录用户信息的点击事件
         *
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public String clientToJsUserInfo() {
            return userInfoJson;
        }

        /**
         * 商品详情分享
         *
         * @param data 分享内容json
         *             author chejianqi
         *             version 1.0.1
         */
        @JavascriptInterface
        public void jsTomygetherShare(String data) {
            goProductShare(data);
        }

        /**
         * 联系团队
         *
         * @param dataObj 数据json
         *                author chejianqi
         *                version 1.0.1
         */
        @JavascriptInterface
        public void jsToMyCustomerervice(String dataObj) {
            goContactService(dataObj);
        }
        /**
         * 微信支付
         *
         * @param dataObj 数据json
         */
        @JavascriptInterface
        public void jsToWeChatpay(String dataObj) {
            String wx="com.tencent.mm";
            if (!Utils.isAvilible(context, wx)){
                ToastUtils.show("未安装微信");
                return;
            }
            payType = NO2;
            goPay(dataObj);
        }

        /**
         * 支付宝支付
         *
         * @param dataObj 数据json
         */
        @JavascriptInterface
        public void jsToAlipay(String dataObj) {
            payType = NO3;
            goPay(dataObj);
        }
    }
    /**
     * 去支付
     *
     * @param data 数据json
     */
    public void goPay(final String data) {
        com.yst.basic.framework.utils.MyLog.e("dataJson", data + "=====");
        final String orderId = "orderIds";
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String orderIds = "";
                    if (!TextUtils.isEmpty(jsonObject.getString(orderId))) {
                        orderIds = jsonObject.getString(orderId);
                    }
                    toPay(orderIds);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    /**
     * 去支付
     */
    private void toPay(String orderIds) {
        // 2微信 3 支付宝
        RequestApi.pay(App.manager.getPhoneNum(), App.manager.getUuid(), orderIds, payType, Utils.getIPAddress(this), new AbstractNetWorkCallback<PayResultBean>() {
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
            public void onSuccess(PayResultBean bean) {
                if (bean.getCode() == Constant.NO1) {
                    if (payType == Constant.NO2) {
                        new OrderPay().pay(context, bean.getContent());
                    } else if (payType == Constant.NO3) {
                        new OrderPay().pay(bean.getContent().getRequestURL(), GoodsDetailActivity.this, new OrderPay.IPayResult() {
                            @Override
                            public void result(boolean success) {
                                if (success) {
                                    // 1微信支付，3支付宝支付
                                    webGoodsDetail.evaluateJavascript("paySuccess('" + 3 + "')", new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String s) {

                                        }
                                    });
                                } else {
                                    ToastUtils.show("支付失败");
                                }
                            }
                        });
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
    /**
     * 商品详情跳转客服
     *
     * @param data json数据
     */
    private void goContactService(final String data) {
        com.yst.basic.framework.utils.MyLog.e("返回的数据", data);
        final String id = "uuid";
        final String imgUrl = "img";
        final String nc = "nc";
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String imId = "", img = "", name = "";
                if (null != data) {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if (!TextUtils.isEmpty(jsonObject.getString(id))) {
                            imId = jsonObject.getString(id);
                        }
                        if (!TextUtils.isEmpty(jsonObject.getString(imgUrl))) {
                            img = jsonObject.getString(imgUrl);
                        }
                        if (!TextUtils.isEmpty(jsonObject.getString(nc))) {
                            name = jsonObject.getString(nc);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    IntentChatEntity intentChatEntity = new IntentChatEntity();
                    intentChatEntity.setAcceptName(name);
                    intentChatEntity.setAcceptId(imId);
                    intentChatEntity.setChatType(ChatType.C2C);
                    ChatScreenActivity.getJumpChatSource(GoodsDetailActivity.this, intentChatEntity);
                }
            }
        });
    }

    /**
     * 商品详情分享
     *
     * @param data 返回的数据
     */
    private void goProductShare(final String data) {

        com.yst.basic.framework.utils.MyLog.e("返回的数据", data);
        final String mUrl = "url";
        final String mTitle = "title";
        final String mContent = "content";
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String title = "", content = "", url = "";
                if (data != null) {
                    try {
                        JSONObject object = new JSONObject(data);
                        if (!TextUtils.isEmpty(object.getString(mUrl))) {
                            if (!TextUtils.isEmpty(object.getString(mTitle))) {
                                title = object.getString(mTitle);
                            }
                            if (!TextUtils.isEmpty(object.getString(mContent))) {
                                content = object.getString(mContent);
                            }
                            if (!TextUtils.isEmpty(object.getString(mUrl))) {
                                url = object.getString(mUrl);
                            }
                        }
                        ShareActivity.getInstance().popShare(GoodsDetailActivity.this, title, content, url, R.drawable.logo_icon);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 为js设置用户信息
     *
     * @author chenjiadi
     * version 1.0.1
     */
    private void jsToClientSetUserInfo() {
        HashMap<String, Object> userInfo = new HashMap<>(16);
        if (App.isLogin) {
            userInfo.put("id", String.valueOf(App.manager.getId()));
            userInfo.put("uuid", App.manager.getUuid());
            userInfo.put("phone", App.manager.getPhoneNum());
            userInfoJson = JsonUtil.stringToJson(userInfo);

        } else {
            userInfoJson = "null";
        }
        com.yst.basic.framework.utils.MyLog.e("asasas", "userinfo--" + userInfoJson);
    }

    /**
     * 跳转未登录去登录
     *
     * @author chenjiadi
     * version 1.0.1
     */
    private void goLogin() {
        JumpIntent.jump(GoodsDetailActivity.this, LoginActivity.class);
    }
    @Subscribe
    public void onEventMainThread(EventBean event) {
        jsToClientSetUserInfo();
        com.yst.basic.framework.utils.MyLog.e("hahahaha", "main-------" + event.getMsg());
        if (REFRESH.equals(event.getMsg())) {
            //刷新h5页面
            webGoodsDetail.evaluateJavascript("reload()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (LOGOUT.equals(event.getMsg())) {
            //退出登录
            webGoodsDetail.evaluateJavascript("jsToLoginAgain()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (PUBLISH.equals(event.getMsg())) {
            //发布防爆
            webGoodsDetail.evaluateJavascript("clientToJsClick()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        }
    }

    @Subscribe
    public void onShareEventMain(com.lxc.sharelibrary.EventBean event) {
        jsToClientSetUserInfo();
        if (SHAREREFRESH.equals(event.getMsg())) {
            //刷新h5分享回调
            com.yst.basic.framework.utils.MyLog.e("hahahaha", "share_eventbus-------" + event.getMsg());
            webGoodsDetail.evaluateJavascript("clientToJsShare()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().post(new EventBean("finish"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
