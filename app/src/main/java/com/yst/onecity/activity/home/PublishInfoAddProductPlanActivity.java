package com.yst.onecity.activity.home;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.HomeProductPlanAdapter;
import com.yst.onecity.bean.home.PublishInfoAddProductPlanBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页-发布资讯-添加服务项目 (原：产品计划)
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/02/26
 */
public class PublishInfoAddProductPlanActivity extends BaseActivity implements OnLoadmoreListener, OnRefreshListener {

    @BindView(R.id.publish_add_product_et_search)
    EditText etSearch;
    @BindView(R.id.publish_add_product_smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.publish_add_product_listView)
    ListView listView;
    @BindView(R.id.publish_add_product_iv_search_clear)
    ImageView ivSearchClear;

    /**
     * 布局管理器
     */
    private HomeProductPlanAdapter adapter;
    /**
     * 搜索框文字
     */
    private String searchText = "";
    private List<PublishInfoAddProductPlanBean.ContentBean> list = new ArrayList<>();
    private int page = 1;
    private boolean isAdd = false;

    /**
     * 返回的计划id
     */
    private String rId = "";
    /**
     * 返回的计划标题
     */
    private String rTitle = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_info_add_product_plan;
    }

    @Override
    public void initData() {
        initTitleBar(this.getString(R.string.add_server_project));
        setRightText("完成");
        setRightTextVisibility(View.VISIBLE);
        setRightTextViewClickable(true);
        //自动刷新
        smartRefreshLayout.autoRefresh(500);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        adapter = new HomeProductPlanAdapter(list, PublishInfoAddProductPlanActivity.this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        list.clear();
        adapter.notifyDataSetChanged();
        getInfo();
    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {
        adapter.setOnItemClickListener(new HomeProductPlanAdapter.OnVpItemClickListener() {
            @Override
            public void onClick(int position) {
                //全部设为未选中
                for (PublishInfoAddProductPlanBean.ContentBean bean : list) {
                    bean.setChecked(false);
                }
                //点击的设为选中
                list.get(position).setChecked(true);
                adapter.notifyDataSetChanged();
                rId = list.get(position).getId() + "";
                rTitle = list.get(position).getTitle();
            }
        });

        //右上角文字点击监听
        setRightTextViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id", rId);
                intent.putExtra("title", rTitle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //设置刷新监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                if (!TextUtils.isEmpty(searchText)) {
                    //搜索
                    search();
                } else {
                    //列表
                    getInfo();
                }
            }
        });
        //设置加载更多监听
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (!TextUtils.isEmpty(searchText)) {
                    search();
                } else {
                    getInfo();
                }
            }
        });

        //清空搜索内容，执行获取商品列表
        ivSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = "";
                etSearch.setText("");
                getInfo();
            }
        });

        //设置文本输入变化监听
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = s.toString().trim();
                if (s.length() > 0) {
                    ivSearchClear.setVisibility(View.VISIBLE);
                } else {
                    ivSearchClear.setVisibility(View.GONE);
                    getInfo();
                }
            }
        });

        //文本输入框监听回车
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                    page = 1;
                    isAdd = false;
                    searchText = String.valueOf(etSearch.getText());
                    if (!TextUtils.isEmpty(searchText)) {
                        //搜索产品计划列表
                        search();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 搜索产品计划列表
     */
    public void search() {
        //每页展示数据条数
        final String pageNumber = "10";
        RequestApi.addProductSearch("1", searchText, page + "", pageNumber, new AbstractNetWorkCallback<PublishInfoAddProductPlanBean>() {
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
            public void onSuccess(PublishInfoAddProductPlanBean publishInfoAddProductPlanBean) {
                onLoad();
                if (publishInfoAddProductPlanBean != null && publishInfoAddProductPlanBean.getCode() == 1) {
                    if (page == 1) {
                        list = publishInfoAddProductPlanBean.getContent();
                    } else {
                        list.addAll(publishInfoAddProductPlanBean.getContent());
                    }
                } else {
                    if (page == 1) {
                        list = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                setRecyclerViewAdapter(list);
            }

            @Override
            public void onError(String errorMsg) {
                onLoad();
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 请求数据
     */
    private void getInfo() {

        RequestApi.addProductPlanList(page + "", 5 + "", new AbstractNetWorkCallback<PublishInfoAddProductPlanBean>() {
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
            public void onSuccess(PublishInfoAddProductPlanBean publishInfoAddProductPlanBean) {
                onLoad();
                if (publishInfoAddProductPlanBean != null && publishInfoAddProductPlanBean.getCode() == 1) {
                    if (page == 1) {
                        list = publishInfoAddProductPlanBean.getContent();
                    } else {
                        list.addAll(publishInfoAddProductPlanBean.getContent());
                    }
                } else {
                    if (page == 1) {
                        list = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                setRecyclerViewAdapter(list);
            }

            @Override
            public void onError(String errorMsg) {
                onLoad();
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 设置适配器
     *
     * @param bean 数据
     */
    private void setRecyclerViewAdapter(List<PublishInfoAddProductPlanBean.ContentBean> bean) {
        if (listView == null) {
            return;
        }
        if (adapter == null) {
            adapter = new HomeProductPlanAdapter(bean, PublishInfoAddProductPlanActivity.this);
            listView.setAdapter(adapter);
        } else {
            adapter.setHomeData(list);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getInfo();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        getInfo();
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

}
