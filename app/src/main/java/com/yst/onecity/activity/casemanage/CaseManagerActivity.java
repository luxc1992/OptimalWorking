package com.yst.onecity.activity.casemanage;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CaseManagerAdapter;
import com.yst.onecity.bean.CaseBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;

/**
 * 案例管理
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/17
 */

public class CaseManagerActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

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
    @BindView(R.id.lv_sermanage)
    ListView lvSermanage;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_no_date)
    LinearLayout llNoDate;
    private List<CaseBean.ContentBean> list = new ArrayList<>();
    private List<String> checkList = new ArrayList<>();
    /**
     * 案例管理适配器
     */
    private CaseManagerAdapter caseManagerAdapter;
    private int page = 1;
    private boolean isEdit;
    private String advisorId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_case_manager;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.case_manage));
        refreshlayout.setOnRefreshListener(this);
        refreshlayout.setOnLoadmoreListener(this);
        refreshlayout.setEnableRefresh(true);
        refreshlayout.setEnableLoadmore(true);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.compile));
        tvRight.setTextColor(ContextCompat.getColor(this, R.color.color_FF999999));
        if (getIntent().getExtras() != null) {
            advisorId = getIntent().getExtras().getString("advisorId");
            requestDataNet();
        }
    }

    /**
     * 请求列表数据
     */
    private void requestDataNet() {
        RequestApi.caseManagerList(App.manager.getPhoneNum(), App.manager.getUuid(), page,"10", new AbstractNetWorkCallback<CaseBean>() {
            @Override
            public void onSuccess(CaseBean caseBean) {
                if (caseBean.getCode() == NO1 && caseBean.getContent() != null) {
                    if (page == 1) {
                        list = caseBean.getContent();
                    } else {
                        list.addAll(caseBean.getContent());
                    }
                } else {
                    if (page == 1) {
                        list = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                flushCaseManagerList();
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
                onLoad();
            }
        });
    }

    /**
     * 刷新适配器
     */
    private void flushCaseManagerList() {
        if (null == lvSermanage) {
            return;
        }
        if (list.size() == 0) {
            llNoDate.setVisibility(View.VISIBLE);
        } else {
            llNoDate.setVisibility(View.GONE);
        }
        if (caseManagerAdapter == null) {
            caseManagerAdapter = new CaseManagerAdapter(CaseManagerActivity.this, list, isEdit);
            lvSermanage.setAdapter(caseManagerAdapter);
            caseManagerAdapter.setListener(new CaseManagerAdapter.OnListener() {
                @Override
                public void onCheckListener(List<String> isCheckList) {
                    checkList = isCheckList;
                }
            });
        } else {
            caseManagerAdapter.setCaseList(list, isEdit);
        }
    }

    /**
     * 编辑
     *
     * @param view
     */
    @OnClick({R.id.tv_right, R.id.tv_delete})
    public void edit(View view) {
        if (!Utils.isClickable()) {
            return;
        }

        switch (view.getId()) {
            case R.id.tv_right:
                if (null == list || list.isEmpty()) {
                    tvRight.setEnabled(false);
                    return;
                } else if (null != list && !list.isEmpty()) {
                    tvRight.setEnabled(true);
                }
                String rightString = tvRight.getText().toString();
                if (getString(R.string.compile).equals(rightString)) {
                    isEdit = true;
                    flushCaseManagerList();
                    tvRight.setText(getString(R.string.finish));
                    tvRight.setTextColor(ContextCompat.getColor(this, R.color.color_FFED5452));
                    tvDelete.setVisibility(View.VISIBLE);
                } else {
                    isEdit = false;
                    flushCaseManagerList();
                    tvRight.setText(getString(R.string.compile));
                    tvRight.setTextColor(ContextCompat.getColor(this, R.color.color_FF999999));
                    tvDelete.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_delete:
                StringBuilder str = new StringBuilder();
                MyLog.e("ischeck", "isCheck----" + new Gson().toJson(checkList));
                for (int i = 0; i < checkList.size(); i++) {
                    str.append(checkList.get(i));
                    str.append(",");
                }
                String caseIds = str.substring(0, str.length() - 1);
                MyLog.e("ischeck", "caseIds----" + caseIds);
                RequestApi.deleteCaseManager(App.manager.getPhoneNum(),App.manager.getUuid(),caseIds, new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            checkList.clear();
                            page = 1;
                            requestDataNet();
                        }
                        ToastUtils.show(msgBean.getMsg());
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
                break;
            default:
                break;
        }
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        refreshlayout.finishRefresh(500);
        refreshlayout.finishLoadmore(500);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        checkList.clear();
        page = 1;
        requestDataNet();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        requestDataNet();
    }
}
