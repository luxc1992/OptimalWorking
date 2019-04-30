package com.yst.onecity.activity.mine.cardorder;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.fragment.cardunion.CardUnionAllOrderFragment;
import com.yst.onecity.fragment.cardunion.CardUnionFinishedOrderFragment;
import com.yst.onecity.fragment.cardunion.CardUnionWaitPayOrderFragment;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO3;

/**
 * 买家卡联盟订单页面
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CardUnionOrderActivity extends BaseActivity {

    @BindView(R.id.indicator_card_union_order)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommomFragmentPagerAdapter adapter;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_union_order;
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type",0);
        initTitleBar("卡联盟订单");
        ViewPagerIndicator.type = 1;
        sortList.add("全部订单");
        sortList.add("待付款");
        sortList.add("已完成");

        fragments.add(new CardUnionAllOrderFragment());
        fragments.add(new CardUnionWaitPayOrderFragment());
        fragments.add(new CardUnionFinishedOrderFragment());

        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fragments, sortList);
        viewpager.setAdapter(adapter);

        indicator.bindViewPager(viewpager, true);

        if (type == NO3) {
            viewpager.setCurrentItem(2,false);
        }
        adapter.notifyDataSetChanged();
    }
}
