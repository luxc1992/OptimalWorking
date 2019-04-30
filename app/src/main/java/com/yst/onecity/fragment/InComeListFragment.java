package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.activity.FetchCashActivity;
import com.yst.onecity.bean.mine.MyInComeListBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO10;

/**
 * 我的收入列表
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/2/23
 */

public class InComeListFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.income_list)
    ListView incomeList;
    @BindView(R.id.no_data)
    LinearLayout noData;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    private AbstractCommonAdapter commonAdapter;
    private ArrayList<MyInComeListBean.ContentBean.ListBean> list = new ArrayList<>();
    private String index;
    private String name;
    private String fetch = "提现";
    private String fetchMoney = "提现手续费";
    private int page = 1;

    /**
     * 创建一个资讯frament的实例，传入资讯类型id
     *
     * @param id   类别id、
     * @param name tag标签name
     * @return 具体fragment
     */
    public static InComeListFragment newInstance(int id, String name) {
        Bundle args = new Bundle();
        args.putInt("index", id);
        args.putString("name", name);
        InComeListFragment fragment = new InComeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.containsKey("index") ? bundle.get("index").toString() : "";
            name = bundle.containsKey("index") ? bundle.get("name").toString() : "";
        }
        return R.layout.fragment_income;
    }

    @Override
    public void init() {
        tvText.setText(String.format("您还没有%1$s哟", name));
        for (int i = 0; i < NO10; i++) {
            list.add(new MyInComeListBean.ContentBean.ListBean());
        }
        commonAdapter = new AbstractCommonAdapter<MyInComeListBean.ContentBean.ListBean>(getActivity(), list, R.layout.item_income) {
            @Override
            public void convert(CommonViewHolder holder, int position, MyInComeListBean.ContentBean.ListBean item) {
                holder.setText(R.id.tv_huntting_bean_title, list.get(position).getDescription());
                if (list.get(position).getDescription().equals(fetch) || list.get(position).getDescription().equals(fetchMoney)) {
                    holder.setText(R.id.tv_huntting_bean_num, "-" + Utils.doubleToString(list.get(position).getMoney()));
                } else {
                    holder.setText(R.id.tv_huntting_bean_num, "+" + Utils.doubleToString(list.get(position).getMoney()));
                }
                holder.setText(R.id.tv_huntting_bean_time, Utils.getCustomStrTime(String.valueOf(item.getCreateTime()), "yyyy-MM-dd HH:mm"));
            }
        };
        incomeList.setAdapter(commonAdapter);
        list.clear();
        getInComeList();
        incomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (list.get(i).getDescription().equals(fetch) || list.get(i).getDescription().equals(fetchMoney)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "2");
                    bundle.putString("id", String.valueOf(list.get(i).getId()));
                    JumpIntent.jump(getActivity(), FetchCashActivity.class, bundle);
                }

            }
        });
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 获取收入列表
     */
    private void getInComeList() {
        RequestApi.getMyInComeListData(String.valueOf(App.manager.getId()),
                index,
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                String.valueOf(page),
                new AbstractNetWorkCallback<MyInComeListBean>() {
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onSuccess(MyInComeListBean myInComeListBean) {
                        if (myInComeListBean.getCode() == 1) {
                            list.addAll(myInComeListBean.getContent().getList());
                            commonAdapter.notifyDataSetChanged();
                            if (list.size() < NO1) {
                                noData.setVisibility(View.VISIBLE);
                                incomeList.setVisibility(View.GONE);
                            } else {
                                noData.setVisibility(View.GONE);
                                incomeList.setVisibility(View.VISIBLE);
                            }
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            incomeList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                        noData.setVisibility(View.VISIBLE);
                        incomeList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                        smartRefreshLayout.finishRefresh(500);
                        smartRefreshLayout.finishLoadmore(500);
                    }
                });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getInComeList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        list.clear();
        commonAdapter.notifyDataSetChanged();
        getInComeList();
    }
}
