package com.yst.onecity.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.yst.basic.framework.utils.ToastUtils;
import com.google.gson.Gson;
import com.lxc.sharelibrary.ShareActivity;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.UMShareAPI;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.activity.mine.setting.PersonDetailActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.HomeInfoShareBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.pay.OrderPay;
import com.yst.onecity.pay.PayResultBean;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.JsonUtil;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;
import com.yst.onecity.view.dialog.SendCommentDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;

import static com.yst.onecity.Constant.LOGOUT;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.PUBLISH;
import static com.yst.onecity.Constant.REFRESH;
import static com.yst.onecity.Constant.SHAREREFRESH;

/**
 * 产品计划详情页
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/3/8
 */
public class ProductPlanDetailActivity extends BaseActivity {
    @BindView(R.id.web_home)
    WebView webView;
    private String id;
    /**
     * 当前咨询id
     */
    private String consultationId;
    private String userInfoJson;
    /**
     * 实名认证弹框
     */
    private AbstractDeleteDialog dialog;
    public static int payType = Constant.NO3;
    @Override
    public int getLayoutId() {
        return R.layout.activity_product_plan_detail;
    }


    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initData() {
        App.isLogin = App.manager.getLoginState();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getString("id");
            Log.d("//////////", id);
            EventBus.getDefault().register(this);
            jsToClientSetUserInfo();
            CommonUtils.setWebSettings(ProductPlanDetailActivity.this, webView, H5Const.H5_PRODUCTDETAIL + "?" + id);
            webView.addJavascriptInterface(new JsToJava(), "stub");
            //阻止出现长按复制
            webView.setOnLongClickListener(new View.OnLongClickListener() {
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
            MyLog.e("collectionfinish", "finish-------" );
            EventBus.getDefault().post(new EventBean("finish"));
            finish();
       }
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
         * 产品计划分享
         *
         * @param shareJson 分享内容json
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientSharePlan(String shareJson) {
            MyLog.e("productPlanInfo", "share--------" + shareJson);
            goShare(shareJson);
        }

        /**
         * 产品计划评论
         *
         * @param id 咨询id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientPComent(String id) {
            MyLog.e("mainh5", "Pcomment--------" + id);
            consultationId = id;
            SendCommentDialog pDialog = new SendCommentDialog(ProductPlanDetailActivity.this, "评论");
            pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                @Override
                public void addComment(String content) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.show("请输入评论内容");
                    }
                    MyLog.e("mainh5", "Pcomment--------" + consultationId);
                    RequestApi.infoPlanComment(content, consultationId, 1, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
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
                                webView.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(ProductPlanDetailActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(ProductPlanDetailActivity.this, RealNameAuthenticationActivity.class);
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
            pDialog.show();
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

        /**
         * 猎头店铺 向我提问
         *
         * @param merchantId 猎头id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientAskMe(final String merchantId) {
            MyLog.e("mainh5ask", "ask--------" + merchantId);
            SendCommentDialog pDialog = new SendCommentDialog(ProductPlanDetailActivity.this, "评论");
            pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                @Override
                public void addComment(String content) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.show("请输入评论内容");
                    }
                    RequestApi.askMe(App.manager.getPhoneNum(), App.manager.getUuid(), content, merchantId, new AbstractNetWorkCallback<MsgBean>() {
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
                                MyLog.e("mainh5ask", "ask--------" + msgBean.getCode());
                                MyLog.e("mainh5ask", "ask--------" + msgBean.getMsg() + "-----" + msgBean.getCode());
                                webView.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(ProductPlanDetailActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(ProductPlanDetailActivity.this, RealNameAuthenticationActivity.class);
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
            pDialog.show();
        }

        /**
         * 猎头店铺   向我提问回复
         *
         * @param quizId 提问或者评论的id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientReplyComments(final String quizId) {
            MyLog.e("mainh5ask", "replyask--------" + quizId);
            SendCommentDialog pDialog = new SendCommentDialog(ProductPlanDetailActivity.this, "评论");
            pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                @Override
                public void addComment(String content) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.show("请输入评论内容");
                    }
                    RequestApi.replyAskMe(App.manager.getPhoneNum(), App.manager.getUuid(), content, quizId, new AbstractNetWorkCallback<MsgBean>() {
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
                                MyLog.e("mainh5ask", "replyask--------flush()");
                                webView.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(ProductPlanDetailActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(ProductPlanDetailActivity.this, RealNameAuthenticationActivity.class);
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
            pDialog.show();
        }
    }
    /**
     * 去支付
     *
     * @param data 数据json
     */
    public void goPay(final String data) {
        MyLog.e("dataJson", data + "=====");
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
                        new OrderPay().pay(bean.getContent().getRequestURL(), ProductPlanDetailActivity.this, new OrderPay.IPayResult() {
                            @Override
                            public void result(boolean success) {
                                if (success) {
                                    // 1微信支付，3支付宝支付
                                    webView.evaluateJavascript("paySuccess('" + 3 + "')", new ValueCallback<String>() {
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
     * 跳转未登录去登录
     *
     * @author chenjiadi
     * version 1.0.1
     */
    private void goLogin() {
        JumpIntent.jump(ProductPlanDetailActivity.this, LoginActivity.class);
    }

    /**
     * 跳转产品计划详情分享
     *
     * @param shareJson 产品计划详情分享 h5返回分享内容
     * @author chenjiadi
     * version 1.0.1
     */
    private void goShare(final String shareJson) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (shareJson != null) {
                    HomeInfoShareBean homeInfoShareBean = new Gson().fromJson(shareJson, HomeInfoShareBean.class);
                    ShareActivity.getInstance().popShare(ProductPlanDetailActivity.this, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);

                }
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
                    ChatScreenActivity.getJumpChatSource(ProductPlanDetailActivity.this, intentChatEntity);
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
                        ShareActivity.getInstance().popShare(ProductPlanDetailActivity.this, title, content, url, R.drawable.logo_icon);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Subscribe
    public void onEventMainThread(EventBean event) {
        jsToClientSetUserInfo();
        MyLog.e("hahahaha", "main-------" + event.getMsg());
        if (REFRESH.equals(event.getMsg())) {
            //刷新h5页面
            webView.evaluateJavascript("reload()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (LOGOUT.equals(event.getMsg())) {
            //退出登录
            webView.evaluateJavascript("jsToLoginAgain()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (PUBLISH.equals(event.getMsg())) {
            //发布防爆
            webView.evaluateJavascript("clientToJsClick()", new ValueCallback<String>() {
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
            MyLog.e("hahahaha", "share_eventbus-------" + event.getMsg());
            webView.evaluateJavascript("clientToJsShare()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        }
    }

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
        JumpIntent.jump(ProductPlanDetailActivity.this, PersonDetailActivity.class, bundle);
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
        MyLog.e("asasas", "userinfo--" + userInfoJson);
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
