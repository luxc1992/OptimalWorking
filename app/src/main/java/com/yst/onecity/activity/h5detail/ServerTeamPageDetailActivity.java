package com.yst.onecity.activity.h5detail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lxc.sharelibrary.ShareActivity;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.UMShareAPI;
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
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.activity.mine.cardbag.CardBagListActivity;
import com.yst.onecity.activity.mine.cardorder.CardUnionOrderActivity;
import com.yst.onecity.activity.mine.order.CheckEvaluateListActivity;
import com.yst.onecity.activity.mine.order.ChooseAfterSalesProductListActivity;
import com.yst.onecity.activity.mine.order.MyServiceOrderActivity;
import com.yst.onecity.activity.mine.order.MyServiceOrderDetailActivity;
import com.yst.onecity.activity.mine.setting.PersonDetailActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.HomeInfoShareBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.fragment.share.WatchReplayListActivity;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.pay.OrderPay;
import com.yst.onecity.pay.PayResultBean;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;
import com.yst.onecity.view.dialog.SendCommentDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

import static com.yst.onecity.Constant.CARDORDERID;
import static com.yst.onecity.Constant.FLUSHPAY;
import static com.yst.onecity.Constant.LOGOUT;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO11;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO20;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;
import static com.yst.onecity.Constant.NO8;
import static com.yst.onecity.Constant.REFRESH;
import static com.yst.onecity.Constant.SHAREREFRESH;
import static com.yst.onecity.Constant.STRING0;
import static com.yst.onecity.Constant.STRING1;
import static com.yst.onecity.Constant.UNDEFINED;
import static com.yst.onecity.Constant.WXPAYTYPE;
import static com.yst.onecity.utils.CommonUtils.goLogin;
import static com.yst.onecity.utils.CommonUtils.jsToClientSetUserInfo;

/**
 * 服务团队主页
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/19
 */
public class ServerTeamPageDetailActivity extends BaseActivity {

