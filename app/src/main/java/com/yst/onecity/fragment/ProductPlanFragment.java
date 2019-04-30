package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.ProductPlanDetailActivity;
import com.yst.onecity.adapter.MyCollectProductPlanAdapter;
import com.yst.onecity.adapter.MyIssueProductPlanAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.bean.mine.MyCollectProductPlan;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.AbstractCommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 产品计划fragment
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/22
 */
public class ProductPlanFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_productplan)
    ListView lvProductPlan;
    @BindView(R.id.smartRefreshLayout_product_plan)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty_product_plan)
    TextView empty;

    /**
     * 判断是哪个页面加载该fragment  1我的收藏  0我的发布
     */
    private int type;
    private List<ProductPlanBean.ContentBean> myIssue = new ArrayList<>();
    private MyIssueProductPlanAdapter myIssueProductPlanAdapter;
    private MyCollectProductPlanAdapter myCollectProductPlanAdapter;
    private int page = 1;
    private List<MyCollectProductPlan.ContentBean> myCollect = new ArrayList<>();
    private AbstractCommonDialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_plan;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.containsKey("type") ? bundle.getInt("type") : 0;
        }
        //自动刷新列表
        smartRefreshLayout.autoRefresh(500);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        //产品计划点击item跳转到产品计划详情页
        lvProductPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (myIssue != null && myIssue.size() > 0) {
                    Bundle b = new Bundle();
                    b.putString("id", myIssue.get(i).getId() + "");
                    JumpIntent.jump(getActivity(), ProductPlanDetailActivity.class, b);
                } else if (myCollect != null && myCollect.size() > 0) {
                    Bundle b = new Bundle();
                    b.putString("id", myCollect.get(i).getId() + "");
                    JumpIntent.jump(getActivity(), ProductPlanDetailActivity.class, b);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        myCollect.clear();
        myIssue.clear();
        page = 1;
        getInfo();
    }

    /**
     * 请求网络
     */
    private void getInfo() {
        if (type == 1) {
            RequestApi.myCollectProductPlan(App.manager.getPhoneNum(), App.manager.getUuid(), page + "", 5 + "", new AbstractNetWorkCallback<MyCollectProductPlan>() {
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
                public void onSuccess(MyCollectProductPlan bean) {
                    onLoad();
                    if (bean != null && bean.getCode() == 1) {
                        if (page == 1) {
                            myCollect = bean.getContent();
                        } else {
                            if (bean.getContent() != null) {
                                myCollect.addAll(bean.getContent());
                            } else {
                                ToastUtils.show("已加载全部数据");
                            }
                        }
                        flushMyCollectAdapter();
                    } else {
                        if (page == 1) {
                            myCollect = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    onLoad();
                    ToastUtils.show(errorMsg);
                }
            });
        } else if (type == 0) {
            RequestApi.myIssueProductPlan("1", App.manager.getUuid(), App.manager.getPhoneNum(), page + "", 5 + "", new AbstractNetWorkCallback<ProductPlanBean>() {
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
                public void onSuccess(ProductPlanBean bean) {
                    onLoad();
                    if (bean != null && bean.getCode() == 1) {
                        if (page == 1) {
                            myIssue = bean.getContent();
                        } else {
                            if (bean.getContent() != null) {
                                myIssue.addAll(bean.getContent());
                            } else {
                                ToastUtils.show("已加载全部数据");
                            }
                        }
                        flushMyIssueAdapter();
                    } else {
                        if (page == 1) {
                            myIssue = new ArrayList<>();
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }
    }

    /**
     * 刷新 我的发布产品计划 适配器
     */
    private void flushMyIssueAdapter() {
        if (null == lvProductPlan) {
            return;
        }
        if (myIssueProductPlanAdapter == null) {
            myIssueProductPlanAdapter = new MyIssueProductPlanAdapter(myIssue, getActivity(), myOnClick);
            lvProductPlan.setAdapter(myIssueProductPlanAdapter);
        } else {
            myIssueProductPlanAdapter.setMyIssueData(myIssue);
            myIssueProductPlanAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 刷新 我的收藏产品计划 适配器
     */
    private void flushMyCollectAdapter() {
        if (null == lvProductPlan) {
            return;
        }
        if (myCollectProductPlanAdapter == null) {
            myCollectProductPlanAdapter = new MyCollectProductPlanAdapter(myCollect, myOnClick, getActivity());
            lvProductPlan.setAdapter(myCollectProductPlanAdapter);
        } else {
            myCollectProductPlanAdapter.setMyCollectData(myCollect);
        }
    }

    /**
     * 取消收藏
     */
    private void unAttention(String id) {
        RequestApi.collectProductPlan(id, "6", App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    ToastUtils.show(msgBean.getMsg());
                    page = 1;
                    getInfo();
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
     * 删除产品计划
     */
    private void deleteProductPlan(String id) {
        RequestApi.myIssueDeleteProductPlan(id, App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    ToastUtils.show(msgBean.getMsg());
                    myIssue.clear();
                    page = 1;
                    getInfo();
                    dialog.dismissDialog();
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
    public static ProductPlanFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ProductPlanFragment fragment = new ProductPlanFragment();
        fragment.setArguments(args);
        return fragment;
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

    /**
     * 删除产品计划的弹出框
     *
     * @param id 产品计划id
     */
    public void showDialog(final String id) {
        dialog = new AbstractCommonDialog(getActivity()) {
            @Override
            public void sureClick() {
                deleteProductPlan(id);
                dismissDialog();
            }
        };
        dialog.setText("提示", "是否确定删除？", "确定", "取消");
        dialog.showDialog();
    }

    /**
     * 点击监听
     */
    private View.OnClickListener myOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //取消收藏
                case R.id.line_product_collect:
                    unAttention((String) view.getTag());
                    break;
                //删除
                case R.id.line_delete:
                    showDialog((String) view.getTag());
                    break;
                default:
                    break;
            }
        }
    };
}
