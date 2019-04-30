package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.cardbag.CardBagDetailActivity;
import com.yst.onecity.adapter.MyCouponTrueAdapter;
import com.yst.onecity.adapter.ValidCardAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ValidCardEventBean;
import com.yst.onecity.bean.mine.MyCardBagListBean;
import com.yst.onecity.utils.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;

/**
 * (我的)卡包-有效卡券
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/17
 */
public class ValidCardFragment extends BaseFragment {
    @BindView(R.id.lv_validcard)
    ListView cardListView;
    @BindView(R.id.lv_validcoupon)
    ListView couponListView;
    @BindView(R.id.ll_no_cardbag)
    LinearLayout noCardBag;
    private ValidCardAdapter cardAdapter;
    private MyCouponTrueAdapter couponAdapter;
    /**
     * 健身卡下单选择优惠券标识
     */
    private int isOrder;
    private List<MyCardBagListBean.ContentBean.TrueMapBean.TrueArrayBean> trueMapCardBeen = new ArrayList<>();
    private List<MyCardBagListBean.ContentBean.TrueMapBean.MyCouponTrueJsonArrayBean> trueMapCouponBeen = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_validcard;
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
        couponListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isOrder == NO1) {
                    if (trueMapCouponBeen != null) {
                        //需要判断卡券类型 只有新人卡才能选择 否则给提示
                        HashMap<String, Object> userInfo = new HashMap<>(16);
                        userInfo.put("id", trueMapCouponBeen.get(i).getCouponId() + "");
                        userInfo.put("price", trueMapCouponBeen.get(i).getMoney() + "");
                        String cardInfo = JsonUtil.stringToJson(userInfo);
                        EventBus.getDefault().post(new EventBean(cardInfo, NO10));
                        getActivity().finish();
                    }
                }
            }
        });
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isOrder == NO1) {
                    ToastUtils.show("请选择优惠券");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", trueMapCardBeen.get(i).getServiceOrderId());
                    JumpIntent.jump(getActivity(), CardBagDetailActivity.class, bundle);
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getValidCardEventData(ValidCardEventBean event) {
        trueMapCardBeen = event.getTrueMapCardBeen();
        trueMapCouponBeen = event.getTrueMapCouponBeen();
        flushCardList();
        flushCouponList();
    }

    /**
     * 刷新卡适配器
     */
    private void flushCardList() {
        if (null == cardListView) {
            return;
        }
        if (cardAdapter == null) {
            if (trueMapCardBeen.size() > 0 || trueMapCouponBeen.size() > 0) {
                cardAdapter = new ValidCardAdapter(trueMapCardBeen, getContext());
                cardListView.setAdapter(cardAdapter);
            } else {
                noCardBag.setVisibility(View.VISIBLE);
            }
        } else {
            cardAdapter.setData(trueMapCardBeen);
        }
    }

    /**
     * 刷新新人卡适配器
     */
    private void flushCouponList() {
        if (null == couponListView) {
            return;
        }
        if (couponAdapter == null) {
            if (trueMapCardBeen.size() > 0 || trueMapCouponBeen.size() > 0) {
                couponAdapter = new MyCouponTrueAdapter(getContext(), trueMapCouponBeen);
                couponListView.setAdapter(couponAdapter);
            } else {
                noCardBag.setVisibility(View.VISIBLE);
            }
        } else {
            couponAdapter.setData(trueMapCouponBeen);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消注册
        EventBus.getDefault().unregister(this);
    }
}
