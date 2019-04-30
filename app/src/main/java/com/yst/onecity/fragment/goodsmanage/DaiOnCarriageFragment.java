package com.yst.onecity.fragment.goodsmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.issue.AddNewCommodityActivity;
import com.yst.onecity.activity.serverteam.MoreManageActivity;
import com.yst.onecity.adapter.goodsmanage.GoodsManageAdapter;
import com.yst.onecity.bean.goodsmanage.GoodsBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.inter.GoodsManageInter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 待上架
 *
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/5/19
 */

public class DaiOnCarriageFragment extends BaseFragment implements GoodsManageInter, OnRefreshLoadmoreListener {

    @BindView(R.id.gv_good_list)
    GridView gvGoodList;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_add_new_good)
    TextView tvAddNewGood;
    @BindView(R.id.tv_more_manage)
    TextView tvMoreManage;
    @BindView(R.id.ll_add_good_contain)
    LinearLayout llAddGoodContain;
    @BindView(R.id.tv_manage_contain)
    TextView tvManageContain;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private GoodsManageAdapter goodsAdapter;
    private int page = 1;
    private List<GoodsBean.ContentBean> goodsList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_on_sale_layout;
    }

    /**
     * 创建实例
     *
     * @return DaiOnCarriageFragment
     */
    public static DaiOnCarriageFragment newInstance() {
        return new DaiOnCarriageFragment();
    }

    @Override
    public void init() {
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        smartRefreshLayout.autoRefresh(500);
        goodsAdapter = new GoodsManageAdapter(getActivity(), Constant.NO1, this);
        gvGoodList.setAdapter(goodsAdapter);
        getGoodsList();
    }

    @OnClick({R.id.tv_more_manage, R.id.tv_add_new_good})
    public void click(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_more_manage:
                Bundle bundle = new Bundle();
                bundle.putInt("type", Constant.NO2);
                JumpIntent.jump(getActivity(), MoreManageActivity.class, bundle);
                break;
            case R.id.tv_add_new_good:
                Bundle bundleAdd = new Bundle();
                bundleAdd.putString("isHunterAdd", "isHunterAdd");
                JumpIntent.jump(getActivity(), AddNewCommodityActivity.class, bundleAdd);
                break;
            default:
                break;
        }
    }

    @Override
    public void xiaJia(int position) {

    }

    @Override
    public void edit(int position) {

    }

    @Override
    public void delete(int position) {

    }

    @Override
    public void isClick(boolean isSuccess) {
        if (isSuccess) {
            getGoodsList();
        }
    }


    /**
     * 获取商品列表
     */
    private void getGoodsList() {
        RequestApi.getGoodsList(App.manager.getPhoneNum(),App.manager.getUuid(), String.valueOf(Constant.NO2), String.valueOf(page), String.valueOf(Constant.NO10), new AbstractNetWorkCallback<GoodsBean>() {
            @Override
            public void onSuccess(GoodsBean goodsManageListBean) {
                if (null != goodsManageListBean) {
                    if (goodsManageListBean.getCode() == Constant.NO1 && null != goodsManageListBean.getContent()) {
                        if (page == Constant.NO1) {
                            goodsList.clear();
                        }
                        goodsList.addAll(goodsManageListBean.getContent());
                        goodsAdapter.addData(goodsList);
                    } else {
                        if (page == 1) {
                            goodsList = new ArrayList<>();
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
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
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
    public void onRefresh(RefreshLayout refreshlayout) {
        page = Constant.NO1;
        getGoodsList();
        refreshlayout.finishRefresh(500);

    }

    /**
     * 是否显示图片
     */
    private void flushUi() {
        if (goodsList.isEmpty()) {
            ivEmpty.setVisibility(View.VISIBLE);
        } else {
            ivEmpty.setVisibility(View.GONE);
        }
    }
}
