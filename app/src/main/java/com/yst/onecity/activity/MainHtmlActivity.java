package com.yst.onecity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.yst.basic.framework.utils.ToastUtils;
import com.google.gson.Gson;
import com.lxc.sharelibrary.ShareActivity;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.LoginEntity;
import com.yst.im.imchatlibrary.model.JudgeTokenModel;
import com.yst.im.imchatlibrary.model.LoginModel;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imchatlibrary.ui.activity.SingleLoginActivity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.PublishInfoOnGraphicsActivity;
import com.yst.onecity.activity.home.PublishInfoVideoActivity;
import com.yst.onecity.activity.hunter.HunterPersonActivity;
import com.yst.onecity.activity.issue.AddProductPlanActivity;
import com.yst.onecity.activity.issue.IssueActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.mine.AttentionActivity;
import com.yst.onecity.activity.mine.ContactFriendsActivity;
import com.yst.onecity.activity.mine.FansActivity;
import com.yst.onecity.activity.mine.HuntingBeansActivity;
import com.yst.onecity.activity.mine.MyCollectActivity;
import com.yst.onecity.activity.mine.MyInComeActivity;
import com.yst.onecity.activity.mine.MyIssueActivity;
import com.yst.onecity.activity.mine.MyZxingActivity;
import com.yst.onecity.activity.mine.ServerCenterActivity;
import com.yst.onecity.activity.mine.accountsafe.AccountSafeActivity;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.activity.mine.message.MessageActivity;
import com.yst.onecity.activity.mine.order.AfterSalesListActivity;
import com.yst.onecity.activity.mine.order.CheckEvaluateListActivity;
import com.yst.onecity.activity.mine.order.ChooseAfterSalesProductListActivity;
import com.yst.onecity.activity.mine.setting.MyActivity;
import com.yst.onecity.activity.mine.setting.PersonDetailActivity;
import com.yst.onecity.activity.mine.setting.SettingActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.HomeInfoShareBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.bean.order.H5OrderBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.pay.OrderPay;
import com.yst.onecity.pay.PayResultBean;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JsonUtil;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;
import com.yst.onecity.view.dialog.SendCommentDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_LOGIN;
import static com.yst.onecity.Constant.COMMA;
import static com.yst.onecity.Constant.FLUSH;
import static com.yst.onecity.Constant.LOGOUT;
import static com.yst.onecity.Constant.MESSAGE;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO11;
import static com.yst.onecity.Constant.NO12;
import static com.yst.onecity.Constant.NO14;
import static com.yst.onecity.Constant.NO15;
import static com.yst.onecity.Constant.NO17;
import static com.yst.onecity.Constant.NO18;
import static com.yst.onecity.Constant.NO19;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO20;
import static com.yst.onecity.Constant.NO200;
import static com.yst.onecity.Constant.NO26;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;
import static com.yst.onecity.Constant.NO8;
import static com.yst.onecity.Constant.NO9;
import static com.yst.onecity.Constant.NOMESSAGE;
import static com.yst.onecity.Constant.ORDERREFRESH;
import static com.yst.onecity.Constant.PUBLISH;
import static com.yst.onecity.Constant.REFRESH;
import static com.yst.onecity.Constant.SHAREREFRESH;
import static com.yst.onecity.Constant.STRING0;
import static com.yst.onecity.Constant.UNDEFINED;
import static com.yst.onecity.Constant.shareContent;
import static com.yst.onecity.Constant.shareTitle;

/**
 * 程序主界面(H5页面)
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/3/1
 */
public class MainHtmlActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, LoginModel.LoginListenerCallBack {
    public static int payType = Constant.NO3;
    private static String contactPermission = Manifest.permission.READ_CONTACTS;
    private static String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static String writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    /**
     * 动态获取摄像头权限请求码
     */
    private final int GET_CAMERA_REQUEST = 1;
    @BindView(R.id.web_home)
    WebView webHome;
    private String shareUrl;
    /**
     * 分享内容
     */
    private String content;
    /**
     * 退出登录的时间
     */
    private long time = 0;
    /**
     * 当前咨询id
     */
    private String consultationId;
    private String userInfoJson;
    /**
     * 实名认证弹框
     */
    private AbstractDeleteDialog dialog;
    private Bundle from;

