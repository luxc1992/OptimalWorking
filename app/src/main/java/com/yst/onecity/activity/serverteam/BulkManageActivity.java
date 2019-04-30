package com.yst.onecity.activity.serverteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.ServiceBulkManageAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.servermanage.ServerManageBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 批量管理的页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/17
 */

public class BulkManageActivity extends BaseActivity implements OnRefreshLoadmoreListener, ServiceBulkManageAdapter.onPoClickInter {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.lv_bulk_data)
    ListView lvBulkData;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.tv_xiajia)
    TextView tvXiajia;
    @BindView(R.id.tv_shangjia)
    TextView tvShangjia;
    @BindView(R.id.tv_deltet)
    TextView tvDeltet;
    @BindView(R.id.ll_shangjia_delete)
    LinearLayout llShangjiaDelete;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.fram_caozuo)
    FrameLayout framCaozuo;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private List<String> poList = new ArrayList<>();
    private int type;
    private String memberId = "623";
    private int page = 1;
    private List<ServerManageBean.ContentBean> serviceGoodsList = new ArrayList<>();
    private String dialogContent = "";
    private boolean isXiaJia;
    private boolean isShangJia;
    private boolean isDelete;
    private ServiceBulkManageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bulk_manage;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.more_manage));
        tvRight.setText(getString(R.string.compile));
        tvRight.setTextColor(ContextCompat.getColor(this, R.color.color_tab_select));
        tvRight.setVisibility(View.VISIBLE);
        refreshlayout.setOnRefreshLoadmoreListener(this);
        refreshlayout.autoRefresh(500);
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            type = bundle.getInt("type");
        }
        adapter = new ServiceBulkManageAdapter(this, type, this);
        lvBulkData.setAdapter(adapter);
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.tv_right, R.id.tv_xiajia, R.id.tv_deltet, R.id.tv_shangjia})
    public void click(View view) {

        switch (view.getId()) {
            case R.id.tv_right:
                adapter.clearList();
                if (serviceGoodsList.isEmpty()) {
                    tvRight.setEnabled(false);
                } else {
                    tvRight.setEnabled(true);
                }
                String rightString = tvRight.getText().toString();
                if (getString(R.string.compile).equals(rightString)) {
                    adapter.isEdit(true);
                    refreshlayout.setEnableLoadmore(false);
                    adapter.notifyDataSetChanged();
                    tvRight.setText(getString(R.string.finish));
                    framCaozuo.setVisibility(View.VISIBLE);
                    switch (type) {
                        case Constant.NO1:
                            llShangjiaDelete.setVisibility(View.GONE);
                            tvXiajia.setVisibility(View.VISIBLE);
                            tvXiajia.setText(getString(R.string.xiajia));
                            break;
                        case Constant.NO2:
                            llShangjiaDelete.setVisibility(View.VISIBLE);
                            tvShangjia.setVisibility(View.GONE);
                            tvXiajia.setVisibility(View.GONE);
                            break;
                        case Constant.NO3:
                            llShangjiaDelete.setVisibility(View.VISIBLE);
                            tvXiajia.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                } else {
                    adapter.isEdit(false);
                    adapter.notifyDataSetChanged();
                    tvRight.setText(getString(R.string.compile));
                    refreshlayout.setEnableLoadmore(true);
                    framCaozuo.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_xiajia:
                isXiaJia = true;
                isShangJia = false;
                isDelete = false;
                dialogContent = "是否确定下架？";
                StringBuilder str = new StringBuilder("");
                if (null != poList) {
                    if (poList.isEmpty()) {
                        ToastUtils.show(getString(R.string.weixuantishi));
                    } else {
                        for (int i = 0; i < poList.size(); i++) {
                            str.append(poList.get(i));
                            if (i != poList.size() - 1) {
                                str.append(",");
                            }
                        }
                        MyLog.e("sss", "-批量下架:" + str);
                        showSureDialog(str.toString());
                    }
                }
                break;
            case R.id.tv_deltet:
                isXiaJia = false;
                isShangJia = false;
                isDelete = true;
                dialogContent = "是否确定删除？";
                StringBuilder strDelete = new StringBuilder("");
                if (null != poList) {
                    if (poList.isEmpty()) {
                        ToastUtils.show(getString(R.string.weixuantishi));
                    } else {
                        for (int i = 0; i < poList.size(); i++) {
                            strDelete.append(poList.get(i));
                            if (i != poList.size() - 1) {
                                strDelete.append(",");
                            }
                        }
                        MyLog.e("sss", "-批量删除:" + strDelete);
                        showSureDialog(strDelete.toString());
                    }
                }
                break;
            case R.id.tv_shangjia:
                isXiaJia = false;
                isShangJia = true;
                isDelete = false;
                dialogContent = "是否确定上架？";
                StringBuilder strShangJia = new StringBuilder("");
                if (null != poList) {
                    if (poList.isEmpty()) {
                        ToastUtils.show(getString(R.string.weixuantishi));
                    } else {
                        for (int i = 0; i < poList.size(); i++) {
                            strShangJia.append(poList.get(i));
                            if (i != poList.size() - 1) {
                                strShangJia.append(",");
                            }
                        }
                        MyLog.e("sss", "-批量上架:" + strShangJia);
                        showSureDialog(strShangJia.toString());
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 显示对话框
     */
    private void showSureDialog(final String poStr) {
        AbstractDeleteDialog dialog = new AbstractDeleteDialog(this) {
            @Override
            public void sureClick() {
                if (isDelete) {
                    operate(poStr, Constant.NO3);
                }
                if (isShangJia) {
                    operate(poStr, Constant.NO2);
                }
                if (isXiaJia) {
                    operate(poStr, Constant.NO1);
                }
            }
        };
        dialog.setText(dialogContent, "确定", "取消");
        dialog.showDialog();
    }

    /**
     * 上下架和删除
     */
    private void operate(String strId, int type) {
        RequestApi.serviceOperate(String.valueOf(type), strId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (null != msgBean) {
                    if (msgBean.getCode() == Constant.NO1) {
                        ToastUtils.show(msgBean.getMsg());
                        refreshlayout.autoRefresh(500);
                    } else {
                        ToastUtils.show(msgBean.getMsg());
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
     * 获取列表
     */
    private void getList() {
        RequestApi.getServiceList(String.valueOf(App.manager.getId()), String.valueOf(type), String.valueOf(page), String.valueOf(Constant.NO10), new AbstractNetWorkCallback<ServerManageBean>() {
            @Override
            public void onSuccess(ServerManageBean serverManageBean) {
                if (null != serverManageBean) {
                    if (serverManageBean.getCode() == Constant.NO1 && null != serverManageBean.getContent()) {
                        if (page == Constant.NO1) {
                            serviceGoodsList.clear();
                        }
                        if (type == Constant.NO2) {
                            for (int i = 0; i < serverManageBean.getContent().size(); i++) {
                                String examineStatus = serverManageBean.getContent().get(i).getExamineStatus();
                                int state = Integer.parseInt(examineStatus);
                                if (state == Constant.NO0) {
                                    serviceGoodsList.add(serverManageBean.getContent().get(i));
                                }
                            }
                        } else {
                            serviceGoodsList.addAll(serverManageBean.getContent());
                        }
                        adapter.addData(serviceGoodsList);
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

    /**
     * 是否显示图片
     */
    private void flushUi() {
        if (serviceGoodsList.isEmpty()) {
            refreshlayout.setVisibility(View.GONE);
            ivEmpty.setVisibility(View.VISIBLE);
        } else {
            refreshlayout.setVisibility(View.VISIBLE);
            ivEmpty.setVisibility(View.GONE);
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
        adapter.isEdit(false);
        framCaozuo.setVisibility(View.GONE);
        refreshlayout.setEnableLoadmore(true);
        tvRight.setText(getString(R.string.compile));
        getList();
        refreshlayout.finishRefresh(500);
    }

    @Override
    public void onPo(ArrayList<String> list) {
        this.poList.clear();
        this.poList.addAll(list);
    }
}
