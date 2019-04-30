package com.yst.onecity.fragment.share;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.bean.ServerProjectBean;
import com.yst.onecity.fragment.LazyBaseFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 服务项目Fragment
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/17
 */
public class ServerProjectFragment extends LazyBaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.smartRefreshLayout_server_project)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.lv_server_project)
    ListView listView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private int page = 1;
    private AbstractCommonAdapter<ServerProjectBean.ContentBean> adapter;
    private List<ServerProjectBean.ContentBean> list = new ArrayList<>();
    private String sortId;
    /**
     * type = 0  关注的
     * type = 1 行业的
     */
    private int type = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_server_project;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        page = 1;
        if (bundle != null) {
            sortId = bundle.containsKey("sortId") ? bundle.get("sortId").toString() : "";
            type = bundle.getInt("type", 0);
        }

        adapter = new AbstractCommonAdapter<ServerProjectBean.ContentBean>(getContext(), list, R.layout.item_server_project_list) {
            @Override
            public void convert(CommonViewHolder holder, int position, ServerProjectBean.ContentBean item) {
                holder.setText(R.id.sever_project_name, ConstUtils.getStringNoEmpty(item.getTitle()));
                holder.setText(R.id.sever_project_price, "¥ " + ConstUtils.changeEmptyStringToZero(item.getPrice()));

                Glide.with(context).load(item.getImageAddress()).into((ImageView) holder.getView(R.id.sever_project_img));
            }
        };
        listView.setAdapter(adapter);

    }

    /**
     * 通过行业分类id 获取服务项目
     *
     * @param page
     * @param sortId
     */
    private void requestServerProjectByIndustryId(final int page, String sortId) {

        RequestApi.getServerProjectByIndustrySortId(String.valueOf(App.manager.getId()), sortId, page, new AbstractNetWorkCallback<ServerProjectBean>() {
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
            public void onSuccess(ServerProjectBean recommendedBean) {
                if (recommendedBean.getCode() == NO1){
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
                }else{
                    ToastUtils.show(recommendedBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    protected void loadData() {
        if (type == 0) {
            list.clear();
            requestData(page);
        } else {
            list.clear();
            requestServerProjectByIndustryId(page, sortId);
        }
    }

    @Override
    protected void setListener() {
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(list.get(position).getId()));
                bundle.putString("url", H5Const.SERVE_TEM_DETAILS);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 获取关注的服务项目列表
     *
     * @param page
     */
    private void requestData(final int page) {
        RequestApi.getAttentionList(String.valueOf(App.manager.getId()), 0, page, new AbstractNetWorkCallback<ServerProjectBean>() {
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
            public void onSuccess(ServerProjectBean recommendedBean) {
                if (recommendedBean.getCode() ==NO1){
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
                }else{
                    ToastUtils.show(recommendedBean.getMsg());
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
        if (type == 0) {
            requestData(page);
        } else {
            requestServerProjectByIndustryId(page, sortId);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        if (type == 0) {
            requestData(page);
        } else {
            requestServerProjectByIndustryId(page, sortId);
        }
    }

    public static ServerProjectFragment newInstance(int sortId, int type) {
        Bundle args = new Bundle();
        args.putInt("sortId", sortId);
        args.putInt("type", type);
        ServerProjectFragment fragment = new ServerProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
