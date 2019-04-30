package com.yst.onecity.activity.hunter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
 * 出售中批量管理页面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/02/09
 */
public class OnSaleBatchManageActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.gv_good_list)
    GridView gvGoodList;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_manage_contain)
    TextView tvManageContain;

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
     * 商品上架弹框
     */
    private AbstractDeleteDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_on_sale_batch_manage;
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
                if (goodsBean.isChecked()) {
                    pIdsList.add(goodsBean.getProductId());
                } else {
                    pIdsList.remove(goodsBean.getProductId());
                }
                MyLog.e("OnSaleBatchManageActivity", "商品id集合——————" + new Gson().toJson(pIdsList));

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        goodsBeanList.clear();
        getGoodsList();
    }

    /**
     * 获取商品列表
     */
    private void getGoodsList() {
        RequestApi.getGoodsManagerList(App.manager.getPhoneNum(), App.manager.getUuid(), "0", String.valueOf(page), "10", new AbstractNetWorkCallback<GoodsManageListBean>() {
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
                onLoad();
                dismissInfoProgressDialog();
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
            adapter = new OnSaleBatchManageAdapter(goodsBeanList, OnSaleBatchManageActivity.this);
            gvGoodList.setAdapter(adapter);
        } else {
            adapter.setGoodsManagerList(goodsBeanList);
        }
    }


    @OnClick({R.id.tv_manage_contain})
    public void onViewClick(View view) {
        if (!Utils.isClickable()){
            return;
        }
        switch (view.getId()) {
            //下架
            case R.id.tv_manage_contain:
                if (pIdsList.size() == 0) {
                    ToastUtils.show("请选择要下架的商品");
                    return;
                }
                dialog = new AbstractDeleteDialog(OnSaleBatchManageActivity.this) {
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
                        MyLog.e("OnSaleBatchManageActivity", "商品id拼接——————" + stringBuffer.toString());
                        RequestApi.goodsManageBatchOperation(pIds, App.manager.getPhoneNum(), App.manager.getUuid(), "0", new AbstractNetWorkCallback<MsgBean>() {
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
                dialog.setText("是否确定下架？", "确定", "取消");
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
