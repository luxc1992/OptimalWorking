package com.yst.onecity.activity.issue;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.issue.AddGoodsAdapter;
import com.yst.onecity.bean.issue.CommodityBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.SearchViewEditText;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import static com.yst.onecity.Constant.NO0;

/**
 * 选择商品界面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/26
 */
public class SelectCommodityActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.grid_select_commodity)
    GridView selectCommodity;
    @BindView(R.id.search)
    SearchViewEditText search;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    List<CommodityBean.ContentBean> goodsList = new ArrayList<>();
    private AddGoodsAdapter adapter;
    private int page = 0;
    /**
     * 选择的商品集合
     */
    List<CommodityBean.ContentBean> productList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_commodity;
    }

    @Override
    public void initData() {
        initTitleBar("添加商品");
        setRightText("完成");
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        setRightTextViewClickable(true);
        getProductList("null");
        /**
         * 完成监听
         */
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productList.size() == NO0) {
                    ToastUtils.show("请选择商品");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("productList", (Serializable) productList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        /**
         * item监听
         */
        selectCommodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommodityBean.ContentBean goodsBean = goodsList.get(i);
                goodsBean.setChecked(!goodsBean.isChecked());
                adapter.notifyDataSetChanged();
                if (goodsBean.isChecked()) {
                    productList.add(goodsBean);
                } else {
                    productList.remove(goodsBean);
                }
            }
        });
        /**
         * 回车监听
         */
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String trim = search.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    trim = "null";
                }
                getProductList(trim);
                return false;
            }
        });
    }

    /**
     * 得到商品列表
     */
    private void getProductList(String name) {
        onLoad();
        RequestApi.getProductListByName(name, App.manager.getUuid(), App.manager.getPhoneNum(), page, new AbstractNetWorkCallback<CommodityBean>() {
            @Override
            public void onSuccess(CommodityBean commodityBean) {
                if (null != commodityBean && commodityBean.getCode() == 1) {
                    if (page == 0) {
                        goodsList.clear();
                        goodsList = commodityBean.getContent();
                    } else {
                        goodsList.addAll(commodityBean.getContent());
                    }
                } else {
                    if (page == 0) {
                        goodsList = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                flushGoodsList();
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

    /**
     * 刷新适配器
     */
    private void flushGoodsList() {
        if (null == selectCommodity) {
            return;
        }
        if (adapter == null) {
            adapter = new AddGoodsAdapter(goodsList, context, 0);
            selectCommodity.setAdapter(adapter);
        } else {
            adapter.setGoodsManagerList(goodsList, 0);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getProductList("null");
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 0;
        getProductList("null");
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
