package com.yst.onecity.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ProjectPlanAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ProjectPlanBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.AbstractCommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目计划fragment
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/24
 */
public class ProjectPlanFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_projectplan)
    ListView listView;
    @BindView(R.id.smartRefreshLayout_project_plan)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty_project_plan)
    TextView empty;
    private ProjectPlanAdapter adapter;
    private List<ProjectPlanBean.ContentBean> list = new ArrayList<>();
    private int page;
    private AbstractCommonDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project_plan;
    }

    @Override
    public void init() {
        //自动刷新
        smartRefreshLayout.autoRefresh(300);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        list.clear();
        getInfo();
    }

    /**
     * 请求数据
     */
    public void getInfo() {
        RequestApi.myIssueProjectPlan("2", App.manager.getUuid(), App.manager.getPhoneNum(), page + "", 5 + "", new AbstractNetWorkCallback<ProjectPlanBean>() {
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
            public void onSuccess(ProjectPlanBean bean) {
                onLoad();
                if (bean != null && bean.getCode() == 1) {
                    if (page == 1) {
                        list = bean.getContent();
                    } else {
                        if (bean.getContent() != null) {
                            list.addAll(bean.getContent());
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }

                    }
                    flushAdapter();
                } else {
                    if (page == 1) {
                        list = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 刷新适配器
     */
    private void flushAdapter() {
        if (null == list) {
            return;
        }
        if (adapter == null) {
            adapter = new ProjectPlanAdapter(list, getActivity(), myOnClick);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(list);
        }
    }

    /**
     * 创建一个资讯frament的实例，传入资讯类型id
     *
     * @return 具体fragment
     */
    public static ProjectPlanFragment newInstance() {
        ProjectPlanFragment fragment = new ProjectPlanFragment();
        return fragment;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getInfo();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getInfo();
    }

    /**
     * 停止刷新
     */
    private void onLoad() {
        smartRefreshLayout.finishRefresh(1000);
        smartRefreshLayout.finishLoadmore(1000);
    }

    /**
     * 删除项目计划
     */
    private void deleteProjectPlan(String id) {
        RequestApi.myIssueDeleteProjectPlan(id, App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    ToastUtils.show(msgBean.getMsg());
                    page = 1;
                    getInfo();
                    dialog.dismissDialog();
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 点击监听
     */
    private View.OnClickListener myOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //删除项目计划
                case R.id.line_delete_project_plan:
                    showDialog((String) view.getTag());
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 删除项目计划弹出框
     *
     * @param id
     */
    public void showDialog(final String id) {
        dialog = new AbstractCommonDialog(getActivity()) {
            @Override
            public void sureClick() {
                deleteProjectPlan(id);
                dismissDialog();
            }
        };
        dialog.setText("提示", "是否确定删除？", "确定", "取消");
        dialog.showDialog();
    }
}
