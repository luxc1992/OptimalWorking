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
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imsdk.ChatType;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.mine.order.ApplicationForRefundActivity;
import com.yst.onecity.activity.mine.order.CheckServiceOrderEvaluateActivity;
import com.yst.onecity.activity.mine.order.PendingRefundAfterActivity;
import com.yst.onecity.activity.mine.order.RefundAfterActivity;
import com.yst.onecity.activity.mine.order.ServiceOrderEvaluateActivity;
import com.yst.onecity.adapter.ServiceOrderAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.order.OrderChildBean;
import com.yst.onecity.bean.order.OrderGroupBean;
import com.yst.onecity.bean.order.ServiceOrderListBean;
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
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;
import static com.yst.onecity.Constant.NO4;
import static com.yst.onecity.Constant.NO5;
import static com.yst.onecity.Constant.NO6;
import static com.yst.onecity.Constant.NO7;
import static com.yst.onecity.Constant.NO8;

/**
 * 我的服务订单
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/5/21
 */
public class MyServiceOrderFragment extends BaseFragment implements ServiceOrderAdapter.OrderClickListener, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.my_service_order_smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.my_service_order_expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.my_service_order_tv_empty)
    TextView tvEmpty;
    @BindView(R.id.my_service_order_iv_empty)
    ImageView ivEmpty;

    private ServiceOrderAdapter adapter;
    private AbstractDeleteDialog deleteDialog;
    private int type = 0;
    /**
     * 请求列表数据参数  0、待付款   1、待使用  2、待评价 4、退款售后 8全部订单
     */
    private int states = 8;
    private int pageIndex = 1;
    /**
     * 标识（0删除订单，1取消订单）
     */
    private int flag;
    private OrderGroupBean groupBean;

    /**
     * //from 用户类型 0 普通用户(买家)   1 服务团队（卖家）
     */
    private int from;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_service_order;
    }

    @Override
    public void init() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
            from = bundle.getInt("from");
        }
        //states 订单状态  0、待付款   1、待使用  2、待评价 3、退款售后 8全部订单
        switch (type){
            case NO0:
                states = 8;
                break;
            case NO1:
                states = 0;
                break;
            case NO2:
                states = 1;
                break;
            case NO3:
                states = 2;
                break;
            case NO4:
                states = 4;
                break;
            default:
                break;
        }
        initDialog();
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 获取订单列表数据
     */
    private void getOrderList(final int page) {
        RequestApi.getServiceOrderList(App.manager.getUuid(),App.manager.getPhoneNum(),states, page, App.manager.getId(), from, new AbstractNetWorkCallback<ServiceOrderListBean>() {
            @Override
            public void onSuccess(ServiceOrderListBean bean) {
                List<OrderGroupBean> groupList = new ArrayList<>();

                if (bean.getCode() == NO1) {
                    if (bean.getContent() != null) {
                        List<ServiceOrderListBean.ContentBean> beanList = bean.getContent();
                        for(int i=0;i<beanList.size();i++){
                            List<OrderChildBean> cList = new ArrayList<>();
                            OrderChildBean ocb = new OrderChildBean();
                            ocb.setName(beanList.get(i).getTitle());
                            ocb.setNum(beanList.get(i).getSalesNum());
                            ocb.setSPrice(Double.toString(beanList.get(i).getPrice()));
                            ocb.setAddress(beanList.get(i).getImageAddress());
                            ocb.setCreatedTime(beanList.get(i).getCreatedTime());
                            cList.add(ocb);
                            OrderGroupBean ogb = new OrderGroupBean();
                            ogb.setStatus(beanList.get(i).getStatus());
                            ogb.setNickName(beanList.get(i).getName());
                            ogb.setTotal_price(Double.toString(beanList.get(i).getPrice()));
                            ogb.setSon(cList);
                            ogb.setId(Integer.toString(beanList.get(i).getId()));
                            ogb.setUuid(beanList.get(i).getUuid());
                            ogb.setOrderNo(beanList.get(i).getOrderNo());
                            groupList.add(ogb);
                        }

                        if (page == 1) {
                            if (groupList.size() == 0) {
                                smartRefreshLayout.setVisibility(View.GONE);
                                setTextImage();
                            } else {
                                smartRefreshLayout.setVisibility(View.VISIBLE);
                                adapter.onRefresh(groupList,from, states);
                            }
                        } else {
                            if(groupList.size() > 0) {
                                adapter.onLoad(groupList, from, states);
                            }
                        }
                        //设置全部展开
                        for (int i = 0; i < adapter.getGroupCount(); i++) {
                            expandableListView.expandGroup(i);
                        }
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

    /**
     * 初始化 删除/取消 订单 弹框
     */
    private void initDialog() {
        deleteDialog = new AbstractDeleteDialog(getActivity()) {
            @Override
            public void sureClick() {
                //1删除订单，2取消订单
                if (flag == NO1) {
                    deleteOrder();
                } else if (flag == NO2) {
                    String orderId = groupBean.getId();
                    cancelServiceOrder(orderId);
                }
            }
        };
    }

    @Override
    public void initCtrl() {
        List<OrderGroupBean> groupList = new ArrayList<>();
        adapter = new ServiceOrderAdapter(groupList, getActivity(), this,from, states);
        expandableListView.setAdapter(adapter);
    }

    /**
     * fragment静态传值
     */
    public static MyServiceOrderFragment newInstance(int type, int from) {
        MyServiceOrderFragment fragment = new MyServiceOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("from", from);
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("groupBean",groupBean);
        bundle.putInt("from",from);
        switch (flag){
            //联系团队
            case NO0:
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(groupBean.getNickName());
                intentChatEntity.setAcceptId(groupBean.getUuid());
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(getContext(), intentChatEntity);
                break;
            //删除订单
            case NO1:
                deleteDialog.setText("确定删除该订单？", "确定", "取消");
                deleteDialog.showDialog();
                break;
            //取消订单（卖家）
            case NO2:
                deleteDialog.setText("确定取消该订单？", "确定", "取消");
                deleteDialog.showDialog();
                break;
            //立即付款
            case NO3:
                String orderId = groupBean.getId();
                bundle.putString("orderId", orderId);
                bundle.putString("payMoney", groupBean.getTotal_price());
                bundle.putString("createdTime", groupBean.getSon().get(0).getCreatedTime());
                bundle.putString("orderNo", groupBean.getOrderNo());
                bundle.putString("url", H5Const.PAYMENT);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
                break;
            //申请退款
            case NO4:
                JumpIntent.jump(getActivity(), ApplicationForRefundActivity.class,bundle);
                break;
            //评价
            case NO5:
                JumpIntent.jump(getActivity(), ServiceOrderEvaluateActivity.class,bundle);
                break;
            //查看评价
            case NO6:
                JumpIntent.jump(getActivity(), CheckServiceOrderEvaluateActivity.class,bundle);
                break;
            //查看详情(买家
            case NO7:
                JumpIntent.jump(getActivity(), RefundAfterActivity.class,bundle);
                break;
            //查看详情(卖家
            case NO8:
                JumpIntent.jump(getActivity(), PendingRefundAfterActivity.class,bundle);
                break;
            default:
                break;
        }
    }

    /**
     * 删除订单
     */
    private void deleteOrder() {
        RequestApi.deleteServiceOrder(App.manager.getUuid(),App.manager.getPhoneNum(), App.manager.getId(), groupBean.getId(), Integer.toString(from), new AbstractNetWorkCallback<MsgBean>() {
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
    private void cancelServiceOrder(String orderId) {
        RequestApi.cancelServiceOrder(App.manager.getPhoneNum(), App.manager.getUuid(),App.manager.getId(), orderId, new AbstractNetWorkCallback<MsgBean>() {
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
        tvEmpty.setText("您还没有相关订单");
        ivEmpty.setImageResource(R.mipmap.empy_dingdan);
    }

    @Subscribe
    public void onEventMainThread(EventBean eventBean){
        String msg = "MyServiceOrderFragment";
        if(msg.equals(eventBean.getMsg())){
            smartRefreshLayout.autoRefresh();
            getOrderList(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
