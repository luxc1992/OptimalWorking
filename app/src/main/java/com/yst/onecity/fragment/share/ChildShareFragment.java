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
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.adapter.RecommendedAdapter;
import com.yst.onecity.bean.RecommendedBean;
import com.yst.onecity.fragment.LazyBaseFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 子分享页面
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/17
 */
public class ChildShareFragment extends LazyBaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.smartRefreshLayout_child_share)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.lv_child_share)
    ListView listView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private int page = 1;
    private RecommendedAdapter adapter;
    private List<RecommendedBean.ContentBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_child_share;
    }

    @Override
    public void init() {
        page = 1;

        adapter = new RecommendedAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        list.clear();
        requestData(page);
    }

    @Override
    protected void setListener() {
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("consultId", String.valueOf(list.get(position).getId()));
                bundle.putString("url", H5Const.H5_CONSULTING_DETAIL);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 请求数据
     * @param page 页码
     */
    private void requestData(final int page) {
        RequestApi.getAttentionList(String.valueOf(App.manager.getId()), 1, page, new AbstractNetWorkCallback<RecommendedBean>() {
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
            public void onSuccess(RecommendedBean recommendedBean) {
                boolean b = page == NO1 && (recommendedBean.getContent() == null || recommendedBean.getContent().size() == 0);
                if (b) {
                    rlEmpty.setVisibility(View.VISIBLE);
                } else {
                    rlEmpty.setVisibility(View.GONE);
                    if (page != 1 && recommendedBean.getContent().size() == 0) {
                        ToastUtils.show("没有更多数据了");
                    }
                    list.addAll(recommendedBean.getContent());
                    adapter.notifyDataSetChanged();
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
        list.clear();
        adapter.notifyDataSetChanged();
        requestData(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        requestData(page);
    }
}
