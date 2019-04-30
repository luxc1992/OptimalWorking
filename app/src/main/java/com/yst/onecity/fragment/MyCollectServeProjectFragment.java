package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.bean.ServeProjectBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * (我的)收藏-服务项目fragment
 *
 * @author liumanqing
 * @version 1.1.0
 * @date 2018/5/19
 */
public class MyCollectServeProjectFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_collect_server_project)
    ListView listView;
    @BindView(R.id.smartRefreshLayout_collect_serve)
    SmartRefreshLayout smartRefreshLayout;
    private AbstractCommonAdapter<ServeProjectBean.ContentBean> adapter;
    private List<ServeProjectBean.ContentBean> mList = new ArrayList<>();
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mycollect_serveproject;
    }

    @Override
    public void init() {
        setListener();
        adapter = new AbstractCommonAdapter<ServeProjectBean.ContentBean>(getContext(), mList, R.layout.item_server_project_list) {
            @Override
            public void convert(CommonViewHolder holder, int position, ServeProjectBean.ContentBean item) {
                holder.setText(R.id.sever_project_name, ConstUtils.getStringNoEmpty(item.getTitle()));
                holder.setText(R.id.sever_project_price, "¥ " + ConstUtils.changeEmptyStringToZero(item.getPrice()));
                Glide.with(context).load(item.getImageAddress()).error(R.mipmap.default_product_icon).into((ImageView) holder.getView(R.id.sever_project_img));
            }

        };
        listView.setAdapter(adapter);

    }

    private void getData(final int page) {
        RequestApi.getServicProjectList(page + "", "10", String.valueOf(App.manager.getId()), App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<ServeProjectBean>() {
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

            @Override
            public void onSuccess(ServeProjectBean bean) {
                onLoad();
                if (bean.getCode() == 1) {
                    if (page != 1 && bean.getContent().size() == 0) {
                        ToastUtils.show("已加载全部数据");
                    }
                    mList.addAll(bean.getContent());
                    adapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mList = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    @Override
    protected void setListener() {
        smartRefreshLayout.autoRefresh(300);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(mList.get(position).getServiceProjectId()));
                bundle.putString("url", H5Const.SERVE_TEM_DETAILS);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
            }
        });
    }


    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getData(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        getData(page);
    }


    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

}