    /**
     * 群组名称
     */
    private String groupName;
    /**
     * 群组Id
     */
    private String groupId;
    /**
     * 是否实名认证
     */
    private boolean isRealName;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "com.yst.im.login":
                    //这里需要退出登录
                    MyApp.getMsClient().isUserOut = true;
                    App.isLogin = false;
                    App.manager.quitLogin();
                    jsToClientSetUserInfo();
                    EventBus.getDefault().post(new EventBean("logout"));
                    Intent intent1 = new Intent(MainHtmlActivity.this, LoginActivity.class);
                    intent1.putExtra("loginType", 1);
                    startActivity(intent1);
                    break;
                case "updateGroupName":
                    final String groupName = intent.getStringExtra("groupName");
                    MyLog.e("sss", groupName);
                    RequestApi.updateChatGroupName(groupName, new AbstractNetWorkCallback<MsgBean>() {
                        @Override
                        public void onSuccess(MsgBean msgBean) {
                            if (msgBean.getCode() == NO1) {
                                App.manager.setChatGroup(groupName);
                            } else {
                                ToastUtils.show(msgBean.getMsg());
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            ToastUtils.show(errorMsg);
                        }

                        @Override
                        public void onAfter() {
                            super.onAfter();
                        }
                    });

                    break;
                default:
                    break;
            }
        }
    };
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_html;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        App.isLogin = App.manager.getLoginState();
        RxBus.get().register(this);
        //跳转拍摄视频页传一个标识
        from = new Bundle();
        from.putString("from", "0");
        EventBus.getDefault().register(this);
        jsToClientSetUserInfo();
        CommonUtils.setWebSettings(MainHtmlActivity.this, webHome, H5Const.H5_HOME);
        webHome.addJavascriptInterface(new MainHtmlActivity.JsToJava(), "stub");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yst.im.login");
        intentFilter.addAction("updateGroupName");
        registerReceiver(broadcastReceiver, intentFilter);


        JudgeTokenModel judgeTokenModel = new JudgeTokenModel(this);
        judgeTokenModel.getJudgeToken();
        if (App.manager.getLoginState()) {
            LoginModel loginModel = new LoginModel(MainHtmlActivity.this);
            loginModel.setLoginListenerCallBack(MainHtmlActivity.this);
            String phone = "root";
            String pwd = "123456";
            try {
                loginModel.sdkLogin(phone, pwd);
                loginModel.login(App.manager.getPhoneNum(), pwd);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    /**
     * 商品详情跳转客服
     *
     * @param data json数据
     */
    private void goContactService(final String data) {
        MyLog.e("返回的数据", data);
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
                    ChatScreenActivity.getJumpChatSource(MainHtmlActivity.this, intentChatEntity);
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

        MyLog.e("返回的数据", data);
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
                        ShareActivity.getInstance().popShare(MainHtmlActivity.this, title, content, url, R.drawable.logo_icon);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 跳转商品群聊
     *
     * @param info 猎头信息
     */
    private void goChat(String info) {
        MyLog.e("sssss", "点击了加入群聊-----------" + info);
        if (!TextUtils.isEmpty(info)) {
            groupName = info.split(",")[0];
            groupId = info.split(",")[1];

            MyLog.e("sss", "--------" + TextUtils.isEmpty(info.split(",")[0]) + "----" + info.split(",")[1]);
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
        JumpIntent.jump(MainHtmlActivity.this, PersonDetailActivity.class, bundle);
    }

    /**
     * 跳转未登录去登录
     *
     * @author chenjiadi
     * version 1.0.1
     */
    private void goLogin() {
        JumpIntent.jump(MainHtmlActivity.this, LoginActivity.class);
    }

    /**
     * 跳转发布资讯
     *
     * @param type 0是导航的发布按钮，1是加号那里的图文 ，2是视频
     * @author chenjiadi
     * version 1.0.1
     */
    private void goPublish(int type) {
        switch (type) {
            case 0:
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, IssueActivity.class);
                } else {
                    goLogin();
                }
                break;
            case 1:
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, PublishInfoOnGraphicsActivity.class);
                } else {
                    goLogin();
                }
                break;
            case 2:
                //检测权限 进行相应页面跳转
                if (App.isLogin) {
                    erquestPermissions();
                } else {
                    goLogin();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 跳转我的页面
     *
     * @param type 跳转类型
     * @author chenjiadi
     * version 1.0.1
     */

    private void goMine(int type) {
        switch (type) {
            case NO1:
                //服务专员中心
                if (App.isLogin) {
                    RequestApi.checkMemberIsHunter(new AbstractNetWorkCallback<MsgBean>() {
                        @Override
                        public void onSuccess(MsgBean bean) {
                            if (bean.getCode() == NO1) {
                                JumpIntent.jump(MainHtmlActivity.this, ServerCenterActivity.class);
                            } else {
                                ToastUtils.show(bean.getMsg());
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            ToastUtils.show(errorMsg);
                        }
                    });

                } else {
                    goLogin();
                }
                break;
            case NO2:
                //消息
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, MessageActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO3:
                //收入
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, MyInComeActivity.class, NO100 + NO11);
                } else {
                    goLogin();
                }
                break;
            case NO4:
                //猎豆
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, HuntingBeansActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO5:
                //登录
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, HunterPersonActivity.class);
                } else {
                    JumpIntent.jump(MainHtmlActivity.this, LoginActivity.class);

                }
                break;
            case NO6:
                //收藏
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, MyCollectActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO7:
                //关注
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, AttentionActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO8:
                //粉丝
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, FansActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO9:
                //申请成为服务专员
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, ApplyServeCommissionerActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO10:
                //申请成为猎头
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, ApplyHunterActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO11:
                //申请加入服务专员头像
                break;
            case NO12:
                //切换专员
                if (App.isLogin) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (EasyPermissions.hasPermissions(this, contactPermission)) {
                            Intent intent = new Intent(MainHtmlActivity.this, SpeciallyPersonListActivity.class);
                            startActivityForResult(intent, 200);
                        } else {
                            EasyPermissions.requestPermissions(this, "请打开通讯录权限", 1003, contactPermission);
                        }
                    } else {
                        Intent intent = new Intent(MainHtmlActivity.this, SpeciallyPersonListActivity.class);
                        startActivityForResult(intent, 200);
                    }
                } else {
                    goLogin();
                }
                break;
            case NO14:
                //我的二维码
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, MyZxingActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO15:
                //我的发布
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, MyIssueActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO17:
                //账户安全
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, AccountSafeActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO18:
                //设置
                if (App.isLogin) {
                    JumpIntent.jump(MainHtmlActivity.this, SettingActivity.class);
                } else {
                    goLogin();
                }
                break;
            case NO19:
                //关于我们
                JumpIntent.jump(MainHtmlActivity.this, MyActivity.class);
                break;
            case NO20:
                //猎头个人主页
                if (App.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    bundle.putString("personId", String.valueOf(App.manager.getId()));
                    JumpIntent.jump(MainHtmlActivity.this, HunterPersonActivity.class, bundle);
                } else {
                    goLogin();
                }
                break;
            case NO26:
                if (App.isLogin) {
                    Constant.userType = 0;
                    JumpIntent.jump(MainHtmlActivity.this, AfterSalesListActivity.class);
                } else {
                    goLogin();
                }
                break;
            default:
                break;
        }

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
                    if (TextUtils.isEmpty(homeInfoShareBean.getContent())) {
                        content = homeInfoShareBean.getTitle();
                    } else {
                        content = homeInfoShareBean.getContent();
                    }
                    if (homeInfoShareBean.getType().equals(String.valueOf(NO1))) {
                        //下方分享
                        ShareActivity.getInstance().popShare(MainHtmlActivity.this, homeInfoShareBean.getTitle(), content, homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                    } else {
                        //右上角分享
                        if (STRING0.equals(homeInfoShareBean.getIsCollect())) {
                            ShareActivity.getInstance().popShare(MainHtmlActivity.this, handler, false, homeInfoShareBean.getTitle(), content, homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                        } else {
                            ShareActivity.getInstance().popShare(MainHtmlActivity.this, handler, true, homeInfoShareBean.getTitle(), content, homeInfoShareBean.getUrl(), R.drawable.logo_icon);
                        }
                    }
                }
            }
        });
    }


    /**
     * 跳转产品计划分享
     *
     * @param shareJson 首页咨询分享 h5返回分享内容
     * @author chenjiadi
     * version 1.0.1
     */
    private void goPlanShare(final String shareJson) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (shareJson != null && !UNDEFINED.equals(shareJson)) {
                    HomeInfoShareBean homeInfoShareBean = new Gson().fromJson(shareJson, HomeInfoShareBean.class);
                    ShareActivity.getInstance().popShare(MainHtmlActivity.this, homeInfoShareBean.getTitle(), homeInfoShareBean.getContent(), homeInfoShareBean.getUrl(), R.drawable.logo_icon);

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
            webHome.evaluateJavascript("reload()", new ValueCallback<String>() {
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
        } else if (PUBLISH.equals(event.getMsg())) {
            //发布防爆
            webHome.evaluateJavascript("clientToJsClick()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (MESSAGE.equals(event.getMsg())) {
            //有消息通知
            webHome.evaluateJavascript("ClientTojsShowMes('" + 1 + "')"
                    , new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            MLog.e("onReceiveValue111111");

                        }
                    });

        } else if (NOMESSAGE.equals(event.getMsg())) {
            MLog.e("onReceiveValue2222");
            //未读消息
            webHome.evaluateJavascript("ClientTojsShowMes('" + 0 + "')"
                    , new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                        }
                    });
        } else if (Constant.PAY.equals(event.getMsg())) {
            // 1微信支付，3支付宝支付
            webHome.evaluateJavascript("paySuccess('" + 1 + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (ORDERREFRESH.equals(event.getMsg())) {
            MLog.e("hhhhhhh", "订单刷新");
            webHome.evaluateJavascript("pingjiaSuccess()"
                    , new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                        }
                    });
        } else if (FLUSH.equals(event.getMsg())) {
            //发布成功之后通知H5刷新页面
            webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
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
            webHome.evaluateJavascript("clientToJsShare()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                }
            });
        }
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
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        EventBus.getDefault().unregister(this);
    }

    /**
     * android6.0运行时权限,检测（获取相机权限）
     *
     * @author chenjiadi
     * version 1.0.1
     */
    public void erquestPermissions() {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查相机权限
            int checkPermissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int checkPermissionAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
            if (checkPermissionCamera != PackageManager.PERMISSION_GRANTED || checkPermissionAudio != PackageManager.PERMISSION_GRANTED) {
                //如果权限返回不为 GRANTED 则申请权限 GET_CAMERA_REQUEST 为请求码
                ActivityCompat.requestPermissions(MainHtmlActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, GET_CAMERA_REQUEST);
            } else {
                //已有权限
                JumpIntent.jump(MainHtmlActivity.this, PublishInfoVideoActivity.class, from);
            }
        } else {
            //6.0以下 无需动态获取
            JumpIntent.jump(MainHtmlActivity.this, PublishInfoVideoActivity.class, from);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webHome.canGoBack()) {
            webHome.goBack();//返回上个页面
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > NO200 * NO10) {
                ToastUtils.show(getString(R.string.main_activity_exit_msg));
                time = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        //退出整个应用程序
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (GET_CAMERA_REQUEST == requestCode) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //同意了相应权限
                JumpIntent.jump(MainHtmlActivity.this, PublishInfoVideoActivity.class, from);
            } else {
                ToastUtils.show("需要相机、录音权限");
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case 1000:
                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QQ, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                break;
            case 1001:
                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QZONE, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                break;
            case 1002:
                Bundle bundle = new Bundle();
                bundle.putString("shareUrl", shareUrl);
                JumpIntent.jump(MainHtmlActivity.this, ContactFriendsActivity.class, bundle);
                break;
            case 1003:
                JumpIntent.jump(MainHtmlActivity.this, SpeciallyPersonListActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.show(perms + "权限被禁用，请到设置里打开");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
        if (requestCode == NO20 * NO10) {
            jsToClientSetUserInfo();
            webHome.evaluateJavascript("changeServiceAdmin()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else if (requestCode == NO100 + NO11) {
            EventBus.getDefault().post(new EventBean(REFRESH));
        }
    }

    @gorden.rxbus2.Subscribe(threadMode = ThreadMode.MAIN)
    public void messageReceive(MessageBean msgInfo) {
        MyLog.e("impjyc", "== " + msgInfo);
        if (msgInfo.getType() == TYPE_USER_OUT_LOGIN) {
            MyApp.getMsClient().close();
            MyApp.getMsClient().isUserOut = true;
            startActivity(new Intent(MainHtmlActivity.this, SingleLoginActivity.class));
        } else if (msgInfo.getEvent() == 1 || msgInfo.getEvent() == NO3) {
            EventBus.getDefault().post(new EventBean("Message"));
        }
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
                        new OrderPay().pay(bean.getContent().getRequestURL(), MainHtmlActivity.this, new OrderPay.IPayResult() {
                            @Override
                            public void result(boolean success) {
                                if (success) {
                                    // 1微信支付，3支付宝支付
                                    webHome.evaluateJavascript("paySuccess('" + 3 + "')", new ValueCallback<String>() {
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
     * 获取信息
     *
     * @param id
     */
    private void getThirdLoginInfo(final String id) {
        RequestApi.getAccountSafe(String.valueOf(App.manager.getId()), App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<AccountSafeBean>() {
            @Override
            public void onSuccess(AccountSafeBean accountSafeBean) {
                if (accountSafeBean.getCode() == NO1) {
                    if (accountSafeBean.getContent().getRealNameStatus() == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putString("planId", id);
                        JumpIntent.jump(MainHtmlActivity.this, AddProductPlanActivity.class, bundle);
                    } else {
                        ToastUtils.show("请先实名认证");
                        Bundle bun = new Bundle();
                        bun.putBoolean("isRealName", false);
                        JumpIntent.jump(MainHtmlActivity.this, RealNameAuthenticationActivity.class, bun);
                    }

                } else {
                    ToastUtils.show(accountSafeBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void onUserLogin(LoginEntity loginEntity) {
        if (loginEntity.getCode() == 0) {
            EventBus.getDefault().post(new EventBean("refresh"));
            ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
                @Override
                public void run() {
                    MyApp.getMsClient().init(Constants.HOST, Constants.PORT, String.valueOf(MyApp.manager.getId()), REQUEST_SS, MyApp.manager.getToken());
                }
            }).start();
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
         * 发布资讯
         *
         * @param type 0是导航的发布按钮，1是加号那里的图文 ，2是视频
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientPublish(int type) {
            MyLog.e("mainh5", "jsToPublish--------" + type);
            goPublish(type);
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
            goShare(shareJson);
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
            goPlanShare(shareJson);
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
            SendCommentDialog dialog = new SendCommentDialog(MainHtmlActivity.this, "评论");
            dialog.commentListener(this);
            dialog.show();
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
            SendCommentDialog pDialog = new SendCommentDialog(MainHtmlActivity.this, "评论");
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
                                webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(MainHtmlActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(MainHtmlActivity.this, RealNameAuthenticationActivity.class);
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
            SendCommentDialog pDialog = new SendCommentDialog(MainHtmlActivity.this, "评论");
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
                                webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(MainHtmlActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(MainHtmlActivity.this, RealNameAuthenticationActivity.class);
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
            SendCommentDialog pDialog = new SendCommentDialog(MainHtmlActivity.this, "评论");
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
                                webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {

                                    }
                                });
                            } else if (msgBean != null && msgBean.getCode() == NO2) {
                                //未实名认证
                                dialog = new AbstractDeleteDialog(MainHtmlActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(MainHtmlActivity.this, RealNameAuthenticationActivity.class);
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
         * 商品评价列表回复评价
         *
         * @param parentId 父级评论id
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientGoodsReplyComment(final String parentId) {
            MyLog.e("mainh5", "parentId--------" + parentId);
            SendCommentDialog pDialog = new SendCommentDialog(MainHtmlActivity.this, "评论");
            pDialog.commentListener(new SendCommentDialog.SendCommentListener() {
                @Override
                public void addComment(String content) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.show("请输入评论内容");
                    }
                    RequestApi.replyComment(App.manager.getPhoneNum(), App.manager.getUuid(), content, parentId, "1", "0", new AbstractNetWorkCallback<MsgBean>() {
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
                                dialog = new AbstractDeleteDialog(MainHtmlActivity.this) {
                                    @Override
                                    public void sureClick() {
                                        JumpIntent.jump(MainHtmlActivity.this, RealNameAuthenticationActivity.class);
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
         * 首页右上角消息
         *
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientMessage() {
            if (App.isLogin) {
                JumpIntent.jump(MainHtmlActivity.this, MessageActivity.class);
            } else {
                goLogin();
            }
        }

        /**
         * 我的页面
         *
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientMine(String type) {
            MyLog.e("mainh5", "mine--------" + type);
            goMine(Integer.parseInt(type));
        }

        /**
         * 我的页面 切换专员 加入群聊
         *
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsToClientChange(String isHunter) {
            MyLog.e("mainh5", "isHunter---------------" + isHunter);
            goChat(isHunter);
        }

        /**
         * 猎头 好友砍价
         *
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void fengxiangjie(final String type) {
            MyLog.e("mainh5", "好友砍价--------" + type);
            ToastUtils.show("分享");
            MainHtmlActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShareActivity.getInstance().popShare(MainHtmlActivity.this, "已经有80万+人砍成啦，快来帮我砍~", "我正在参加砍价，砍刀0元就可以免费拿啦，帮我砍一下吧", type, R.drawable.logo_icon);
                }
            });
        }

        /**
         * 猎头 我要加入
         *
         * @author jiaofan
         * version 1.0.1
         */
        @JavascriptInterface
        public void ClickToJoin(String id) {
            MyLog.e("mainh5", "id===" + id);
            if (App.isLogin) {
                Constant.IS_FLUSH = true;
                getThirdLoginInfo(id);

            } else {
                goLogin();
            }
        }

        /**
         * 参与有奖
         *
         * @param str
         * @author chenjiadi
         * version 1.0.1
         */
        @JavascriptInterface
        public void jsMethod(final String str) {
            if (TextUtils.isEmpty(str) || !str.contains(COMMA)) {
                return;
            }
            shareUrl = str.split(",")[0] + "?phone=" + App.manager.getPhoneNum() + "&token=" + MyApp.manager.getToken();
            String type = str.split(",")[1];
            if (!App.manager.getLoginState()) {
                JumpIntent.jump(MainHtmlActivity.this, LoginActivity.class);
                return;
            }
            switch (type) {
                case "wx":
                    MainHtmlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String wx = "com.tencent.mm";
                            if (!Utils.isAvilible(context, wx)) {
                                ToastUtils.show("未安装微信");
                                return;
                            }
                            ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.WEIXIN, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                        }
                    });
                    break;
                case "wxc":
                    MainHtmlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String wx = "com.tencent.mm";
                            if (!Utils.isAvilible(context, wx)) {
                                ToastUtils.show("未安装微信");
                                return;
                            }
                            ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.WEIXIN_CIRCLE, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                        }
                    });
                    break;
                case "qq":
                    MainHtmlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String qq = "com.tencent.mobileqq";
                            if (!Utils.isAvilible(context, qq)) {
                                ToastUtils.show("未安QQ");
                                return;
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (EasyPermissions.hasPermissions(MainHtmlActivity.this, readExternalStoragePermission, writeExternalStoragePermission)) {
                                    ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QQ, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                                } else {
                                    EasyPermissions.requestPermissions(MainHtmlActivity.this, "请打开存储权限", 1000, readExternalStoragePermission, writeExternalStoragePermission);
                                }
                            } else {
                                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QQ, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                            }
                        }
                    });
                    break;
                case "qzone":
                    String qq = "com.tencent.mobileqq";
                    if (!Utils.isAvilible(context, qq)) {
                        ToastUtils.show("未安装QQ");
                        return;
                    }
                    MainHtmlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (EasyPermissions.hasPermissions(MainHtmlActivity.this, readExternalStoragePermission, writeExternalStoragePermission)) {
                                    ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QZONE, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                                } else {
                                    EasyPermissions.requestPermissions(MainHtmlActivity.this, "请打开存储权限", 1001, readExternalStoragePermission, writeExternalStoragePermission);
                                }
                            } else {
                                ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.QZONE, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                            }
                        }
                    });
                    break;
                case "xl":
                    String sina = "com.sina.weibo";
                    if (!Utils.isAvilible(context, sina)) {
                        ToastUtils.show("未安装微博");
                        return;
                    }
                    MainHtmlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShareActivity.getInstance().sharePlatform(SHARE_MEDIA.SINA, MainHtmlActivity.this, shareTitle, shareContent, shareUrl, "", R.drawable.logo_icon);
                        }
                    });
                    break;
                case "dx":
                    MainHtmlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (EasyPermissions.hasPermissions(MainHtmlActivity.this, contactPermission)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("shareUrl", shareUrl);
                                    JumpIntent.jump(MainHtmlActivity.this, ContactFriendsActivity.class, bundle);
                                } else {
                                    EasyPermissions.requestPermissions(MainHtmlActivity.this, "请打开通讯录权限", 1002, contactPermission);
                                }
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("shareUrl", shareUrl);
                                JumpIntent.jump(MainHtmlActivity.this, ContactFriendsActivity.class, bundle);
                            }
                        }
                    });
                    break;
                default:
                    finish();
                    break;
            }
        }

        /**
         * 我的订单 申请售后
         *
         * @author chenjiadi
         * version 1.0.1
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
                JumpIntent.jump(MainHtmlActivity.this, ChooseAfterSalesProductListActivity.class, bundle);
            } else if (orderBean.getData().equals(evaluate)) {
                Constant.isMember = true;
                bundle.putString("orderNo", orderBean.getOrderNo());
                JumpIntent.jump(MainHtmlActivity.this, CheckEvaluateListActivity.class, bundle);
            } else if (orderBean.getData().equals(contact)) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(orderBean.getNickName());
                intentChatEntity.setAcceptId("".equals(ConstUtils.getStringNoEmpty(orderBean.getUserId())) ? ConstUtils.getStringNoEmpty(orderBean.getUuid()) : ConstUtils.getStringNoEmpty(orderBean.getUserId()));
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(MainHtmlActivity.this, intentChatEntity);
            } else if (orderBean.getData().equals(check)) {
                Constant.isMember = true;
                bundle.putString("orderNo", orderBean.getOrderNo());
                JumpIntent.jump(MainHtmlActivity.this, CheckEvaluateListActivity.class, bundle);
            }
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
         * 首页资讯详情评论
         *
         * @param content 评论内容
         */
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
                        webHome.evaluateJavascript("flush()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {

                            }
                        });
                    } else if (msgBean != null && msgBean.getCode() == NO2) {
                        //未实名认证
                        dialog = new AbstractDeleteDialog(MainHtmlActivity.this) {
                            @Override
                            public void sureClick() {
                                JumpIntent.jump(MainHtmlActivity.this, RealNameAuthenticationActivity.class);
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
            String wx = "com.tencent.mm";
            if (!Utils.isAvilible(context, wx)) {
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
}
