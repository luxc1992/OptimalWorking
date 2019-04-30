package com.yst.onecity.activity.agent;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.MyViewHolder;
import com.yst.onecity.bean.agent.AgentListBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO13;

/**
 * 经纪人中心页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */
public class AgentCenterActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.rel_center_list)
    RelativeLayout mRelCenterList;
    @BindView(R.id.txt_center_add)
    TextView mTxtCenterAdd;
    @BindView(R.id.recycler_center)
    RecyclerView mRecyclerCenter;
    @BindView(R.id.refresh_agent)
    SmartRefreshLayout mRefreshAgent;

    private List<AgentListBean.ContentBean> mList = new ArrayList<>();
    private AbstractCommonAdapter<AgentListBean.ContentBean> mAdapter;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_agent_center;
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        mList.clear();
        getTopicList();
    }

    /**
     * 停止列表刷新
     */
    private void onLoadFinish() {
        mRefreshAgent.finishRefresh(500);
        mRefreshAgent.finishLoadmore(500);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        getTopicList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getTopicList();
    }

    @Override
    public void initData() {
        initTitleBar("经纪人中心");
        mRelCenterList.setBackgroundColor(Color.TRANSPARENT);
        mRefreshAgent.autoRefresh(300);
        mRefreshAgent.setOnRefreshListener(this);
        mRefreshAgent.setOnLoadmoreListener(this);
        mRefreshAgent.setEnableRefresh(true);
        mRefreshAgent.setEnableLoadmore(true);
        initAdapter();
        getTopicList();
    }

    /**
     * 课题列表接口
     */
    private void getTopicList() {
        RequestApi.getTopicList(String.valueOf(App.manager.getId()), String.valueOf(page), new AbstractNetWorkCallback<AgentListBean>() {
            @Override
            public void onSuccess(AgentListBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() > NO0) {
                        byStatusShowUi(true);
                        mList.addAll(bean.getContent());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (page == NO1) {
                            byStatusShowUi(false);
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                byStatusShowUi(false);
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
                onLoadFinish();
            }
        });
    }

    /**
     * 根据状态显示ui
     */
    private void byStatusShowUi(boolean b) {
        if (b) {
            mTxtCenterAdd.setVisibility(View.GONE);
            mRelCenterList.setBackgroundColor(Color.TRANSPARENT);
        } else {
            mTxtCenterAdd.setVisibility(View.VISIBLE);
            mRelCenterList.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mRecyclerCenter.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerCenter.addItemDecoration(new SpacesItemDecoration(NO0, NO13));
        mAdapter = new AbstractCommonAdapter<AgentListBean.ContentBean>(this, R.layout.item_center, mList) {
            @Override
            public void convert(MyViewHolder holder, AgentListBean.ContentBean item) {
                holder.setText(R.id.txt_list_title, item.getTitle());
                holder.setText(R.id.txt_list_content, item.getContent());
                holder.setText(R.id.txt_eye, String.valueOf(item.getReadNum()));
                holder.setText(R.id.txt_zan, String.valueOf(item.getPriseNum()));
                if (TextUtils.isEmpty(item.getImageAddress())) {
                    holder.setImageResource(R.id.iv_list_bg, R.mipmap.list_bg);
                } else {
                    holder.setImageHttpUrl(R.id.iv_list_bg, item.getImageAddress());
                }
            }

            @Override
            public void bindClick(MyViewHolder holder, AgentListBean.ContentBean item) {

            }
        };
        mRecyclerCenter.setAdapter(mAdapter);
    }

    @OnClick({R.id.rel_center_list, R.id.txt_center_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_center_list:
                JumpIntent.jump(this, CourseListActivity.class);
                break;
            case R.id.txt_center_add:
                JumpIntent.jump(this, CourseListActivity.class);
                break;
            default:
                break;
        }
    }
}
