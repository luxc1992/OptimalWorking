package com.yst.onecity.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.ui.activity.SingleLoginActivity;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.fragment.HomePageFragment;
import com.yst.onecity.fragment.MineFragment;
import com.yst.onecity.fragment.ServeTeamFragment;
import com.yst.onecity.fragment.ShareFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imsdk.MessageConstant.REQUEST_SS;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_LOGIN;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2000;
import static com.yst.onecity.Constant.NO3;

/**
 * 程序主界面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.frame_root_layout)
    FrameLayout frameRootLayout;
    @BindView(R.id.main_activity_home_image_view)
    ImageView mainActivityHomeImageView;
    @BindView(R.id.main_activity_classification_image_view)
    ImageView mainActivityClassificationImageView;
    @BindView(R.id.main_activity_rank_image_view)
    ImageView mainActivityRankImageView;
    @BindView(R.id.main_activity_mine_image_view)
    ImageView mainActivityMineImageView;
    /**
     * 退出登录的时间
     */
    private long time = 0;
    /**
     * 页面标签的标志位
     */
    private int tagPosition = 0;
    /**
     * 页面跳转控制器
     */
    private FragmentManager fragmentManager;
    private HomePageFragment homeFragment;
    private ServeTeamFragment serveTeamFragment;
    private ShareFragment shareFragment;
    private MineFragment mineFragment;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "com.yst.im.login":
                    //这里需要退出登录
                    MyApp.getMsClient().isUserOut = true;
                    App.isLogin = false;
                    App.manager.quitLogin();
                    EventBus.getDefault().post(new EventBean("logout"));
                    App.getInstance().exitActivity();
                    Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                    intent1.putExtra("loginType", 1);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_new;
    }

    @Override
    public void initData() {
        ImmersionBar.with(this).reset().init();
        App.isLogin = PreferenceUtil.getBoolean("isLogin", false);
        if (App.manager.getLoginState()) {
            MyApp.getMsClient().close();
            MyApp.resetMsClient();
            ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
                @Override
                public void run() {
                    MyApp.getMsClient().init(Constants.HOST, Constants.PORT, String.valueOf(MyApp.manager.getId()), REQUEST_SS, MyApp.manager.getToken());
                }
            }).start();
        }
        fragmentManager = getSupportFragmentManager();
        setTabSelection(tagPosition);
        RxBus.get().register(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yst.im.login");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.manager.getLoginState()) {
            getMessageState();
        }
    }

    @OnClick({R.id.main_activity_home_linear_layout, R.id.main_activity_classification_linear_layout, R.id.main_activity_rank_linear_layout, R.id.main_activity_mine_linear_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_activity_home_linear_layout:
                setTabSelection(0);
                tagPosition = 0;
                break;
            case R.id.main_activity_classification_linear_layout:
                setTabSelection(1);
                tagPosition = 1;
                break;
            case R.id.main_activity_rank_linear_layout:
                setTabSelection(2);
                tagPosition = 2;
                break;
            case R.id.main_activity_mine_linear_layout:
                setTabSelection(3);
                tagPosition = 3;
                break;
            default:
                break;
        }
    }

    /**
     * 设置当前选中的标签状态和对应的内容展示
     *
     * @param position 位置
     */
    private void setTabSelection(int position) {
        // TODO Auto-generated method stub
        this.tagPosition = position;
        // 更改底部导航栏按钮状态
        FragmentTransaction ftn = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ftn);
        refresh();
        switch (tagPosition) {
            case 0:
                mainActivityHomeImageView.setSelected(true);
                if (homeFragment == null) {
                    homeFragment = new HomePageFragment();
                    ftn.add(R.id.frame_root_layout, homeFragment, "homeFragment");
                } else {
                    ftn.show(homeFragment);
                }
                break;
            case 1:
                mainActivityClassificationImageView.setSelected(true);
                if (serveTeamFragment == null) {
                    serveTeamFragment = new ServeTeamFragment();
                    ftn.add(R.id.frame_root_layout, serveTeamFragment, "serveTeamFragment");
                } else {
                    ftn.show(serveTeamFragment);
                    EventBus.getDefault().post(new EventBean("clear"));
                }

                break;
            case 2:
                mainActivityRankImageView.setSelected(true);
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    ftn.add(R.id.frame_root_layout, shareFragment, "shareFragment");
                } else {
                    ftn.show(shareFragment);
                }
                break;
            case 3:
                mainActivityMineImageView.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    ftn.add(R.id.frame_root_layout, mineFragment, "myFragment");
                } else {
                    ftn.show(mineFragment);
                    EventBus.getDefault().post(new EventBean("refresh", 1));
                }
                break;
            default:
                break;
        }
        ftn.commitAllowingStateLoss();
    }

    /**
     * 当fragment已被实例化，就隐藏起来
     *
     * @param ftn
     */
    private void hideFragments(FragmentTransaction ftn) {
        // TODO Auto-generated method stub
        if (homeFragment != null) {
            ftn.hide(homeFragment);
        }
        if (serveTeamFragment != null) {
            ftn.hide(serveTeamFragment);
        }
        if (shareFragment != null) {
            ftn.hide(shareFragment);
        }
        if (mineFragment != null) {
            ftn.hide(mineFragment);
        }
    }

    private void refresh() {
        mainActivityHomeImageView.setSelected(false);
        mainActivityClassificationImageView.setSelected(false);
        mainActivityRankImageView.setSelected(false);
        mainActivityMineImageView.setSelected(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > NO2000) {
                ToastUtils.show(getResources().getString(R.string.main_activity_exit_msg));
                time = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取消息阅读状态
     */
    private void getMessageState() {
        RequestApi.getMessage(App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    if (App.manager.getLoginState()) {
                        EventBus.getDefault().post(new EventBean(msgBean.getMsg(), 0));
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @gorden.rxbus2.Subscribe(threadMode = ThreadMode.MAIN)
    public void messageReceive(MessageBean msgInfo) {
        MyLog.e("impjyc", "== " + msgInfo);
        if (msgInfo.getType() == TYPE_USER_OUT_LOGIN) {
            MyApp.getMsClient().close();
            MyApp.resetMsClient();
            MyApp.getMsClient().isUserOut = true;
            App.isLogin = false;
            App.manager.quitLogin();
            startActivity(new Intent(MainActivity.this, SingleLoginActivity.class));
        } else if (msgInfo.getEvent() == 1 || msgInfo.getEvent() == NO3) {
            EventBus.getDefault().post(new EventBean("Message"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}

