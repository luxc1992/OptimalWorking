package com.yst.onecity.fragment.share;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.SearchHomeActivity;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.bean.IndustryBean;
import com.yst.onecity.fragment.LazyBaseFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 行业Fragment
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/16
 */
public class IndustryFragment extends LazyBaseFragment {
    @BindView(R.id.indicator_industry)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private CommomFragmentPagerAdapter adapter;

    @Override
    protected void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_industry;
    }

    @Override
    public void init() {
        sortList.clear();
        fragments.clear();
        requestIndustrySort();
    }

    private void requestIndustrySort() {
        RequestApi.getIndustrySort(new AbstractNetWorkCallback<IndustryBean>() {
            @Override
            public void onSuccess(IndustryBean industryBean) {
//                sortList.add("全部");
//                fragments.add(ServerProjectFragment.newInstance(0, 1));
                for (int i = 0; i < industryBean.getContent().size(); i++) {
                    sortList.add(industryBean.getContent().get(i).getName());
                    fragments.add(ServerProjectFragment.newInstance(industryBean.getContent().get(i).getId(), 1));
                }

                adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, sortList);
                viewpager.setAdapter(adapter);
                viewpager.setCurrentItem(0);
                indicator.bindViewPager(viewpager, false);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @OnClick(R.id.search)
    public void onSearchClicked() {
        JumpIntent.jump(getActivity(), SearchHomeActivity.class);
    }
}
