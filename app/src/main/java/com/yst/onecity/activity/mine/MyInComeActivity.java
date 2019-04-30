package com.yst.onecity.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.activity.FetchCashActivity;
import com.yst.onecity.activity.mine.setting.BankNumActivity;
import com.yst.onecity.activity.mine.setting.NoBankNumActivity;
import com.yst.onecity.bean.BankinfroBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.mine.MyInComeListBean;
import com.yst.onecity.fragment.InComeListFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.AbstractTransactionHintDialog;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;

/**
 * 我的收入页面
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/23
 */

public class MyInComeActivity extends BaseActivity {

    @BindView(R.id.indicator)
    ViewPagerIndicator viewPagerIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.income_money)
    TextView incomeMoney;
    /**
     * viewpager下fragment集合
     */
    private List<Fragment> list = new ArrayList<>();
    private MyAdapter adapter;
    /**
     * 标题集合
     */
    private List<String> titles;

    /**
     * 是否设置绑定银行卡
     */
    private boolean hasBindCard = false;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myincome;
    }

    @Override
    public void initData() {
        setTitleBarTitle("我的收入");
        setRightText("银行卡");
        initViewPager();
        getInComeList();
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasBindCard) {
                    JumpIntent.jump(MyInComeActivity.this, BankNumActivity.class);
                } else {
                    JumpIntent.jump(MyInComeActivity.this, NoBankNumActivity.class);

                }

            }
        });
        setResult(RESULT_OK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankInfo();
        getInComeList();
    }

    private void initViewPager() {
        titles = new ArrayList<>();
        titles.add(getString(R.string.all_detail));
        titles.add(getString(R.string.team_returns));
        titles.add(getString(R.string.bean_conversion));
        titles.add(getString(R.string.broker_earnings));
        titles.add(getString(R.string.money_detail));
        titles.add(getString(R.string.recommend_earnings));
        titles.add(getString(R.string.team_commissions));
        for (int i = 0; i < titles.size(); i++) {
            list.add(InComeListFragment.newInstance(i, titles.get(i)));
        }
        adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewPagerIndicator.bindViewPager(viewpager, false);
    }
    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                EventBus.getDefault().post(new EventBean("refresh"));
                break;
            default:
                break;
        }
    }
    @OnClick(R.id.tv_cash)
    public void onViewClicked() {
        if (!hasBindCard) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "1");
            JumpIntent.jump(this, FetchCashActivity.class, bundle);
        } else {
            AbstractTransactionHintDialog hintDialog = new AbstractTransactionHintDialog(this) {
                @Override
                public void sureClick() {
                    JumpIntent.jump(MyInComeActivity.this, NoBankNumActivity.class);
                }

                @Override
                public void cancelClick() {

                }
            };
            hintDialog.setText("使用提现功能需添加一张支持提现的储蓄卡");
            hintDialog.setSureText("添加储蓄卡");
            hintDialog.showDialog();
        }

    }

    /**
     * 获取收入列表
     * version 1.0.1
     */
    private void getInComeList() {
        RequestApi.getMyInComeListData(String.valueOf(App.manager.getId()),
                "0",
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(page),
                new AbstractNetWorkCallback<MyInComeListBean>() {
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        incomeMoney.setText("0.0");
                    }

                    @Override
                    public void onSuccess(MyInComeListBean myInComeListBean) {
                        if (myInComeListBean.getCode() == NO1) {
                            if (!TextUtils.isEmpty(String.valueOf(myInComeListBean.getContent().getMoney()))) {
                                if (myInComeListBean.getContent().getMoney() == NO0) {
                                    incomeMoney.setText("0.0");
                                } else {
                                    incomeMoney.setText(Utils.doubleToString(myInComeListBean.getContent().getMoney()));

                                }
                            }
                        } else {
                            incomeMoney.setText("0.0");
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        incomeMoney.setText("0.0");
                        ToastUtils.show(errorMsg);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                    }
                });
    }

    /**
     * 获取银行卡信息
     * version 1.0.1
     */
    public void getBankInfo() {
        RequestApi.getBindInfo(App.manager.getUuid(),
                App.manager.getPhoneNum(),
                new AbstractNetWorkCallback<BankinfroBean>() {
                    @Override
                    public void onSuccess(BankinfroBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            if (null == msgBean.getContent().getBankNo()) {
                                hasBindCard = true;
                            }
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        MyLog.e("onError", errorMsg);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().post(new EventBean("refresh"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 适配器
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * 重写这个方法，将设置每个Tab的标题
         *
         * @param position s索引
         * @return 返回title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
