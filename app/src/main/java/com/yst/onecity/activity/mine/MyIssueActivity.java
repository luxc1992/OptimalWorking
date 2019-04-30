package com.yst.onecity.activity.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.fragment.InformationFragmrnt;
import com.yst.onecity.fragment.ProductPlanFragment;
import com.yst.onecity.fragment.ProjectPlanFragment;
import com.yst.onecity.utils.TabLayoutIndicatorUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

import static com.yst.onecity.Constant.FINSIH;

/**
 * 我的发布
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/22
 */
public class MyIssueActivity extends BaseActivity {
    @BindView(R.id.myissue_vp_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.myissue_vp)
    ViewPager viewPager;
    /**
     * 分类适配器
     */
    private CommomFragmentPagerAdapter adapter;
    /**
     * 分类
     */
    private ArrayList<String> sortList;
    private ArrayList<Fragment> fragments;
    private String[] sortName = {"资讯", "服务项目", "课题"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_issue;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.myissue));
        EventBus.getDefault().register(this);
        tabLayout.addTab(tabLayout.newTab().setText(sortName[0]));
        tabLayout.addTab(tabLayout.newTab().setText(sortName[1]));
        tabLayout.addTab(tabLayout.newTab().setText(sortName[2]));
        initSort();
    }

    /**
     * 获取分类
     */
    private void initSort() {
        sortList = new ArrayList<>();
        fragments = new ArrayList<>();
        sortList.addAll(Arrays.asList(sortName));
        fragments.add(InformationFragmrnt.newInstance(String.valueOf(1), 0));
        fragments.add(ProductPlanFragment.newInstance(0));
        fragments.add(ProjectPlanFragment.newInstance());
        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fragments, sortList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // 设置tabLayout的分隔线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.shape_vpindicator_right));
        linearLayout.setDividerPadding(30);
        //设置游标的宽度
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutIndicatorUtils.setIndicator(tabLayout, 30, 30);
            }
        });
    }
    @Subscribe
    public void onShareEventMain(EventBean event) {
        if (FINSIH.equals(event.getMsg())) {
            MyLog.e("collectionfinish", "finish-------" + event.getMsg());
            if (!MyIssueActivity.this.isFinishing()) {
                finish();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
