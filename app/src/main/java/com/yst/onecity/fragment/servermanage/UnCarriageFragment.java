package com.yst.onecity.fragment.servermanage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.CreateServeActivity;
import com.yst.onecity.activity.serverteam.BulkManageActivity;
import com.yst.onecity.adapter.ServerManageAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.servermanage.ServerManageBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.inter.ServiceManageInter;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已下架
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/19
 */

public class UnCarriageFragment extends BaseFragment implements OnRefreshLoadmoreListener, ServiceManageInter {

    @BindView(R.id.lv_sermanage)
    ListView lvSermanage;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.tv_create_server)
    TextView tvCreateServer;
    @BindView(R.id.tv_piliang_manage)
    TextView tvPiliangManage;
    @BindView(R.id.ll_create_bulk)
    LinearLayout llCreateBulk;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private ServerManageAdapter goodsAdapter;
    /**
     * 1.下架 2.上架 3.删除
     */
    private int type = 0;
    private int page = Constant.NO1;
    private List<ServerManageBean.ContentBean> serviceGoodsList = new ArrayList<>();
    private String diaContent = "";

    /**
     * 创建实例
     *
     * @return UnCarriageFragment
     */
    public static UnCarriageFragment newInstance() {
        return new UnCarriageFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_manage;
    }

    @Override
    public void init() {
        refreshlayout.setOnRefreshLoadmoreListener(this);
        goodsAdapter = new ServerManageAdapter(getActivity(), Constant.NO2, this);
        lvSermanage.setAdapter(goodsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshlayout.autoRefresh(500);
    }

    /**
     * 获取列表
     */
    private void getList() {
        RequestApi.getServiceList(String.valueOf(App.manager.getId()), String.valueOf(Constant.NO3), String.valueOf(page), String.valueOf(Constant.NO10), new AbstractNetWorkCallback<ServerManageBean>() {
            @Override
            public void onSuccess(ServerManageBean serverManageBean) {
                if (null != serverManageBean) {
                    if (serverManageBean.getCode() == Constant.NO1 && null != serverManageBean.getContent()) {
                        if (page == Constant.NO1) {
                            serviceGoodsList.clear();
                        }
                        serviceGoodsList.addAll(serverManageBean.getContent());
                        goodsAdapter.addData(serviceGoodsList);
                        MyLog.e("sss", "--" + serviceGoodsList.size());
                        if (serverManageBean.getContent().isEmpty()) {
                            ToastUtils.show("已加载全部数据");
                        }
                    } else {
                        if (page == 1) {
                            serviceGoodsList = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                    flushUi();
                } else {
                    flushUi();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @OnClick({R.id.tv_create_server, R.id.tv_piliang_manage})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //创建服务
            case R.id.tv_create_server:
                Bundle bundle1 =new Bundle();
                bundle1.putInt("type",1);
                JumpIntent.jump(getActivity(), CreateServeActivity.class,bundle1);
                break;
            //批量管理
            case R.id.tv_piliang_manage:
                Bundle bundle = new Bundle();
                bundle.putInt("type", Constant.NO3);
                JumpIntent.jump(getActivity(), BulkManageActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getList();
        refreshlayout.finishLoadmore(500);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = Constant.NO1;
        getList();
        refreshlayout.finishRefresh(500);
    }

    /**
     * 是否显示图片
     */
    private void flushUi() {
        if (serviceGoodsList.isEmpty()) {
            lvSermanage.setVisibility(View.GONE);
            ivEmpty.setVisibility(View.VISIBLE);
        } else {
            lvSermanage.setVisibility(View.VISIBLE);
            ivEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void xiaJia(String strId) {
    }

    @Override
    public void shangJia(String strId) {
        MyLog.e("sss", "-单个上架:" + strId);
        type = Constant.NO2;
        diaContent = "是否确定上架?";
        shoSureDialog(strId);
    }

    /**
     * 上架 编辑和删除
     */
    private void operate(String strId) {
        if (TextUtils.isEmpty(String.valueOf(type)) || TextUtils.isEmpty(strId)) {
            return;
        }
        RequestApi.serviceOperate(String.valueOf(type), strId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (null != msgBean) {
                    if (msgBean.getCode() == Constant.NO1) {
                        ToastUtils.show(msgBean.getMsg());
                        serviceGoodsList.clear();
                        page = Constant.NO1;
                        getList();
                    } else {
                        ToastUtils.show(msgBean.getMsg());
                    }
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
            }
        });
    }

    @Override
    public void edit(int position) {
        Bundle bundle =new Bundle();
        bundle.putInt("type",2);
        bundle.putInt("serviceId",serviceGoodsList.get(position).getId());
        JumpIntent.jump(getActivity(), CreateServeActivity.class,bundle);
    }

    @Override
    public void delete(String strId) {
        MyLog.e("sss", "-单个删除:" + strId);
        type = Constant.NO3;
        diaContent = "是否确定删除?";
        shoSureDialog(strId);
    }

    /**
     * 显示对话框
     */
    private void shoSureDialog(final String strId) {
        AbstractDeleteDialog dialog = new AbstractDeleteDialog(getActivity()) {
            @Override
            public void sureClick() {
                operate(strId);
            }
        };
        dialog.setText(diaContent, "确定", "取消");
        dialog.showDialog();
    }

    @Override
    public void isClick(boolean isSuccess) {

    }


}
