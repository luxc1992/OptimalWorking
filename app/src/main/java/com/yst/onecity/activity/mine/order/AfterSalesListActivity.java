package com.yst.onecity.activity.mine.order;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ReturnMoneyAfterSalesAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.order.AfterSalesContentBean;
import com.yst.onecity.bean.order.AfterSalesProductBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 退款/售后
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/02/28
 */
public class AfterSalesListActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private List<AfterSalesProductBean> groupList = new ArrayList<>();
    private ReturnMoneyAfterSalesAdapter adapter;
    private int pageIndex = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_return_money_after_sales;
    }

    @Override
    public void initData() {
        setTitleBarTitle("退款/售后");
        EventBus.getDefault().register(this);
        smartRefreshLayout.autoRefresh();
    }

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                EventBus.getDefault().post(new EventBean("orderrefresh"));
                break;
            default:
                break;
        }
    }
    /**
     * 获取售后列表
     */
    private void getAfterSalesList(final int page) {
        RequestApi.getAfterSalesList(Constant.userType, page, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<AfterSalesContentBean>() {
            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onSuccess(AfterSalesContentBean bean) {
                if (bean.getCode() == 1) {
                    if (bean.getContent() != null) {
                        groupList = bean.getContent();
                        if (page == 1) {
                            if (groupList.size() == 0) {
                                smartRefreshLayout.setVisibility(View.GONE);
                            } else {
                                smartRefreshLayout.setVisibility(View.VISIBLE);
                                adapter.onRefresh(groupList);
                            }
                        } else {
                            adapter.addData(groupList);
                        }
                        //设置全部展开
                        for (int i = 0; i < adapter.getGroupCount(); i++) {
                            expandableListView.expandGroup(i);
                        }
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                    smartRefreshLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                smartRefreshLayout.setVisibility(View.GONE);
            }
        });
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

    @Override
    public void initCtrl() {
        adapter = new ReturnMoneyAfterSalesAdapter(groupList, this);
        expandableListView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        super.setListener();
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageIndex = 1;
        getAfterSalesList(pageIndex);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageIndex++;
        getAfterSalesList(pageIndex);
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (event.getFlag() == Constant.NO1) {
            getAfterSalesList(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().post(new EventBean("orderrefresh"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
