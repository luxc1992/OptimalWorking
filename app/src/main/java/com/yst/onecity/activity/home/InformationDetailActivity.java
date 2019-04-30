package com.yst.onecity.activity.home;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lxc.sharelibrary.ShareActivity;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.UMShareAPI;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
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
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.JsonUtil;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;
import com.yst.onecity.view.dialog.SendCommentDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import butterknife.BindView;

import static com.yst.onecity.Constant.LOGOUT;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;
import static com.yst.onecity.Constant.NO8;
import static com.yst.onecity.Constant.PUBLISH;
import static com.yst.onecity.Constant.REFRESH;
import static com.yst.onecity.Constant.SHAREREFRESH;
import static com.yst.onecity.Constant.STRING0;

/**
 * 咨询详情
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/24.
 */
public class InformationDetailActivity extends BaseActivity {

    @BindView(R.id.web_home)
    WebView webInfoDetail;
    private String conId;
    /**
     * 当前咨询id
     */
    private String consultationId;
    private String userInfoJson;
    /**
     * 实名认证弹框
     */
    private AbstractDeleteDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_information_detail;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initData() {
        App.isLogin = App.manager.getLoginState();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
//            conId = bundle.getString("id");
            EventBus.getDefault().register(this);
            jsToClientSetUserInfo();
            CommonUtils.setWebSettings(InformationDetailActivity.this, webInfoDetail, H5Const.H5_CONSULTING_DETAIL);
            webInfoDetail.addJavascriptInterface(new JsToJava(), "stub");
            //阻止出现长按复制
            webInfoDetail.setOnLongClickListener(new View.OnLongClickListener()

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
    private class JsToJava implements SendCommentDialog.SendCommentListener {

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
         * 返回事件
         */
        @JavascriptInterface
        public void jsToClientBack(String shareJson) {
            MyLog.e("mainhtml==","back------------");
            finish();
        }


        /**
         * 首页资讯分享
         *
         * @param shareJson 分享内容json
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientShare(String shareJson) {
            MyLog.e("mainh5", "share--------" + shareJson);
//            goShare(shareJson);
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
            SendCommentDialog dialog = new SendCommentDialog(InformationDetailActivity.this, "评论");
            dialog.commentListener(this);
            dialog.show();
        }

        /**
         * 回复
         * @param id 回复的id
         */
        @JavascriptInterface
        public void jsToClientCommentHf(String id) {
            MyLog.e("mainh5", "jsToClientCommentHf--------" + id);
//            consultationId = id;
//            SendCommentDialog dialog = new SendCommentDialog(InformationDetailActivity.this, "评论");
//            dialog.commentListener(this);
//            dialog.show();
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

        @Override
        public void addComment(String content) {
            if (TextUtils.isEmpty(content)) {
                ToastUtils.show("请输入评论内容");
            }
            MyLog.e("mainh5", "comment--------" + consultationId);
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
                        webInfoDetail.evaluateJavascript("flush()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {

                            }
                        });
                    } else if (msgBean != null && msgBean.getCode() == NO2) {
                        //未实名认证
                        dialog = new AbstractDeleteDialog(InformationDetailActivity.this) {
                            @Override
                            public void sureClick() {
                                JumpIntent.jump(InformationDetailActivity.this, RealNameAuthenticationActivity.class);
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

    }


    /**
     * 跳转未登录去登录
     *
     * @author chenjiadi
     * version 1.0.1
     */
    private void goLogin() {
        JumpIntent.jump(InformationDetailActivity.this, LoginActivity.class);
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
                if (shareJson != null) {
                    HomeInfoShareBean homeInfoShareBean = new Gson().fromJson(shareJson, HomeInfoShareBean.class);
                    if (homeInfoShareBean.getType().equals(String.valueOf(NO1))) {
                        //下方分享
                        ShareActivity.getInstance().popShare(InformationDetailActivity.this, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                    } else {
                        //右上角分享
                        if (STRING0.equals(homeInfoShareBean.getIsCollect())) {
                            ShareActivity.getInstance().popShare(InformationDetailActivity.this, handler, false, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                        } else {
                            ShareActivity.getInstance().popShare(InformationDetailActivity.this, handler, true, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                        }
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
            webInfoDetail.evaluateJavascript("reload()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (LOGOUT.equals(event.getMsg())) {
            //退出登录
            webInfoDetail.evaluateJavascript("jsToLoginAgain()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (PUBLISH.equals(event.getMsg())) {
            //发布防爆
            webInfoDetail.evaluateJavascript("clientToJsClick()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        }


    }

    @Subscribe
    public void onShareEventMain(com.lxc.sharelibrary.EventBean event) {
        MyLog.e("hahahaha啊啊啊啊", "share_eventbus-------" + event.getMsg());
        jsToClientSetUserInfo();
        if (SHAREREFRESH.equals(event.getMsg())) {
            //刷新h5分享回调
            MyLog.e("hahahaha", "share_eventbus-------" + event.getMsg());
            webInfoDetail.evaluateJavascript("clientToJsShare()", new ValueCallback<String>() {
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
        JumpIntent.jump(InformationDetailActivity.this, PersonDetailActivity.class, bundle);
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
                webInfoDetail.evaluateJavascript("collection('" + consultationId + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            } else if (msg.what == NO8) {
                //举报
                webInfoDetail.evaluateJavascript("report('" + consultationId + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }
}
