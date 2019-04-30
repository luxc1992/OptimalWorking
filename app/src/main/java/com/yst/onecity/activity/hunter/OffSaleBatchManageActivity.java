package com.yst.onecity.activity.hunter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.OnSaleBatchManageAdapter;
import com.yst.onecity.bean.GoodsManageListBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 已下架批量管理页面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/02/09
 */
public class OffSaleBatchManageActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.gv_good_list)
    GridView gvGoodList;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_off_shelf)
    TextView tvOffShelf;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_manage_contain)
    LinearLayout llManageContain;
    private OnSaleBatchManageAdapter adapter;
    private List<GoodsManageListBean.ContentBean> goodsBeanList = new ArrayList<>();
    private int page = 1;
    /**
     * 商品拼接的id
     */
    private String pIds;
    /**
     * 商品id集合
     */
    private List<String> pIdsList = new ArrayList<>();
    /**
     * 商品上架 删除弹框
     */
    private AbstractDeleteDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_off_sale_batch_manage;
    }

    @Override
    public void initData() {
        initTitleBar("批量管理");
        smartRefreshLayout.autoRefresh(100);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        gvGoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsManageListBean.ContentBean goodsBean = goodsBeanList.get(i);
                goodsBean.setIsclicked(true);
                goodsBean.setChecked(!goodsBean.isChecked());
                adapter.notifyDataSetChanged();
                MyLog.e("OffSaleBatchManageActivity", "商品id——————" + goodsBean.getProductId());
                if (goodsBean.isChecked()) {
                    pIdsList.add(goodsBean.getProductId());
                } else {
                    pIdsList.remove(goodsBean.getProductId());
                }
                MyLog.e("OffSaleBatchManageActivity", "商品id集合——————" + new Gson().toJson(pIdsList));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        super.onResume();
        page = 1;
        goodsBeanList.clear();
        getGoodsList();
    }

    /**
     * 获取商品列表
     */
    private void getGoodsList() {
        RequestApi.getGoodsManagerList(App.manager.getPhoneNum(), App.manager.getUuid(), "1", String.valueOf(page), "10", new AbstractNetWorkCallback<GoodsManageListBean>() {
            @Override
            public void onSuccess(GoodsManageListBean goodsManageListBean) {
                if (goodsManageListBean != null && goodsManageListBean.getCode() == 1) {
                    if (page == 1) {
                        goodsBeanList = goodsManageListBean.getContent();
                    } else {
                        goodsBeanList.addAll(goodsManageListBean.getContent());
                    }
                } else {
                    if (page == 1) {
                        goodsBeanList = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                flushGoodsList();
            }

            @Override
            public void onError(final String errorMsg) {
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
                onLoad();
            }
        });

    }

    /**
     * 刷新适配器
     */
    private void flushGoodsList() {
        if (null == gvGoodList) {
            return;
        }
        if (adapter == null) {
            adapter = new OnSaleBatchManageAdapter(goodsBeanList, OffSaleBatchManageActivity.this);
            gvGoodList.setAdapter(adapter);
        } else {
            adapter.setGoodsManagerList(goodsBeanList);
        }
    }


    @OnClick({R.id.tv_off_shelf, R.id.tv_delete})
    public void onViewClick(View view) {
        if (!Utils.isClickable()){
            return;
        }
        switch (view.getId()) {
            //上架
            case R.id.tv_off_shelf:
                if (pIdsList.size() == 0) {
                    ToastUtils.show("请选择要上架的商品");
                    return;
                }
                dialog = new AbstractDeleteDialog(OffSaleBatchManageActivity.this) {
                    @Override
                    public void sureClick() {
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < pIdsList.size(); i++) {
                            stringBuffer.append(pIdsList.get(i));
                            if (i != pIdsList.size() - 1) {
                                stringBuffer.append(",");
                            }
                        }
                        pIds = stringBuffer.toString();
                        MyLog.e("OffSaleBatchManageActivity", "商品id拼接——————" + stringBuffer.toString());
                        RequestApi.goodsManageBatchOperation(pIds, App.manager.getPhoneNum(), App.manager.getUuid(), "1", new AbstractNetWorkCallback<MsgBean>() {
                            @Override
                            public void onSuccess(MsgBean msgBean) {
                                if (msgBean != null && msgBean.getCode() == NO1) {
//                                    page = 1;
//                                    goodsBeanList.clear();
//                                    getGoodsList();
//                                    pIdsList.clear();
                                    finish();
                                }
                                ToastUtils.show(msgBean.getMsg());
                            }

                            @Override
                            public void onError(final String errorMsg) {
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
                };
                dialog.setText("是否确定上架？", "确定", "取消");
                dialog.showDialog();

                break;
            //删除
            case R.id.tv_delete:
                if (pIdsList.size() == 0) {
                    ToastUtils.show("请选择要删除的商品");
                    return;
                }
                dialog = new AbstractDeleteDialog(OffSaleBatchManageActivity.this) {
                    @Override
                    public void sureClick() {
                        StringBuffer stringBuffer1 = new StringBuffer();
                        for (int i = 0; i < pIdsList.size(); i++) {
                            stringBuffer1.append(pIdsList.get(i));
                            if (i != pIdsList.size() - 1) {
                                stringBuffer1.append(",");
                            }
                        }
                        pIds = stringBuffer1.toString();
                        MyLog.e("OffSaleBatchManageActivity", "商品id拼接——————" + stringBuffer1.toString());
                        RequestApi.goodsManageBatchOperation(pIds, App.manager.getPhoneNum(), App.manager.getUuid(), "2", new AbstractNetWorkCallback<MsgBean>() {
                            @Override
                            public void onSuccess(MsgBean msgBean) {
                                if (msgBean != null && msgBean.getCode() == NO1) {
//                                    page = 1;
//                                    goodsBeanList.clear();
//                                    getGoodsList();
//                                    pIdsList.clear();
                                    finish();
                                }
                                ToastUtils.show(msgBean.getMsg());
                            }

                            @Override
                            public void onError(final String errorMsg) {
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
                };
                dialog.setText("是否确定删除？", "确定", "取消");
                dialog.showDialog();

                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getGoodsList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getGoodsList();
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
