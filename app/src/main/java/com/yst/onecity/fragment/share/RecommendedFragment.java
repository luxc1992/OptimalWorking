package com.yst.onecity.fragment.share;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.NetworkUtils;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.security.PreferenceUtil;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.home.SearchHomeActivity;
import com.yst.onecity.adapter.RecommendedAdapter;
import com.yst.onecity.bean.RecommendedBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 推荐Fragment
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/16
 */
public class RecommendedFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.smartRefreshLayout_recommended)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.list_recommended)
    ListView listView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private int page = 1;
    private RecommendedAdapter adapter;
    private List<RecommendedBean.ContentBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommended;
    }

    @Override
    public void init() {

        MyLog.e("aaa", PreferenceUtil.getString("RecommendedFragment", ""));
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        adapter = new RecommendedAdapter(getContext(), list);
        page = 1;
        list.clear();

        if (!NetworkUtils.isConnectNet(getContext())) {
            String data = PreferenceUtil.getString("RecommendedFragment", "");
            if (!TextUtils.isEmpty(data)) {
                RecommendedBean contentBean = new Gson().fromJson(data, RecommendedBean.class);
                list.addAll(contentBean.getContent());
                adapter.notifyDataSetChanged();
            }
        } else {
            //请求数据
            requestData(page);
        }
        listView.setAdapter(adapter);

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
     */
    private void requestData(final int page) {
        RequestApi.getRecommendedList("1", "1", String.valueOf(App.manager.getId()), page, new AbstractNetWorkCallback<RecommendedBean>() {
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

                if (recommendedBean.getCode() == NO1) {
                    boolean b = (page == NO1 && (recommendedBean.getContent() == null || recommendedBean.getContent().size() == 0));
                    if (b) {
                        rlEmpty.setVisibility(View.VISIBLE);
                    } else {
                        rlEmpty.setVisibility(View.GONE);
                        if (page != 1 && recommendedBean.getContent().size() == 0) {
                            ToastUtils.show("没有更多数据了");
                        }
                        list.addAll(recommendedBean.getContent());
                        if (page == NO1) {
                            PreferenceUtil.put("RecommendedFragment", new Gson().toJson(recommendedBean));
                        }

                    }
                } else {
                    ToastUtils.show(recommendedBean.getMsg());
                    rlEmpty.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
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

    private void loadFinish() {
        if (smartRefreshLayout == null) {
            return;
        }
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
