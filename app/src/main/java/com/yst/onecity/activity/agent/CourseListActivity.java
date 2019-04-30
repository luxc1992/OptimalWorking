package com.yst.onecity.activity.agent;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.MyViewHolder;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.bean.agent.AgentListBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.view.SpacesItemDecoration;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO13;

/**
 * 课题列表页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */
public class CourseListActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.txt_list_add)
    TextView mTxtListAdd;
    @BindView(R.id.ll_list_empty)
    LinearLayout mLlListEmpty;
    @BindView(R.id.txt_list_addcourse)
    TextView mTxtListAddcourse;
    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerList;
    @BindView(R.id.txt_list_delete)
    TextView mTxtListDelete;
    @BindView(R.id.ll_list_content)
    RelativeLayout mLlListContent;
    @BindView(R.id.refresh_course)
    SmartRefreshLayout mRefreshCourse;

    private List<AgentListBean.ContentBean> mList = new ArrayList<>();
    private boolean isEdit;
    private AbstractCommonAdapter<AgentListBean.ContentBean> mAdapter;
    private int page = 1;
    private List<String> idCheckList = new ArrayList<>();
    private boolean isRealName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_course_list;
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        mList.clear();
        idCheckList.clear();
        getTopicList();
        getThirdLoginInfo();
    }

    /**
     * 停止列表刷新
     */
    private void onLoadFinish() {
        mRefreshCourse.finishRefresh(500);
        mRefreshCourse.finishLoadmore(500);
    }

    @Override
    public void initData() {
        initTitleBar("课题列表");
        mRefreshCourse.autoRefresh(300);
        mRefreshCourse.setOnRefreshListener(this);
        mRefreshCourse.setOnLoadmoreListener(this);
        mRefreshCourse.setEnableRefresh(true);
        mRefreshCourse.setEnableLoadmore(true);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("编辑");
        mTvRight.setTextSize(13);
        mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
        initAdapter();
        getTopicList();
    }

    /**
     * 课题列表接口
     */
    private void getTopicList() {
        RequestApi.getTopicList(String.valueOf(App.manager.getId()), String.valueOf(page), new AbstractNetWorkCallback<AgentListBean>() {
            @Override
            public void onSuccess(AgentListBean bean) {
                if (bean.getCode() == NO1) {
                    if (bean.getContent().size() > NO0) {
                        byStatusShowUi(true);
                        mList.addAll(bean.getContent());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (page == NO1) {
                            byStatusShowUi(false);
                        } else {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                    byStatusShowUi(false);
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                byStatusShowUi(false);
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
                onLoadFinish();
            }
        });
    }

    /**
     * 根据状态显示ui
     */
    private void byStatusShowUi(boolean isHaveData) {
        if (isHaveData) {
            mLlListEmpty.setVisibility(View.GONE);
            mLlListContent.setVisibility(View.VISIBLE);
            mTvRight.setVisibility(View.VISIBLE);
        } else {
            mLlListEmpty.setVisibility(View.VISIBLE);
            mLlListContent.setVisibility(View.GONE);
            mTvRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        idCheckList.clear();
        getTopicList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getTopicList();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerList.addItemDecoration(new SpacesItemDecoration(NO0, NO13));
        mAdapter = new AbstractCommonAdapter<AgentListBean.ContentBean>(this, R.layout.item_center, mList) {
            @Override
            public void convert(MyViewHolder holder, final AgentListBean.ContentBean item) {
                holder.setText(R.id.txt_list_title, item.getTitle());
                holder.setText(R.id.txt_list_content, item.getContent());
                holder.setText(R.id.txt_eye, String.valueOf(item.getReadNum()));
                holder.setText(R.id.txt_zan, String.valueOf(item.getPriseNum()));
                if (TextUtils.isEmpty(item.getImageAddress())) {
                    holder.setImageResource(R.id.iv_list_bg, R.mipmap.list_bg);
                } else {
                    holder.setImageHttpUrl(R.id.iv_list_bg, item.getImageAddress());
                }
                final CheckBox cb = holder.getView(R.id.cb_list);
                if (isEdit) {
                    cb.setVisibility(View.VISIBLE);
                } else {
                    cb.setVisibility(View.GONE);
                }

                if (item.isClick()) {
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean click = item.isClick();
                        if (click) {
                            cb.setChecked(false);
                            idCheckList.remove(String.valueOf(item.getId()));
                        } else {
                            cb.setChecked(true);
                            idCheckList.add(String.valueOf(item.getId()));
                        }
                        item.setClick(!click);
                    }
                });
            }

            @Override
            public void bindClick(MyViewHolder holder, AgentListBean.ContentBean item) {

            }
        };
        mRecyclerList.setAdapter(mAdapter);
        final Bundle bundle = new Bundle();
        mAdapter.setOnItemClickListener(new AbstractCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if (!isEdit) {
                    bundle.putString("id", mList.get(position).getId()+ "");
                    bundle.putString("url", H5Const.PROJECT_DETAILS);
                    JumpIntent.jump(CourseListActivity.this, ServerTeamPageDetailActivity.class, bundle);
                }
            }
        });
    }

    @OnClick({R.id.tv_right, R.id.txt_list_add, R.id.txt_list_addcourse, R.id.txt_list_delete})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_right:
                if (getString(R.string.compile).equals(mTvRight.getText().toString())) {
                    editBefore();
                } else {
                    editAfter();
                }
                mAdapter.notifyDataSetChanged();
                break;
            //立即添加
            case R.id.txt_list_add:
                //判断是否实名认证
                if (isRealName) {
                    JumpIntent.jump(CourseListActivity.this, PublishCourseActivity.class);
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 2);
                    JumpIntent.jump(CourseListActivity.this, RealNameAuthenticationActivity.class, bundle);
                }
                break;
            //新建课题
            case R.id.txt_list_addcourse:
                //判断是否实名认证
                if (isRealName) {
                    JumpIntent.jump(CourseListActivity.this, PublishCourseActivity.class);
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 2);
                    JumpIntent.jump(CourseListActivity.this, RealNameAuthenticationActivity.class, bundle);
                }
                break;
            //删除
            case R.id.txt_list_delete:
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < idCheckList.size(); i++) {
                    str.append(idCheckList.get(i));
                    str.append(",");
                }
                final String clickId = str.substring(0, str.length() - 1);
                MyLog.e("zzz", "clickId===" + clickId);
                AbstractDeleteDialog dialog = new AbstractDeleteDialog(this) {
                    @Override
                    public void sureClick() {
                        requestDelete(clickId);
                    }
                };
                dialog.setText("确认删除么？", "确定", "取消");
                dialog.showDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 删除课题接口
     *
     * @param clickId 课题id拼接字符串
     */
    private void requestDelete(String clickId) {
        RequestApi.deleteTopic(String.valueOf(App.manager.getId()), clickId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    ToastUtils.show(msgBean.getMsg());
                    idCheckList.clear();
                    page = 1;
                    editAfter();
                    getTopicList();
                    onRefresh(mRefreshCourse);
                } else {
                    ToastUtils.show(msgBean.getMsg());
                }
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
                onLoadFinish();
            }
        });
    }

    /**
     * 编辑之前操作
     */
    private void editBefore() {
        mTvRight.setText(getString(R.string.finish));
        mTvRight.setTextColor(ContextCompat.getColor(CourseListActivity.this, R.color.color_ED5452));
        isEdit = true;
        mTxtListDelete.setVisibility(View.VISIBLE);
        mTxtListAddcourse.setVisibility(View.GONE);
        //正在编辑状态禁用刷新和加载功能
        mRefreshCourse.setEnableRefresh(false);
        mRefreshCourse.setEnableLoadmore(false);
        if (mRefreshCourse.isRefreshing()) {
            mRefreshCourse.finishRefresh();
        } else if (mRefreshCourse.isLoading()) {
            mRefreshCourse.finishLoadmore();
        }
    }

    /**
     * 编辑之后操作
     */
    private void editAfter() {
        mTvRight.setText(getString(R.string.compile));
        mTvRight.setTextColor(ContextCompat.getColor(CourseListActivity.this, R.color.color_999999));
        isEdit = false;
        mTxtListDelete.setVisibility(View.GONE);
        mTxtListAddcourse.setVisibility(View.VISIBLE);
        mRefreshCourse.setEnableRefresh(true);
        mRefreshCourse.setEnableLoadmore(true);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 获取信息 判断是否实名认证
     */
    private void getThirdLoginInfo() {
        RequestApi.getAccountSafe(App.manager.getId() + "", App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<AccountSafeBean>() {
            @Override
            public void onSuccess(AccountSafeBean accountSafeBean) {
                if (accountSafeBean.getCode() == Constant.NO1) {
                    if (accountSafeBean.getContent().getRealNameStatus() == NO1) {
                        isRealName = true;
                    }
                } else {
                    ToastUtils.show(accountSafeBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
