package com.yst.onecity.fragment.share;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.adapter.SearchRecommendedAdapter;
import com.yst.onecity.bean.search.SearchShareBean;
import com.yst.onecity.fragment.SearchServerProjectFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 搜索分享页面
 *
 * @author qinchaoshuai
 * @version 1.1.0
 * @date 2018/5/26
 */
public class SearchShareFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.smartRefreshLayout_child_share)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.lv_child_share)
    ListView listView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private int page = 1;
    /**
     * 搜索的关键词
     */
    private String keyWord = "";
    private SearchRecommendedAdapter adapter;
    private List<SearchShareBean.ContentBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_child_share;
    }

    @Override
    public void init() {
        if (null != getArguments()) {
            keyWord = getArguments().getString(SearchServerProjectFragment.searchName);
        }
        adapter = new SearchRecommendedAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }

    @Override    protected void setListener() {
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(list.get(position).getId()));
                bundle.putString("url", H5Const.H5_CONSULTING_DETAIL);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
            }
        });
        requestShareData(page, keyWord);
    }

    /**
     * 搜索分享数据
     */
    public void requestShareData(final int page, String title) {
        if (page == NO1) {
            list.clear();
            if (null != adapter) {
                adapter.notifyDataSetChanged();
            }
        }
        RequestApi.getconsultationbytitle(String.valueOf(App.manager.getId()), title, page, new AbstractNetWorkCallback<SearchShareBean>() {
            @Override
            public void onAfter() {
                super.onAfter();
                loadFinish();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(SearchShareBean mSearchShareBean) {
                boolean b = page == NO1 && (mSearchShareBean.getContent() == null || mSearchShareBean.getContent().size() == 0);
                if (b) {
                    rlEmpty.setVisibility(View.VISIBLE);
                } else {
                    rlEmpty.setVisibility(View.GONE);
                    if (page != 1 && mSearchShareBean.getContent().size() == 0) {
                        ToastUtils.show("没有更多数据了");
                    }
                    list.addAll(mSearchShareBean.getContent());
                    if (null != adapter) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    private void loadFinish() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        requestShareData(page, keyWord);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page ++;
        requestShareData(page, keyWord);
    }
}
