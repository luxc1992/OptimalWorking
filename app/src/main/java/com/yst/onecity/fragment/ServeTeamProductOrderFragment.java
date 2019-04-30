package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.HunterOrderAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.HunterOrderListBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO7;

/**
 * 服务团队商品订单
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/21
 */
public class ServeTeamProductOrderFragment extends BaseFragment implements HunterOrderAdapter.OrderClickListener, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private List<OrderGroupBean> groupList = new ArrayList<>();
    private HunterOrderAdapter adapter;
    private AbstractDeleteDialog deleteDialog;
    private int type = 0;
    /**
     * 订单状态 0待付款 2待发货（已付款） 3待收货 4已评价 6已撤销 7已收货（待评价）8 全部
     */
    private int states = 8;
    private int pageIndex = 1;
    /**
     * 标识（0删除，1取消，2查看物流）
     */
    private int flag;
    private OrderGroupBean groupBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_order;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
        switch (type) {
            case 0:
                states = 8;
                break;
            case 1:
                states = 0;
                break;
            case 2:
                states = 2;
                break;
            case 3:
                states = 3;
                break;
            default:
                states = 7;
                break;
        }
        initDialog();
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取订单列表数据
     */
    private void getOrderList(final int page) {
        RequestApi.getHunterOrderList(App.manager.getPhoneNum(), App.manager.getUuid(), states, 1, page, new AbstractNetWorkCallback<HunterOrderListBean>() {
            @Override
            public void onSuccess(HunterOrderListBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent() != null) {
                        groupList = bean.getContent();
                        if (page == 1) {
                            if (groupList.size() == 0) {
                                smartRefreshLayout.setVisibility(View.GONE);
                                setTextImage();
                            } else {
                                smartRefreshLayout.setVisibility(View.VISIBLE);
                                adapter.onRefresh(groupList);
                            }
                        } else {
                            adapter.onLoad(groupList);
                        }
                    }
                    //设置全部展开
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        expandableListView.expandGroup(i);
                    }
                } else {
                    smartRefreshLayout.setVisibility(View.GONE);
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                smartRefreshLayout.setVisibility(View.GONE);
                ToastUtils.show(errorMsg);
            }
        });
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

    private void initDialog() {
        deleteDialog = new AbstractDeleteDialog(getActivity()) {
            @Override
            public void sureClick() {
                //0删除，1取消
                if (flag == NO0) {
                    deleteOrder();
                } else if (flag == NO1) {
                    if (groupBean.getSon() != null) {
                        List<OrderChildBean> childData = groupBean.getSon();
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < childData.size(); i++) {
                            str.append(childData.get(i).getId() + ",");
                        }
                        String oIds = str.substring(0, str.length() - 1);
                        cancleOrder(oIds);
                    }
                }
            }
        };
    }

    @Override
    public void initCtrl() {
        adapter = new HunterOrderAdapter(groupList, getActivity(), this);
        expandableListView.setAdapter(adapter);
    }

    /**
     * fragment静态传值
     */
    public static HunterOrderFragment newInstance(int type) {
        HunterOrderFragment fragment = new HunterOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setListener() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
    }

    @Override
    public void clickListener(int flag, OrderGroupBean groupBean) {
        this.groupBean = groupBean;
        this.flag = flag;
        if (flag == NO0) {
            deleteDialog.setText("确定删除该订单？", "确定", "取消");
            deleteDialog.showDialog();
        } else if (flag == NO1) {
            deleteDialog.setText("确定取消该订单？", "确定", "取消");
            deleteDialog.showDialog();
        }
    }

    /**
     * 删除订单
     */
    private void deleteOrder() {
        RequestApi.deleteOrder(App.manager.getPhoneNum(), App.manager.getUuid(), groupBean.getOrderNo(), 2, new AbstractNetWorkCallback<MsgBean>() {
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
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    smartRefreshLayout.autoRefresh();
                }
                ToastUtils.show(msgBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 取消订单
     */
    private void cancleOrder(String oIds) {
        RequestApi.cancleOrderForHunter(App.manager.getPhoneNum(), App.manager.getUuid(), oIds, 1, new AbstractNetWorkCallback<MsgBean>() {
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
            public void onSuccess(MsgBean msgBean) {
                if (msgBean != null) {
                    if (msgBean.getCode() == 1) {
                        smartRefreshLayout.autoRefresh();
                    }
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageIndex = 1;
        getOrderList(pageIndex);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageIndex++;
        getOrderList(pageIndex);
    }

    /**
     * 设置空数据页面展示
     */
    private void setTextImage() {
        if (states == NO7) {
            tvEmpty.setText("您还没有相关评价");
            ivEmpty.setImageResource(R.mipmap.empy_pingjia);
        } else {
            tvEmpty.setText("您还没有相关订单");
            ivEmpty.setImageResource(R.mipmap.empy_dingdan);
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (event.getFlag() == Constant.NO1) {
            smartRefreshLayout.autoRefresh();
            getOrderList(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
