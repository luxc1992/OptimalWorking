package com.yst.onecity.activity.h5detail;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

import static com.yst.onecity.Constant.REFRESH;
import static com.yst.onecity.utils.CommonUtils.goLogin;
import static com.yst.onecity.utils.CommonUtils.jsToClientSetUserInfo;

/**
 * 服务商品详情
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/19
 */
public class ServerGoodsDetailActivity extends BaseActivity {

    @BindView(R.id.web_home)
    WebView webHome;
    private String userInfoJson;

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_goods_detail;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        App.isLogin = App.manager.getLoginState();
        userInfoJson = jsToClientSetUserInfo();
        MyLog.e("aaaaaaa", "userinfo----" + userInfoJson);
        CommonUtils.setWebSettings(ServerGoodsDetailActivity.this, webHome, H5Const.GOODS_DETAIL_NEW + "?id=" + 1 + "&from=app");
        webHome.addJavascriptInterface(new JsToJava(), "stub");
    }

    /**
     * js与android得交互
     */
    private class JsToJava {
        /**
         * 返回按钮
         *
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToClientBack() {
            finish();
        }

        /**
         * @author chenjiadi
         * version 1.1.0
         * 去登录
         */
        @JavascriptInterface
        public void jsToClientLogin() {
            goLogin(ServerGoodsDetailActivity.this);
        }

        /**
         * 商品详情分享
         *
         * @param shareJson 分享内容json
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsToClientShare(String shareJson) {
            ToastUtils.show("分享");
            MyLog.e("goodsdetailh5", "share--------" + shareJson);
        }

        /**
         * 联系客服
         *
         * @param userinfojson 客服信息json
         * @author chenjiadi
         * version 1.1.0
         */
        @JavascriptInterface
        public void jsCustomerService(String userinfojson) {
            ToastUtils.show("联系客服");
            MyLog.e("goodsdetailh5", "userinfojson--------" + userinfojson);
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
            ToastUtils.show("联系团队");
            MyLog.e("goodsdetailh5", "userinfojson--------" + userinfojson);
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
    }


    @Subscribe
    public void onEventMainThread(EventBean event) {
        jsToClientSetUserInfo();
        if (REFRESH.equals(event.getMsg())) {
            //刷新h5页面
            webHome.evaluateJavascript("reloads()", new ValueCallback<String>() {
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
}