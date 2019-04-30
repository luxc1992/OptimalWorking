package com.yst.onecity.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.AddShareAdapter;
import com.yst.onecity.adapter.AddShareSearchAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.agent.ByIdBean;
import com.yst.onecity.bean.search.SearchShareBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.GONE;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.VISIBILITY;

/**
 * 添加分享fragment
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/18
 */
public class AddShareFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.lv_share)
    ListView mLvShare;
    @BindView(R.id.refresh_share)
    SmartRefreshLayout mRefreshShare;

    private List<ByIdBean.ContentBean> mList = new ArrayList<>();
    private List<ByIdBean.ContentBean> list = new ArrayList<>();
    private List<SearchShareBean.ContentBean> searchList = new ArrayList<>();
    private AddShareAdapter mAdapter;
    private AddShareSearchAdapter mSearchAdapter;
    private String id;
    private int page = 1;
    private String search;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_share;
    }

    @Override
    public void init() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.containsKey("id") ? bundle.get("id").toString() : "";
        }
        Log.i("liumanqing id.....",id+"");
        mRefreshShare.setOnRefreshListener(this);
        mRefreshShare.setOnLoadmoreListener(this);
        mRefreshShare.setEnableRefresh(true);
        mRefreshShare.setEnableLoadmore(true);
        if (String.valueOf(NO0).equals(id)) {
            byIdRequestData(String.valueOf(NO0));
        } else {
            byIdRequestData(id);
        }
        initAdapter();
    }

    /**
     * 根据导航标题id请求不同fragment下的列表数据
     *
     * @param id 导航标题id
     */
    private void byIdRequestData(String id) {
        RequestApi.getConsultation(id, String.valueOf(page), new AbstractNetWorkCallback<ByIdBean>() {
            @Override
            public void onSuccess(ByIdBean byIdBean) {
                if (byIdBean.getCode() == NO1) {
                    if (byIdBean.getContent().size() > NO0) {
                        mList.addAll(byIdBean.getContent());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (page != NO1) {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                } else {
                    ToastUtils.show(byIdBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
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
     * 停止列表刷新
     */
    private void onLoadFinish() {
        mRefreshShare.finishRefresh(500);
        mRefreshShare.finishLoadmore(500);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        mAdapter.notifyDataSetChanged();
        if (String.valueOf(NO0).equals(id)) {
            byIdRequestData(String.valueOf(NO0));
            if (!TextUtils.isEmpty(search)) {
                requestSearchData(search, page);
            }
        } else {
            byIdRequestData(id);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        if (String.valueOf(NO0).equals(id)) {
            byIdRequestData(String.valueOf(NO0));
            if (!TextUtils.isEmpty(search)) {
                requestSearchData(search, page);
            }
        } else {
            byIdRequestData(id);
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mAdapter = new AddShareAdapter(getActivity(), mList);
        mLvShare.setAdapter(mAdapter);
    }

    /**
     * 获取搜索结果数据
     *
     * @param search 搜索关键字
     * @param page 页数
     */
    public void requestSearchData(String search, final int page) {
        RequestApi.getConsultationByTitle(search, String.valueOf(page), new AbstractNetWorkCallback<ByIdBean>() {
            @Override
            public void onSuccess(ByIdBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() == NO0) {
                        if (page == NO1) {
                            ToastUtils.show("没有搜索结果，换个关键字再试试吧~");
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    } else {
                        mList.clear();
                        mList.addAll(bean.getContent());
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                onLoadFinish();
                dismissInfoProgressDialog();
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (VISIBILITY.equals(event.getMsg())) {
            mRefreshShare.setEnableRefresh(false);
            mRefreshShare.setEnableLoadmore(false);
            mAdapter.isEdit(event.isEdit());
        } else if (GONE.equals(event.getMsg())) {
            mRefreshShare.setEnableRefresh(true);
            mRefreshShare.setEnableLoadmore(true);
            mAdapter.isEdit(event.isEdit());
            list.clear();
            for (int i = 0; i < mList.size(); i++) {
                boolean click = mList.get(i).isClick();
                if (click) {
                    ByIdBean.ContentBean bean = new ByIdBean.ContentBean();
                    bean.setTitle(mList.get(i).getTitle());
                    bean.setId(mList.get(i).getId());
                    bean.setClick(true);
                    list.add(bean);
                }
            }
            MyLog.e("tag", "list==="+list.size());

            EventBus.getDefault().post(new EventBean("list", list));
        } else if (event.getFlag() == NO1) {
            search = event.getMsg();
            if (String.valueOf(NO0).equals(id)) {
                if (!TextUtils.isEmpty(search)) {
                    requestSearchData(search, NO1);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 创建一个fragment的实例，传入id
     *
     * @param id 导航栏标题id
     * @return
     */
    public static AddShareFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        AddShareFragment fragment = new AddShareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
            super.onDestroy();
        }
    }
}
