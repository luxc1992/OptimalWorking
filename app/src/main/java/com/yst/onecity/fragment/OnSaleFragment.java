package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.hunter.OnSaleBatchManageActivity;
import com.yst.onecity.activity.issue.AddNewCommodityActivity;
import com.yst.onecity.adapter.GoodsManagerAdapter;
import com.yst.onecity.bean.GoodsManageListBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品管理页面出售中
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/9.
 */
public class OnSaleFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.gv_good_list)
    GridView gvGoodList;
    @BindView(R.id.tv_add_new_good)
    TextView tvAddNewGood;
    @BindView(R.id.tv_more_manage)
    TextView tvMoreManage;
    @BindView(R.id.ll_add_good_contain)
    LinearLayout llAddGoodContain;
    @BindView(R.id.tv_manage_contain)
    TextView tvManageContain;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    /**
     * 用户id
     */
    private String id;
    private GoodsManagerAdapter adapter;
    private List<GoodsManageListBean.ContentBean> goodsBeanList = new ArrayList<>();
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_on_sale_layout;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.containsKey("id") ? bundle.get("id").toString() : "";
        }
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        gvGoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String productId = goodsBeanList.get(i).getProductId();
//                getHunterProductContent(productId);
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
                onLoad();
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
                onLoad();
                ToastUtils.show(errorMsg);
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
            adapter = new GoodsManagerAdapter(goodsBeanList, getActivity());
            gvGoodList.setAdapter(adapter);
        } else {
            adapter.setGoodsManagerList(goodsBeanList);
        }
    }

    @OnClick({R.id.tv_add_new_good, R.id.tv_more_manage})
    public void onViewClick(View view) {
        switch (view.getId()) {
            //添加新商品
            case R.id.tv_add_new_good:
                Bundle bundle = new Bundle();
                bundle.putString("isHunterAdd", "isHunterAdd");
                JumpIntent.jump(getActivity(), AddNewCommodityActivity.class, bundle);
                break;
            //商品管理
            case R.id.tv_more_manage:
                JumpIntent.jump(getActivity(), OnSaleBatchManageActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 创建一个资讯frament的实例，传入用户id
     *
     * @param id 用户id、
     * @return 具体fragment
     */
    public static OnSaleFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        OnSaleFragment fragment = new OnSaleFragment();
        fragment.setArguments(args);
        return fragment;
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
