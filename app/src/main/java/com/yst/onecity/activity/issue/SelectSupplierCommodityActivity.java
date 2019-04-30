package com.yst.onecity.activity.issue;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.issue.AddGoodsAdapter;
import com.yst.onecity.bean.issue.CommodityBean;
import com.yst.onecity.bean.issue.TextViewBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.SearchViewEditText;
import com.yst.onecity.view.SupplierPopWindow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import static com.yst.onecity.Constant.NO0;

/**
 * 选择商品界面  供应商
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/26
 */
public class SelectSupplierCommodityActivity extends BaseActivity implements AdapterView.OnItemClickListener, PopupWindow.OnDismissListener, View.OnClickListener, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.grid_select_commodity)
    GridView selectCommodity;
    @BindView(R.id.image_spinner)
    ImageView imageSpinner;
    @BindView(R.id.spinner)
    TextView mSpinner;
    @BindView(R.id.search)
    SearchViewEditText search;
    @BindView(R.id.relative_spinner)
    RelativeLayout relativeSpinner;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private List<CommodityBean.ContentBean> goodsList = new ArrayList<>();
    private AddGoodsAdapter adapter;
    ArrayList mItems = new ArrayList();
    ArrayList idItems = new ArrayList();
    private ArrayAdapter<String> mSpinnerAdapter;
    private SupplierPopWindow supplierPopWindow;
    /**
     * 供应商分类id
     */
    private String supplierId;
    /**
     * 商品名称
     */
    private String supplierName;
    private int page = 0;
    /**
     * 选择的商品集合
     */
    List<CommodityBean.ContentBean> productList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_supplier_commodity;
    }

    @Override
    public void initData() {
        initTitleBar("供应商商品");
        setRightText("完成");
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        setRightTextViewClickable(true);
        initSpinnerData();
        //完成监听
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
        //商品列表
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, mItems);
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
        supplierPopWindow = new SupplierPopWindow(this, mItems, this);
        supplierPopWindow.setOnDismissListener(this);
        /**
         * 回车监听
         */
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                supplierName = search.getText().toString().trim();
                getProductListBySupplier(supplierId, supplierName);
                return false;
            }
        });
    }

    @Override
    public void setListener() {
        super.setListener();
        relativeSpinner.setOnClickListener(this);
    }

    /**
     * 查找供应商列表接口  供应商
     */
    private void initSpinnerData() {
        RequestApi.getSupplierAllList(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<TextViewBean>() {
            @Override
            public void onSuccess(TextViewBean textViewBean) {
                if (textViewBean.getCode() == Constant.NO1) {
                    List<TextViewBean.ContentBean> content = textViewBean.getContent();
                    if (content != null && content.size() > Constant.NO0) {
                        for (TextViewBean.ContentBean bean : content) {
                            mItems.add(bean.getSupplier_name());
                            idItems.add(bean.getId());
                        }
                        ConstUtils.setTextString(content.get(0).getSupplier_name(), mSpinner);
                        getProductListBySupplier(content.get(0).getId() + "", supplierName);
                        supplierId = String.valueOf(content.get(0).getId());
                        mSpinnerAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.show(textViewBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

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
        });
    }

    /**
     * 获取商品列表
     *
     * @param spId 供应商id
     * @param name 查找的商品名称
     */
    private void getProductListBySupplier(String spId, String name) {
        onLoad();
        if (TextUtils.isEmpty(spId)) {
            return;
        }
        if (TextUtils.isEmpty(name)) {
            name = "null";
        }

        RequestApi.getProductListBySupplierId(spId, page + "", "10", name, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<CommodityBean>() {
            @Override
            public void onSuccess(CommodityBean commodityBean) {
                if (commodityBean != null && commodityBean.getCode() == 1) {
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
            adapter = new AddGoodsAdapter(goodsList, context, 1);
            selectCommodity.setAdapter(adapter);
        } else {
            adapter.setGoodsManagerList(goodsList, 1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        supplierPopWindow.dismiss();
        String string = mItems.get(i).toString();
        if (idItems.size() != NO0) {
            //点击供应商列表item设置数据
            supplierId = idItems.get(i).toString();
            page = 0;
            getProductListBySupplier(supplierId, supplierName);
        }
        mSpinner.setText(string);
    }

    @Override
    public void onDismiss() {
        //popWindow 关闭以后设置下拉图标
        imageSpinner.setImageResource(R.mipmap.xiala);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //展开供应商列表
            case R.id.relative_spinner:
                supplierPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                supplierPopWindow.showAsDropDown(mSpinner);
                imageSpinner.setImageResource(R.mipmap.triangle_close);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        //加载更多
        page++;
        getProductListBySupplier(supplierId, supplierName);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        ///上拉刷新
        page = 0;
        getProductListBySupplier(supplierId, supplierName);
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
