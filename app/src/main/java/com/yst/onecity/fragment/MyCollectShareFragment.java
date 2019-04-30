package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.yst.onecity.adapter.MyCollectShareAdapter;
import com.yst.onecity.bean.MyCollectShareBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * (我的)收藏-分享fragment
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/19
 */
public class MyCollectShareFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_collect_share)
    ListView listView;
    @BindView(R.id.smartRefreshLayout_collect_share)
    SmartRefreshLayout smartRefreshLayout;
    private int page = 1;

    private MyCollectShareAdapter adapter;
    private List<MyCollectShareBean.ContentBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mycollect_share;
    }

    @Override
    public void init() {
        list.clear();
        getCollectShare(page);
        adapter = new MyCollectShareAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }

    private void getCollectShare(final int page) {
        RequestApi.getCollectShare(page + "", "5", App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MyCollectShareBean>() {
            @Override
            public void onAfter() {
                super.onAfter();
                onLoad();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(MyCollectShareBean myCollectShareBean) {
                if (myCollectShareBean.getCode() == 1) {
                    if (page != 1 && myCollectShareBean.getContent().size()==0) {
                        ToastUtils.show("没有更多数据了");
                    }
                    if (myCollectShareBean.getContent() != null) {
                        list.addAll(myCollectShareBean.getContent());
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    ToastUtils.show(myCollectShareBean.getMsg());
                }

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
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


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        list.clear();
        adapter.notifyDataSetChanged();
        getCollectShare(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getCollectShare(page);
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
