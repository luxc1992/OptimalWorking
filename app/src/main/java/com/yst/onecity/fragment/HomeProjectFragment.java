package com.yst.onecity.fragment;

import android.os.Bundle;
import android.widget.ListView;
import com.google.gson.Gson;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.NetworkUtils;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.HomePageAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.home.HomeProjectBean;
import com.yst.onecity.bean.home.HomeProjectContentBean;
import com.yst.onecity.bean.home.ProjectClassifyBean;
import com.yst.onecity.bean.home.ProjectClassifyContentBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.PreferenceUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import static com.yst.onecity.Constant.NO1;

/**
 * 首页课题
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/6/12
 */
public class HomeProjectFragment extends BaseFragment {
    public static final String ID = "id";
    private String classifyId;
    @BindView(R.id.listView)
    ListView listView;
    private List<HomeProjectBean> groupList = new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    private int pageIndex = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_project;
    }

    @Override
    public void init() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            classifyId = bundle.getString(ID);
            if (!NetworkUtils.isConnectNet(App.mContext)) {
                String labelJson = PreferenceUtil.getString("homeList", "");
                HomeProjectContentBean homeProjectContentBean = new Gson().fromJson(labelJson, HomeProjectContentBean.class);
                if (homeProjectContentBean != null && homeProjectContentBean.getCode() == NO1) {
                    groupList = homeProjectContentBean.getContent();
                }
            } else {
                initHomeList(pageIndex);
            }
        }
    }

    /**
     * 列表数据
     */
    private void initHomeList(final int page) {
        RequestApi.getProjectList(classifyId, page, new AbstractNetWorkCallback<HomeProjectContentBean>() {

            @Override
            public void onAfter() {
                super.onAfter();
                HomePageFragment.smartRefreshLayout.finishRefresh(500);
                HomePageFragment.smartRefreshLayout.finishLoadmore(500);
            }

            @Override
            public void onSuccess(HomeProjectContentBean homeProjectBean) {
                if (homeProjectBean.getCode() == Constant.NO1) {
                    groupList = homeProjectBean.getContent();
                    if (groupList != null) {
                        if (page == 1) {
                            homePageAdapter.onRefresh(groupList);
                        } else {
                            homePageAdapter.addData(groupList);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onSuccessString(String jsonData) {
                super.onSuccessString(jsonData);
                String id = "";
                String labelJson = PreferenceUtil.getString("homeLabel", "");
                ProjectClassifyContentBean projectClassifyContentBean = new Gson().fromJson(labelJson, ProjectClassifyContentBean.class);
                if (projectClassifyContentBean != null && projectClassifyContentBean.getCode() == NO1) {
                    List<ProjectClassifyBean> labels = projectClassifyContentBean.getContent();
                    if (labels != null) {
                        id = labels.get(0).getId();
                    }
                }
                if (page == Constant.NO1 && id.equals(classifyId)) {
                    PreferenceUtil.put("homeList", jsonData);
                }
            }
        });
    }

    public static HomeProjectFragment newInstance(String id) {
        HomeProjectFragment fragment = new HomeProjectFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initCtrl() {
        homePageAdapter = new HomePageAdapter(getActivity(), groupList);
        listView.setAdapter(homePageAdapter);
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        String msg = "page";
        if (event.getMsg().equals(msg)) {
            pageIndex = event.getFlag();
            initHomeList(pageIndex);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
