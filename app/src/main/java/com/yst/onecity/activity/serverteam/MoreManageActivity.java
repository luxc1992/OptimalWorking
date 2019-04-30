package com.yst.onecity.activity.serverteam;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.goodsmanage.GoodsMoewManageAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.goodsmanage.GoodsBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.inter.ServiceManageInter;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO2;

/**
 * 商品管理的批量管理
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/19
 */

public class MoreManageActivity extends BaseActivity implements OnRefreshListener, OnRefreshLoadmoreListener, ServiceManageInter {
    @BindView(R.id.gv_good_list)
    GridView gvGoodList;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.tv_xiajia)
    TextView tvXiajia;
    @BindView(R.id.tv_shangjia)
    TextView tvShangjia;
    @BindView(R.id.tv_deltet)
    TextView tvDeltet;
    @BindView(R.id.ll_shangjia_delete)
    LinearLayout llShangjiaDelete;
    @BindView(R.id.fram_caozuo)
    FrameLayout framCaozuo;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private AbstractDeleteDialog dialog;
    String content = "";
    private int type;
    private int page = 1;
    private List<GoodsBean.ContentBean> goodsList=new ArrayList<>();
    private ArrayList<String> proIdList = new ArrayList<>();
    private boolean isShangJia;
    private boolean isDelete;
    private GoodsMoewManageAdapter manageAdapter;
    private String idString = "";
    private int operateType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_more_manage;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.more_manage));
        Intent intent = getIntent();
        if (null != intent) {
            type = intent.getExtras().getInt("type");
            switch (type) {
                /*
                 * 上架中
                 */
                case Constant.NO1:
                    tvXiajia.setVisibility(View.VISIBLE);
                    llShangjiaDelete.setVisibility(View.GONE);
                    break;
                  /*
                 * 下架
                 */
                case NO0:
                    tvXiajia.setVisibility(View.GONE);
                    llShangjiaDelete.setVisibility(View.VISIBLE);
                    break;
                 /*
                 * 待上架
                 */
                case NO2:
                    tvXiajia.setVisibility(View.GONE);
                    llShangjiaDelete.setVisibility(View.VISIBLE);
                    tvShangjia.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
        manageAdapter = new GoodsMoewManageAdapter(this, this, type);
        gvGoodList.setAdapter(manageAdapter);
        refresh.setOnRefreshLoadmoreListener(this);
        refresh.autoRefresh(500);
    }


    /**
     * 获取批量管理的列表
     */
    private void getGoodsList() {
        RequestApi.getGoodsList(App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(type), String.valueOf(page), String.valueOf(Constant.NO10), new AbstractNetWorkCallback<GoodsBean>() {
            @Override
            public void onSuccess(GoodsBean goodsManageListBean) {
                if (goodsManageListBean != null && goodsManageListBean.getCode() == 1) {
                    if (page == 1) {
                        goodsList.clear();
                    }
                    if (type == Constant.NO2) {
                        for (int i = 0; i < goodsManageListBean.getContent().size(); i++) {
                            if (goodsManageListBean.getContent().get(i).getExamineStatus() == Constant.NO0) {
                                goodsList.add(goodsManageListBean.getContent().get(i));
                            }
                        }
                    } else {
                        goodsList.addAll(goodsManageListBean.getContent());
                    }
                } else {
                    if (page == 1) {
                        goodsList = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                if (goodsList.isEmpty()) {
                    ivEmpty.setVisibility(View.VISIBLE);
                } else {
                    ivEmpty.setVisibility(View.GONE);
                }
                manageAdapter.addData(goodsList);
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getGoodsList();
        refreshlayout.finishRefresh(500);
    }

    @OnClick({R.id.tv_xiajia, R.id.tv_shangjia, R.id.tv_deltet})
    public void onDealClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        if (TextUtils.isEmpty(idString)){
            ToastUtils.show("请选择要操作的商品");
            return;
        }
        switch (view.getId()) {
            case R.id.tv_xiajia:
                operateType = Constant.NO0;
                content = "是否确定下架?";
                showDialogToDeal(idString);
                break;
            case R.id.tv_shangjia:
                operateType = Constant.NO1;
                content = "是否确定上架?";
                isDelete = false;
                isShangJia = true;
                showDialogToDeal(idString);
                break;
            case R.id.tv_deltet:
                operateType = Constant.NO2;
                content = "是否确定删除?";
                isDelete = true;
                isShangJia = false;
                showDialogToDeal(idString);
                break;
            default:
                break;
        }
    }

    /**
     * 显示弹出框
     */
    private void showDialogToDeal(final String tab) {
        dialog = new AbstractDeleteDialog(this) {
            @Override
            public void sureClick() {
                operate();
            }
        };

        dialog.setText(content, getString(R.string.ok), getString(R.string.cancle));
        dialog.setButtonColor(ContextCompat.getColor(MoreManageActivity.this, R.color.black), ContextCompat.getColor(MoreManageActivity.this, R.color.color_FF4852));
        dialog.showDialog();
    }

    /**
     * 批量操作
     */
    private void operate() {
        MyLog.e("sss","idstring-------"+idString);
        RequestApi.goodsManageBatchOperation(idString, App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(operateType), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (null != msgBean) {
                    if (msgBean.getCode() == Constant.NO1) {
                        getGoodsList();
                    } else {
                        ToastUtils.show(msgBean.getMsg());
                    }
                }
                idString = "";
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

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getGoodsList();
        refreshlayout.finishLoadmore(500);

    }

    @Override
    public void xiaJia(String strId) {
        MyLog.e("sss", "--下架——" + strId);
        idString = "";
        idString = strId;
    }

    @Override
    public void shangJia(String strId) {
        MyLog.e("sss", "--上架——" + strId);
        idString = "";
        idString = strId;
    }

    @Override
    public void edit(int position) {

    }

    @Override
    public void delete(String strId) {
        MyLog.e("sss", "--删除——" + strId);
        idString = "";
        idString = strId;
    }

    @Override
    public void isClick(boolean isSuccess) {

    }
}