    @BindView(R.id.web_home)
    WebView webHome;
    private String userInfoJson;
    private String url;
    private String id;
    /**
     * 订单状态 0 待付款  2待发货 3待收货 4待评价  8全部订单
     */
    private String status;
    /**
     * 购物车标识
     */
    private String isCart;
    /**
     * 实名认证弹框
     */
    private AbstractDeleteDialog dialog;
    /**
     * 当前咨询id
     */
    private String consultationId;
    /**
     * 订单列表立即付款 订单id
     */
    private String orderIds;
    /**
     * 服务团队主页案例
     */
    private String isAnli;
    private String consultId;
    public static int payType = NO3;
    /**
     * 卡联盟订单id
     */
    private String cardUnionOrderId;
    /**
     * 卡联盟订单价钱
     */
    private String payMoney;
    /**
     * 卡联盟订单创建时间
     */
    private String createTime;
    /**
     * 卡联盟订单编号
     */
    private String cardUnionOrderNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_team_page_detail;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initData() {
        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString("id");
            consultId = getIntent().getExtras().getString("consultId");
            url = getIntent().getExtras().getString("url");
            status = getIntent().getExtras().getString("status");
            isCart = getIntent().getExtras().getString("isCart");
            orderIds = getIntent().getExtras().getString("orderIds");
            isAnli = getIntent().getExtras().getString("isAnli");
            cardUnionOrderId = getIntent().getExtras().getString("orderId");
            payMoney = getIntent().getExtras().getString("payMoney");
            createTime = getIntent().getExtras().getString("createdTime");
            cardUnionOrderNo = getIntent().getExtras().getString("orderNo");

        }
        EventBus.getDefault().register(this);
        App.isLogin = App.manager.getLoginState();
        userInfoJson = jsToClientSetUserInfo();
        if (!TextUtils.isEmpty(consultId)) {
            url = url + "?id=" + consultId + "&from=app";
        }
        if (!TextUtils.isEmpty(id)) {
            url = url + "?id=" + id + "&from=app";
        }
        if (!TextUtils.isEmpty(isAnli)) {
            url = url + "?id=" + id + "&from=app&isAnli=true";
        }
        if (!TextUtils.isEmpty(status)) {
            url = url + "?phone=" + App.manager.getPhoneNum() + "&uuid=" + App.manager.getUuid() + "&status=" + status;
        }
        if (!TextUtils.isEmpty(isCart)) {
            url = url + "?phone=" + App.manager.getPhoneNum() + "&uuid=" + App.manager.getUuid();
        }
        if (!TextUtils.isEmpty(orderIds)) {
            url = url + "?orderIds=" + orderIds + "&from=app";
        }
        if (!TextUtils.isEmpty(cardUnionOrderId) && !TextUtils.isEmpty(payMoney)) {
            url = url + "?t=" + createTime + "&p=" + payMoney + "&v=" + cardUnionOrderNo + "&id=" + cardUnionOrderId + "&type=2&from=app";
        }
        MyLog.e("url", "h5url--------" + url);
        CommonUtils.setWebSettings(ServerTeamPageDetailActivity.this, webHome, url);
        webHome.addJavascriptInterface(new JsToJava(), "stub");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webHome.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                showInfoProgressDialog();
                if (progress == NO100) {
                    dismissInfoProgressDialog();
                    MyLog.e("qqqqqqqqqqqqqq", "loginstate----" + App.isLogin + "======" + progress);
                    if (App.manager.getLoginState()) {
                        EventBus.getDefault().post(new EventBean("refresh"));
                    } else {
                        EventBus.getDefault().post(new EventBean("logout"));
                    }
                }
            }
        });
    }

    /**
     * js与android得交互
     */
    private class JsToJava implements SendCommentDialog.SendCommentListener {
        //服务团队主页交互

        /**
         * 返回按钮
         *
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void goBack() {
            finish();
        }

        /**
         * @author chenjiadi
         * version 1.1.0
         * 去登录
         */
        @JavascriptInterface
        public void jsToClientLogin() {
            goLogin(ServerTeamPageDetailActivity.this);
        }

        /**
         * 服务团队主页分享
         *
         * @param shareJson 分享内容json
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToClientShare(String shareJson) {
            MyLog.e("serverteamh5", "share--------" + shareJson);
            goShare(shareJson);
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
        //服务项目下单交互

        /**
         * 服务项目下单 微信支付
         *
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToWeChatpay(final String orderinfo) {
            MyLog.e("pay", "微信支付------" + orderinfo);
            String wx = "com.tencent.mm";
            if (!Utils.isAvilible(context, wx)) {
                ToastUtils.show("未安装微信");
                return;
            }
            payType = NO2;

            ServerTeamPageDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goPay(orderinfo);

                }
            });

        }

        /**
         * 服务项目下单 支付宝支付
         *
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToAlipay(final String orderinfo) {
            MyLog.e("pay", "支付宝支付------" + orderinfo);
            payType = NO3;
            ServerTeamPageDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goPay(orderinfo);

                }
            });
        }

        /**
         * 服务项目下单 支付结果 查看订单详情
         *
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToClientSoDetail(String orderNo) {
            MyLog.e("orderdetail", "查看订单详情-------" + orderNo);
            goOrderDetail(orderNo);
        }

        //商品详情交互

        /**
         * 联系客服
         *
         * @param userinfojson 客服信息json
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsCustomerService(String userinfojson) {
            MyLog.e("goodsdetailh5", "userinfojson--------" + userinfojson);
            goContactService(userinfojson);
        }

        /**
         * 联系团队
         *
         * @param userinfojson 团队信息json
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void customerervice(String userinfojson) {
            MyLog.e("goodsdetailh5", "userinfojson--------" + userinfojson);
            goContactService(userinfojson);
        }
        //案例详情交互

        /**
         * @author chenjiadi
         * version 1.1.0
         * 去登录
         */
        @JavascriptInterface
        public void jsToClientLgin() {
            goLogin(ServerTeamPageDetailActivity.this);
        }

        /**
         * 案例详情分享
         *
         * @param shareJson 分享内容json
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToDetailsCaseShare(String shareJson) {
            MyLog.e("goodsdetailh5", "share--------" + shareJson);
            goShare(shareJson);
        }

        /**
         * 课题分享
         */
        @JavascriptInterface
        public void getherShare(String jsonData) {
            goShare(jsonData);
        }

//        <!-- 咨询详情  -->

        /**
         * 点击头像进入个人主页
         *
         * @param personId 用户id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientUserDetail(String personId) {
            MyLog.e("mainh5", "personId--------" + personId);
            goPersonPage(personId);
        }

        /**
         * 查看全部回复
         *
         * @author luxuchang
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsSurpluscomment(String id) {
            MyLog.e("mainh5", "jsSurpluscomment--------" + id);
            try {
                JSONObject object = new JSONObject(id);
                String consultationId = object.getString("id");
                String commentId = object.getString("plid");
                Bundle bundle = new Bundle();
                bundle.putString("commentId", commentId);
                bundle.putString("consultationId", consultationId);
                JumpIntent.jump(ServerTeamPageDetailActivity.this, WatchReplayListActivity.class, bundle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * 首页资讯评论
         *
         * @param id 咨询id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientComment(String id) {
            MyLog.e("mainh5", "comment--------" + id);
            consultationId = id;
            SendCommentDialog dialog = new SendCommentDialog(ServerTeamPageDetailActivity.this, "评论");
            dialog.commentListener(this);
            dialog.show();
        }

        @Override
        public void addComment(final String content) {
            if (TextUtils.isEmpty(content)) {
                ToastUtils.show("请输入评论内容");
            }
            MyLog.e("mainh5", "comment--------" + consultationId);
            ServerTeamPageDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RequestApi.infoComment(content, consultationId, 0, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
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
                            if (msgBean != null && msgBean.getCode() == NO1) {
                                webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {
                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(ServerTeamPageDetailActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(ServerTeamPageDetailActivity.this, RealNameAuthenticationActivity.class);
                                    }
                                };
                                dialog.setText("是否去实名认证？", "确定", "取消");
                                dialog.showDialog();
                            }
                            ToastUtils.show(msgBean.getMsg());
                        }

                        @Override
                        public void onError(String errorMsg) {
                            ToastUtils.show(errorMsg);
                        }
                    });
                }
            });
        }

        /**
         * 回复
         *
         * @param commentId 回复的id
         */
        @JavascriptInterface
        public void jsToClientCommentHf(final String commentId) {
            MyLog.e("mainh5", "jsToClientCommentHf--------" + commentId);
            SendCommentDialog pDialog = new SendCommentDialog(ServerTeamPageDetailActivity.this, "回复");
            pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                @Override
                public void addComment(final String content) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.show("请输入回复内容");
                    }
                    ServerTeamPageDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RequestApi.replyConsultationComment(App.manager.getPhoneNum(), App.manager.getUuid(), commentId, content, new AbstractNetWorkCallback<MsgBean>() {
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
                                    if (msgBean != null && msgBean.getCode() == NO1) {
                                        webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String s) {

                                            }
                                        });
                                    } else if (msgBean != null && msgBean.getCode() == NO2) {
                                        //未实名认证
                                        dialog = new AbstractDeleteDialog(ServerTeamPageDetailActivity.this) {
                                            @Override
                                            public void sureClick() {
                                                JumpIntent.jump(ServerTeamPageDetailActivity.this, RealNameAuthenticationActivity.class);
                                            }
                                        };
                                        dialog.setText("是否去实名认证？", "确定", "取消");
                                        dialog.showDialog();
                                    }
                                    ToastUtils.show(msgBean.getMsg());
                                }

                                @Override
                                public void onError(String errorMsg) {
                                    ToastUtils.show(errorMsg);
                                }
                            });
                        }
                    });
                }
            });
            pDialog.show();
        }

        /**
         * 买家商品订单订单 申请售后
         *
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToClientAfterSale(String jsonData) {
            //30申请售后，31评价商品, 32联系团队，33查看评价
            MyLog.e("jsonData", "jsonData------------" + jsonData);
            String sales = "30";
            String evaluate = "31";
            String contact = "32";
            String check = "33";
            H5OrderBean orderBean = new Gson().fromJson(jsonData, H5OrderBean.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", orderBean);
            if (orderBean.getData().equals(sales)) {
                Constant.isMember = true;
                JumpIntent.jump(ServerTeamPageDetailActivity.this, ChooseAfterSalesProductListActivity.class, bundle);
            } else if (orderBean.getData().equals(evaluate)) {
                Constant.isMember = true;
                bundle.putString("orderNo", orderBean.getOrderNo());
                JumpIntent.jump(ServerTeamPageDetailActivity.this, CheckEvaluateListActivity.class, bundle);
            } else if (orderBean.getData().equals(contact)) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(orderBean.getNickName());
                intentChatEntity.setAcceptId("".equals(ConstUtils.getStringNoEmpty(orderBean.getUserId())) ? ConstUtils.getStringNoEmpty(orderBean.getUuid()) : ConstUtils.getStringNoEmpty(orderBean.getUserId()));
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(ServerTeamPageDetailActivity.this, intentChatEntity);
            } else if (orderBean.getData().equals(check)) {
                Constant.isMember = true;
                bundle.putString("orderNo", orderBean.getOrderNo());
                JumpIntent.jump(ServerTeamPageDetailActivity.this, CheckEvaluateListActivity.class, bundle);
            }
        }

        /**
         * 卡联盟 选择优惠券
         */
        @JavascriptInterface
        public void jsToClientCoupon() {
            MyLog.e("card", "jsToClientCoupon--------");
            Bundle bundle = new Bundle();
            bundle.putInt("isOrder", 1);
            JumpIntent.jump(ServerTeamPageDetailActivity.this, CardBagListActivity.class, bundle);
        }

        @JavascriptInterface
        public void jsTogohome() {
            JumpIntent.jump(ServerTeamPageDetailActivity.this, MainActivity.class, true);
        }

        /**
         * 服务项目详情拨打电话
         */
        @JavascriptInterface
        public void jsToClientCall(String phoneNum) {
            MyLog.e("call", "jsToClientCall--------" + phoneNum);
            call(phoneNum);
        }

        /**
         * 服务项目下单完成跳转服务订单列表
         */
        @JavascriptInterface
        public void jsToserverorder() {
            Bundle bundle = new Bundle();
            bundle.putInt("flag", 2);
            bundle.putInt("from",0);
            JumpIntent.jump(ServerTeamPageDetailActivity.this, MyServiceOrderActivity.class,bundle,true);

        }
    }

    /**
     * 拨打电话
     *
     * @param phoneNum 电话号
     */
    private void call(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 跳转服务项目订单详情
     *
     * @param orderNo 订单id
     */
    private void goOrderDetail(String orderNo) {
        Bundle bundle = new Bundle();
        bundle.putInt("status", 1);
        bundle.putString("orderId", orderNo);
        bundle.putInt("from", 0);
        JumpIntent.jump(ServerTeamPageDetailActivity.this, MyServiceOrderDetailActivity.class, bundle);
    }

    /**
     * 去支付
     *
     * @param data 数据json
     */
    public void goPay(final String data) {
        MyLog.e("dataJson", data + "=====");
        final String orderId = "orderId";
        final String orderNum = "orderNums";
        final String type = "type";
        try {
            JSONObject jsonObject = new JSONObject(data);
            String orderIds = "";
            String orderNums = "";
            String types = "";
            if (!TextUtils.isEmpty(jsonObject.getString(orderNum))) {
                orderNums = jsonObject.getString(orderNum);
                orderIds = jsonObject.getString(orderId);
                CARDORDERID = orderIds;
                types = jsonObject.getString(type);
                toPay(orderIds, types, orderNums);
                WXPAYTYPE = jsonObject.getString(type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 去支付
     *
     * @param orderIds  订单id
     * @param type      商品下单 0 服务下单 1 卡卡联盟下单   2
     * @param orderNums 订单no
     */
    private void toPay(final String orderIds, final String type, String orderNums) {
        // 2微信 3 支付宝
        RequestApi.pay(App.manager.getPhoneNum(), App.manager.getUuid(), orderNums, payType, Utils.getIPAddress(this), new AbstractNetWorkCallback<PayResultBean>() {
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
                    if (payType == NO2) {
                        new OrderPay().pay(context, bean.getContent());
                    } else if (payType == NO3) {
                        new OrderPay().pay(bean.getContent().getRequestURL(), ServerTeamPageDetailActivity.this, new OrderPay.IPayResult() {
                            @Override
                            public void result(boolean success) {
                                if (success) {
                                    // 商品下单 0 服务下单 1 卡卡联盟下单   2
                                    if (STRING1.equals(type)) {
                                        webHome.evaluateJavascript("paySuccess('" + 3 + "')", new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String s) {

                                            }
                                        });
                                    } else if (STRING0.equals(type)) {
                                        webHome.evaluateJavascript("paySuccess('" + 3 + "')", new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String s) {

                                            }
                                        });
                                    } else {
                                        //跳转到卡联盟订单详情页面
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("type", 3);
                                        JumpIntent.jump(ServerTeamPageDetailActivity.this, CardUnionOrderActivity.class, bundle,true);
                                    }
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
     * 通知h5支付完成
     */

    private void flushPayState() {
        EventBus.getDefault().post(new EventBean("flushpay"));
    }

    /**
     * 跳转首页分享
     *
     * @param shareJson 首页咨询分享 h5返回分享内容
     * @author chenjiadi
     * version 1.0.1
     */
    private void goShare(final String shareJson) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (shareJson != null && !UNDEFINED.equals(shareJson)) {
                    HomeInfoShareBean homeInfoShareBean = new Gson().fromJson(shareJson, HomeInfoShareBean.class);
                    if (homeInfoShareBean.getType() != null) {
                        if (homeInfoShareBean.getType().equals(String.valueOf(NO1))) {
                            //下方分享
                            ShareActivity.getInstance().popShare(ServerTeamPageDetailActivity.this, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                        } else {
                            //右上角分享
                            if (STRING0.equals(homeInfoShareBean.getIsCollect())) {
                                ShareActivity.getInstance().popShare(ServerTeamPageDetailActivity.this, handler, false, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                            } else {
                                ShareActivity.getInstance().popShare(ServerTeamPageDetailActivity.this, handler, true, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                            }
                        }
                    }
                }
            }

        });


    }

    /**
     * handler 分享页面弹窗 按钮和页面交互
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == NO6) {
                //复制
                String url = (String) msg.obj;
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", url);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
            } else if (msg.what == NO7) {
                //收藏
                webHome.evaluateJavascript("collection('" + consultationId + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            } else if (msg.what == NO8) {
                //举报
                webHome.evaluateJavascript("report('" + consultationId + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            }
        }
    };

    /**
     * 跳转普通用户个人信息页面
     *
     * @param personId
     * @author chenjiadi
     * version 1.0.1
     */

    private void goPersonPage(String personId) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        bundle.putString("personId", personId);
        JumpIntent.jump(this, PersonDetailActivity.class, bundle);
    }

    /**
     * 商品详情跳转客服
     *
     * @param data json数据
     */
    private void goContactService(final String data) {
        MyLog.e("返回的数据", data);
        final String id = "uuid";
        final String imgUrl = "imgUrl";
        final String nc = "nikeName";

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
//                        if (!TextUtils.isEmpty(jsonObject.getString(imgUrl))) {
//                            img = jsonObject.getString(imgUrl);
//                        }
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
                    ChatScreenActivity.getJumpChatSource(ServerTeamPageDetailActivity.this, intentChatEntity);
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        MyLog.e("logout", "----------lossssssssssssssgout--------" + event.getMsg());

        userInfoJson = jsToClientSetUserInfo();
        if (REFRESH.equals(event.getMsg())) {
            //刷新h5页面
            MyLog.e("aaasasasa", "ssssssssssssssss------" + event.getMsg());
            webHome.evaluateJavascript("reloads()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (LOGOUT.equals(event.getMsg())) {
            MyLog.e("sss", "----------logout--------");
            //退出登录
            webHome.evaluateJavascript("jsToLoginAgain()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (FLUSHPAY.equals(event.getMsg())) {
            MyLog.e("state", event.getMsg() + "----");
            //通知h5支付完成
            webHome.evaluateJavascript("StatePayment()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (event.getFlag() == NO10) {
            MyLog.e("statessss", event.getMsg() + "----");
            String cardInfo = event.getMsg();
            //通知h5选择优惠券
            webHome.evaluateJavascript("getCardId('" + cardInfo + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (Constant.PAY.equals(event.getMsg())) {
            MyLog.e("wxpay", CARDORDERID + "--------------------" + event.getMsg());
            // 商品下单 0 服务下单 1 卡卡联盟下单   2
            if (STRING1.equals(WXPAYTYPE)) {
                webHome.evaluateJavascript("paySuccess('" + 1 + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            } else if (STRING0.equals(WXPAYTYPE)) {
                webHome.evaluateJavascript("paySuccess('" + 1 + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            } else {
                //跳转到卡联盟订单详情页面
                Bundle bundle = new Bundle();
                bundle.putInt("type", 3);
                JumpIntent.jump(ServerTeamPageDetailActivity.this, CardUnionOrderActivity.class, bundle,true);
            }
        }
    }

    @Subscribe
    public void onShareEventMain(com.lxc.sharelibrary.EventBean event) {
        userInfoJson = jsToClientSetUserInfo();
        if (SHAREREFRESH.equals(event.getMsg())) {
            //刷新h5分享回调
            MyLog.e("hahahaha", "share_eventbus-------" + event.getMsg());
            webHome.evaluateJavascript("clientToJsShare()", new ValueCallback<String>() {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webHome.canGoBack()) {
            webHome.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
        if (requestCode == NO20 * NO10) {
            userInfoJson = jsToClientSetUserInfo();
            webHome.evaluateJavascript("changeServiceAdmin()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (requestCode == NO100 + NO11) {
            EventBus.getDefault().post(new EventBean(REFRESH));
        }
    }
}
