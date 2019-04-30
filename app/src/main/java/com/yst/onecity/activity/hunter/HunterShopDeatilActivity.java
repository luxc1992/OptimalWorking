package com.yst.onecity.activity.hunter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.yst.basic.framework.utils.ToastUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.JsonUtil;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;
import com.yst.onecity.view.dialog.SendCommentDialog;

import java.util.HashMap;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 猎头店铺
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/03/12
 */
public class HunterShopDeatilActivity extends BaseActivity {
    @BindView(R.id.web_home)
    WebView webHome;
    /**
     * 猎头id
     */
    private String hunterId;
    private String userInfoJson;
    /**
     * 实名认证弹框
     */
    private AbstractDeleteDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hunter_shop_deatil;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initData() {
        App.isLogin = App.manager.getLoginState();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            hunterId = bundle.getString("personId");
            jsToClientSetUserInfo();
            MyLog.e("hunterask", "hunterid----" + hunterId);
            CommonUtils.setWebSettings(HunterShopDeatilActivity.this, webHome, H5Const.H5_HUNTER_SHOP_DETAIL + "?" + hunterId);
            webHome.addJavascriptInterface(new JsToJava(), "stub");
            //阻止出现长按复制
            webHome.setOnLongClickListener(new View.OnLongClickListener()

            {
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
         * 猎头店铺 向我提问
         *
         * @param merchantId 猎头id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientAskMe(final String merchantId) {
            MyLog.e("hunterask", "ask--------" + merchantId);
            SendCommentDialog pDialog = new SendCommentDialog(HunterShopDeatilActivity.this, "评论");
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
                                MyLog.e("hunterask", "ask--------" + msgBean.getCode());
                                MyLog.e("hunterask", "ask--------" + msgBean.getMsg() + "-----" + msgBean.getCode());
                                webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(HunterShopDeatilActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(HunterShopDeatilActivity.this, RealNameAuthenticationActivity.class);
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
            MyLog.e("hunterask", "replyask--------" + quizId);
            SendCommentDialog pDialog = new SendCommentDialog(HunterShopDeatilActivity.this, "评论");
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
                                MyLog.e("hunterask", "replyask--------flush()");
                                webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(HunterShopDeatilActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(HunterShopDeatilActivity.this, RealNameAuthenticationActivity.class);
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
     * 跳转未登录去登录
     *
     * @author chenjiadi
     * version 1.0.1
     */
    private void goLogin() {
        JumpIntent.jump(HunterShopDeatilActivity.this, LoginActivity.class);
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
}