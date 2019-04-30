package com.yst.onecity.activity.mine.cardbag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.TabWithVpUtil;
import com.yst.onecity.bean.ValidCardEventBean;
import com.yst.onecity.bean.mine.MyCardBagListBean;
import com.yst.onecity.fragment.InValidCardFragment;
import com.yst.onecity.fragment.ValidCardFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * (我的)卡包列表
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CardBagListActivity extends BaseActivity {
    @BindView(R.id.tablayout_cardbag)
    XTabLayout tablayout;
    @BindView(R.id.viewpager_cardbag)
    ViewPager viewpager;
    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    /**
     * 健身卡下单选择优惠券标识
     */
    private int isOrder;
    private ArrayList<MyCardBagListBean.ContentBean.FalseMapBean> falseMap = new ArrayList<>();
    private ArrayList<MyCardBagListBean.ContentBean.TrueMapBean> trueMap = new ArrayList<>();
    private List<MyCardBagListBean.ContentBean.TrueMapBean.MyCouponTrueJsonArrayBean> myCouponTrueJsonArray;
    private List<MyCardBagListBean.ContentBean.TrueMapBean.TrueArrayBean> trueArray;
    private List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> myCouponFalseJsonArray;
    private List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> falseArray;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_bag_list;
    }

    @Override
    public void initData() {
        initTitleBar("卡包列表");
        ValidCardFragment validCardFragment = new ValidCardFragment();
        InValidCardFragment inValidCardFragment = new InValidCardFragment();
        Bundle bundle = new Bundle();
        if (getIntent().getExtras() != null) {
            isOrder = getIntent().getExtras().getInt("isOrder");
            bundle.putInt("isOrder", isOrder);
            MyLog.e("fragment", "isOrder------------" + isOrder);
            validCardFragment.setArguments(bundle);
            inValidCardFragment.setArguments(bundle);
        }
        sortList.add("有效卡券");
        sortList.add("无效卡劵");
        fragments.add(validCardFragment);
        fragments.add(inValidCardFragment);
        TabWithVpUtil.tabWithViewPager(tablayout, viewpager, fragments, sortList, getSupportFragmentManager());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCardList();
    }

    /**
     * 卡包列表数据
     */
    private void getCardList() {
        RequestApi.getCardBagList(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MyCardBagListBean>() {
            @Override
            public void onSuccess(MyCardBagListBean myCardBagListBean) {
                if (myCardBagListBean.getCode() == 1) {
                    falseMap.add(myCardBagListBean.getContent().getFalseMap());
                    trueMap.add(myCardBagListBean.getContent().getTrueMap());
                    myCouponTrueJsonArray = myCardBagListBean.getContent().getTrueMap().getMyCouponTrueJsonArray();
                    trueArray = myCardBagListBean.getContent().getTrueMap().getTrueArray();
                    myCouponFalseJsonArray = myCardBagListBean.getContent().getFalseMap().getMyCouponFalseJsonArray();
                    falseArray = myCardBagListBean.getContent().getFalseMap().getFalseArray();

                    EventBus.getDefault().post(new ValidCardEventBean(trueArray,
                            myCouponTrueJsonArray,
                            falseArray,
                            myCouponFalseJsonArray));
                } else {
                    ToastUtils.show(myCardBagListBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }
}
