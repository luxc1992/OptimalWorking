package com.yst.onecity.fragment.cardunion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.cardorder.CardUnionOrderDetailActivity;
import com.yst.onecity.adapter.CardUnionOrderAdapter;
import com.yst.onecity.bean.CardUnionOrderBean;
import com.yst.onecity.fragment.LazyBaseFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 全部订单列表
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/17
 */
public class CardUnionAllOrderFragment extends LazyBaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.lv_card_union)
    ListView lvCardUnion;

    private CardUnionOrderAdapter adapter;
    private List<CardUnionOrderBean.ContentBean.CardOrderVOListBean> mList = new ArrayList<>();
    private int page = 1;

    @Override
    protected void loadData() {
//        page = 1;
//        requestData(page);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_card_union;
    }

    @Override
    public void onResume() {
        super.onResume();
        mList.clear();
        adapter.notifyDataSetChanged();
        page = 1;
        requestData(page);
    }

    @Override
    public void init() {

        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        adapter = new CardUnionOrderAdapter(getContext(), mList);
        lvCardUnion.setAdapter(adapter);

        lvCardUnion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("status", mList.get(position).getStatus());
                bundle.putString("serviceId", mList.get(position).getServiceOrderId());
                JumpIntent.jump(getActivity(), CardUnionOrderDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 请求数据
     *
     * @param page
     */
    private void requestData(final int page) {
        RequestApi.getCardUnionOrderList(App.manager.getPhoneNum(), App.manager.getUuid(), App.manager.getId() + "", "", page, new AbstractNetWorkCallback<CardUnionOrderBean>() {
            @Override
            public void onAfter() {
                super.onAfter();
                loadFinish();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onSuccess(CardUnionOrderBean cardUnionOrderBean) {
                boolean b = (page == NO1 && (cardUnionOrderBean.getContent() == null || cardUnionOrderBean.getContent().getCardOrderVOList().size() == 0));
                if (b) {
                    rlEmpty.setVisibility(View.VISIBLE);
                } else {
                    rlEmpty.setVisibility(View.GONE);
                    if (page != 1 && cardUnionOrderBean.getContent().getCardOrderVOList().size() == 0) {
                        ToastUtils.show("没有更多数据了");
                    }
                    mList.addAll(cardUnionOrderBean.getContent().getCardOrderVOList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    private void loadFinish() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        adapter.notifyDataSetChanged();
        requestData(page);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
//        page++;
//        requestData(page);
        smartRefreshLayout.finishLoadmore(500);
    }
}
