package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.cardbag.CardBagDetailActivity;
import com.yst.onecity.adapter.InValidCardAdapter;
import com.yst.onecity.adapter.MyCouponFalseAdapter;
import com.yst.onecity.bean.ValidCardEventBean;
import com.yst.onecity.bean.mine.MyCardBagListBean;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * (我的)卡包-无效卡券
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/17
 */
public class InValidCardFragment extends BaseFragment {
    @BindView(R.id.ll_no_cardbag)
    LinearLayout noCardBag;
    @BindView(R.id.lv_invalidcard)
    ListView cardListView;
    @BindView(R.id.lv_invalidcoupon)
    ListView couponListView;

    private InValidCardAdapter cardAdapter;
    private MyCouponFalseAdapter couponAdapter;
    /**
     * 健身卡下单选择优惠券标识
     */
    private int isOrder;
    private List<MyCardBagListBean.ContentBean.FalseMapBean.FalseArrayBean> falseMapCardBeen = new ArrayList<>();
    private List<MyCardBagListBean.ContentBean.FalseMapBean.MyCouponFalseJsonArrayBean> falseMapCouponBeen = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invalidcard;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isOrder = bundle.getInt("isOrder", -1);
            MyLog.e("fragment", "isOrder" + isOrder);
        }
        //注册
        EventBus.getDefault().register(this);
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isOrder == NO1) {
                    ToastUtils.show("请选择有效卡券");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", falseMapCardBeen.get(i).getClubCardId());
                    JumpIntent.jump(getActivity(), CardBagDetailActivity.class, bundle);
                }
            }
        });
        couponListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isOrder == NO1) {
                    ToastUtils.show("请选择有效卡券");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", falseMapCardBeen.get(i).getClubCardId());
                    JumpIntent.jump(getActivity(), CardBagDetailActivity.class, bundle);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getInValidCardEventData(ValidCardEventBean event) {
        falseMapCardBeen = event.getFalseMapCardBeen();
        falseMapCouponBeen = event.getFalseMapCouponBeen();
        flushCardList();
        flushCouponList();
    }

    /**
     * 刷新新人卡适配器
     */
    private void flushCouponList() {
        if (null == couponListView) {
            return;
        }
        if (couponAdapter == null) {
            if (falseMapCardBeen.size() > 0 || falseMapCouponBeen.size() > 0) {
                couponAdapter = new MyCouponFalseAdapter(getContext(), falseMapCouponBeen);
                couponListView.setAdapter(couponAdapter);
            } else {
                noCardBag.setVisibility(View.VISIBLE);
            }
        } else {
            couponAdapter.setData(falseMapCouponBeen);
        }
    }

    /**
     * 刷新卡适配器
     */
    private void flushCardList() {
        if (null == cardListView) {
            return;
        }
        if (cardAdapter == null) {
            if (falseMapCardBeen.size() > 0 || falseMapCouponBeen.size() > 0) {
                cardAdapter = new InValidCardAdapter(falseMapCardBeen, getContext());
                cardListView.setAdapter(cardAdapter);
            } else {
                noCardBag.setVisibility(View.VISIBLE);
            }
        } else {
            cardAdapter.setData(falseMapCardBeen);
        }
    }

}
