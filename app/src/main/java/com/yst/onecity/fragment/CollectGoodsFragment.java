package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.adapter.CollectGoodsAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.mine.MyCollectGoodBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的收藏 商品fragment
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/26
 */
public class CollectGoodsFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.tv_collect_good_fail)
    TextView fail;
    @BindView(R.id.gv_collect_good_list)
    GridView gvGoodList;
    @BindView(R.id.smartRefreshLayout_collect_good)
    SmartRefreshLayout smartRefreshLayout;

    private CollectGoodsAdapter adapter;
    private List<MyCollectGoodBean.ContentBean> goodsBeanList = new ArrayList<>();
    private int page;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_goods;
    }

    @Override
    public void init() {
        //自动刷新
        smartRefreshLayout.autoRefresh(300);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        //点击商品item跳转到商品详情页
        gvGoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("id", goodsBeanList.get(i).getProductId() + "");
                bundle.putString("url", H5Const.GOODS_DETAIL_NEW);
                JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (smartRefreshLayout.isRefreshing()) {
            smartRefreshLayout.finishRefresh();
        }
    }

    /**
     * 获取商品列表
     */
    private void getGoodsList() {
        RequestApi.myCollectGood(App.manager.getPhoneNum(), App.manager.getUuid(), page + "", 6 + "", new AbstractNetWorkCallback<MyCollectGoodBean>() {
            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(MyCollectGoodBean myCollectGoodBean) {
                onLoad();
                if (myCollectGoodBean.getCode() == 1) {
                    if (page == 1) {
                        goodsBeanList = myCollectGoodBean.getContent();
                    } else {
                        if (myCollectGoodBean.getContent() != null) {
                            goodsBeanList.addAll(myCollectGoodBean.getContent());
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
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
            public void onError(String errorMsg) {
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
            adapter = new CollectGoodsAdapter(goodsBeanList, getActivity(), myOnClick);
            gvGoodList.setAdapter(adapter);
        } else {
            adapter.setData(goodsBeanList);
        }
    }

    /**
     * 取消收藏商品
     *
     * @param id
     */
    private void unAttention(String id) {
        RequestApi.collectGood(id, "7", App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    ToastUtils.show(msgBean.getMsg());
                    page = 1;
                    getGoodsList();
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 创建一个资讯frament的实例
     *
     * @return 具体fragment
     */
    public static CollectGoodsFragment newInstance() {
        CollectGoodsFragment fragment = new CollectGoodsFragment();
        return fragment;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getGoodsList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getGoodsList();
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

    /**
     * 点击监听
     */
    private View.OnClickListener myOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //商品取消收藏
                case R.id.line_collect:
                    unAttention((String) view.getTag());
                    break;
                default:
                    break;
            }
        }
    };
}
